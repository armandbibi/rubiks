package rubik2;

public class TestSuitForCorrection {


    public static void suit() {
        Pruner.initialize();

        System.out.println("\n\n----------------------------------------------------");
        for (int i = 1; i < 25; i++) {
            System.out.println("test for: " + i + "scramble");
            for (int j = 0; j < 10; j++) {
                SolverV2 solver = new SolverV2(i);
                System.out.println("\nshuffled as " + solver.getArgs());
                solver.resolve();
                System.out.println("solved as " + solver.getSolution());
            }
            System.out.println("----------------------------------------------------");
        }
    }
}
