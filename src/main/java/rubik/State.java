package rubik;

/**
 *  class representing a rubycube. the cube it self is represented using a table of 6 long
 *  each one being the representation of a face.
 *  We do use long to store because each cubi (one the 9 tile on each face) is stored on 7 bits, such as
 *  | 0 |1 | 0 | 1 | 0 | 1 | 0 |
 *  | position on  |  face ID  |
 *  |  the face    |           |
 *  making the representation of a full face on 9 * (3 + 4) = 63.
 *
 *  To represent the cube, faces are attributed at the begining because operations on them will
 *  be totally different.
 *
 *  0 is the front referred as F
 *  1 is the left  referred as L
 *  2 is the right  referred as R
 *  3 is the up referred as U
 *  4 is the DOWN referred as D
 *  5 is the back referred as B
 *
 *  for the coordonates of the tiles, we will assume F, B, L, R will be table with the 1 behing in the top left corner
 *  for U and D, we will assume that the values are mirrored, the tile one behing at the corner of the back and left faces.
 *  we can represent the cube as
 *
 *  | 0 | 1 | 2 |
 *  | 3 | 4 | 5 | <-- top face
 *  | 6 | 7 | 8 |
 *  | 0 | 1 | 2 | 0 | 1 | 2 | 0 | 1 | 2 | 0 | 1 | 2 |
 *  | 3 | 4 | 5 | 3 | 4 | 5 | 3 | 4 | 5 | 3 | 4 | 5 | <-- left face
 *  | 6 | 7 | 8 | 6 | 7 | 8 | 6 | 7 | 8 | 6 | 7 | 8 |
 *  | 6 | 7 | 8 |
 *  | 3 | 4 | 5 | <-- bottom face
 *  | 0 | 1 | 2 |
 *   */
public class State implements Cloneable{

    // constants for better understanding of where the edge are.
    public static final int FRONT = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int BACK = 5;

    private long[] board;

    private State previous;

    private int realCost;

    private Move move;

    int heuristicCost;

    // draw a cube looking like
    //
    // color are represented from their number.
    // for Optimal save on the memory, we use an int for each face of the cube
    // modulo and division will permit to pick up the number using bitwise operator.
    public State() {
        this.board = new long[6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                int indexAndFace = i + (j << 3);
                putCuby(i, j, indexAndFace);
            }
        }
        this.previous = null;
        this.heuristicCost = -1;
        this.realCost = 0;
        this.move = null;
    }

    /**
     *
     * @param face face we wich to get
     * @param cuby cucby we wich to put a value
     * @param value value to put in
     *
     * this function is core on and permit to put a value inside a
     * cuby, allowing us to encapsulate the abstraction of the cube inside the 6 long.
     */
    public void putCuby(int face, int cuby, long value) {
        long mask = (long)(0b1111111) << (cuby * 7);
        board[face] = (board[face] & ~mask) | (value << (cuby * 7));
    }

    /**
     *
     * @param face face we need to get the cuby from
     * @param cuby index of the cuby in the int.
     * @return the value of the cuby in parameter.
     */
    public long getCuby(int face, int cuby) {
        long mask = (long)(0b1111111) << (cuby * 7);
        return ((board[face] & mask) >> (cuby * 7));

    }

    public long[] getBoard() {return this.board;};

    public void displayDebug() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" | " + getCuby(UP, i * 3 + j));
            }
            System.out.println(" |");
        }

        System.out.print(" | " + getCuby(FRONT, 0) + " | " + getCuby(FRONT, 1) + " | "+ getCuby(FRONT, 2) + " | ");
        System.out.print(" | " + getCuby(RIGHT, 0) + " | " + getCuby(RIGHT, 1) + " | "+ getCuby(RIGHT, 2) + " | ");
        System.out.print(" | " + getCuby(BACK , 0) + " | " + getCuby(BACK , 1) + " | "+ getCuby(BACK , 2) + " | ");
        System.out.println(" | " + getCuby(LEFT , 0) + " | " + getCuby(LEFT , 1) + " | "+ getCuby(LEFT , 2) + " | ");

        System.out.print(" | " + getCuby(FRONT, 3) + " | " + getCuby(FRONT, 4) + " | "+ getCuby(FRONT, 5) + " | ");
        System.out.print(" | " + getCuby(RIGHT, 3) + " | " + getCuby(RIGHT, 4) + " | "+ getCuby(RIGHT, 5) + " | ");
        System.out.print(" | " + getCuby(BACK , 3) + " | " + getCuby(BACK , 4) + " | "+ getCuby(BACK , 5) + " | ");
        System.out.println(" | " + getCuby(LEFT , 3) + " | " + getCuby(LEFT , 4) + " | "+ getCuby(LEFT , 5) + " | ");

        System.out.print(" | " + getCuby(FRONT, 6) + " | " + getCuby(FRONT, 7) + " | "+ getCuby(FRONT, 8) + " | ");
        System.out.print(" | " + getCuby(RIGHT, 6) + " | " + getCuby(RIGHT, 7) + " | "+ getCuby(RIGHT, 8) + " | ");
        System.out.print(" | " + getCuby(BACK , 6) + " | " + getCuby(BACK , 7) + " | "+ getCuby(BACK , 8) + " | ");
        System.out.println    (" | " + getCuby(LEFT , 6) + " | " + getCuby(LEFT , 7) + " | "+ getCuby(LEFT , 8) + " | ");

        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" | " + getCuby(DOWN, i * 3 + j));
            }
            System.out.println(" |");
        }
    }

    public State clone() throws CloneNotSupportedException {
        State newState = (State) super.clone();
        newState.board = this.board.clone();
        return newState;
    }
}
