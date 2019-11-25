package rubik.stageSolution;

import rubik.Move;
import rubik.Rubicube;
import rubik.coordinates.CornerOrientationCoordinate;
import rubik.coordinates.BorderOrientationCoordinate;
import rubik.coordinates.UDSliceCoordinate;

public class firstStageInformater implements StageSolutionInformater {


    /**
     *
     * @param cube check if the cube belong to this stage
     * @return
     */
    @Override
    public boolean ContainsCube(Rubicube cube) {
        return CornerOrientationCoordinate.check(cube) + BorderOrientationCoordinate.check(cube) + UDSliceCoordinate.check(cube) == 0;
    }

    @Override
    public boolean acceptAsLastMove(Move previousMove) {
        return false;
    }

}
