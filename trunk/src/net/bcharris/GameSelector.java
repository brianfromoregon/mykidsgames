package net.bcharris;

import jgame.JGPoint;
import jgame.platform.JGEngine;

public class GameSelector extends JGEngine {

    static int lettersAndNumbersGame = '1';
    static int wordGame = '2';
    Game selectedGame = null;

    public GameSelector() {
        initEngineApplet();
    }

    public GameSelector(JGPoint size) {
        initEngine(size.x, size.y);
    }

    @Override
    public void doFrame() {
        if (selectedGame == null) {
            if (getKey(lettersAndNumbersGame)) {
                selectGame(new LettersAndNumbersGame(this));
            } else if (getKey(wordGame)) {
                selectGame(new WordGame(this));
            }
        } else {
            moveObjects(null, 0);
        }
    }

    @Override
    public void initCanvas() {
        setCanvasSettings(20, 15, 16, 16, null, null, null);
    }

    @Override
    public void initGame() {
        setFrameRate(35, 2);
        defineMedia("media.tbl");
    }

    @Override
    public void paintFrame() {
        if (selectedGame == null) {
            drawString("Choose a game:", 25, 50, -1);
            drawString("  1. Letters and Numbers", 25, 60, -1);
            drawString("  2. Words", 25, 70, -1);
        }
        if (!isApplet()) {
            drawString("Shift+Escape to Exit", 5, 5, -1);
        }
    }

    private void selectGame(Game game) {
        selectedGame = game;
        game.init();
    }
}
