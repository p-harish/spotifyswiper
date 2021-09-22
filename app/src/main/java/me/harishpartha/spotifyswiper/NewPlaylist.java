package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

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
                if (s.length() == 0)
                    break;
                LinearLayout createPlaylistView = findViewById(R.id.createPlaylistView);
                createPlaylistView.setVisibility(View.GONE);

                LinearLayout searchSongsView = findViewById(R.id.songSearchView);
                searchSongsView.setVisibility(View.VISIBLE);


                Spotify.createPlaylist(s);
                break;
            case R.id.songSearchButton:
                EditText songSearch = findViewById(R.id.songSearch);
                String s1 = songSearch.getText().toString();
                if (s1.length() == 0)
                    break;

                TableLayout searchResults = findViewById(R.id.searchResults);
                searchResults.setVisibility(View.VISIBLE);

                Spotify.searchSongs(s1, this);
                break;
            case R.id.tableRow0:
                nextPage(0);
                break;
            case R.id.tableRow1:
                nextPage(1);
                break;
            case R.id.tableRow2:
                nextPage(2);
                break;
            case R.id.tableRow3:
                nextPage(3);
                break;
            case R.id.tableRow4:
                nextPage(4);
                break;
            case R.id.tableRow5:
                nextPage(5);
                break;
            case R.id.tableRow6:
                nextPage(6);
                break;
            case R.id.tableRow7:
                nextPage(7);
                break;
            case R.id.tableRow8:
                nextPage(8);
                break;
            case R.id.tableRow9:
                nextPage(9);
                break;
            default:
                break;
        }

    }

    private void nextPage(int num) {
        Spotify.addSong(songs[num], 1);
        Swipe.setup();
        Intent intent2 = new Intent(this, Swipe.class);
        ProgressBar prog = findViewById(R.id.newLoadBar);
        prog.setVisibility(View.VISIBLE);

        View songView = findViewById(R.id.searchSongsView);
        songView.setVisibility(View.GONE);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("This code is outside of the thread");
                startActivity(intent2);
                finish();
            }
        });
        thread.start();
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