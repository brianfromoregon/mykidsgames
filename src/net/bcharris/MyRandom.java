package net.bcharris;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import jgame.JGColor;
import static java.util.Collections.*;

public class MyRandom {

    private static ImmutableList<Character> LOWER_LETTERS;
    private static ImmutableList<Character> UPPER_LETTERS;
    private static ImmutableList<Character> NUMBERS;
    public static Iterator<Character> LETTER_NUMBER_CYCLE;
    public static Iterator<JGColor> COLOR_CYCLE;

    static {
        Builder<Character> llb = new Builder<Character>();
        Builder<Character> ulb = new Builder<Character>();
        Builder<Character> nb = new Builder<Character>();
        for (int i = 0; i < 26; i++) {
            llb.add((char) (i + 97));
            ulb.add((char) (i + 65));
            if (i < 10) {
                nb.add((char) (i + 48));
            }
        }
        LOWER_LETTERS = llb.build();
        UPPER_LETTERS = ulb.build();
        NUMBERS = nb.build();
        COLOR_CYCLE = Iterators.cycle(JGColor.blue, JGColor.cyan, JGColor.green, JGColor.magenta, JGColor.orange, JGColor.pink, JGColor.red, JGColor.white, JGColor.yellow);
        List<Character> allChars = Lists.newArrayList();
        allChars.addAll(LOWER_LETTERS);
        allChars.addAll(UPPER_LETTERS);
        allChars.addAll(NUMBERS);
        shuffle(allChars);
        LETTER_NUMBER_CYCLE = Iterators.cycle(allChars);
    }
}
