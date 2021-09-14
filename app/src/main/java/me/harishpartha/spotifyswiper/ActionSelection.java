package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionSelection extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_selection);

        Button newPlaylist = findViewById(R.id.newPlaylist);
        newPlaylist.setOnClickListener(this);
        Button oldPlaylist = findViewById(R.id.oldPlaylist);
        oldPlaylist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.newPlaylist:
                Intent intent = new Intent(this, NewPlaylist.class);
                startActivity(intent);
                break;
            case R.id.oldPlaylist:
                Intent intent1 = new Intent(this, OldPlaylist.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}