package com.diamond.iain.tennisgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    protected float x;
    protected float y;
    protected int screenWidth;
    protected int screenHeight;
    private Bitmap image;
    private Bitmap shadow;

    private Rect bounds;

    protected int imageHeight;
    protected int imageWidth;

    public Sprite(int screenWidth, int screenHeight, int x, int y) {

        // Starting position
        this.x = x;
        this.y = y;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init(Bitmap image, Bitmap shadow) {
        this.image = image;
        this.shadow = shadow;

        imageHeight = image.getHeight();
        imageWidth = image.getWidth();

        bounds = new Rect(0, 0, imageWidth, imageHeight);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(shadow, x, y, null);
        canvas.drawBitmap(image, x, y, null);
    }

    public Rect getSpriteBounds() {

        int _x = (int) x;
        int _y = (int) y;

        return new Rect(_x, _y, _x + bounds.width(), _y + bounds.height());
    }
}
