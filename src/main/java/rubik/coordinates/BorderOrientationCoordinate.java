package rubik.coordinates;

import rubik.Rubicube;
import rubik.State;

import java.awt.*;
import java.util.logging.Level;

import static rubik.State.*;

public class BorderOrientationCoordinate implements coordinate {

    private static State solution = new Rubicube().getState();
    private final static long COLOR_UP = solution.getCuby(UP,  0) & 3;
    private final static long COLOR_DOWN = solution.getCuby(State.DOWN, 0) & 3;
    private final static long COLOR_RIGHT = solution.getCuby(State.RIGHT, 0) & 3;
    private final static long COLOR_LEFT = solution.getCuby(State.LEFT, 0) & 3;;
    private final static long COLOR_FRONT = solution.getCuby(State.FRONT, 0) & 3;;
    private final static long COLOR_BACK = solution.getCuby(State.BACK, 0) & 3;;

    /*
    * list of the color og the edge to check with the adjacent edge following directly
    */
    private static long[] edges = new long[12];
    private static long[] edgesNextToIt = new long[12];

    public static int check(Rubicube cube) {
        State state = cube.getState();
        edges[0] = state.getCuby(UP, 1) & 3;
        edgesNextToIt[0] = state.getCuby(BACK, 1) & 3;
        edges[1] = state.getCuby(UP, 3) & 3;
        edgesNextToIt[1] = state.getCuby(LEFT, 1) & 3;
        edges[2] = state.getCuby(UP, 5) & 3;
        edgesNextToIt[2] = state.getCuby(FRONT, 1) & 3;
        edges[3] = state.getCuby(UP, 7) & 3;

        edgesNextToIt[3] = state.getCuby(RIGHT, 1) & 3;
        edges[4] = state.getCuby(DOWN, 1) & 3;
        edgesNextToIt[4] = state.getCuby(BACK, 1) & 3;
        edges[5] = state.getCuby(DOWN, 3) & 3;
        edgesNextToIt[5] = state.getCuby(LEFT, 1) & 3;
        edges[6] = state.getCuby(DOWN, 5) & 3;
        edgesNextToIt[6] = state.getCuby(FRONT, 1) & 3;
        edges[7] = state.getCuby(DOWN, 7) & 3;
        edgesNextToIt[7] = state.getCuby(RIGHT, 1) & 3;

        // for the F/B face
        edges[8] = state.getCuby(FRONT, 3) & 3;
        edgesNextToIt[8] = state.getCuby(LEFT, 5) & 3;
        edges[9] = state.getCuby(FRONT, 5) & 3;
        edgesNextToIt[9] = state.getCuby(RIGHT, 3) & 3;


        edges[10] = state.getCuby(BACK, 3) & 3;
        edgesNextToIt[10] = state.getCuby(RIGHT, 5) & 3;
        edges[11] = state.getCuby(BACK, 5) & 3;
        edgesNextToIt[11] = state.getCuby(LEFT, 3) & 3;

        int coordinate = 0;

        for (int i = 0; i < 12; i++) {
            if (edges[i] == COLOR_LEFT || edges[i] == COLOR_RIGHT)
                coordinate = coordinate * 2;
            else if ((edges[i] == COLOR_FRONT || edges[i] == COLOR_BACK) && (edgesNextToIt[i] == COLOR_UP || edgesNextToIt[i] == COLOR_DOWN))
                coordinate = coordinate *2;
            else
                coordinate = coordinate * 2 + 1;
        }
        return coordinate;
    }
}
