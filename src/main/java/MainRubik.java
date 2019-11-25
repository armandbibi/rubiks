import rubik.Move;
import rubik.Rubicube;
import rubik.State;
import rubik.Swaper;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.UDSliceCoordinate;
import rubik.coordinates.pruningTable.PruningTableOne;
import rubik.utils.CubeUtils;

public class MainRubik {

    public static void main(String[] args) {


        Rubicube cube = new Rubicube();
        CubeUtils.scramble(cube, args);
        Swaper.swap(cube.getState(), Move.LEFT);
        System.out.println(UDSliceCoordinate.check(cube));
        System.out.println(CornerOrientationCoordinate.check(cube));

        cube.getState().displayDebug();
        int[][] pruning = PruningTableOne.generatePruningTable();
        State solution = new Rubicube().getState();

        int k = 0;

    }
}
