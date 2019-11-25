package rubik;

import rubik.coordinates.BorderOrientationCoordinate;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.pruningTable.PruningTableOne;

public class Heuristic {

    private static int[][] prune = PruningTableOne.generatePruningTable();

    public static int estimate (Rubicube cube) {
        return prune[BorderOrientationCoordinate.check()][CornerOrientationCoordinate.check()];
    }
}
