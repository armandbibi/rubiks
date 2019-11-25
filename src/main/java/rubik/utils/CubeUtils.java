package rubik.utils;

import rubik.Move;
import rubik.Rubicube;
import rubik.Swaper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rubik.Swaper.swap;

public class CubeUtils {
    public static List<Rubicube> visitChildrenOf(Rubicube cube) {

        Rubicube[] children = new Rubicube[18];

            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.LEFT), 0, children, 0,3);
            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.RIGHT), 0, children, 3,3);
            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.UP), 0, children, 6,3);
            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.DOWN), 0, children, 9,3);
            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.FRONT), 0, children, 12,3);
            System.arraycopy(cube.clone3PossibilitiesForDirection(cube, Move.BACK), 0, children, 15,3);

        return Arrays.asList(children);
    }

    public static void scramble(Rubicube cube, String[] args) {
        for (String word : args) {
            if (word.equals("UP"))
                Swaper.swap(cube.getState(), Move.UP);
            else if (word.equals("DOWN"))
                Swaper.swap(cube.getState(), Move.DOWN);
            else if (word.equals("RIGHT"))
                Swaper.swap(cube.getState(), Move.RIGHT);
            else if (word.equals("LEFT"))
                Swaper.swap(cube.getState(), Move.LEFT);
            else if (word.equals("FRONT"))
                Swaper.swap(cube.getState(), Move.FRONT);
            else if (word.equals("BACK"))
                Swaper.swap(cube.getState(), Move.BACK);
            else throw new IllegalArgumentException("one of the command is not supported");

        }    }
}
