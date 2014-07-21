package com.diamond.iain.tennisgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class Game {
    private static final String TAG = Game.class.getSimpleName();

    private SurfaceHolder holder;
    private Resources resources;

    private Ball ball;
    private Paddle player;
    private Paddle opponent;

    public Game(SurfaceHolder holder, Resources resources) {
        this.holder = holder;
        this.resources = resources;
    }

    public void init() {

        int width = holder.getSurfaceFrame().width();
        int height = holder.getSurfaceFrame().height();
        Bitmap ballImage = BitmapFactory.decodeResource(resources, R.drawable.ball);
        Bitmap ballShadow = BitmapFactory.decodeResource(resources, R.drawable.ballshadow);
        Bitmap paddleImage = BitmapFactory.decodeResource(resources, R.drawable.paddle);
        Bitmap paddleShadow = BitmapFactory.decodeResource(resources, R.drawable.paddleshadow);

        int paddlePosY = (height / 2) - paddleImage.getHeight() / 2;

        ball = new Ball(width, height, 50, 50);
        player = new Paddle(width, height, width - 100, paddlePosY);
        opponent = new Paddle(width, height, 50, paddlePosY);

        ball.init(ballImage, ballShadow);
        player.init(paddleImage, paddleShadow);
        opponent.init(paddleImage, paddleShadow);
    }

    public void update(long elapsed) {
        ball.update(elapsed);
    }

    public void draw() {
        Canvas canvas = holder.lockCanvas();

        if (canvas != null) {
            canvas.drawColor(Color.WHITE);

            ball.draw(canvas);
            player.draw(canvas);
            opponent.draw(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}
