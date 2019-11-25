package rubik;

import rubik.coordinates.BorderOrientationCoordinate;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.pruningTable.PruningTableOne;

public class Heuristic {

    private static int[][] prune;

    public static void generate() {

        prune = PruningTableOne.generatePruningTable();
        //prune =  new int[64430][2187];
    }

    public static int estimate (Rubicube cube) {
        return prune[BorderOrientationCoordinate.check(cube)][CornerOrientationCoordinate.check(cube)];
    }
}
