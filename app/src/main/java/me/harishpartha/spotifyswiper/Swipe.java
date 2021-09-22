package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;

public class Swipe extends AppCompatActivity {

    private static String[] recommendationsName = new String[100];
    private static String[] recommendationsCover = new String[100];
    private static String[] recommendationsArtist = new String[100];
    private static String[] recommendationsAlbum = new String[100];
    private static String[] recommendationsURL = new String[100];

    public static boolean loadingDone = false;

    private SwipeDeck cardStack;
    private ArrayList<ImagesModal> songCardArray;

    public static void setup() {

        recommendationsName = Spotify.getRecommendationsName();
        recommendationsCover = Spotify.getRecommendationsCover();
        recommendationsArtist = Spotify.getRecommendationsArtist();
        recommendationsURL = Spotify.getRecommendationsURL();
        recommendationsAlbum = Spotify.getRecommendationsAlbum();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        Log.e("swipe hello", "zz");

        Log.d("ok", "oi");

        songCardArray = new ArrayList<>();
        cardStack = findViewById(R.id.swipe_deck);

        for (int i = 0; i < Spotify.getRecommendationsSize(); i++) {
            songCardArray.add(new ImagesModal(recommendationsName[i], recommendationsAlbum[i], recommendationsArtist[i], recommendationsCover[i]));
            Log.e("song", recommendationsURL[i]);
        }

        Spotify.playSong(recommendationsURL[0]);

        final SongStack adapter = new SongStack(songCardArray, this);

        cardStack.setAdapter(adapter);

        loadingDone = true;

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                if (position <= 98) {
                    Spotify.playSong(recommendationsURL[position + 1]);
                    Log.e("song", recommendationsURL[position + 1]);
                }
            }

            @Override
            public void cardSwipedRight(int position) {
                if (position <= 98) {
                    Spotify.playSong(recommendationsURL[position + 1]);
                    Log.e("song", recommendationsURL[position + 1]);

                }
                Spotify.addSong(recommendationsURL[position], 0);
            }

            @Override
            public void cardsDepleted() {
                Log.e("End", "At end of stack");
                done();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swipped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void done () {
        TextView doneView = findViewById(R.id.finishMessage);
        doneView.setVisibility(View.VISIBLE);
    }
}