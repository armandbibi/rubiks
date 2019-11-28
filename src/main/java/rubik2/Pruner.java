package rubik2;

import org.springframework.boot.autoconfigure.jdbc.JdbcOperationsDependsOnPostProcessor;

public class Pruner implements ConstantCoords, ConstantCubieCube{



    /*------------------------------------------------------------------------------*/
    /*--- table of values: get different values for each needed table            ---*/
    /*--- merges the coordinates / position / rotation to help generate prunings ---*/
    /*------------------------------------------------------------------------------*/

    private static short[][] twistMove;
    private static short[][] flipMove;
    private static short[][] parityMove;
    private static short[][] FRtoBRMove;
    private static short[][] URFtoDLFMove;
    private static short[][] URtoDFMove;
    private static short[][] URtoULMove;
    private static short[][] UBtoDFMove;
    private static short[][] mergeURtoULandUBtoDF;


    /*------------------------------------------------------------------------------------------*/
    /*--- pruning table generate by the table (see top) give a quick access to values        ---*/
    /*--- all of this need a bit of group theory knowledges: Go to kociema and speedsolver   ---*/
    /*--- the general idea is too give a lower bound according to which group C is belonging ---*/
    /*------------------------------------------------------------------------------------------*/

    private static PruningTable tableCornerPermutation_UDSlicePermutation_Phase2;
    private static PruningTable tableEdgePermutation_Phase2;
    private static PruningTable tableCornerTwist_UDSlicePosition_Phase1;
    private static PruningTable tableEdgeFlip_UDSlicePosition_phaseOne1;

    public static void initialize() {

        // init value tables
        initTwistMove();
        initFlipMove();
        initParityMove();
        initFRtoBRMove();
        initURFtoDLFMove();
        initURtoDFMove();
        initURtoULMove();
        initUBtoDFMove();
        initMergeURtoULandUBtoDF();

        //init pruning tables
        initTableCornerPermutation_UDSlicePermutation_Phase2();
        initTableEdgePermutation_Phase2();
        initTableCornerTwist_UDSlicePosition_Phase1();
        initTableEdgeFlip_UDSlicePosition_phaseOne1();
    }

    /**
     * generate the table for the twist of the corner
     */
    private static void initTwistMove() {

        twistMove = new short[N_TWIST][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (short i = 0; i < N_TWIST; i++) {
            cubieCube.setTwist(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheCorners(moveCube[j]);
                    twistMove[i][3 * j + k] = cubieCube.getTwist();
                }
                cubieCube.multiplyTheCorners(moveCube[j]);
            }
        }
    }

    /**
     * generate table for fliping the edges
     */
    private static void initFlipMove () {

        flipMove = new short[N_FLIP][N_MOVE];
        CubieCube cubiCube = new CubieCube();
        for (int i = 0; i < N_FLIP; i++) {
            cubiCube.setFlip(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubiCube.multiplyTheEdges(moveCube[j]);
                    flipMove[i][3 * j + k] = cubiCube.getFlip();
                }
                cubiCube.multiplyTheEdges(moveCube[j]);
            }
        }
    }

    /**
     * generate the parity of the corner permutations
     */
    public static void initParityMove() {

        parityMove = new short[][]{
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
        {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0},
        };
    }

    /**
     * generate move table for UD-Slices
     */
    public static void initFRtoBRMove() {

        FRtoBRMove = new short[N_FRtoBR][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (int i = 0; i < N_FRtoBR; i++) {
            cubieCube.setFRtoBR(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheEdges(moveCube[j]);
                    FRtoBRMove[i][3 * j + k] = cubieCube.getFRtoBR();
                }
                cubieCube.multiplyTheEdges(moveCube[j]);
            }
        }
    }

    /**
     * generate table for corner permutations.
     * thks to parity, we do not need to calculate DBL and DRB
     */
    public static void initURFtoDLFMove() {

        URFtoDLFMove = new short[N_URFtoDLF][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (int i = 0; i < N_URFtoDLF; i++) {
            cubieCube.setURFtoDLF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheCorners(moveCube[j]);
                    URFtoDLFMove[i][3 * j + k] = (short) cubieCube.getURFtoDLF();
                }
                cubieCube.multiplyTheCorners(moveCube[j]);
            }
        }
    }

    /**
     * generate table for face U and D. used only in phase2
     * thks to parity, we do not need to calculate DL and DB
     */
    public static void initURtoDFMove() {

        URtoDFMove = new short[N_URtoDF][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (int i = 0; i < N_URtoDF; i++) {
            cubieCube.setURtoDF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheEdges(moveCube[j]);
                    URtoDFMove[i][3 * j + k] = (short) cubieCube.getURtoDF();
                }
                cubieCube.multiplyTheEdges(moveCube[j]);
            }
        }
    }

    /**
     * generate table for UR UF and UL phase 1
     */

    public static void initURtoULMove() {

        URtoULMove = new short[N_URtoUL][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (int i = 0; i < N_URtoUL; i++) {
            cubieCube.setURtoUL((short) i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheEdges(moveCube[j]);
                    URtoULMove[i][3 * j + k] = cubieCube.getURtoUL();
                }
                cubieCube.multiplyTheEdges(moveCube[j]);
            }
        }
    }

    /**
     * generate table for edges UB, DR, and DF
     */
    public static void initUBtoDFMove() {

        UBtoDFMove = new short[N_UBtoDF][N_MOVE];
        CubieCube cubieCube = new CubieCube();
        for (int i = 0; i < N_UBtoDF; i++) {
            cubieCube.setUBtoDF(i);
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 3; k++) {
                    cubieCube.multiplyTheEdges(moveCube[j]);
                    UBtoDFMove[i][3 * j + k] = (short) cubieCube.getUBtoDF();
                }
                cubieCube.multiplyTheEdges(moveCube[j]);
            }
        }
    }

    /**
     *  generate the table to merge UR, UL, UF and UB, DR, DF
     */
    public static void initMergeURtoULandUBtoDF() {
        mergeURtoULandUBtoDF = new short[SIZE_MERGE_UR_TO_UL_AND_UB_TO_DF][SIZE_MERGE_UR_TO_UL_AND_UB_TO_DF];
        for (int urToUL = 0; urToUL < SIZE_MERGE_UR_TO_UL_AND_UB_TO_DF; urToUL++) {
            for (int UBtoUL = 0; UBtoUL < SIZE_MERGE_UR_TO_UL_AND_UB_TO_DF; UBtoUL++) {
                mergeURtoULandUBtoDF[urToUL][UBtoUL] = (short) CubieCube.getURtoDF(urToUL, UBtoUL);
            }
        }
    }


    private static void initTableCornerPermutation_UDSlicePermutation_Phase2() {

        final int size = N_SLICE2 * N_URFtoDLF * N_PARITY / 2;

        PruningTable table = new PruningTable(size);
        int depth = 0;
        table.setPruning(0, 0);
        int done = 1;

        while (done != N_SLICE2 * N_URFtoDLF * N_PARITY) {
            for (int i = 0; i < N_SLICE2 * N_URFtoDLF * N_PARITY; i++) {
                int parity = i % 2;
                int URFtoDLF = (i / 2) / N_SLICE2;
                int slice = (i / 2) % N_SLICE2;

                if (table.getPruning(i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        // we are only using 8 moves, not 18. have to check the list
                        if (!badCoordonatesCornersPrunePhase2.contains(j)) {
                            int newSlice = FRtoBRMove[slice][j];
                            int newURFtoDLF = URFtoDLFMove[URFtoDLF][j];
                            int newParity = parityMove[parity][j];
                            int index = (N_SLICE2 * newURFtoDLF + newSlice) * 2 + newParity;
                            if (table.getPruning(index) == 0x0F) {
                                table.setPruning(index, (char)(depth + 1) & 0xFF);
                                done++;
                            }
                        }
                    }
                }
            }
            depth +=1;
        }
        tableCornerPermutation_UDSlicePermutation_Phase2 = table;
    }

    private static void initTableEdgePermutation_Phase2() {

        final int size = N_SLICE2 * N_URtoDF * N_PARITY / 2;

        PruningTable table = new PruningTable(size);
        int depth = 0;
        table.setPruning(0, 0);
        int done = 1;

        while (done != size * 2) {
            for (int i = 0; i < size * 2; i++) {
                int parity = i % 2;
                int URtoDF = i / 2 / N_SLICE2;
                int slice = (i / 2) % N_SLICE2;

                if (table.getPruning(i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        // we are only using 8 moves, not 18. have to check the list
                        if (!badCoordonatesCornersPrunePhase2.contains(j)) {
                            int newSlice = FRtoBRMove[slice][j];
                            int newURtoDF = URFtoDLFMove[URtoDF][j];
                            int newParity = parityMove[parity][j];
                            int index = (N_SLICE2 * newURtoDF + newSlice) * 2 + newParity;
                            if (table.getPruning(index) == 0x0F) {
                                table.setPruning(index, depth + 1 & 0xFF);
                                done++;
                            }
                        }
                    }
                }
            }
            depth++;
        }
        tableEdgePermutation_Phase2 = table;
    }

    private static void initTableCornerTwist_UDSlicePosition_Phase1() {

        int size = N_SLICE1 * N_TWIST / 2 + 1;

        PruningTable table = new PruningTable(size);
        int depth = 0;
        table.setPruning(0, 0);
        int done = 1;

        while (done !=  N_SLICE1 * N_TWIST) {
            for (int i = 0; i < N_SLICE1 * N_TWIST; i++) {
                int twist = i / N_SLICE1;
                int slice = i % N_SLICE1;

                if (table.getPruning(i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        int newSlice = FRtoBRMove[slice * 24][j] / 24;
                        int newTwist = twistMove[twist][j];
                        int k = 0;
                        if (table.getPruning(N_SLICE1 * newTwist + newSlice) == 0x0F) {
                            table.setPruning(N_SLICE1 * newTwist + newSlice, (char)(depth + 1));
                            done++;
                        }
                    }
                }
            }
            depth++;
        }
        tableCornerTwist_UDSlicePosition_Phase1 = table;
    }

    private static void initTableEdgeFlip_UDSlicePosition_phaseOne1() {

        int size = N_SLICE1 * N_FLIP / 2;

        PruningTable table = new PruningTable(size);
        int depth = 0;
        table.setPruning(0, 0);
        int done = 1;
        while (done != N_SLICE1 * N_FLIP) {
            for (int i = 0; i < N_SLICE1 * N_FLIP; i++) {
                int flip = i / N_SLICE1;
                int slice = i % N_SLICE1;
                if (table.getPruning(i) == depth) {
                    for (int j = 0; j < 18; j++) {
                        int newSlice = FRtoBRMove[slice * 24][j] / 24;
                        int newFlip = flipMove[flip][j];
                        if (table.getPruning(N_SLICE1 * newFlip + newSlice) == 0x0f) {
                            table.setPruning(N_SLICE1 * newFlip + newSlice, (char) (depth + 1));
                            done++;
                        }
                    }
                }
            }
            depth++;
        }
    }
}
