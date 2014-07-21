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

        final int width = holder.getSurfaceFrame().width();
        final int height = holder.getSurfaceFrame().height();
        final Bitmap ballImage = BitmapFactory.decodeResource(resources, R.drawable.ball);
        final Bitmap ballShadow = BitmapFactory.decodeResource(resources, R.drawable.ballshadow);
        final Bitmap paddleImage = BitmapFactory.decodeResource(resources, R.drawable.paddle);
        final Bitmap paddleShadow = BitmapFactory.decodeResource(resources, R.drawable.paddleshadow);

        final int paddlePosY = (height - paddleImage.getHeight()) / 2;

        ball = new Ball(width, height, (width - ballImage.getWidth()) / 2,
                (height - ballImage.getHeight()) / 2);
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
