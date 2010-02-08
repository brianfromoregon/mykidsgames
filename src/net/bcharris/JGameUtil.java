package net.bcharris;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import jgame.JGFont;
import jgame.platform.JGEngine;

public class JGameUtil {

    /**
     * Get the unscaled (model units, not view units) size of a String rendered in a JGFont in a JGEngine.
     */
    public static Size2d getStringSizeForEngine(String string, JGFont jgFont, JGEngine jgEngine) {
        // String width and height calculation derived from JGEngine.setFont(Graphics g,JGFont jgfont) and
        // JGEngine.drawString(Graphics g, String str, double x, double y, int align, boolean pf_relative)
        Font javaAwtFont = new Font(jgFont.name, jgFont.style, (int) jgFont.size);
        javaAwtFont = javaAwtFont.deriveFont((float) (jgFont.size * jgEngine.getMinScaleFactor()));
        Rectangle2D mySizeInPixels = new TextLayout(string, javaAwtFont, ((Graphics2D) jgEngine.getGraphics()).getFontRenderContext()).getBounds();
        return new Size2d(mySizeInPixels.getWidth() / jgEngine.getXScaleFactor(), mySizeInPixels.getHeight() / jgEngine.getYScaleFactor());
    }
}
