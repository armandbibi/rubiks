package rubik2;

public abstract class Solver implements ConstantCubieCube, ConstantCoords {

    CubieCube cubieCube;

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

    int maxDepth = 99;

    String[] args;

    int maxDepthPhase2;

    private String[] solution;

    private void initSolver() {

        ax = new int[31];
        po = new int[31];
        flip = new int[31];
        twist = new int[31];
        slice = new int[31];
        parity = new int[31];

        URFtoDLF = new int[31];
        FRtoBR = new int[31];
        URtoUL = new int[31];
        URtoDF = new short[31];
        UBtoDF = new Short[31];
        minDistPhase1 = new int[31];
        minDistPhase2 = new int[31];
    }

    public Solver(String[] args) {
        initSolver();
        cubieCube = Parser.parse(args);
        coordCube = new CoordCube(cubieCube);
        this.args = args;
        initCube();
    }

    public Solver(int randSize) {

        String[] initPath = null;
        try {
            initPath = RandomPathGenerator.generate(randSize);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        args = initPath;
        initSolver();
        cubieCube = Parser.parse(initPath);
        coordCube = new CoordCube(cubieCube);
        initCube();
    }



    private void initCube() {
        po[0] = 0;
        ax[0] = 0;
        flip[0] = coordCube.getFlip();
        twist[0] = coordCube.getTwist();
        parity[0] = coordCube.getParity();
        slice[0] = coordCube.getFRtoBR() / 24;
        URFtoDLF[0] = coordCube.getURFtoDLF();
        FRtoBR[0] = coordCube.getFRtoBR();
        URtoUL[0] = coordCube.getURtoUl();
        UBtoDF[0] = coordCube.getUBtoDF();

        time = System.currentTimeMillis();
        minDistPhase1[1] = 1;
        mv = 0;
        n = 0;
        busy = false;
        depthPhase1 = 1;
    };

    public abstract void resolve();

    public String[] debugSolution(int size) {

        String[] tab = new String[30];
        int i;
        for (i = 0; i < size; i++) {

            StringBuilder s = new StringBuilder();

            switch (ax[i]) {
                case 0:
                    s.append('U');
                    break;
                case 1:
                    s.append('R');
                    break;
                case 2:
                    s.append('F');
                    break;
                case 3:
                    s.append('D');
                    break;
                case 4:
                    s.append('L');
                    break;
                case 5:
                    s.append('B');
                    break;
                default:
                    maxDepth--;
                    s.append(" ? ");
            }
            switch (po[i]) {
                case 0:
                    s.append(' ');
                    break;
                case 1:
                    s.append("2 ");
                    break;
                case 2:
                    s.append("' ");
                    break;
                default:
                    s.append(" ??? ");
            }

            if (i == depthPhase1)
                s.append(" . ");
            tab[i] = s.toString();
        }

        this.solution = tab;
        return tab;
    }

    public String getSolution() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < solution.length && solution[i] != null; i++) {
            s.append(solution[i] + " ");
        }
        return s.toString();
    }
    public String getArgs() {

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            s.append(args[i] + " ");
        }
        return s.toString();
    }
}

