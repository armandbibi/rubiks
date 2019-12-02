package rubik2;

import java.util.Arrays;

public class CubieCube implements ConstantCubieCube, Cloneable{

    private char[] cp;

    private char[] co;

    private char[] ep;

    private char[] eo;

    public CubieCube() {
        cp = new char[]{URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};
        co = new char[]{0, 0 ,0 ,0 ,0 ,0, 0 ,0};
        ep = new char[]{ UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR};
        eo = new char[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public CubieCube(char[] cp, char[] co, char[] ep, char[]eo) {
        this.cp = cp;
        this.co = co;
        this.ep = ep;
        this.eo = eo;
    }

    public FaceCube toFaceCube() {

        FaceCube faceCube = new FaceCube();
        for (int i = 0; i < cornerValues.length; i++) {
            int corner = cp[i];
            int orientation = co[i];
            for (int j = 0; j < 3; j++) {
                byte tmp = faceCube.getCornerFacelet()[i][j + orientation % 3];
                // TODO finish function
            }
        }
        return null;
    }

    /**
     * multiply 2 cubes. use symetric rduction for the corner. see kociemba.org (like all this code)
     * @param multiplier cube to multiply
     */
    public void multiply(CubieCube multiplier) {
        multiplyTheCorners(multiplier);
        multiplyTheEdges(multiplier);
    }

    /**
     * multiply this cube with an other  (only the corners)
     * @param multiplier the cube to multiply with
     */
    public void multiplyTheCorners(CubieCube multiplier) {

        char[] cornerPerms = new char[cornerValues.length];
        char[] cornerOrientation = new char[cornerValues.length];

        for (int i = 0; i < cornerValues.length; i++) {

            cornerPerms[i] = cp[multiplier.cp[i]];

            char oriA = co[multiplier.cp[i]];
            char oriB = multiplier.co[i];
            char oriResult = 0;

            // check if the cube are regular or mirrored
            // regular * regular = regular
            if (oriA < 3 && oriB < 3) {
                oriResult = (char) (oriA + oriB);
                if (oriResult >= 3)
                    oriResult -= 3;
            }

            //regular * mirrored = mirrored
            else  if (oriA < 3 && oriB >= 3) {
              oriResult = (char) (oriA + oriB); ;
              if (oriResult >= 6)
                  oriResult -= 3;
            }

            // mirrored* regular = mirrored
            else if (oriA >= 3 && oriB < 3) {
                oriResult = (char) ((oriA - oriB) & 0xff);
                if (oriResult < 3) {
                    oriResult += 3;
                }
            }

            //mirrored * mirrored = regular
            else if (oriA >= 3 && oriB >= 3) {
                oriResult = (char) ((oriA - oriB) & 0xff);
                if (oriResult < 0)
                    oriResult += 3;
            }

            cornerOrientation[i] = oriResult;
        }

        this.cp = cornerPerms;
        this.co = cornerOrientation;
    }

    /**
     * multiply 2 cubes (edge level only)
     * @param multiplier
     */
    public void multiplyTheEdges(CubieCube multiplier) {

        char[] edgePerms = new char[edgesValues.length];
        char[] edgeOrientation = new char[edgesValues.length];

        for (int i = 0; i < 12; i++) {
            edgePerms[i] = this.ep[multiplier.ep[i]];
            edgeOrientation[i] = (char) ((multiplier.eo[i] + this.eo[multiplier.ep[i]]) % 2);
        }
        this.ep = edgePerms;
        this.eo = edgeOrientation;
    }

    /**
     * inverse the cube. use for symetric reduction.
     */
    public CubieCube inverseCube() {

        CubieCube clone = this.clone();
        for (int i = 0; i < edgesValues.length; i++) {
            clone.eo[i] = this.eo[clone.ep[i]];
        }
        for (int i = 0; i < cornerValues.length; i++) {
            char ori = this.co[clone.cp[i]];
            if (ori >= 3)
                clone.co[i] = ori;
            else {
                clone.co[i] = (char) -ori;
                if (clone.co[i] < 0)
                    clone.co[i] += 3;
            }
        }
        return clone;
    }

    public short getTwist() {

        short ret = 0;
        for (int i = URF; i < DRB; i++)
            ret = (short) (3 * ret + co[i]);
        return ret;
    }

    public void setTwist(short twist) {
        int twistParity = 0;
        for (int i = DRB - 1; i > URF - 1; i--) {
            twistParity += co[i] = (char) (twist % 3);
            twist /= 3;
        }
        co[DRB] = (char) ((3 -twistParity % 3) % 3);
    }

    public short getFlip() {

        short ret = 0;
        for (int i = 0; i < 11; i++)
            ret = (short) (2 * ret + eo[i]);
        return ret;
    }

    public void setFlip(short flip) {
        int flipParity = 0;
        for (int i = 10; i >= 0; i--) {
            flipParity += eo[i] = (char) (flip % 2);;
            flip /= 2;

        }
        eo[11] = (char) ((2 - flipParity % 2) % 2);
    }

    public int getCornerParity() {

        int s = 0;
        for (int i = DRB; i > URF; i--) {
            for (int j = i - 1; j > URF - 1; j--) {
                if (cp[j] > cp[i])
                    s++;
            }
        }
        return (s % 2);
    }

    public int getEdgeParity() {

        int s = 0;
        for (int i = BR; i > UR; i--) {
            for (int j = i - 1; j > UR - 1; j--) {
                if (ep[j] > ep[i])
                    s += 1;
            }
        }
        return  s % 2;
    }

    public short getFRtoBR() {

        int a = 0;
        int b = 0;
        int x = 0;

        char[] edges = new char[4];
        for (int i = BR; i >= UR ; i--) {
            if (FR <= ep[i] && ep[i] <= BR) {
                a += CnK(11 - i, x + 1);
                edges[3 - x++] = ep[i];
            }
        }

        for (int i = 3; i > 0 ; i--) {
            int k = 0;
            while (edges[i] != (i + 8)) {
                rotateLeft(edges, 0, i);
                k++;
            }
            b = (i + 1) * b + k;
        }
        return (short) (24 * a + b);
    }

    /**
     * generate  fr to br maths. we first generate permutation from index, then generate the combinaison forslices edges
     * finally we set the remaining edges
     * @param idx the index of the cube
     */
    public void setFRtoBR(int idx) {

        char[] sliceEdges = {FR, FL, BL, BR};
        char[] notSlicesEdges = {UR, UF, UL, UB, DR, DF, DL, DB};
        int b = idx % 24;
        int a = idx / 24;

        Arrays.fill(ep, (char) DB);

        for (int i = 1; i < 4; i++) {

            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0) rotateRight(sliceEdges, 0, i);
        }

        int x = 3;
        for (int i = UR; i <= BR; i++) {
            if (a - CnK(11 - i, x + 1) >=0) {
                ep[i] = sliceEdges[3 - x];
                a -= CnK(11 - i, x + 1);
                x--;
            }
        }
        x = 0;
        for (int i = UR; i <= BR; i++) {
            if (ep[i] == DB) {
                ep[i] = notSlicesEdges[x];
                x++;
            }
        }
    }

    public int getURFtoDLF() {

        int a = 0;
        int x = 0;
        int b = 0;

        char[] corners = new char[8];


        for (int i = URF; i < DRB + 1; i++) {

            if (cp[i] <= DLF) {
                a += CnK(i, x + 1);
                corners[x++] = cp[i];
            }
        }

        for (int i = 5; i > 0; i--) {

            int k = 0;
            while (corners[i] != i) {
                rotateLeft(corners, 0, i);
                k++;
            }
            b = (i + 1) * b + k;
        }
        return  720 * a + b;
    }

    public void setURFtoDLF(int idx) {

        char[] cornrs = {URF, UFL, ULB, UBR, DFR, DLF};
        char[] badCorners = {DBL, DRB};

        int a = idx / 720;
        int b = idx % 720;

        Arrays.fill(cp, (char) DRB);

        for (int i = 1; i < 6; i++) {
            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0)
                rotateRight(cornrs, 0, i);
        }

        int x = 5;
        for (int i = DRB; i >= 0; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                cp[i] = cornrs[x];
                a -= CnK(i, x + 1);
                x -= 1;
            }
        }

        x = 0;
        for (int i = URF; i <= DRB; i++) {
            if (cp[i] == DRB) {
                cp[i] = badCorners[x];
                x += 1;
            }
        }
    }

    public int getURtoDF() {

        int a = 0;
        int x = 0;

        char[] edges = new char[6];

        for (int i = UR; i < BR + 1; i++) {
            if(ep[i] <= DF) {
                a += CnK(i, x + 1);
                edges[x++] = ep[i];
            }
        }

        int b = 0;
        for (int i = 5; i > 0; i--) {
            int k = 0;
            while (edges[i] != i) {
                rotateLeft(edges, 0, i);
                k++;
            }
            b += (i + 1) * b + k;
        }
        return 720 * a + b;
    }

    public void setURtoDF(int idx) {
        char[] goodEdges = {UR, UF, UL, UB, DR, DF};
        char[] badEdges = {DL, DB, FR, FL, BL, BR};

        int a = idx / 720;
        int b = idx % 720;

        Arrays.fill(ep, (char) BR);
        for (int i = 1; i < 6; i++) {
            int k = b % ( i + 1);
            b /= i + 1;
            while (k-- > 0) {
                rotateRight(goodEdges, 0, i);
            }
        }

        int x = 5;
        for (int i = BR; i >= 0; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                ep[i] = goodEdges[x];
                a -= CnK(i, x + 1);
                x--;
            }
        }

        x = 0;
        for (int i = UR; i <= BR; i++) {
            if (ep[i] == BR)
                ep[i] = badEdges[x++];
        }
    }

    public short getURtoUL() {

        int a = 0;
        int x = 0;
        char[] edges = new char[3];

        for (int i = UR; i < BR + 1; i++) {
            if(ep[i] <= UL) {
                a += CnK(i, x + 1);
                edges[x++] = ep[i];
            }
        }

        int b = 0;
        for (int i = 2; i > 0; i--) {
            int k = 0;
            while (edges[i] != i) {
                rotateLeft(edges, 0, i);
                k += 1;
            }
            b = (i + 1) * b + k;
        }
        return (short) (6 * a + b);
    }

    public void setURtoUL(short idx) {

        char[] edges = {UR, UF, UL};
        int a = idx / 6;
        int b = idx % 6;

        Arrays.fill(ep, (char) BR);
        for (int i = 1; i < 3; i++) {
            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0)
                rotateRight(edges, 0, i);
        }

        int x = 2;
        for (int i = BR; i >= 0; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                ep[i] = edges[x];
                a -= CnK(i, x + 1);
                x--;
            }
        }
    }

    public short getUBtoDF() {

        int a = 0;
        int x = 0;
        char[] edges = new char[3];

        for (int i = UR; i <= BR; i++) {
            if (UB <= ep[i] && ep[i] <= DF) {
                a += CnK(i, x + 1);
                edges[x++] = ep[i];
            }
        }

        int b = 0;
        for (int i = 2; i > 0; i--) {
           int k = 0;
           while (edges[i] != UB + i) {
               rotateLeft(edges, 0, i);
               k++;
           }
           b = (i + 1) * b + k;
        }
        return (short) (6 * a + b);
    }

    public void setUBtoDF(int idx) {

        char[] edges = {UB, DR, DF};
        int a = idx / 6;
        int b = idx % 6;

        Arrays.fill(ep, (char) BR);
        for (int i = 1; i < 3; i++) {
             int k = b % (i + 1);
             b /= i + 1;
             while (k-- > 0)
                 rotateRight(edges, 0, i);
        }

        int x = 2;
        for (int i = BR; i >= 0; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                ep[i] = edges[x];
                a -= CnK(i, x + 1);
                x--;
            }
        }
    }

    public int getURFtoDLB() {
        
        char[] permutations = cp.clone();
        int b = 0;

        for (int i = 7; i > 0 ; i--) {
            int k = 0;
            while (permutations[i] != i) {
                rotateLeft(permutations, 0, i);
            }
            b = (i + 1) * b + k;
        }
        return b;
    }

    public void setURFtoDLB(int idx) {

        char[] perms = {URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};
        for (int i = 1; i < 8; i++) {
            int k = idx % (i + 1);
            idx /= i + 1;
            while (k-- > 0)
                rotateRight(perms, 0, i);
        }

        int x = 7;
        for (int i = 7; i > -1; i--) {
            cp[i] = perms[x];
            x--;
        }
    }

    public int getURtoBR() {

        char[] perms = ep.clone();
        int b = 0;

        for (int i = 11; i > 0 ; i--) {
            int k = 0;
            while (perms[i] != i) {
                rotateLeft(perms, 0, i);
                k++;
            }
            b = (i + 1) * b + k;
        }
        return b;
    }

    public void setURtoBR(int idx) {
        char[] perms = {URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};
        for (int i = 1; i < 12; i++) {
            int k = idx % (i + 1);
            idx /= i + 1;
            while (k-- > 0) {
                rotateRight(perms, 0, i);
            }
        }

        int x = 11;
        for (int i = 11; i > 1 ; i++) {
            ep[i] = perms[x];
            x--;
        }
    }

    private int CnK(int n, int k) {

        if (n < k)
            return 0;
        if(k > n / 2)
            k = n - k;

        int s, i, j;
        for ( s = 1, i = n, j = 1; i != n - k; i--, j++) {
            s *= i;
            s /= j;
        }
        return s;
    }


    public static void rotateLeft(char[] arr, int l, int r) {
        char tmp = arr[l];
        for (int i = l; i < r; i++)
            arr[i] = arr[i + 1];
        arr[r] = tmp;
    }

    public static void rotateRight(char[] arr, int l, int r) {
        char tmp = arr[r];
        for (int i = r; i > l; i--) {
            arr[i] = arr[i - 1];
        }
        arr[l] = tmp;
    }

    public static int getURtoDF(int idx1, int idx2) {
        CubieCube a = new CubieCube();
        CubieCube b = new CubieCube();
        a.setURtoUL((short) idx1);
        b.setUBtoDF(idx2);

        for (int i = 0; i < cornerValues.length; i++) {
            if (a.ep[i] != BR) {
                if (b.ep[i] != BR)
                    return -1;
                else
                    b.ep[i]= a.ep[i];
            }
        }
        return b.getURtoDF();
    }

    public CubieCube clone()  {
        CubieCube cloned = null;
        try {
            cloned = (CubieCube) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        cloned.ep = this.ep.clone();
        cloned.eo = this.eo.clone();
        cloned.co = this.co.clone();
        cloned.cp = this.cp.clone();
        return cloned;
    }
}
