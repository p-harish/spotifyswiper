package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPlaylist extends AppCompatActivity implements View.OnClickListener {


    private static String[] images;
    private static String[] texts;
    private static String[] songs;

    private static Resources r;
    private static String n;

    private static ImageView[] imageViews;
    private static TextView[] textViews;
    private static TableRow[] tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);
        Button enterPlaylistName = findViewById(R.id.enterPlaylistName);
        enterPlaylistName.setOnClickListener(this);
        Button songSearchButton = findViewById(R.id.songSearchButton);
        songSearchButton.setOnClickListener(this);

        r = getResources();
        n = getPackageName();

        images = new String[10];
        texts = new String[10];
        songs = new String[10];

        imageViews = new ImageView[10];
        textViews = new TextView[10];
        tableRows = new TableRow[10];

        for (int i = 0; i < 10; i++) {
            int j = r.getIdentifier("image" + i, "id", n);
            ImageView imageView = findViewById(j);
            imageViews[i] = imageView;
            int k = r.getIdentifier("textView" + i, "id", n);
            TextView textView = findViewById(k);
            textViews[i] = textView;
            int l = r.getIdentifier("tableRow" + i, "id", n);
            TableRow tableRow = findViewById(l);
            tableRow.setOnClickListener(this);
            tableRows[i] = tableRow;
        }

    }

    @Override
    public void onClick(View view) {


        switch(view.getId()) {
            case R.id.enterPlaylistName:
                EditText playlistName = findViewById(R.id.playlistName);
                String s = playlistName.getText().toString();

                LinearLayout createPlaylistView = findViewById(R.id.createPlaylistView);
                createPlaylistView.setVisibility(View.GONE);

                LinearLayout searchSongsView = findViewById(R.id.searchSongsView);
                searchSongsView.setVisibility(View.VISIBLE);
                Spotify.createPlaylist(s);
                break;
            case R.id.songSearchButton:
                EditText songSearch = findViewById(R.id.songSearch);
                String s1 = songSearch.getText().toString();
                Spotify.searchSongs(s1, this);
                break;
            case R.id.tableRow0:
                Spotify.addSong(songs[0], this);
                break;
            case R.id.tableRow1:
                Spotify.addSong(songs[1], this);
                break;
            case R.id.tableRow2:
                Spotify.addSong(songs[2], this);
                break;
            case R.id.tableRow3:
                Spotify.addSong(songs[3], this);
                break;
            case R.id.tableRow4:
                Spotify.addSong(songs[4], this);
                break;
            case R.id.tableRow5:
                Spotify.addSong(songs[5], this);
                break;
            case R.id.tableRow6:
                Spotify.addSong(songs[6], this);
                break;
            case R.id.tableRow7:
                Spotify.addSong(songs[7], this);
                break;
            case R.id.tableRow8:
                Spotify.addSong(songs[8], this);
                break;
            case R.id.tableRow9:
                Spotify.addSong(songs[9], this);
                break;
            default:
                break;
        }

    }

    public static void setImages(JSONObject jsonObject, Context context) {
        String s = "";
        for (int i = 0; i < 10; i ++) {
            try {
                s = jsonObject.getJSONObject("tracks").getJSONArray("items").getJSONObject(i).getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
                images[i] = s;
                s = jsonObject.getJSONObject("tracks").getJSONArray("items").getJSONObject(i).getString("name");
                texts[i] = s;
                s = jsonObject.getJSONObject("tracks").getJSONArray("items").getJSONObject(i).getString("uri");
                songs[i] = s;
                Log.d("song", s);

            }
            catch (JSONException e) {
                Log.e("songUrl", "failed to get" + e);
            }
        }


        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                changeImages(context);
            }
        });
    }

    public static void changeImages(Context context) {

        for (int i = 0; i < 10; i++) {
            Glide.with(context).load(images[i]).into(imageViews[i]);
            textViews[i].setText(texts[i]);
        }

    }
}