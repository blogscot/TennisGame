package com.diamond.iain.tennisgame;

import android.util.Log;

public class GameRunner extends Thread {

    private static final String TAG = GameRunner.class.getSimpleName();
    private Game game;
    private volatile boolean isRunning = true;

    public GameRunner(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Log.d(TAG, "run");

        game.init();
        long startTime = System.currentTimeMillis();

        // Game loop
        while (isRunning) {

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                Log.d(TAG, "sleep interrupted");
            }

            long currentTime = System.currentTimeMillis();
            long elapsed = (currentTime - startTime) % 100; // avoid very large values

            game.update(elapsed);
            game.draw();

            startTime = currentTime;
        }

    }

    public void shutdown() {
        Log.d(TAG, "shutdown");
        isRunning = false;
    }
}
