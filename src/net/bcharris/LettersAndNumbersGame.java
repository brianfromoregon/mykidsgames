package net.bcharris;

import jgame.JGColor;
import jgame.JGFont;
import jgame.platform.JGEngine;

public class LettersAndNumbersGame extends BounceGame {

    public LettersAndNumbersGame(JGEngine engine) {
        super(engine);
    }

    @Override
    protected Bouncer nextBouncer() {
        return new MyBouncer();
    }

    private class MyBouncer implements Bouncer {

        final JGFont myFont = new JGFont("serif", JGFont.BOLD, 50);
        final char myChar;
        final double width, height;
        final JGColor myColor = MyRandom.COLOR_CYCLE.next();
        boolean nextCalled = false;

        public MyBouncer() {
            myChar = MyRandom.LETTER_NUMBER_CYCLE.next();
            Size2d size = JGameUtil.getStringSizeForEngine(Character.toString(myChar), myFont, engine);
            width = size.width;
            height = size.height;
        }

        @Override
        public Integer nextKeyCode() {
            if (nextCalled) {
                return null;
            }
            nextCalled = true;
            return (int) Character.toUpperCase(myChar);
        }

        @Override
        public void paint(double xPos, double yPos) {
            engine.drawString(Character.toString(myChar), xPos, yPos, -1, myFont, myColor);
        }

        @Override
        public double width() {
            return width;
        }

        @Override
        public double height() {
            return height;
        }

        @Override
        public double initialX() {
            return engine.random(engine.pfWidth() / 4d, engine.pfWidth() * 3d / 4d, 1);
        }

        @Override
        public double initialY() {
            return engine.random(engine.pfHeight() / 4d, engine.pfHeight() * 3d / 4d, 1);
        }
    }
}
