package com.stakoun.shove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((TextView) findViewById(R.id.error_message)).setText("");
    }

    private void error(String message)
    {
        ((TextView) findViewById(R.id.error_message)).setText(message);
    }

    public void playGame(View v)
    {
        String displayName = ((EditText) findViewById(R.id.display_name_field)).getText().toString();

        if (displayName.length() < 3) {
            error("Display name must be at least 3 characters in length.");
        } else if (!displayName.matches("[A-Za-z0-9]+")) {
            error("Display name must be alphanumeric.");
        } else if (displayName.length() > 16) {
            error("Display name must be at most 16 characters in length.");
        } else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("displayname", displayName);
            startActivity(intent);
        }
    }

}
