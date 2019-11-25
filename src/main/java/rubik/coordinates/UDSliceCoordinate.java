package rubik.coordinates;

import rubik.Rubicube;
import rubik.State;

public class UDSliceCoordinate implements coordinate {

    public static Rubicube solution = new Rubicube();

    public static Boolean[] occupied = new Boolean[12];

    public static long[] UDSlice = {
            solution.getState().getCuby(State.FRONT, 3),
            solution.getState().getCuby(State.FRONT, 5),
            solution.getState().getCuby(State.BACK, 3),
            solution.getState().getCuby(State.BACK, 5)
    };
    /**
     *
     * @param cube
     * @return the value of the UDSlice coordonate according to http://kociemba.org/math/UDSliceCoord.htm
     * return the sum of the binominial coefficient from the pruning table according to Kociemba work for the UDSlice coordinates
     * this function may need refactor
     * if the result is zero, it means the midle layer is composed by the cubies belonging to it even if they are not correctly placed
     *
     */
    public static int check(Rubicube cube) {

        State state = cube.getState();

        occupied[0] = (isUDSLice(state.getCuby(State.UP, 1)));
        occupied[1] = (isUDSLice(state.getCuby(State.UP, 3)));
        occupied[2] = (isUDSLice(state.getCuby(State.UP, 5)));
        occupied[3] = (isUDSLice(state.getCuby(State.UP, 7)));
        occupied[4] = (isUDSLice(state.getCuby(State.DOWN, 1)));
        occupied[5] = (isUDSLice(state.getCuby(State.DOWN, 3)));
        occupied[6] = (isUDSLice(state.getCuby(State.DOWN, 5)));
        occupied[7] = (isUDSLice(state.getCuby(State.DOWN, 7)));
        occupied[8] = (isUDSLice(state.getCuby(State.FRONT, 3)));
        occupied[9] = (isUDSLice(state.getCuby(State.FRONT, 5)));
        occupied[10] = (isUDSLice(state.getCuby(State.BACK, 3)));
        occupied[11] = (isUDSLice(state.getCuby(State.BACK, 5)));

        int s = 0;
        int k = 3;
        int n = 11;
        while (k >= 0) {
            if (occupied[n]) k--;
            else s += binomial(n, k);
            n--;
        }
        return s;
    }

    private static Boolean isUDSLice(long cuby) {
        return (cuby == UDSlice[0] || cuby == UDSlice[1] || cuby == UDSlice[2] || cuby == UDSlice[3]);
    }

    private static long binomial(int n, int k)
    {
        if (k>n-k)
            k=n-k;

        long b=1;
        for (int i=1, m=n; i<=k; i++, m--)
            b=b*m/i;
        return b;
    }
}
