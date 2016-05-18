package com.stakoun.shove;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity
{
    private static final float GAME_SIZE = 500f;
    private static final int PLAYER_RADIUS = 15;
    private static final int[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW };

    private GameView gameView;
    private Player self;
    private Player[] players;
    private ServerConnection serverConnection;
    private Handler gameHandler;
    private Location touchLocation;
    private boolean paused;
    private int screenWidth;

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
            serverConnection = new ServerConnection(this, "52.24.206.203", 7000);
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
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL) {
            touchLocation = null;
        } else {
            touchLocation = new Location(unscale(e.getX()), unscale(e.getY()));
        }
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
                    serverConnection.update(self.getName()+" "+(touchLocation == null ? "null null" : touchLocation.toString()));
                    gameView.invalidate();
                    gameHandler.postDelayed(this, 50);
                }
            }, 50);
    }

    private float scale(float n)
    {
        return n*(screenWidth/GAME_SIZE);
    }

    private float unscale(float n)
    {
        return n*(GAME_SIZE/screenWidth);
    }

    private class GameView extends View
    {
        public GameView(Context context)
        {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {
            screenWidth = w;
            super.onSizeChanged(w, h, oldW, oldH);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(paint.getTextSize() * 5);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawPaint(paint);

            // Draw players
            for (Player p : players) {
                if (p.getName().equals(self.getName())) {
                    self = p;
                }
                if (p.getLocation() != null) {
                    paint.setColor(colors[p.getColor()]);
                    canvas.drawCircle(scale(p.getLocation().getX()), scale(p.getLocation().getY()), scale(PLAYER_RADIUS), paint);
                    paint.setColor(Color.WHITE);
                    canvas.drawText(p.getName(), scale(p.getLocation().getX() - PLAYER_RADIUS), scale(p.getLocation().getY() - PLAYER_RADIUS * 1.5f), paint);
                }
                Log.d("drawing", p.toString());
            }

            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(0, screenWidth, screenWidth, screenWidth, paint);
        }

    }

}
