package rubik2;

import java.util.Random;

public class Parser implements ConstantCubieCube {

    public static CubieCube parse(String[] args) {

        CubieCube cubieCube = new CubieCube();

        for (int i = 0; i < args.length; i++) {


            int pow = 1;
            switch (args[i].charAt(1)) {
                case ' ':
                    pow = 1;
                    break;
                case '2' :
                    pow = 2;
                    break;
                case '\'':
                    pow = 3;
                    break;
            }
            if (pow == 0) {
                int k = pow;
            }
            switch (args[i].charAt(0)) {
                case 'U':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[U]);
                    break;
                case 'R':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[R]);
                    break;
                case 'F':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[F]);
                    break;
                case 'D':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[D]);
                    break;
                case 'L':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[L]);
                    break;
                case 'B':
                    for (int j = 0; j < pow; j++)
                        cubieCube.multiply(moveCube[B]);
                    break;
            }

        }

     //   System.out.println("shuffled as: " + s.toString());
        return cubieCube;
    }
}
