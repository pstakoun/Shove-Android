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

import java.util.Random;

/**
 * The GameActivity class controls the display of player sprites.
 *
 * @author Peter Stakoun
 */
public class GameActivity extends Activity
{
    private static final float GAME_SIZE = 400f;
    private static final int PLAYER_RADIUS = 16;
    private static final int[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW };

    private GameView gameView;
    private Player self;
    private int id;
    private Player[] players;
    private ServerConnection serverConnection;
    private Handler gameHandler;
    private Location touchLocation;
    private boolean paused;
    private int screenWidth;
    private int screenHeight;

    /**
     * The onCreate method sets the game's initial state and creates the connection to the server.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set fullscreen layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(this);
        setContentView(gameView);

        // Initialize player array
        self = new Player(getIntent().getStringExtra("displayname"));
        players = new Player[] { self };

        // Generate player id
        Random random = new Random();
        id = random.nextInt(Integer.MAX_VALUE);

        // Create connection to server
        try {
            serverConnection = new ServerConnection(this, "52.24.206.203", 7000);
        } catch (Exception e) {
            Log.d("serverConnection", e.getMessage());
        }

        // Start game loop
        startGame();
    }

    /**
     * The onPause method is called when the application is taken out of focus.
     */
    @Override
    public void onPause()
    {
        super.onPause();

        // Stop game loop
        paused = true;
        finish();
    }

    /**
     * The onTouchEvent method is called when a the screen is touched by the user.
     * This is where the stored touch location is updated.
     *
     * @param e MotionEvent containing the event information
     * @return whether or not the given event was handled
     */
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL) {
            // Remove the stored location if the user is no longer touching the screen
            touchLocation = null;
        } else {
            // Set the stored location
            touchLocation = new Location(unscale(e.getX()), unscale(e.getY() - (screenHeight - screenWidth) / 2));
        }
        return true;
    }

    /**
     * The updatePlayers method uses a provided String of player data and updates the players array.
     * @param str player data
     */
    public void updatePlayers(String str)
    {
        // Get Player array from data String
        players = Player.arrayFromString(str);
    }

    /*
     * The startGame method starts the game loop and calls update functions after getting updated data from the server.
     */
    private void startGame()
    {
        // Initialize paused to false
        paused = false;
        // Initialize game Handler
        gameHandler = new Handler();

        // Start game loop
        gameHandler.postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    if (paused) {
                        // Stop game loop if game paused
                        return;
                    }

                    // Send update to server
                    serverConnection.update(id+" "+self.getName()+" "+(touchLocation == null ? "null null" : touchLocation.toString()));

                    // Update game display
                    gameView.invalidate();

                    // Continue to next iteration of loop
                    gameHandler.postDelayed(this, 20);
                }
            }, 20);
    }

    /*
     * The scale method scales a given number to the screen size.
     */
    private float scale(float n)
    {
        return n*(screenWidth/GAME_SIZE);
    }

    /*
     * The unscale method scales a given number to the game size.
     */
    private float unscale(float n)
    {
        return n*(GAME_SIZE/screenWidth);
    }

    /*
     * The GameView class manages the visuals of the game.
     */
    private class GameView extends View
    {
        /*
         * The default constructor for the GameView class.
         */
        public GameView(Context context)
        {
            super(context);
        }

        /*
         * The onSizeChanged method is called when the screen size is initialized/updated.
         */
        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {
            // Update screen dimensions
            screenWidth = w;
            screenHeight = h;

            super.onSizeChanged(w, h, oldW, oldH);
        }

        /*
         * The onDraw method is called when the current view is invalidated.
         * It redraws the players on the screen at their updated locations.
         */
        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);

            // Initialize Paint with which to draw on the Canvas
            Paint paint = new Paint();
            paint.setTextSize(paint.getTextSize() * 5);

            // Draw background
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            canvas.drawPaint(paint);

            // Draw circular boundaries
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(screenWidth/2, screenHeight/2, screenWidth/2, paint);

            // Draw players
            paint.setStyle(Paint.Style.FILL);
            for (Player p : players) {
                if (p.getName().equals(self.getName())) {
                    // Update self
                    self = p;
                }
                if (p.getLocation() != null) {
                    // Draw player on screen
                    paint.setColor(colors[p.getColor()]);
                    canvas.drawCircle(scale(p.getLocation().getX()), scale(p.getLocation().getY()) + (screenHeight - screenWidth) / 2, scale(PLAYER_RADIUS), paint);
                    //Draw player display name on screen
                    paint.setColor(Color.WHITE);
                    canvas.drawText(p.getName(), scale(p.getLocation().getX() - PLAYER_RADIUS), scale(p.getLocation().getY() - PLAYER_RADIUS * 1.5f) + (screenHeight - screenWidth) / 2, paint);
                }
                // Log player information to console
                Log.d("drawing", p.toString());
            }
        }

    }

}
