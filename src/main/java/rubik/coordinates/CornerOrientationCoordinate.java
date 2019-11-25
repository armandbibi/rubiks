package rubik.coordinates;

import rubik.Rubicube;
import rubik.State;

public class CornerOrientationCoordinate implements coordinate{

    private static Rubicube solution = new Rubicube();

    private static long[] fancyCorner = {
            solution.getState().getCuby(State.UP, 0),
            solution.getState().getCuby(State.UP, 2),
            solution.getState().getCuby(State.UP, 6),
            solution.getState().getCuby(State.UP, 8),
            solution.getState().getCuby(State.DOWN, 0),
            solution.getState().getCuby(State.DOWN, 2),
            solution.getState().getCuby(State.DOWN, 6),
            solution.getState().getCuby(State.DOWN, 8)
    };

    public static int check(Rubicube cube) {

        State state = cube.getState();
        int coordinate = 0;

        coordinate = (isFancyCorner(state.getCuby(State.UP, 8)) ? 0 : (isFancyCorner(state.getCuby(State.FRONT, 2)) ? 2 : 1));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.UP, 6)) ? 0 : (isFancyCorner(state.getCuby(State.FRONT, 0)) ? 1 : 2));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.UP, 0)) ? 0 : (isFancyCorner(state.getCuby(State.BACK, 2)) ? 2 : 1));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.UP, 2)) ? 0 : (isFancyCorner(state.getCuby(State.BACK, 0)) ? 1 : 2));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.DOWN, 0)) ? 0 : (isFancyCorner(state.getCuby(State.BACK, 0)) ? 2 : 1));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.DOWN, 2)) ? 0 : (isFancyCorner(state.getCuby(State.BACK, 0)) ? 1 : 2));

        coordinate = coordinate * 3 +  (isFancyCorner(state.getCuby(State.DOWN, 6)) ? 0 : (isFancyCorner(state.getCuby(State.FRONT, 6)) ? 1 : 2));

        return coordinate;
    }

    private static boolean isFancyCorner(long c) {
        return c == fancyCorner[0] || c == fancyCorner[1] || c == fancyCorner[2] || c == fancyCorner[3] ||
                c == fancyCorner[4] || c == fancyCorner[5] || c == fancyCorner[6] || c == fancyCorner[7];
    }
}
