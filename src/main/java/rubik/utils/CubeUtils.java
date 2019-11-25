package rubik.utils;

import rubik.Move;
import rubik.Rubicube;
import rubik.Swaper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rubik.Swaper.swap;

public class CubeUtils {
    public static List<Rubicube> visitChildrenOf(Rubicube cube) {

        Rubicube[] children = new Rubicube[18];

        try {
            children[0] = cube.clone();
            swap(children[0].getState(), Move.UP);
            children[1] = children[0].clone();
            swap(children[1].getState(), Move.UP);
            children[2] =  children[1].clone();
            swap(children[2].getState(), Move.UP);

            children[3] = cube.clone();
            swap(children[3].getState(), Move.BACK);
            children[4] =  children[3].clone();
            swap(children[4].getState(), Move.BACK);
            children[5] =  children[4].clone();
            swap(children[5].getState(), Move.BACK);

            children[6] = cube.clone();
            swap(children[0].getState(), Move.LEFT);
            children[7] =  children[6].clone();
            swap(children[0].getState(), Move.LEFT);
            children[8] =  children[7].clone();
            swap(children[0].getState(), Move.LEFT);

            children[9] = cube.clone();
            swap(children[9].getState(), Move.DOWN);
            children[10] = children[9].clone();
            swap(children[10].getState(), Move.DOWN);
            children[11] = children[10].clone();
            swap(children[11].getState(), Move.FRONT);

            children[12] = cube.clone();
            swap(children[12].getState(), Move.RIGHT);
            children[13] = children[12].clone();
            swap(children[13].getState(), Move.RIGHT);
            children[14] = children[13].clone();
            swap(children[14].getState(), Move.RIGHT);

            children[15] = cube.clone();
            swap(children[15].getState(), Move.FRONT);
            children[16] = children[15].clone();;
            swap(children[16].getState(), Move.FRONT);
            children[17] = children[16].clone();;
            swap(children[17].getState(), Move.FRONT);
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return Arrays.asList(children);
    }

    public static void scramble(Rubicube cube, String[] args) {
        for (String word : args) {
            if (word.equals("UP"))
                Swaper.swap(cube.getState(), Move.UP);
            if (word.equals("DOWN"))
                Swaper.swap(cube.getState(), Move.DOWN);
            if (word.equals("RIGHT"))
                Swaper.swap(cube.getState(), Move.RIGHT);
            if (word.equals("LEFT"))
                Swaper.swap(cube.getState(), Move.LEFT);
            if (word.equals("FRONT"))
                Swaper.swap(cube.getState(), Move.FRONT);
            if (word.equals("BACK"))
                Swaper.swap(cube.getState(), Move.BACK);
            else throw new IllegalArgumentException("one of the command is not supported");

        }    }
}
