package rubik.stageSolution;

import rubik.Move;
import rubik.Rubicube;

public interface StageSolutionInformater {


    boolean ContainsCube(Rubicube cube);

    boolean acceptAsLastMove(Move previousMove);
}
