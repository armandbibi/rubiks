package rubik2;

import java.util.Arrays;

public class PruningTable {

    char[] table;

    /**
     * generate a pruning table with the expected size;
     * @param size
     */
    public PruningTable(int size) {
        table = new char[size];
        Arrays.fill(table, (char) -1);
    }

    /**
     * put a value in a pruning table. we put 2 values in one byte
     *
     * @param index where to put it
     * @param value what to put where
     */
    public void setPruning(int index, int value) {

        if ((index & 1) == 0)
            table[index / 2] &= 0xF0 | value;
        else
            table[index / 2] &= 0x0F | (value << 4);
    }

    public int getPruning(int index) {

        char ret;
        if((index & 1) == 0)
            ret = (char) (table[index / 2] & 0x0f);
        else
            ret = (char) ((table[index / 2] >> 4) & 0x0F);
        return ret;
    }
}
