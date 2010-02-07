package net.bcharris;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.platform.JGEngine;

public class Game extends JGEngine {

    public Game() {
        initEngineApplet();
    }

    public Game(JGPoint size) {
        initEngine(size.x, size.y);
    }

    @Override
    public void doFrame() {
        moveObjects(null, 0);
    }

    public void initCanvas() {
        setCanvasSettings(20, 15, 16, 16, null, null, null);
    }

    public void initGame() {
        setFrameRate(35, 2);
        defineMedia("media.tbl");
        new MyObject();
    }

    @Override
    public void paintFrame() {
        if (!isApplet()) {
            drawString("Shift+Escape to Exit", 5, 5, -1);
        }
    }

    class MyObject extends JGObject {

        char myChar;
        int myKeyCode;
        JGColor color;
        JGFont myFont = new JGFont("serif", JGFont.BOLD, 50);
        double myHeight;
        double myWidth;

        MyObject() {
            super("myobject", true, random(pfwidth / 4d, pfwidth * 3d / 4d, 1), random(pfheight / 4d, pfheight * 3d / 4d, 1), 1, null);
            myChar = MyRandom.LETTER_NUMBER_CYCLE.next();
            myKeyCode = Character.toUpperCase(myChar);
            // Give the object an initial speed in a random direction.
            xspeed = random(-1, 1);
            yspeed = random(-1, 1);
            color = MyRandom.COLOR_CYCLE.next();

            // String width and height calculation derived from JGEngine.setFont(Graphics g,JGFont jgfont) and
            // JGEngine.drawString(Graphics g, String str, double x, double y, int align, boolean pf_relative)
            Font font = new Font(myFont.name, myFont.style, (int) myFont.size);
            font = font.deriveFont((float) (myFont.size * getMinScaleFactor()));
            Rectangle2D mySizeInPixels = new TextLayout(Character.toString(myChar), font, ((Graphics2D) getGraphics()).getFontRenderContext()).getBounds();
            myWidth = mySizeInPixels.getWidth() / getXScaleFactor();
            myHeight = mySizeInPixels.getHeight() / getYScaleFactor();
        }

        @Override
        public void move() {
            if (x > pfWidth() - myWidth && xspeed > 0) {
                xspeed = -xspeed;
            }
            if (x < 0 && xspeed < 0) {
                xspeed = -xspeed;
            }
            if (y > pfHeight() - myHeight && yspeed > 0) {
                yspeed = -yspeed;
            }
            if (y < 0 && yspeed < 0) {
                yspeed = -yspeed;
            }

            if (getKey(myKeyCode)) {
                playAudio("explode_sound");
                new JGObject("explo", true, x - myWidth, y - myHeight, 0, "explo", 0, 0, 32);
                remove();
                new MyObject();
            }
        }

        @Override
        public void paint() {
            drawString(Character.toString(myChar), x, y, -1, myFont, color);
        }
    }
}
