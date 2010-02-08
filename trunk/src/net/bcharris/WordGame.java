package net.bcharris;

import jgame.JGColor;
import jgame.JGFont;
import jgame.platform.JGEngine;

public class WordGame extends BounceGame {

    public WordGame(JGEngine engine) {
        super(engine);
    }

    @Override
    protected Bouncer nextBouncer() {
        return new MyBouncer();
    }

    private class MyBouncer implements Bouncer {

        final char[] myChars;
        final double[] widths;
        final JGFont myFont = new JGFont("serif", JGFont.BOLD, 50);
        final JGColor myColor = MyRandom.COLOR_CYCLE.next();
        final JGColor fadeColor = MyRandom.COLOR_CYCLE.next();
        final int wobbleRadius = 7;
        final int wobbleSteps = 50, fadeSteps = 70;
        final int charGap = 1;
        double width, height;
        int wobbleStep = 0, fadeStep = 0;
        int charPos = 0;

        public MyBouncer() {
            String word = MyRandom.DOLCH_WORD_CYCLE.next().toUpperCase();
            myChars = new char[word.length()];
            widths = new double[word.length()];
            for (int i = 0; i < myChars.length; i++) {
                myChars[i] = word.charAt(i);
                Size2d size = JGameUtil.getStringSizeForEngine(Character.toString(myChars[i]), myFont, engine);
                widths[i] = size.width;
                width += widths[i];
                if (size.height > height) {
                    height = size.height;
                }
            }
            width += charGap * word.length() - 1;
        }

        @Override
        public Integer nextKeyCode() {
            if (charPos >= myChars.length) {
                return null;
            }
            int nextKeyCode = (int) Character.toUpperCase(myChars[charPos]);

            prepNext();

            charPos++;
            return nextKeyCode;
        }

        @Override
        public void paint(double xPos, double yPos) {
            step();

            double currX = xPos;
            for (int i = 0; i < myChars.length; i++) {
                String c = Character.toString(myChars[i]);
                if (i + 1 == charPos) {
                    JGColor color = fadedColor();
                    engine.drawString(c, currX, yPos + wobble(), -1, myFont, color);
                } else {
                    engine.drawString(c, currX, yPos, -1, myFont, myColor);
                }
                currX += widths[i] + charGap;
            }
        }

        @Override
        public double width() {
            return width;
        }

        @Override
        public double height() {
            return height;
        }

        // Number from -1 to 1 indicating the vertical offset (current level of wobbliness).
        private double wobble() {
            return Math.cos(Math.PI + (2 * Math.PI * wobbleStep) / wobbleSteps) * wobbleRadius;
        }

        // Number from 0 to 1
        private JGColor fadedColor() {
            double zeroToOne = (Math.cos(Math.PI + (2 * Math.PI * fadeStep) / fadeSteps) + 1) / 2d;
            int rd = (int) ((fadeColor.r - myColor.r) * zeroToOne);
            int gd = (int) ((fadeColor.g - myColor.g) * zeroToOne);
            int bd = (int) ((fadeColor.b - myColor.b) * zeroToOne);
            return new JGColor(myColor.r + rd, myColor.g + gd, myColor.b + bd);
        }

        private void step() {
            wobbleStep++;
            if (wobbleStep == wobbleSteps) {
                wobbleStep = 0;
            }
            fadeStep++;
            if (fadeSteps == fadeStep) {
                fadeStep = 0;
            }
        }

        private void prepNext() {
            wobbleStep = 0;
            fadeStep = 0;
        }

        @Override
        public double initialX() {
            return engine.pfWidth() / 2d - width / 2d;
        }

        @Override
        public double initialY() {
            return engine.pfHeight() / 2d - height / 2d;
        }
    }
}
