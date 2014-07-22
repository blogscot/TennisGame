package com.diamond.iain.tennisgame;

import java.util.Random;

public class Ball extends Sprite {
    private final static String TAG = Ball.class.getSimpleName();

    private final float speedX = 0.6f;
    private final float speedY = 0.6f;

    private int directionX = 1;
    private int directionY = 1;

    public Ball(int screenWidth, int screenHeight, int x, int y) {
        super(screenWidth, screenHeight, x, y);

        Random random = new Random();

        directionX = random.nextInt(2) * 2 - 1; // (0,1) -> (0,2) -> (-1, 1)
        directionY = random.nextInt(2) * 2 - 1;
    }

    public void update(long elapsed) {

        final int maxWidth = (screenWidth - imageWidth);
        final int maxHeight = (screenHeight - imageHeight);

        x += directionX * speedX * elapsed;
        y += directionY * speedY * elapsed;

        // Prevents multi-bounce when x or y has gone past the min or max value such that
        // it will still be past the min or max after a change of direction at the next update.
        if (x <= 0) {
            x = 0;
            directionX *= -1;
        } else if (x >= maxWidth) {
            x = maxWidth;
            directionX *= -1;
        }
        if (y <= 0) {
            y = 0;
            directionY *= -1;
        } else if (y >= maxHeight) {
            y = maxHeight;
            directionY *= -1;
        }
    }
}
