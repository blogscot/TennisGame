package com.diamond.iain.tennisgame;

public class Ball extends Sprite {

    private final float speedX = 0.5f;
    private final float speedY = 0.5f;

    private int directionX = 1;
    private int directionY = 1;

    public Ball(int screenWidth, int screenHeight, int x, int y) {
        super(screenWidth, screenHeight, x , y);
    }

    public void update(long elapsed) {

        x += directionX * speedX * elapsed;
        y += directionY * speedY * elapsed;

        if (x > (screenWidth - imageWidth) || x <= 0) directionX *= -1;
        if (y > (screenHeight - imageHeight) || y <= 0) directionY *= -1;

    }
}
