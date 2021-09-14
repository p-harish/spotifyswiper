package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OldPlaylist extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_playlist);

        Button enterPlaylistName = findViewById(R.id.enterPlaylistName);
        enterPlaylistName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText playlistName = findViewById(R.id.playlistName);
        String s = playlistName.getText().toString();



    }
}