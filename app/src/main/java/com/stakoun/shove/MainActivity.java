package com.stakoun.shove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The MainActivity class manages the main menu of the app.
 * It creates a new instance of GameActivity when the user proceeds to play the game.
 *
 * @version 1.0
 * @author Peter Stakoun
 */
public class MainActivity extends AppCompatActivity
{
    /**
     * The onCreate method sets the app's initial layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * The onResume method is called when the application resumes and clears the displayed error message.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        // Clear error message
        ((TextView) findViewById(R.id.error_message)).setText("");
    }

    /**
     * The error method displays the given error message.
     *
     * @param message provided error message
     */
    private void error(String message)
    {
        // Set error message
        ((TextView) findViewById(R.id.error_message)).setText(message);
    }

    /**
     * The playGame method ensures that the user's display name is valid and then starts GameActivity.
     *
     * @param v View from which playGame was called
     */
    public void playGame(View v)
    {
        // Get display name from text field
        String displayName = ((EditText) findViewById(R.id.display_name_field)).getText().toString();

        // Check validity of display name
        // 3.1h Calling methods in same class
        if (displayName.length() < 3) {
            error("Display name must be at least 3 characters in length.");
        } else if (!displayName.matches("[A-Za-z0-9]+")) {
            error("Display name must be alphanumeric.");
        } else if (displayName.length() > 16) {
            error("Display name must be at most 16 characters in length.");
        } else {
            // Start GameActivity
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("displayname", displayName);
            startActivity(intent);
        }
    }

}
