package rubik.utils;

import rubik.Rubicube;
import rubik.State;

public enum borderUtils {



    UR { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.FRONT, 5);}},
    UF { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.FRONT, 7);}},
    UL { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.FRONT, 3);}},
    UB { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.FRONT, 1);}},
    DR { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.BACK,  5);}},
    DF { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.BACK,  7);}},
    DL { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.BACK,  3);}},
    DB { @Override long getColor(Rubicube cube) {return cube.getState().getCuby(State.BACK,  1);}};
    /*FR,
    FL,
    BL,
    BR;
*/
    abstract long getColor(Rubicube cube);
}
