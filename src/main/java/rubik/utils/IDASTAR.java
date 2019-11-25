package rubik.utils;

import rubik.Heuristic;
import rubik.Rubicube;
import rubik.State;

import java.awt.print.PrinterIOException;
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
        while (solutionDebugDisplay.getPreviousCube() != null) {
            solutionDebugDisplay.getState().displayDebug();
            solutionDebugDisplay = solutionDebugDisplay.getPreviousCube();
        }
        return finalCube;
    }

    private Rubicube depthFirstSearch(Rubicube cube, int realBound) {
        if (Arrays.equals(cube.getState().getBoard(), (solution.getState().getBoard())))
            return cube;
        totalMoveSeen++;
        
        List<Rubicube> children = CubeUtils.visitChildrenOf(cube);
        PriorityQueue<Rubicube> validChildren = new PriorityQueue<>(comparator);
        for (Rubicube child : children) {
            int hash = Arrays.hashCode(child.getState().getBoard());
            if (!openSet.containsKey(hash)) {
                int h = Heuristic.estimate(child);
                int value = child.getRealDistance() + h;
                child.setTotalDistance(value);
                validChildren.add(child);
                openSet.put(hash, child);
                if (nextCostBound < value)
                    nextCostBound = value;
            }
        }

        for (Rubicube child: validChildren) {
            if (child.getTotalDistance() <= currentBound) {
                Rubicube result = depthFirstSearch(child, realBound + 1);
                if (result != null)
                    return result;
            }
            openSet.remove(Arrays.hashCode(child.getState().getBoard()), child);

        }
        return null;
    }
}
