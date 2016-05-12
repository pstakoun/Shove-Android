package com.stakoun.shove;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity
{
    private GameView gameView;
    private Player self;
    private Player[] players;
    private ServerConnection serverConnection;
    private Handler gameHandler;
    private Location touchLocation;
    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(this);
        setContentView(gameView);

        self = new Player(getIntent().getStringExtra("displayname"));

        players = new Player[] { self };

        try {
            serverConnection = new ServerConnection(this, "54.201.81.77", 7000);
        } catch (Exception e) {
            Log.d("serverConnection", e.getMessage());
        }

        startGame();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        paused = true;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        touchLocation = new Location(e.getX(), e.getY());
        Log.d("touch", touchLocation.toString());
        return true;
    }

    public void updatePlayers(String str)
    {
        players = Player.arrayFromString(str);
    }

    private void startGame()
    {
        paused = false;
        gameHandler = new Handler();
        gameHandler.postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    if (paused) {
                        return;
                    }
                    serverConnection.update(self.toString()+" "+(touchLocation == null ? "null null" : touchLocation.toString()));
                    gameView.invalidate();
                    gameHandler.postDelayed(this, 50);
                }
            }, 50);
    }

    private class GameView extends View
    {
        public GameView(Context context)
        {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius = 10;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawPaint(paint);
            paint.setColor(Color.WHITE);

            // Draw players
            for (Player p : players) {
                if (p.getName().equals(self.getName())) {
                    self = p;
                }
                if (p.getLocation() != null) {
                    canvas.drawCircle(p.getLocation().getX(), p.getLocation().getY(), radius, paint);
                }
                Log.d("drawing", p.toString());
            }
        }

    }

}
