package rubik.coordinates.pruningTable;

import rubik.Rubicube;
import rubik.coordinates.BorderOrientationCoordinate;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.UDSliceCoordinate;
import rubik.utils.CubeUtils;
import java.util.List;

public class PruningTableOne {

    private static int count = 0;

    public static int[][] generatePruningTable() {

        Rubicube cube = new Rubicube();
        int[][]prune = new int[64430][2187];

        loopForPruning(prune, 0, cube);
        System.out.println(count);
        return prune;
    }

    private static void loopForPruning(int[][] prune, int depth, Rubicube cube) {
        if (depth < 12) {
            List<Rubicube> children = CubeUtils.visitChildrenOf(cube);

            for (Rubicube child : children) {
                int boc = BorderOrientationCoordinate.check(child);
                int coc = CornerOrientationCoordinate.check(child);
                int valueIntTable = prune[boc][coc];
                if (valueIntTable == 0 || (valueIntTable != 0 && depth < valueIntTable)) {
                    prune[boc][coc] = depth;
                    loopForPruning(prune, depth + 1, child);
                    count++;
                }
            }
        }
    }
}
