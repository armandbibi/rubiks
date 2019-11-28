package rubik2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ConstantCoords {

    /*---------------------*/
    /*--- constant used ---*/
    /*---------------------*/

    int N_TWIST = 2187; // 3^7 possible corner orientations
    int N_FLIP = 2048; // 2^11 possible edge flips
    int N_SLICE1 = 495; // 12 choose 4 possible positions of FR,FL,BL,BR edges
    int N_SLICE2 = 24; // 4! permutations of FR,FL,BL,BR edges in phase2
    int N_PARITY = 2; // 2 possible corner parities
    int N_URFtoDLF = 20160; // 8!/(8-6)! permutation of URF,UFL,ULB,UBR,DFR,DLF corners
    int N_FRtoBR = 11880; // 12!/(12-4)! permutation of FR,FL,BL,BR edges
    int N_URtoUL = 1320; // 12!/(12-3)! permutation of UR,UF,UL edges
    int N_UBtoDF = 1320; // 12!/(12-3)! permutation of UB,DR,DF edges
    int N_URtoDF = 20160; // 8!/(8-6)! permutation of UR,UF,UL,UB,DR,DF edges in phase2

    int N_URFtoDLB = 40320; // 8! permutations of the corners
    int N_URtoBR = 479001600; // 8! permutations of the corners

    int SIZE_MERGE_UR_TO_UL_AND_UB_TO_DF = 336;

    int N_MOVE = 18;

    /*--------------------------------------------------------------*/
    /*--- table for resolving unecessary coords, can be anything ---*/
    /*--- lot of operations dont need all corders / edges        ---*/
    /*--- will try to stay readable                              ---*/
    /*--------------------------------------------------------------*/

    public static final List<Integer> badCoordonatesCornersPrunePhase2 = new ArrayList<>(Arrays.asList(3, 5, 6, 8, 12, 14, 15, 17));

}
