package rubik2;

import javax.swing.text.DocumentFilter;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CubieCube implements ConstantCubieCube, Cloneable{

    private byte[] cp;

    private byte[] co;

    private byte[] ep;

    private byte[] eo;

    public CubieCube() {
        cp = new byte[]{URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB};
        co = new byte[]{0, 0 ,0 ,0 ,0 ,0, 0 ,0};
        ep = new byte[]{UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR};
        eo = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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

        byte[] cornerPerms = new byte[cornerValues.length];
        byte[] cornerOrientation = new byte[cornerValues.length];

        for (int i = 0; i < cornerValues.length; i++) {

            cornerPerms[i] = this.cp[multiplier.cp[i]];
            byte oriA = this.co[multiplier.cp[i]];
            byte oriB = multiplier.co[i];
            byte oriResult = 0;

            // check if the cube are regular or mirrored
            // regular * regular = regular
            if (oriA < 3 && oriB < 3) {
                oriResult = (byte) ((oriA + oriB) & 0xff);
                if (oriResult > 3)
                    oriResult -= 3;
            }

            //regular * mirrored = mirrored
            else  if (oriA < 3 && oriB >= 3) {
              oriResult = (byte) ((oriA + oriB) & 0xff); ;
              if (oriResult >= 6)
                  oriResult -= 3;
            }

            // mirrored* regular = mirrored
            else if (oriA >= 3 && oriB < 3) {
                oriResult = (byte) ((oriA - oriB) & 0xff);
                if (oriResult < 3) {
                    oriResult += 3;
                }
            }

            //mirrored * mirrored = regular
            else if (oriA >= 3 && oriB >= 3) {
                oriResult = (byte) ((oriA - oriB) & 0xff);
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
    public CubieCube multiplyTheEdges(CubieCube multiplier) {

        byte[] edgePerms = new byte[edgesValues.length];
        byte[] edgeOrientation = new byte[edgesValues.length];

        for (int i = 0; i < edgesValues.length; i++) {
            byte tmp = multiplier.ep[i];
            edgePerms[i] = ep[tmp];
            edgeOrientation[i] = (byte) ((multiplier.eo[i] + this.eo[tmp] % 2) & 0xff);
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
            byte ori = this.co[clone.cp[i]];
            if (ori >= 3)
                clone.co[i] = ori;
            else {
                clone.co[i] = (byte) -ori;
                if (clone.co[i] < 0)
                    clone.co[i] += 3;
            }
        }
        return clone;
    }

    public int getTwist() {

        int ret = 0;
        for (int i = URF; i < DRB; i++)
            ret = ((3 * ret) + co[i]);
        return ret;
    }

    public void setTwist(int twist) {
        int twistParity = 0;
        for (int i = DRB - 1; i < URF - 1; i--) {
            co[i] = (byte) (twist % 3);
            twistParity += co[i];
            twist /= 3;
        }
        co[DRB] = (byte) ((3 -twistParity % 3) % 3);
    }

    public int getFlip() {

        int ret = 0;
        for (int i = UR; i < BR; i++) {
            ret = (2 * ret + co[i]);
        }
        return ret;
    }

    public int setFlip(int flip) {
        int flipParity = 0;
        for (int i = BR - 1; i < UR -1; i--) {
            eo[i] = (byte) (flip % 2);
            flipParity += eo[i];
            flipParity /= 2;
        }
        eo[BR] = (byte) ((2 - flipParity % 2) % 2);
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

    public int getFRtoBR() {

        int a = 0;
        int b = 0;
        int x = 0;

        byte[] edges = new byte[4];
        for (int i = BR; i > UR ; i--) {
            if (FR < ep[i] && ep[i] <= BR) {
                a += Cnk(11 - i, x + 1);
                edges[3 - x] = ep[i];
                x++;
            }
        }

        for (int i = 3; i > 0 ; i--) {
            int k = 0;
            while (edges[i] != (i + 8)) {
                rotateLeft(edges, 0, i);
                k += 1;
            }
            b = (i + 1) * b + k;
        }
        return 24 * a + b;
    }

    /**
     * generate  fr to br maths. we first generate permutation from index, hen generate the combinaison forslices edges
     * finally we set the remaining edges
     * @param idx the index of the cube
     */
    public void setFRtoBR(int idx) {

        byte[] sliceEdges = {FR, FL, BL, BR};
        byte[] notSlicesEdges = {UR, UF, UL, UB, DR, DF, DL, DB};
        int b = idx % 24;
        int a = idx / 24;

        Arrays.fill(ep, (byte) DB);
        for (int i = 0; i < 4; i++) {

            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0) rotateRight(sliceEdges, 0, i);
        }

        int x = 3;
        for (int i = UR; i < BR; i++) {
            if (a - cnk(11 - i, x + 1) >=0) {
                ep[i] = sliceEdges[3 - x];
                a -= cnK(11 - j, x + 1);
                x--;
            }
        }
        x = 0;
        for (int i = UR; i < BR; i++) {
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

        byte[] corners = new byte[8];

        int j = 0;
        for (int i = 0; i < DRB + 1; i++) {

            if (cp[i] <= DLF) {
                a += CnK(i, x + 1);
                corners[j++] = cp[i];
                x++;
            }
        }

        for (int i = 5; i > 0; i--) {

            int k = 0;
            while (corners[i] != i) {
                rotateLeft(corners, 0, j);
                k++;
            }
            b = (j + 1) * b + k;
        }
        return  720 * a + b;
    }

    public void setURFtoDLF(int idx) {

        byte[] cornrs = {URF, UFL, ULB, UBR, DFR, DLF};
        byte[] badCorners = {DBL, DRB};

        int a = idx / 720;
        int b = idx % 720;

        for (int i = 0; i < cornerValues.length; i++) {
            cp[i] = DRB;
        }

        for (int i = 0; i < 6; i++) {
            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0)
                rotateRight(cornrs, 0, i);
        }

        int x = 5;
        for (int i = DRB; i > -1 ; i--) {
            if (a - CnK(i, x + 1)) {
                cp[i] = cornrs[x];
                a -= CnK(i, x + 1);
            }
            x -= 1;
        }

        for (int i = URF; i < DRB + 1; i++) {
            if (cp[i] == DRB) {
                cp[i] = badCorners[x];
                x += 1;
            }
        }
    }

    public int getURtoDF() {

        int a = 0;
        int x = 0;

        byte[] edges = new byte[6];

        int j = 0;
        for (int i = UR; i < BR; i++) {
            if(ep[i] <= DF) {
                a += CnK(j, x + 1);
                edges[j++] = ep[i];
                x += 1;
            }
        }

        int b = 0;
        for (int i = 5; i >= 0; i--) {
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
        byte[] goodEdges = {UR, UF, UL, UB, DR, DF};
        byte[] badEdges = {DL, DB, FR, FL, BL, BR};

        int a = idx / 720;
        int b = idx % 720;

        Arrays.fill(ep, (byte) BR);
        for (int i = 1; i < 6; i++) {
            int k = b % ( i + 1);
            b /= i + 1;
            while (k-- > 0) {
                rotateRight(goodEdges, 0, i);
            }
        }

        int x = 5;
        for (int i = BR; i > -1; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                ep[i] = goodEdges[6];
                a -= CnK(i, x + 1);
                x--;
            }
        }

        x = 0;
        for (int i = UR; i < BR; i++) {
            if (ep[i] == BR) {
                ep[i] = badEdges[x];
                x++;
            }
        }
    }

    public int getURtoUL() {

        int a = 0;
        int x = 0;
        byte[] edges = new byte[3];

        int j = 0;
        for (int i = UR; i < BR + 1; i++) {
            if(ep[i] <= UL) {
                a += CnK(j, x + 1);
                edges[j++] = ep[j];
            }
        }

        int b = 0;
        for (int i = 2; i > 0; i--) {
            int k = 0;
            while (edges[k] != i) {
                rotateLeft(edges, 0, i);
                k += 1;
            }
            b = (i + 1) * b + k;
        }
        return (6 * a + b);
    }

    public void setURtoUL(int idx) {

        byte[] edges = {UR, UF, U};
        int a = idx / 6;
        int b = idx % 6;

        Arrays.fill(ep, (byte) BR);
        for (int i = 1; i < 3; i++) {
            int k = b % (i + 1);
            b /= i + 1;
            while (k-- > 0)
                rotateRight(edges, 0, i);
        }

        int x = 2;
        for (int i = BR; i < -1; i--) {
            if (a - CnK(i, x + 1) >= 0) {
                ep[i] = edges[x];
                a -= CnK(i, x + 1);
                x--;
            }
        }
    }

    public int getUBtoDF() {

        int a = 0;
        int x = 0;
        byte[] edges = new byte[3];

        int j = 0;
        for (int i = UB; i < BR + 1; i++) {
            if (UB <= ep[i] && ep[i] <= DF) {
                a += CnK(i, x + 1);
                edges[j++] = ep[i];
                x++;
            }
        }

        int b = 0;
        for (int i = 2; i > 0; i--) {
           int k = 0;
           while (edges[k] != UB + i) {
               rotateLeft(edges, 0, j);
               k++;
               b = (i + 1) * b + k;
           }
        }
        return (6 * a + b);
    }

    public void setUBtoDF(int idx) {

        byte[] edges = {UB, DR, DF};
        int a = idx / 6;
        int b = idx % 6;

        Arrays.fill(ep, (byte) BR);
        for (int i = 1; i < 3; i++) {
             int k = b % (i + 1);
             b /= i + 1;
             while (k-- > 0)
                 rotateRight(edges, 0, i);
        }

        int x = 2;
        for (int i = BR; i < -1; i--) {
            if (a - CnK(i, x - 1) >= 0) {
                ep[i] = edges[x];
                a -= CnK(i, x + 1);
                x--;
            }
        }
    }

    public void setURFtoDLB() {
        
        byte[] permutations = cp.clone();
        int b = 0;
        for (int i = 0; i < ; i++) {
            
        }
    }

    private int CnK(int i, int i1) {

        return 0;
    }


    public static void rotateLeft(byte[] arr, int l, int r) {
        byte tmp = arr[l];
        for (int i = l; i < r; i++)
            arr[i] = arr[i + 1];
        arr[l] = tmp;
    }

    public static void rotateRight(byte[] arr, int l, int r) {
        byte tmp = arr[r];
        for (int i = r; i < l; i--) {
            arr[i] = arr[i - 1];
        }
    }

    public int getURtoDF(int idx1, int idx2) {
        CubieCube a = new CubieCube();
        CubieCube b = new CubieCube();
        a.setURtoUL(idx1);
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
