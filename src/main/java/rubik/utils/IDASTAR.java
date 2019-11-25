package rubik.utils;

import rubik.Heuristic;
import rubik.Rubicube;
import rubik.State;

import java.util.*;

public class IDASTAR {

    private Heuristic heuristic;

    private Rubicube initialCube;

    private static final Rubicube solution = new Rubicube();

    private int totalMoveSeen;

    private float timeComplexity;

    private int currentBound;

    private int nextCostBound;

    private Rubicube finalCube;

    private Map<Integer, Rubicube> openSet = new HashMap<>();

    private static final Comparator<Rubicube> comparator = new Comparator<Rubicube>() {
        @Override
        public int compare(Rubicube o1, Rubicube o2) {
            return o1.getHeuristicDistance() - o2.getHeuristicDistance();
        }
    };

    public IDASTAR(Rubicube initialCube) {
        this.initialCube = initialCube;
        totalMoveSeen = 0;
        currentBound = 0;
        nextCostBound = 0;
        finalCube = null;
    }

    public Rubicube resolve() {
        long start = System.currentTimeMillis();
        while (finalCube == null) {
            currentBound = nextCostBound;
            finalCube = depthFirstSearch(initialCube, 0);
            initialCube.setTotalDistance(0);
        }
        long end = System.currentTimeMillis();
        Rubicube solutionDebugDisplay = this.finalCube;
        while (solutionDebugDisplay.getPreviousMove() != null) {
            System.out.println(solutionDebugDisplay.getState().displayDebug(););
        }
    }

    private Rubicube depthFirstSearch(Rubicube cube, int realBound) {
        if (cube.getState().equals(solution.getState()))
            return cube;
        totalMoveSeen++;
        
        List<Rubicube> children = CubeUtils.visitChildrenOf(cube);
        List<Rubicube> validChildren = new PriorityQueue<>(comparator);
        for (Rubicube child : children) {
            int hash = Arrays.hashCode(child.getState().getBoard());
            if (!openSet.containsKey(hash)) {
                int h = heuristic.estimate(child, pruningTable);
                int value = child.getRealDistance + h;
                validChildren.add(child);
                if (nextCostBound < value)
                    nextCostBound = value;
            }
        }

        for (Rubicube child: validChildren) {
            if (child.getTotalDistance() <= totalDistance) {

            }
        }
    }
}
