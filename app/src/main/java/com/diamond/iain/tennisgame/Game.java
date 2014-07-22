package com.diamond.iain.tennisgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Game {

    private static final String TAG = Game.class.getSimpleName();

    private SoundPool soundPool;
    private enum State {
        PAUSED, WON, LOST, RUNNING
    }

    private State state = State.PAUSED;

    private Context context;
    private SurfaceHolder holder;
    private Resources resources;

    private int[] sounds = new int[4];

    private Ball ball;
    private Paddle player;
    private Paddle opponent;

    private int width;
    private int height;
    private int paddlePosY;

    private Bitmap ballImage;
    private Bitmap ballShadow;
    private Bitmap paddleImage;
    private Bitmap paddleShadow;

    public Game(Context context, SurfaceHolder holder, Resources resources) {

        this.context = context;
        this.holder = holder;
        this.resources = resources;

        width = holder.getSurfaceFrame().width();
        height = holder.getSurfaceFrame().height();

        ballImage = BitmapFactory.decodeResource(resources, R.drawable.ball);
        ballShadow = BitmapFactory.decodeResource(resources, R.drawable.ballshadow);
        paddleImage = BitmapFactory.decodeResource(resources, R.drawable.paddle);
        paddleShadow = BitmapFactory.decodeResource(resources, R.drawable.paddleshadow);

        paddlePosY = (height - paddleImage.getHeight()) / 2;
    }

    public void init() {

        ball = new Ball(width, height, (width - ballImage.getWidth()) / 2,
                (height - ballImage.getHeight()) / 2);
        player = new Paddle(width, height, width - 100, paddlePosY);
        opponent = new Paddle(width, height, 50, paddlePosY);

        ball.init(ballImage, ballShadow);
        player.init(paddleImage, paddleShadow);
        opponent.init(paddleImage, paddleShadow);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sounds[Sounds.START] = soundPool.load(context, R.raw.start, 1);
        sounds[Sounds.WIN] = soundPool.load(context, R.raw.win, 1);
        sounds[Sounds.LOSE] = soundPool.load(context, R.raw.lose, 1);
        sounds[Sounds.BOUNCE] = soundPool.load(context, R.raw.beep, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (sounds[Sounds.START] == sampleId)
                    soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1);
            }
        });
    }

    private void resetGamePositions() {

        ball.setPosition((width - ballImage.getWidth()) / 2,
                (height - ballImage.getHeight()) / 2);
        player.setPosition(width - 100, paddlePosY);
        opponent.setPosition(50, paddlePosY);

        ball.setRandomDirection();
    }

    private void updateGame(long elapsed) {

        if (player.getSpriteBounds().contains(ball.getSpriteBounds().right,
                ball.getSpriteBounds().centerY())) {
            ball.moveLeft();
            soundPool.play(sounds[Sounds.BOUNCE], 1.0f, 1.0f, 1, 0, 1);
        } else if (opponent.getSpriteBounds().contains(ball.getSpriteBounds().left,
                ball.getSpriteBounds().centerY())) {
            ball.moveRight();
            soundPool.play(sounds[Sounds.BOUNCE], 1.0f, 1.0f, 1, 0, 1);
        } else if (ball.getSpriteBounds().left < opponent.getSpriteBounds().left) {
            resetGamePositions();
            state = State.WON;
            soundPool.play(sounds[Sounds.WIN], 1.0f, 1.0f, 1, 0, 1);
        } else if (ball.getSpriteBounds().right > player.getSpriteBounds().right) {
            resetGamePositions();
            state = State.LOST;
            soundPool.play(sounds[Sounds.LOSE], 1.0f, 1.0f, 1, 0, 1);
        }

        ball.update(elapsed);
        opponent.update(elapsed, ball);
    }

    public void update(long elapsed) {

        if (state == State.RUNNING) {
            updateGame(elapsed);
        }
    }

    private void drawText(Canvas canvas, String text) {

        Paint textPaint = new Paint();
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(62);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 2, textPaint);
    }

    public void draw() {
        Canvas canvas = holder.lockCanvas();

        if (canvas != null) {
            canvas.drawColor(Color.WHITE);

            switch (state) {
                case PAUSED:
                    drawText(canvas, "Tap Screen to Start");
                    break;
                case WON:
                    drawText(canvas, "You Won!");
                    break;
                case LOST:
                    drawText(canvas, "You Lost!");
                    break;
                case RUNNING:
                    drawGame(canvas);
                    break;
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawGame(Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        ball.draw(canvas);
        player.draw(canvas);
        opponent.draw(canvas);
    }

    public void onTouchEvent(MotionEvent event) {

        // User has to tap the centre of the screen to restart game
        float screenCentreX = width / 2;
        float screenCentreY = height / 2;
        int diffX = (int) Math.abs(screenCentreX - event.getX());
        int diffY = (int) Math.abs(screenCentreY - event.getY());

        if (state == State.RUNNING) {
            player.setPosition(event);
        } else if (diffX < 200 && diffY < 200) {

            state = State.RUNNING;
        }
    }
}
