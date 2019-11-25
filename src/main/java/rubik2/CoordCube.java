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

    }

    /**
     * move coordonates thks to the beauty of math
     * @param m permit to access in the table what we are looking for> see it as an index.
     */
    public void move(int m) {

     /*   twist = twistMove[twist][m];
        flip = flipMove[flip][m];
        parity = parityMove[parity][m];
        FRtoBR = FRtoBRMove[FRtoBR][m];
        URFtoDLF = URFtoDLFMove[URFtoDLF][m];
        URtoUl = URtoUlMove[URtoUl][m];
        UBtoDF = UBtoDFMove[UBtoDF][m];
        // because of group if UR, UL, UL, UB, DR, DF are not in UD, we dont need to update this.
        if (URtoUl < 336 && UBtoDF < 336) {
            URtoDF = mergeFlip();
        }
*/
    }
}
