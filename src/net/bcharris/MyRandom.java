package net.bcharris;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import jgame.JGColor;
import static java.util.Collections.*;

public class MyRandom {

    private static final ImmutableList<Character> LOWER_LETTERS;
    private static final ImmutableList<Character> UPPER_LETTERS;
    private static final ImmutableList<Character> NUMBERS;
    public static final Iterator<Character> LETTER_NUMBER_CYCLE;
    public static final Iterator<JGColor> COLOR_CYCLE;
    public static final Iterator<Character> UPPER_LETTER_CYCLE;
    public static final Iterator<Character> LOWER_LETTER_CYCLE;
    public static final Iterator<String> DOLCH_WORD_CYCLE;
    public static final Random RANDOM = new Random();

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
        List<JGColor> colors = Lists.newArrayList(JGColor.blue, JGColor.cyan, JGColor.green, JGColor.magenta, JGColor.orange, JGColor.pink, JGColor.red, JGColor.white, JGColor.yellow);
        shuffle(colors);
        COLOR_CYCLE = Iterators.cycle(colors);
        List<Character> l1 = Lists.newArrayList();
        l1.addAll(LOWER_LETTERS);
        l1.addAll(UPPER_LETTERS);
        l1.addAll(NUMBERS);
        shuffle(l1);
        LETTER_NUMBER_CYCLE = Iterators.cycle(l1);
        l1 = Lists.newArrayList(LOWER_LETTERS);
        shuffle(l1);
        LOWER_LETTER_CYCLE = Iterators.cycle(l1);
        l1 = Lists.newArrayList(UPPER_LETTERS);
        shuffle(l1);
        UPPER_LETTER_CYCLE = Iterators.cycle(l1);
        List<String> l2 = Lists.newArrayList();
        l2.addAll(DolchWords.FIRST_GRADE);
        l2.addAll(DolchWords.NOUNS);
        l2.addAll(DolchWords.PRE_PRIMER);
        l2.addAll(DolchWords.PRIMER);
        l2.addAll(DolchWords.SECOND_GRADE);
        l2.addAll(DolchWords.THIRD_GRADE);
        shuffle(l2);
        DOLCH_WORD_CYCLE = Iterators.cycle(l2);
    }
}
