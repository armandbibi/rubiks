package rubik2;

import java.util.Random;

public class RandomPathGenerator {

    public static String[] generate(int size) throws IllegalAccessException {

        if (size < 0 || size > 35)
            throw new IllegalAccessException("invalid size");
        Random r = new Random();

        int mov = -1;
        int saved = -1;
        int pow;

        String[] table = new String[size];

        for (int i = 0; i < size; i++) {

            while (mov == saved) {
                mov = r.nextInt(6);
            }
            saved = mov;

            pow = r.nextInt(3);
            StringBuilder s = new StringBuilder();

            switch (mov) {
                case 0:
                    s.append("U");
                    break;
                case 1:
                    s.append("R");
                    break;
                case 2:
                    s.append("F");
                    break;
                case 3:
                    s.append("D");
                    break;
                case 4:
                    s.append("L");
                    break;
                case 5:
                    s.append("B");
                    break;
            }


            switch (pow) {
                case 0:
                    s.append(" ");
                    break;
                case 1:
                    s.append("2 ");
                    break;
                case 2:
                    s.append("' ");
            }
            table[i] = s.toString();
        }
        return table;
    }
}
