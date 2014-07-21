package com.diamond.iain.tennisgame;

import android.view.MotionEvent;

public class Paddle extends Sprite {
    private static final String TAG = Paddle.class.getSimpleName();

    public Paddle(int screenWidth, int screenHeight, int x, int y) {
        super(screenWidth, screenHeight, x, y);

        this.x = x;
        this.y = y;
    }

    public void update() {

    }

    public void setPosition(MotionEvent event) {

        float paddleCentreY = y + imageHeight / 2;
        float paddleCentreX = x + imageWidth / 2;
        int diffY = (int) Math.abs(paddleCentreY - event.getY());
        int diffX = (int) Math.abs(paddleCentreX - event.getX());

        if (diffY < imageHeight / 2 && diffX < imageWidth) {
            this.y = event.getY() - imageHeight / 2;
        }
    }
}
