package rubik;

public class Rubicube implements Cloneable {

    private State state;

    private int realDistance;

    private int heuristicDistance;

    private int totalDistance;

    private Rubicube previousCube;

    private Move previousMove;

    public Rubicube() {

        state = new State();
        realDistance = 0;
        heuristicDistance = 0;
        totalDistance = 0;
        previousCube = null;
        previousMove = null;
    }

    public State getState() {
        return state;
    }

    public int getHeuristicDistance() {
        return heuristicDistance;
    }

    public Move getPreviousMove() {
        return previousMove;
    }

    public boolean isSolved() {
        // TODO compare with final State
        return false;
    }

    public Rubicube clone() throws CloneNotSupportedException {
        Rubicube cloned = (Rubicube) super.clone();
        cloned.state = this.state.clone();
        cloned.previousCube = this;
        cloned.totalDistance = this.totalDistance + 1;
        return cloned;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void getPreviousCube() { return this.previousCube;}
}
