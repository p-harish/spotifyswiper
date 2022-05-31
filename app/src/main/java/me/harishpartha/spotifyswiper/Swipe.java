package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Swipe extends AppCompatActivity {

    private static Deque<Recommendation> recommendations;
    public static boolean loadingDone = false;

    private SwipeDeck cardStack;
    private ArrayList<ImagesModal> songCardArray;

    public static boolean refreshStack = false;

    public static void setup() {
        recommendations = new LinkedList<>(Arrays.asList(Spotify.getRecommendationsArray()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        songCardArray = new ArrayList<>();
        cardStack = findViewById(R.id.swipe_deck);

        Recommendation[] spotifyArray = Spotify.getRecommendationsArray();

        for (int i = 0; i < Spotify.getRecommendationsFilled(); i++) {
            Recommendation r = spotifyArray[i];
            songCardArray.add(new ImagesModal(r.getName(), r.getAlbum(), r.getArtist(), r.getCover()));
            Log.e("song", r.getUrl());
        }

        Spotify.playSong(recommendations.peek().getUrl());

        final SongStack adapter = new SongStack(songCardArray, this);

        cardStack.setAdapter(adapter);

        loadingDone = true;

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                recommendations.pop();
                Spotify.playSong(recommendations.peek().getUrl());
                Log.e("song", recommendations.peek().getUrl());
                if (refreshStack)
                    updateRecommendationStack();
            }

            @Override
            public void cardSwipedRight(int position) {
                Spotify.addSong(recommendations.peek().getUrl());
                recommendations.pop();
                Spotify.playSong(recommendations.peek().getUrl());
                Log.e("song", recommendations.peek().getUrl());
                Spotify.getFreshRecommendations();
                if (refreshStack)
                    updateRecommendationStack();
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

    private void updateRecommendationStack() {
//        ArrayList<ImagesModal> songCardArrayNew = new ArrayList<>();
//        Deque<Recommendation> recommendationsNew = recommendations;
//
//        Recommendation[] spotifyArray = Spotify.getRecommendationsArray();
//        for (int i = 0; i < Spotify.getRecommendationsFilled(); i++) {
//            Recommendation r = spotifyArray[i];
//            songCardArray.add(new ImagesModal(r.getName(), r.getAlbum(), r.getArtist(), r.getCover()));
//            Log.e("song", r.getUrl());
//        }
//
//        final SongStack adapter = new SongStack(songCardArray, this);
//        cardStack.setAdapter(adapter);
//        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
//            @Override
//            public void cardSwipedLeft(int position) {
//                recommendations.pop();
//                Spotify.playSong(recommendations.peek().getUrl());
//                Log.e("song", recommendations.peek().getUrl());
//                if (refreshStack)
//                    updateRecommendationStack();
//            }
//
//            @Override
//            public void cardSwipedRight(int position) {
//                Spotify.addSong(recommendations.peek().getUrl());
//                recommendations.pop();
//                Spotify.playSong(recommendations.peek().getUrl());
//                Log.e("song", recommendations.peek().getUrl());
//                Spotify.getFreshRecommendations();
//                if (refreshStack)
//                    updateRecommendationStack();
//            }
//
//            @Override
//            public void cardsDepleted() {
//                Log.e("End", "At end of stack");
//                done();
//            }
//
//            @Override
//            public void cardActionDown() {
//                // this method is called when card is swipped down.
//                Log.i("TAG", "CARDS MOVED DOWN");
//            }
//
//            @Override
//            public void cardActionUp() {
//                // this method is called when card is moved up.
//                Log.i("TAG", "CARDS MOVED UP");
//            }
//        });


    }
}