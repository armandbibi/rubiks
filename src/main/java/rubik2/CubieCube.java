package rubik2;

public class CubieCube implements ConstantCubieCube{

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
        a.setU
    }

    public void multiplyTheEdges() {

    }

    public short getFlip() {
        return 0;
    }

    public void multiplyTheCorners() {

    }

    public void setTwist(int i) {

    }

    public void setFlip(int i) {

    }

    public void setFRtoBR(int i) {

    }

    public short getFRtoBR() {
        return 0;
    }

    public void setURFtoDLF(int i) {

    }

    public void setURtoDF(int i) {
        
    }

    public short getURtoDF() {
        return 0;
    }


    public void setURtoUL(int i) {

    }

    public void setUBtoDF(int i) {

    }

    public short getUBtoDF() {
        return 0;
    }

    public short getURToUL() {
        return 0;
    }

    public short getURFtoDLF() {
        return 0;
    }

    public short getTwist() {
        return 0;
    }
}
