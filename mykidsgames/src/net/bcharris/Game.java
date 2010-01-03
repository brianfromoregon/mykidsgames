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
        // Move all objects.
        moveObjects(
                null,// object name prefix of objects to move (null means any)
                0 // object collision ID of objects to move (0 means any)
                );
    }

    /** This method is called by the engine when it is ready to intialise the
     * canvas (for an applet, this is after the browser has called init()).
     * Note that applet parameters become available here and not
     * earlier (don't try to access them from within the parameterless
     * constructor!).  Use isApplet() to check if we started as an applet.
     */
    public void initCanvas() {
        // The only thing we need to do in this method is to tell the engine
        // what canvas settings to use.  We should not yet call any of the
        // other game engine methods here!
        setCanvasSettings(
                20, // width of the canvas in tiles
                15, // height of the canvas in tiles
                16, // width of one tile
                16, // height of one tile
                //    (note: total size = 20*16=320  x  15*16=240)
                null,// foreground colour -> use default colour white
                null,// background colour -> use default colour black
                null // standard font -> use default font
                );
    }

    /** This method is called when the engine has finished initialising and is
     * ready to produce frames.  Note that the engine logic runs in its own
     * thread, separate from the AWT event thread, which is used for painting
     * only.  This method is the first to be called from within its thread.
     * During this method, the game window shows the intro screen. */
    public void initGame() {
        // We can set the frame rate, load graphics, etc, at this point.
        // (note that we can also do any of these later on if we wish)
        setFrameRate(
                35,// 35 = frame rate, 35 frames per second
                2 //  2 = frame skip, skip at most 2 frames before displaying
                //      a frame again
                );
        defineMedia("media.tbl");
        new MyObject();
    }

    /** Any graphics drawing beside objects or tiles should be done here.
     * Usually, only status / HUD information needs to be drawn here. */
    @Override
    public void paintFrame() {
        if (!isApplet())
            drawString("Shift+Escape to Exit", 5, 5, -1);
    }

    class MyObject extends JGObject {

        char myChar;
        int myKeyCode;
        JGColor color;
        JGFont myFont = new JGFont("serif", JGFont.BOLD, 50);
        Rectangle2D mySize;

        MyObject() {
            // Initialise game object by calling an appropriate constructor
            // in the JGObject class.
            super(
                    "myobject",// name by which the object is known
                    true,//true means add a unique ID number after the object name.
                    //If we don't do this, this object will replace any object
                    //with the same name.
                    random(pfwidth / 4d, pfwidth * 3d / 4d, 1), // X position
                    random(pfheight / 4d, pfheight * 3d / 4d, 1), // Y position
                    1, // the object's collision ID (used to determine which classes
                    // of objects should collide with each other)
                    null // name of sprite or animation to use (null is none)
                    );

            myChar = MyRandom.LETTER_NUMBER_CYCLE.next();
            myKeyCode = Character.toUpperCase(myChar);
            // Give the object an initial speed in a random direction.
            xspeed = random(-1, 1);
            yspeed = random(-1, 1);
            color = MyRandom.COLOR_CYCLE.next();

            mySize = new TextLayout(Character.toString(myChar), new Font(myFont.name, myFont.style, (int) myFont.size), ((Graphics2D) getGraphics()).getFontRenderContext()).getBounds();
        }

        @Override
        public void move() {
            if (x > pfWidth() - mySize.getWidth() && xspeed > 0) {
                xspeed = -xspeed;
            }
            if (x < 0 && xspeed < 0) {
                xspeed = -xspeed;
            }
            if (y > pfHeight() - mySize.getHeight() && yspeed > 0) {
                yspeed = -yspeed;
            }
            if (y < 0 && yspeed < 0) {
                yspeed = -yspeed;
            }

            if (getKey(myKeyCode)) {
                playAudio("explode_sound");
                new JGObject("explo", true, x - mySize.getWidth(), y - mySize.getHeight(), 0, "explo", 0, 0, 32);
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
