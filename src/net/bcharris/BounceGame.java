package net.bcharris;

import jgame.JGObject;
import jgame.platform.JGEngine;

public abstract class BounceGame implements Game {

    protected interface Bouncer {

        /**
         * Called every frame.
         */
        void paint(double xPos, double yPos);

        /**
         * Called very often.
         */
        double width();

        /**
         * Called very often.
         */
        double height();

        /**
         * Next key to press, or null if mission accomplished.
         * Called either after the previous key was pressed or at the start.
         */
        Integer nextKeyCode();

        /**
         * Called once.
         */
        double initialX();

        /**
         * Called once.
         */
        double initialY();
    }
    protected final JGEngine engine;

    public BounceGame(JGEngine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        new MyBouncer(nextBouncer());
    }

    protected abstract Bouncer nextBouncer();

    private class MyBouncer extends JGObject {

        final Bouncer bouncer;
        Integer nextKeyCode;

        MyBouncer(Bouncer bouncer) {
            super("bouncer", true, bouncer.initialX(), bouncer.initialY(), 1, null);
            this.bouncer = bouncer;
            // Give the object an initial speed in a random direction.
            xspeed = engine.random(-1, 1);
            yspeed = engine.random(-1, 1);
            nextKeyCode = bouncer.nextKeyCode();
        }

        @Override
        public void move() {
            if (x > engine.pfWidth() - bouncer.width() && xspeed > 0) {
                xspeed = -xspeed;
            }
            if (x < 0 && xspeed < 0) {
                xspeed = -xspeed;
            }
            if (y > engine.pfHeight() - bouncer.height() && yspeed > 0) {
                yspeed = -yspeed;
            }
            if (y < 0 && yspeed < 0) {
                yspeed = -yspeed;
            }

            if (engine.getKey(nextKeyCode)) {
                engine.clearKey(nextKeyCode);
                nextKeyCode = bouncer.nextKeyCode();
                if (nextKeyCode == null) {
                    engine.playAudio("explode_sound");
                    new JGObject("explo", true, x, y, 0, "explo", 0, 0, 32);
                    remove();
                    new MyBouncer(nextBouncer());
                } else {
                    engine.playAudio("scissors_sound");
                }
            }
        }

        @Override
        public void paint() {
            bouncer.paint(x, y);
        }
    }
}
