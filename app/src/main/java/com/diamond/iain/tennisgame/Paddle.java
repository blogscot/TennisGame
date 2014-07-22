package com.diamond.iain.tennisgame;

import android.view.MotionEvent;

import java.util.Random;

public class Paddle extends Sprite {

    private Random random = new Random();
    private int direction;
    private float speed = 30.0f;

    private static final String TAG = Paddle.class.getSimpleName();

    public Paddle(int screenWidth, int screenHeight, int x, int y) {
        super(screenWidth, screenHeight, x, y);

        this.x = x;
        this.y = y;
    }

    public void update(long elapsed, Ball ball) {
        int decision = random.nextInt(12);

        switch (decision) {
            case 0:
                //direction = 0;
                break;
            case 1:
                // (0,1) -> (0,2) -> (-1,1)
                //direction = random.nextInt(2) * 2 - 1;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                if (ball.y + ball.imageHeight / 2 < y + imageHeight / 2) {
                    direction = -1;
                } else {
                    direction = 1;
                }
            default:
        }

        if (y <= 0) {
            y = 0;
        } else if (y >= screenHeight - imageHeight) {
            y = screenHeight - imageHeight;
        }

        y += direction * speed;
    }

    public void setPosition(MotionEvent event) {

        float paddleCentreY = y + imageHeight / 2;
        float paddleCentreX = x + imageWidth / 2;
        int diffY = (int) Math.abs(paddleCentreY - event.getY());
        int diffX = (int) Math.abs(paddleCentreX - event.getX());

        // Make it easy for users with fat fingers to select Sprite
        if (diffY < imageHeight / 2 && diffX < imageWidth) {
            this.y = event.getY() - imageHeight / 2;
        }
    }
}
