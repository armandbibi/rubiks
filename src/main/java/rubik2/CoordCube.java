package rubik2;

public class CoordCube implements ConstantCoords {

    public static final int NUMBER_OF_MOVE = 18;

    private short twist;
    private short flip;
    private short parity;
    private short FRtoBR;
    private short URFtoDLF;
    private short URtoUl;
    private short UBtoDF;
    private short URtoDF;

    public CoordCube(CubieCube cube) {

        twist = cube.getTwist();
        flip = cube.getFlip();
        parity = (short) cube.getCornerParity();
        FRtoBR = cube.getFRtoBR();
        URFtoDLF = (short) cube.getURFtoDLF();
        URtoUl = cube.getURtoUL();
        UBtoDF = cube.getUBtoDF();
        URtoDF = (short) cube.getURtoDF();
    }

    public CubieCube toCubieCube() {

        CubieCube cube = new CubieCube();
        cube.setTwist(twist);
        cube.setFlip(flip);
        cube.setFRtoBR(getFRtoBR());
        cube.setURtoUL(URtoUl);
        cube.setURFtoDLF(URFtoDLF);
        cube.setUBtoDF(UBtoDF);
        cube.setURtoDF(URtoDF);
        return cube;
    }

    /**
     * move coordonates thks to the beauty of math
     * @param m permit to access in the table what we are looking for> see it as an index.
     */
    public void move(int m) {

        twist = Pruner.twistMove[twist][m];
        flip = Pruner.flipMove[flip][m];
        parity = Pruner.parityMove[parity][m];
        FRtoBR = Pruner.FRtoBRMove[FRtoBR][m];
        URFtoDLF = Pruner.URFtoDLFMove[URFtoDLF][m];
        URtoUl = Pruner.URtoULMove[URtoUl][m];
        UBtoDF = Pruner.UBtoDFMove[UBtoDF][m];
        // because of group if UR, UL, UL, UB, DR, DF are not in UD, we dont need to update this.
        if (URtoUl < 336 && UBtoDF < 336) {
            URtoDF = Pruner.mergeURtoULandUBtoDF[URtoUl][UBtoDF];
        }
    }

    /*--------------------------*/
    /*--- getter and setters ---*/
    /*--------------------------*/

    public short getTwist() {
        return twist;
    }

    public short getFlip() {
        return flip;
    }

    public short getParity() {
        return parity;
    }

    public short getFRtoBR() {
        return FRtoBR;
    }

    public short getURFtoDLF() {
        return URFtoDLF;
    }

    public short getURtoUl() {
        return URtoUl;
    }

    public short getUBtoDF() {
        return UBtoDF;
    }

}
