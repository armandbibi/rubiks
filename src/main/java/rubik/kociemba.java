package rubik;

import rubik.stageSolution.StageSolutionInformater;

import java.util.HashMap;
import java.util.Map;

public class kociemba {

    private Rubicube initialCube;

    private int maxDepth;

    int currentBound;

    int depth;

    int exploredState;

    Map<Integer, Rubicube> closedSet;

    private StageSolutionInformater firstStageSolution;

    public kociemba(Rubicube initialCube) {

        this.initialCube = initialCube;
        currentBound = 0;
        depth = 0;
        exploredState = 0;
        closedSet = new HashMap<>();
    }

    public Rubicube kociemba() {
        for (int depth = 0; depth < maxDepth; depth++) {
            phaseOneSearch(initialCube, depth);
        }
        return null;
    }

    private void phaseOneSearch(Rubicube cube, int depth) {
        if (depth == 0) {
            if(firstStageSolution.ContainsCube(cube) && firstStageSolution.acceptAsLastMove(cube.getPreviousMove()));
                StartPhaseTwo(cube, depth);
        }
        else if (depth > 0) {

        }
    }

    private void StartPhaseTwo(Rubicube cube, int previousDepth) {
        for (int depth = 0; depth < maxDepth - previousDepth; depth++) {
            phaseTwoSearch(cube, depth);
        }
    }

    private void phaseTwoSearch(Rubicube cube, int depth) {
        if (depth == 0) {
            if (cube.isSolved()) {
                maxDepth = depth;
            }
        }
        else
            return;
    }
}
