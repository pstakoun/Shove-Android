package com.stakoun.shove;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

public class GameActivity extends AppCompatActivity
{
    private Player self;
    private Player[] players;
    private ServerConnection serverConnection;
    private Handler gameHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        self = new Player(getIntent().getStringExtra("displayname"));
        players = new Player[] { self };

        try {
            serverConnection = new ServerConnection(this, "54.201.81.77", 7000);
        } catch (Exception e) {
            Log.d("serverConnection", e.getMessage());
        }

        startGame();
    }

    public void updatePlayers(String str)
    {
        players = Player.arrayFromString(str);
    }

    private void startGame()
    {
        gameHandler = new Handler();
        gameHandler.postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    serverConnection.update(self);
                    drawPlayers();
                    gameHandler.postDelayed(this, 1000);
                }
            }, 1000L);
    }

    private void drawPlayers()
    {
        for (Player p : players) {
            if (p.getName().equals(self.getName())) {
                self = p;
            }
        }
    }

}
