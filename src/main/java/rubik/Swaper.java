package rubik;

/**
 * utility class made to apply move to the cube.
 * could have been made directly in the RubyClass but for readability made it there.
 * it is compose of 1 function allowing us to apply movement to the cube.
 *
 *  Only permutations or moves allowed are the rotations of the faces.
 *  15 pieces changes places for each permutations, the 8 pieces around the center, and all their
 *  neibourght from the adjacent faces.
 *  because of the way we saves rubicubes, their is no general function to do so, even if we can
 *  use same code for each.
 *
 *  we will refer to center for the piece in the middle, border for the one being adjacent to it and corner for
 *  the ones who are not.
 *
 * centers cannot move.
 */

public class Swaper {

    /**
     *
     * @param cube the cube we want to apply a movement
     * @param move the movement we want to apply to the cube
     */
    public static void swap(State cube, Move move) {
        switch (move) {
            case UP: up(cube); break;
            case DOWN: down(cube); break;
            case RIGHT: right(cube); break;
            case LEFT: left(cube); break;
            case FRONT: front(cube); break;
            case BACK: back(cube); break;
        }
    }

    /**
     *  apply rotation to the back face
     * @param cube the cube to do the back action
     */
    private static void back(State cube) {

        rotateBorders(cube, State.BACK);
        rotateCorners(cube, State.BACK);

        // save the value of the 3 values of the neighbours on the Top face;
        long tmpTop1 = cube.getCuby(State.UP, 0);
        long tmpTop2 = cube.getCuby(State.UP, 1);
        long tmpTop3 = cube.getCuby(State.UP, 2);

        cube.putCuby(State.UP, 0, cube.getCuby(State.RIGHT, 2));
        cube.putCuby(State.UP, 1, cube.getCuby(State.RIGHT, 5));
        cube.putCuby(State.UP, 2, cube.getCuby(State.RIGHT, 8));

        cube.putCuby(State.RIGHT, 8, cube.getCuby(State.DOWN, 0));
        cube.putCuby(State.RIGHT, 5, cube.getCuby(State.DOWN, 1));
        cube.putCuby(State.RIGHT, 2, cube.getCuby(State.DOWN, 2));

        cube.putCuby(State.DOWN, 0, cube.getCuby(State.LEFT, 0));
        cube.putCuby(State.DOWN, 1, cube.getCuby(State.LEFT, 3));
        cube.putCuby(State.DOWN, 2, cube.getCuby(State.LEFT, 6));

        cube.putCuby(State.LEFT, 6, tmpTop1);
        cube.putCuby(State.LEFT, 3, tmpTop2);
        cube.putCuby(State.LEFT, 0, tmpTop3);
    }

    private static void front(State cube) {

        rotateBorders(cube, State.FRONT);
        rotateCorners(cube, State.FRONT);

        long tmp1 = cube.getCuby(State.RIGHT, 0);
        long tmp2 = cube.getCuby(State.RIGHT, 3);
        long tmp3 = cube.getCuby(State.RIGHT, 6);

        cube.putCuby(State.RIGHT, 0 , cube.getCuby(State.UP, 6));
        cube.putCuby(State.RIGHT, 3 , cube.getCuby(State.UP, 7));
        cube.putCuby(State.RIGHT, 6 , cube.getCuby(State.UP, 8));

        cube.putCuby(State.UP, 6, cube.getCuby(State.LEFT, 8));
        cube.putCuby(State.UP, 7, cube.getCuby(State.LEFT, 5));
        cube.putCuby(State.UP, 8, cube.getCuby(State.LEFT, 2));

        cube.putCuby(State.LEFT, 8, cube.getCuby(State.DOWN, 8));
        cube.putCuby(State.LEFT, 5, cube.getCuby(State.DOWN, 7));
        cube.putCuby(State.LEFT, 2, cube.getCuby(State.DOWN, 6));

        cube.putCuby(State.DOWN, 8, tmp3);
        cube.putCuby(State.DOWN, 7, tmp2);
        cube.putCuby(State.DOWN, 6, tmp1);

    }

    private static void left(State cube) {

        rotateBorders(cube, State.LEFT);
        rotateCorners(cube, State.LEFT);

        long tmp1 = cube.getCuby(State.UP, 0);
        long tmp2 = cube.getCuby(State.UP, 3);
        long tmp3 = cube.getCuby(State.UP, 6);

        cube.putCuby(State.UP, 0, cube.getCuby(State.BACK, 8));
        cube.putCuby(State.UP, 3, cube.getCuby(State.BACK, 5));
        cube.putCuby(State.UP, 6, cube.getCuby(State.BACK, 2));

        cube.putCuby(cube.BACK, 2, cube.getCuby(cube.DOWN, 0));
        cube.putCuby(cube.BACK, 5, cube.getCuby(cube.DOWN, 3));
        cube.putCuby(cube.BACK, 8, cube.getCuby(cube.DOWN, 6));

        cube.putCuby(cube.DOWN, 6, cube.getCuby(cube.FRONT, 0));
        cube.putCuby(cube.DOWN, 3 , cube.getCuby(cube.FRONT, 3));
        cube.putCuby(cube.DOWN,  0, cube.getCuby(cube.FRONT, 6));

        cube.putCuby(cube.FRONT, 0, tmp1);
        cube.putCuby(cube.FRONT, 3, tmp2);
        cube.putCuby(cube.FRONT, 6, tmp3);
    }

    private static void right(State cube) {


        rotateBorders(cube, State.RIGHT);
        rotateCorners(cube, State.RIGHT);


        long tmp1 = cube.getCuby(cube.UP, 2);
        long tmp2 = cube.getCuby(cube.UP, 5);
        long tmp3 = cube.getCuby(cube.UP, 8);

        cube.putCuby(cube.UP, 2, cube.getCuby(cube.FRONT, 2));
        cube.putCuby(cube.UP, 6, cube.getCuby(cube.FRONT, 6));
        cube.putCuby(cube.UP, 8, cube.getCuby(cube.FRONT, 8));

        cube.putCuby(cube.FRONT, 2, cube.getCuby(cube.DOWN, 8));
        cube.putCuby(cube.FRONT, 5, cube.getCuby(cube.DOWN, 5));
        cube.putCuby(cube.FRONT, 8, cube.getCuby(cube.DOWN, 2));

        cube.putCuby(cube.DOWN, 2, cube.getCuby(cube.BACK, 0));
        cube.putCuby(cube.DOWN, 5, cube.getCuby(cube.BACK, 3));
        cube.putCuby(cube.DOWN, 8, cube.getCuby(cube.BACK, 6 ));
    }

    private static void down(State cube) {

        rotateBorders(cube, State.DOWN);
        rotateCorners(cube, State.DOWN);

        long tmp1 = cube.getCuby(cube.FRONT, 6);
        long tmp2 = cube.getCuby(cube.FRONT, 7);
        long tmp3 = cube.getCuby(cube.FRONT, 8);

        cube.putCuby(cube.FRONT , 6, cube.getCuby(cube.LEFT,6));
        cube.putCuby(cube.FRONT , 7, cube.getCuby(cube.LEFT ,7));
        cube.putCuby(cube.FRONT , 8, cube.getCuby(cube.LEFT ,8));

        cube.putCuby( cube.LEFT, 6, cube.getCuby(cube.BACK,6));
        cube.putCuby( cube.LEFT, 7, cube.getCuby(cube.BACK ,7));
        cube.putCuby( cube.LEFT, 8, cube.getCuby(cube.BACK ,8));

        cube.putCuby(cube.BACK , 6, cube.getCuby( cube.RIGHT,6));
        cube.putCuby(cube.BACK , 7, cube.getCuby( cube.RIGHT,7));
        cube.putCuby(cube.BACK , 8, cube.getCuby( cube.RIGHT,8));

        cube.putCuby(cube.RIGHT, 6, tmp1);
        cube.putCuby(cube.RIGHT, 7, tmp2);
        cube.putCuby(cube.RIGHT, 8, tmp3);
    }

    private static void up(State cube) {

        rotateBorders(cube, State.UP);
        rotateCorners(cube, State.UP);

        long tmp1 = cube.getCuby(cube.FRONT, 0);
        long tmp2 = cube.getCuby(cube.FRONT, 1);
        long tmp3 = cube.getCuby(cube.FRONT, 2);

        cube.putCuby(cube.FRONT , 0, cube.getCuby(cube.RIGHT,0));
        cube.putCuby(cube.FRONT , 1, cube.getCuby(cube.RIGHT ,1));
        cube.putCuby(cube.FRONT , 2, cube.getCuby(cube.RIGHT ,2));

        cube.putCuby( cube.RIGHT, 0, cube.getCuby(cube.BACK,0));
        cube.putCuby( cube.RIGHT, 1, cube.getCuby(cube.BACK ,1));
        cube.putCuby( cube.RIGHT, 0, cube.getCuby(cube.BACK ,2));

        cube.putCuby(cube.BACK , 0, cube.getCuby( cube.LEFT,0));
        cube.putCuby(cube.BACK , 1, cube.getCuby( cube.LEFT,1));
        cube.putCuby(cube.BACK , 2, cube.getCuby( cube.LEFT,2));

        cube.putCuby(State.LEFT, 0, tmp1);
        cube.putCuby(State.LEFT, 1, tmp2);
        cube.putCuby(State.LEFT , 2, tmp3);
    }

    private static void rotateBorders(State cube, int face) {


        long tmp = cube.getCuby(face, 1);

        cube.putCuby(face, 1, cube.getCuby(face, 3));
        cube.putCuby(face, 3, cube.getCuby(face, 7));
        cube.putCuby(face, 7, cube.getCuby(face, 5));
        cube.putCuby(face,5, tmp);
    }

    private static void rotateCorners(State cube, int face) {
        long tmp = cube.getCuby(face, 0);

        cube.putCuby(face, 0, cube.getCuby(face, 2));
        cube.putCuby(face, 2, cube.getCuby(face, 8));
        cube.putCuby(face, 8, cube.getCuby(face, 6));
        cube.putCuby(face, 6, tmp);
    }
}
