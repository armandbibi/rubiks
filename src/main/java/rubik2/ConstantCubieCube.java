package rubik2;

public interface ConstantCubieCube {


    /*--------------------*/
    /*--- color const ---*/
    /*--------------------*/

    int U = 0;
    int R = 1;
    int F = 2;
    int D = 3;
    int L = 4;
    int B = 5;

    /*--------------------*/
    /*--- corner const ---*/
    /*--------------------*/

    int URF = 0;
    int UFL = 1;
    int ULB = 2;
    int UBR = 3;
    int DFR = 4;
    int DLF = 5;
    int DBL = 6;
    int DRB = 7;

    /*--------------------*/
    /*---  edge const  ---*/
    /*--------------------*/

    int UR = 0;
    int UF = 1;
    int UL = 2;
    int UB = 3;
    int DR = 4;
    int DF = 5;
    int DL = 6;
    int DB = 7;
    int FR = 8;
    int FL = 9;
    int BL = 10;
    int BR = 11;


    /*---------------------*/
    /*--- facelet const ---*/
    /*---------------------*/

    int U1 = 0;
    int U2 = 1;
    int U3 = 2;
    int U4 = 3;
    int U5 = 4;
    int U6 = 5;
    int U7 = 6;
    int U8 = 7;
    int U9 = 8;
    int R1 = 9;
    int R2 = 10;
    int R3 = 11;
    int R4 = 12;
    int R5 = 13;
    int R6 = 14;
    int R7 = 15;
    int R8 = 16;
    int R9 = 17;
    int F1 = 18;
    int F2 = 19;
    int F3 = 20;
    int F4 = 21;
    int F5 = 22;
    int F6 = 23;
    int F7 = 24;
    int F8 = 25;
    int F9 = 26;
    int D1 = 27;
    int D2 = 28;
    int D3 = 29;
    int D4 = 30;
    int D5 = 31;
    int D6 = 32;
    int D7 = 33;
    int D8 = 34;
    int D9 = 35;
    int L1 = 36;
    int L2 = 37;
    int L3 = 38;
    int L4 = 39;
    int L5 = 40;
    int L6 = 41;
    int L7 = 42;
    int L8 = 43;
    int L9 = 44;
    int B1 = 45;
    int B2 = 46;
    int B3 = 47;
    int B4 = 48;
    int B5 = 49;
    int B6 = 50;
    int B7 = 51;
    int B8 = 52;
    int B9 = 53;

    /*-----------------------------*/
    /*---- Array to hold values ---*/
    /*-----------------------------*/

    public static final byte[] cornerValues = { URF, UFL, ULB, UBR, DFR, DLF, DBL, DRB,};

    public static final byte[] edgesValues = new byte[]{UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR};


    char  cpU[]  = { UBR, URF, UFL, ULB, DFR, DLF, DBL, DRB };
    char  coU[]  = { 0, 0, 0, 0, 0, 0, 0, 0 };
    char  epU[] = { UB, UR, UF, UL, DR, DF, DL, DB, FR, FL, BL, BR };
    char  eoU[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    char  cpR[]  = { DFR, UFL, ULB, URF, DRB, DLF, DBL, UBR };
    char  coR[]  = { 2, 0, 0, 1, 1, 0, 0, 2 };
    char  epR[] = { FR, UF, UL, UB, BR, DF, DL, DB, DR, FL, BL, UR };
    char  eoR[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    char  cpF[]  = { UFL, DLF, ULB, UBR, URF, DFR, DBL, DRB };
    char  coF[]  = { 1, 2, 0, 0, 2, 1, 0, 0 };
    char  epF[] = { UR, FL, UL, UB, DR, FR, DL, DB, UF, DF, BL, BR };
    char  eoF[] = { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0 };
    char  cpD[]  = { URF, UFL, ULB, UBR, DLF, DBL, DRB, DFR };
    char  coD[]  = { 0, 0, 0, 0, 0, 0, 0, 0 };
    char  epD[] = { UR, UF, UL, UB, DF, DL, DB, DR, FR, FL, BL, BR };
    char  eoD[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    char  cpL[]  = { URF, ULB, DBL, UBR, DFR, UFL, DLF, DRB };
    char  coL[]  = { 0, 1, 2, 0, 0, 2, 1, 0 };
    char  epL[] = { UR, UF, BL, UB, DR, DF, FL, DB, FR, UL, DL, BR };
    char  eoL[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    char  cpB[]  = { URF, UFL, UBR, DRB, DFR, DLF, ULB, DBL };
    char  coB[]  = { 0, 0, 1, 2, 0, 0, 2, 1 };
    char  epB[] = { UR, UF, UL, BR, DR, DF, DL, BL, FR, FL, UB, DB };
    char  eoB[] = { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1 };

    /*-----------------------------*/
    /*---- move for the cubicub ---*/
    /*-----------------------------*/


    CubieCube[] moveCube = {
    new CubieCube(cpU, coU, epU, eoU),
    new CubieCube(cpR, coR, epR, eoR),
    new CubieCube(cpF, coF, epF, eoF),
    new CubieCube(cpD, coD, epD, eoD),
    new CubieCube(cpL, coL, epL, eoL),
    new CubieCube(cpB, coB, epB, eoB)};
}
