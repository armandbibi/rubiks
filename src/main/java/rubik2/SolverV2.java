package rubik2;



public class SolverV2 extends Solver {


    int currentBound;

    int currentBoundPhaseTwo;


    public SolverV2(String[] args) {
        super(args);
    }

    public SolverV2(int i) {
       super(i);
    }

    @Override
    public void resolve() {


        minDistPhase1[0] = Math.max(
                Pruner.Slice_Flip_Prun.getPruning(N_SLICE1 * flip[0] + slice[0]),
                Pruner.Slice_Twist_Prun.getPruning(N_SLICE1 * twist[0] + slice[0])
        );

        try {
            kociemba();
        } catch (SolutionException e) {
            debugSolution(n);
            System.out.println(e.getMessage());
        }
    }

    public void kociemba() throws SolutionException {

        for (currentBound = 0; currentBound < maxDepth; currentBound++) {
            PhaseOneSearch(0, 0, 0);
        }

    }

    /**
     * loop and find the solution to group g1 only on the edge to aoid filtering again previous ones
     * @param  depth of movement we want to check
     */
    private void PhaseOneSearch(int depth, int axi, int poi) throws SolutionException {

        if (depth == currentBound) {
            if (minDistPhase1[depth] == 0 && lastMoveWasQuaterTurnOfExpectedPhaseOne(axi, poi)) {
                PhaseTwoInit(depth);
            }
        }
        else if (depth < currentBound && minDistPhase1[depth] <= currentBound - depth) {

            for (int i = 0; i < 6; i++) {
                if (depth == 0 || i != ax[depth - 1] && isGoingToBeOptiMovePhaseOne(i, 0, depth)) {
                    for (int j = 0; j < 3; j++) {
                        ax[depth] = i;
                        po[depth] = j;
                        getPrunePhaseOne(depth + 1, i, j);
                        PhaseOneSearch(depth + 1, i, j);
                    }
                }
            }
        }
    }

    private int getPrunePhaseOne(int depth, int i, int j) {

        if (depth != 0) {

            int mv = 3 * ax[depth - 1] + po[depth - 1];

            flip[depth] = Pruner.flipMove[flip[depth - 1]][mv];
            twist[depth] = Pruner.twistMove[twist[depth - 1]][mv];
            slice[depth] = Pruner.FRtoBRMove[slice[depth - 1] * 24][mv] / 24;
        }
        minDistPhase1[depth] = Math.max(
                Pruner.Slice_Flip_Prun.getPruning(N_SLICE1 * flip[depth] + slice[depth]),
                Pruner.Slice_Twist_Prun.getPruning(N_SLICE1 * twist[depth] + slice[depth])
        );

        return  minDistPhase1[depth];
    }

    private int PhaseTwoInit(int depth) throws SolutionException {

        int mv = 0;
        //init the rest of the arrays we use for phase2;
        for (int i = 0; i <= depth; i++) {

            mv = 3 * ax[i] + po[i];
            URFtoDLF[i + 1] = Pruner.URFtoDLFMove[this.URFtoDLF[i]][mv];
            FRtoBR[i + 1] = Pruner.FRtoBRMove[this.FRtoBR[i]][mv];
            parity[i + 1] = Pruner.parityMove[this.parity[i]][mv];

        }

        maxDepthPhase2 = Math.min(10, maxDepth - depth);
        int d1;
        int d2;
        d1 = Pruner.Slice_URFtoDLF_Parity_Prun.getPruning((N_SLICE2 * URFtoDLF[depth] + FRtoBR[depth]) * 2 + parity[depth]);
        if (d1 > maxDepthPhase2)
            return -1;

        for (int i = 0; i <= depth; i++) {
            mv = 3 * ax[i] + po[i];
            URtoUL[i + 1] = Pruner.URtoULMove[this.URtoUL[i]][mv];
            UBtoDF[i + 1] = Pruner.UBtoDFMove[this.UBtoDF[i]][mv];

        }

        URtoDF[depth] = Pruner.mergeURtoULandUBtoDF[URtoUL[depth]][UBtoDF[depth]];
        d2 = Pruner.Slice_URtoDF_Parity_Prun.getPruning((N_SLICE2 * URtoDF[depth] + FRtoBR[depth]) * 2 + parity[depth]);
        if (d2 > maxDepthPhase2)
            return -1;

        if ((minDistPhase2[depth] = Math.max(d1, d2)) == 0) {
            n = depth;
            throw new SolutionException(System.currentTimeMillis() - time, depth);
        }
        depthPhase1 = depth;
        maxDepthPhase2 += depth;
        for (currentBoundPhaseTwo = depth + 1; currentBoundPhaseTwo < maxDepthPhase2; currentBoundPhaseTwo++) {
            phaseTwoSearch(depth);
        }
        return 1;

    }

    private int phaseTwoSearch(int depth) throws SolutionException {

        if (minDistPhase2[depth] == 0) {
            n = depth;
            throw new SolutionException(System.currentTimeMillis() - time, depth);
        }
        else if (depth == currentBoundPhaseTwo) {
            ;
        }
        else if (depth < currentBoundPhaseTwo &&  minDistPhase2[depth] <= maxDepthPhase2 - depth) {
            for (int i = 0; i < 6; i++) {
                if ((ax[depth - 1] != i)) {
                    for (int j = 0; j < 3; j++) {
                        if (isAllowedMovePhaseTwo(i, j) && isGoingToBeOptiMovePhaseOne(i, j, depth)) {
                            ax[depth] = i;
                            po[depth] = j;
                            getPrunePhaseTwo(depth + 1, i, j);
                            phaseTwoSearch(depth + 1);
                        }
                    }
                }
            }
        }
        return 0;
    }

    private void getPrunePhaseTwo(int depth, int oxi, int poi) {
        if (depth > 0) {
            mv = 3 * ax[depth - 1] + po[depth- 1];
            URFtoDLF[depth] = Pruner.URFtoDLFMove[this.URFtoDLF[depth - 1]][mv];
            FRtoBR[depth] = Pruner.FRtoBRMove[this.FRtoBR[depth - 1]][mv];
            parity[depth] = Pruner.parityMove[this.parity[depth - 1]][mv];
            URtoDF[depth] = Pruner.URtoDFMove[this.URtoDF[depth - 1]][mv];
        }
        minDistPhase2[depth] = Math.max(
                Pruner.Slice_URtoDF_Parity_Prun.getPruning((N_SLICE2 * URtoDF[depth] + FRtoBR[depth]) * 2 + parity[depth]),
                Pruner.Slice_URFtoDLF_Parity_Prun.getPruning((N_SLICE2 * URFtoDLF[depth] + FRtoBR[depth]) * 2 + parity[depth]));

    }

    public boolean isGoingToBeOptiMovePhaseOne(int i, int j, int depth) {
        if (i == U  && ax[depth - 1] == D)
            return false;

        if (i == L && ax[depth  - 1] == R)
            return false;

        if (i == F && ax[depth - 1] == B)
            return false;

        return true;
    }

    public boolean isAllowedMovePhaseTwo(int i, int j) {
        return (i == U || i == D || j== 1);
    }

    private boolean lastMoveWasQuaterTurnOfExpectedPhaseOne(int i,int j) {

        return  ((i == R || i == L || i == F || i == B) && (j == 0 || j == 2));
    }
}
