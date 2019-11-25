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

    public  Rubicube[] clone3PossibilitiesForDirection(Rubicube cube, Move move) {

        Rubicube[] list = new Rubicube[3];
        try {
            list[0] = cube.clone();
            list[1] = cube.clone();
            list[2] = cube.clone();
            Swaper.swap(list[0].state = cube.state.clone(), move);
            Swaper.swap(list[1].state = list[0].state.clone(), move);
            Swaper.swap(list[2].state = list[1].state.clone(), move);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Rubicube clone() throws CloneNotSupportedException {
        Rubicube cloned = (Rubicube) super.clone();
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

    public Rubicube getPreviousCube() { return this.previousCube;}

    public int getRealDistance() {
        return this.realDistance;
    }

    public int getTotalDistance() {
        return totalDistance;
    }
}
