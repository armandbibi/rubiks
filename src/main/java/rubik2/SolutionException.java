package rubik2;

public class SolutionException extends Exception {

    long    time;

    int nbrOfShots;

    public SolutionException(long time, int nbrOfShots) {
        this.time = time;
        this.nbrOfShots = nbrOfShots;
    }

    public String getMessage() {

        return "founded the solution in " + time + " ms and took "+ nbrOfShots + " shots";
    }
}
