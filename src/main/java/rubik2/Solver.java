package rubik2;

import rubik.Rubicube;

public class Solver implements ConstantCubieCube, ConstantCoords {

    CubieCube cubieCube;

    FaceCube faceCube;

    CoordCube coordCube;

    int ax[];

    int po[];

    int flip[];

    int twist[];

    int slice[];

    int parity[];

    int URFtoDLF[];

    int FRtoBR[];

    int URtoUL[];

    Short[] UBtoDF;

    short[] URtoDF;

    int minDistPhase1[];

    int minDistPhase2[];

    long time;

    int mv;

    int n;

    boolean busy;

    int depthPhase1;

    int maxDepth = 30;

    int totalDepth;

    int maxDepthPhase2;

    public Solver(String[] args) {

        ax = new int[31];
        po = new int[31];
        flip = new int[31];
        twist = new int[31];
        slice = new int[31];
        parity = new int[31];;
        URFtoDLF = new int[31];
        FRtoBR = new int[31];
        URtoUL = new int[31];
        URtoDF = new short[31];
        UBtoDF = new Short[31];
        minDistPhase1 = new int[31];
        minDistPhase2 = new int[31];

        cubieCube = Parser.parse(args);
        coordCube = new CoordCube(cubieCube);

        flip[0] = coordCube.getFlip();
        twist[0] = coordCube.getTwist();
        parity[0] = coordCube.getParity();
        slice[0] = coordCube.getFRtoBR() / 24;
        URFtoDLF[0] = coordCube.getURFtoDLF();
        FRtoBR[0] = coordCube.getFRtoBR();
        URtoUL[0] = coordCube.getURtoUl();
        URtoDF[0] = coordCube.getURtoDF();
        UBtoDF[0] = coordCube.getUBtoDF();

        minDistPhase1[1]= 1;
        mv = 0;
        n = 0;
        busy = false;
        depthPhase1 = 1;
    }


    public void resolve() {

        long start = System.nanoTime();
        mainLoop();
        time = System.nanoTime() - start;
    }

    private void mainLoop() {

        while (true) {
            do {
                if (depthPhase1 - n > minDistPhase1[n + 1] && !busy) {
                    if (ax[n] == 0 || ax[n] == 3)
                        ax[++n] = 1;
                    else ax[++n] = 0;
                }
                else if (++po[n] > 3) {
                    do {
                        if (++ax[n] > 5) {

                            if (n == 0) {
                                if (depthPhase1 >= maxDepth)
                                    return;
                                else {
                                    depthPhase1++;
                                    ax[n] = 0;
                                    po[n] = 1;
                                    busy = false;
                                    break;
                                }
                            } else {
                                n--;
                                busy = true;
                                break;
                            }
                        } else {
                            po[n] = 1;
                            busy = false;
                        }
                    }while (n != 0 && (ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]));
                }
                else busy = false;
            } while (busy);
            computePhaseOne();
        }
    }

    public void computePhaseOne() {
        mv = 3 * ax[n] + po[n] - 1;
        flip[n + 1] = Pruner.flipMove[flip[n]][mv];
        twist[n + 1] = Pruner.twistMove[twist[n]][mv];
        slice[n + 1] = Pruner.FRtoBRMove[slice[n] * 24][mv] / 24;
        minDistPhase1[n + 1] = Math.max(
                Pruner.Slice_Flip_Prun.getPruning(N_SLICE1 * flip[n + 1] + slice[n + 1]),
                Pruner.Slice_Twist_Prun.getPruning(N_SLICE1 * twist[n + 1] + slice[n + 1])
        );

        if (minDistPhase1[n + 1] == 0 && n >= depthPhase1 - 5) {
            minDistPhase1[n + 1] = 10;

            int s;
            if (n == depthPhase1 - 1 && (s = totalDepth(depthPhase1, maxDepth)) >= 0) {
                if (s == depthPhase1 || 
                        (ax[depthPhase1 - 1] != ax[depthPhase1] && ax[depthPhase1] != ax[depthPhase1] + 3)) {
                    System.out.println("success");
                    return;
                }
            }
        }

    }

    /**
     * First part of the function check eather or not ze zil find asolution with the current threshold.
     * Then, if it exist, we try to resolve;
     * @param depthPhase1
     * @param maxDepth
     * @return
     */
    private int totalDepth(int depthPhase1, int maxDepth) {

        for (int i = 0; i < depthPhase1; i++) {
            mv = 3 * ax[i] + po[i] - 1;
            URFtoDLF[i + 1] = Pruner.URFtoDLFMove[this.URFtoDLF[i]][mv];
            FRtoBR[i + 1] = Pruner.FRtoBRMove[this.FRtoBR[i]][mv];
            parity[i + 1] = Pruner.parityMove[this.parity[i]][mv];
        }

        maxDepthPhase2 = Math.min(10, maxDepth - depthPhase1);
        int d1;
        if ((d1 = Pruner.Slice_URFtoDLF_Parity_Prun.getPruning(N_SLICE2 * URFtoDLF[depthPhase1] + FRtoBR[depthPhase1] * 2 + parity[depthPhase1])) > maxDepthPhase2)
            return -1;

        for (int i = 0; i < depthPhase1; i++) {
            mv = 3 * ax[i] + po[i] - 1;
            URtoUL[i + 1] = Pruner.URtoULMove[this.URtoUL[i]][mv];
            UBtoDF[i + 1] = Pruner.UBtoDFMove[this.UBtoDF[i]][mv];
        }
        URtoDF[depthPhase1] = Pruner.mergeURtoULandUBtoDF[URtoUL[depthPhase1]][UBtoDF[depthPhase1]];

        int d2;
        if ((d2 = Pruner.Slice_URtoDF_Parity_Prun.getPruning((N_SLICE2 * URtoDF[depthPhase1] + FRtoBR[depthPhase1] * 2 + parity[depthPhase1]))) > maxDepthPhase2)
            return -1;

        //Solved cube
        if ((minDistPhase2[depthPhase1] = Math.max(d1, d2)) == 0)
            return depthPhase1;


        // do the search
        // may try to put it somewhere else, start to be a really big function

        int depthPhaseTwo = 1;
        int n = depthPhase1;
        po[depthPhase1] = 0;
        ax[depthPhase1] = 0;
        minDistPhase2[n + 1] = 1;
        boolean busy = false;
        do {
            do {
                if ((depthPhase1 + depthPhaseTwo - n > minDistPhase2[n + 1]) && !busy) {

                    if(ax[n] == 0 || ax[n] == 3) {
                        ax[++n] = 1;
                        po[n] = 2;
                    } else {
                        ax[++n] = 0;
                        po[n] = 1;
                    }
                }else if ((ax[n] == 0 || ax[n] == 3) ? (++po[n] > 3) : ((po[n] = po[n] + 2) > 3)) {
                    do {
                        if (++ax[n] > 5) {
                            if (n == depthPhase1) {
                                if (depthPhaseTwo >= maxDepthPhase2)
                                    return -1;

                                else {
                                    depthPhaseTwo++;
                                    ax[n] = 0;
                                    po[n] = 1;
                                    busy = false;
                                    break;
                                }
                            } else {
                                n--;
                                busy = true;
                                break;
                            }
                        } else {
                            if (ax[n] == 0 || ax[n] == 3)
                                po[n] = 1;
                            else
                                po[n] = 2;
                        }
                    } while (n != depthPhase1 && ax[n - 1] == ax[n] || ax[n - 1] - 3 == ax[n]);
                } else
                    busy = false;
            } while (busy);

            mv = 3 * ax[n] + po[n] - 1;
            URFtoDLF[n + 1] = Pruner.URFtoDLFMove[this.URFtoDLF[n]][mv];
            FRtoBR[n + 1] = Pruner.FRtoBRMove[this.FRtoBR[n]][mv];
            parity[n + 1] = Pruner.parityMove[parity[n]][mv];
            URtoDF[n+ 1] = Pruner.URtoDFMove[URtoDF[n]][mv];

            minDistPhase2[n + 1] = Math.max((
                    Pruner.Slice_URtoDF_Parity_Prun.getPruning(N_SLICE2 * this.URtoDF[n + 1] + this.FRtoBR[n + 1]) * 2 + this.parity[n + 1]),
                    Pruner.Slice_URFtoDLF_Parity_Prun.getPruning(N_SLICE2 * this.URFtoDLF[n + 1] + this.FRtoBR[n + 1]) * 2 + this.parity[n + 1]);

        } while (minDistPhase2[n + 1] != 0);
        return depthPhase1 + depthPhaseTwo;
    }


}
