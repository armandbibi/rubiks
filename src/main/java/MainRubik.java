import rubik.*;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.UDSliceCoordinate;
import rubik.coordinates.pruningTable.PruningTableOne;
import rubik.utils.CubeUtils;
import rubik.utils.IDASTAR;
import rubik2.CubieCube;
import rubik2.Pruner;
import rubik2.Solver;

public class MainRubik {

    public static void main(String[] args) {


    /*    Rubicube cube = new Rubicube();
        CubeUtils.scramble(cube, args);
        Heuristic.generate();
        System.out.println(UDSliceCoordinate.check(cube));
        System.out.println(CornerOrientationCoordinate.check(cube));
*/
        Pruner.initialize();
        Solver solver = new Solver(args);
        solver.resolve();

  /*      IDASTAR idastar = new IDASTAR(cube);
        idastar.resolve();
        cube.getState().displayDebug();
*/
        int k = 0;

    }
}
