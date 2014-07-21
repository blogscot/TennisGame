package com.diamond.iain.tennisgame;

import android.util.Log;

public class Ball extends Sprite {
    private final static String TAG = Ball.class.getSimpleName();

    private final float speedX = 0.6f;
    private final float speedY = 0.6f;

    private int directionX = 1;
    private int directionY = 1;

    public Ball(int screenWidth, int screenHeight, int x, int y) {
        super(screenWidth, screenHeight, x, y);
    }

    public void update(long elapsed) {

        final int maxWidth = (screenWidth - imageWidth);
        final int maxHeight = (screenHeight - imageHeight);

        x += directionX * speedX * elapsed;
        y += directionY * speedY * elapsed;

        if (x <= 0 || x >= maxWidth) {
            // Prevent multi-bounce when x has gone past the min or max value such that
            // it will still be past the min or max after a change of direction. Same for Y value.
            if (x < 0) x = 0;
            if (x > maxWidth) x = maxWidth;
            Log.d(TAG,  "Max Width="+maxWidth+", x=" + x);
            directionX *= -1;
        }
        if (y <= 0 || y >= maxHeight) {
            if (y < 0) y = 0;
            if (y > maxHeight) y = maxHeight;
            Log.d(TAG,  "Max Height="+maxHeight+", y=" + y);
            directionY *= -1;
        }
    }
}
