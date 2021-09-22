package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;

public class Swipe extends AppCompatActivity {

    private static String[] recommendationsName = new String[100];
    private static String[] recommendationsCover = new String[100];
    private static String[] recommendationsArtist = new String[100];
    private static String[] recommendationsURL = new String[100];

    public static boolean loadingDone = false;

    private SwipeDeck cardStack;
    private ArrayList<ImagesModal> courseModalArrayList;

    public static void setup() {

        recommendationsName = Spotify.getRecommendationsName();
        recommendationsCover = Spotify.getRecommendationsCover();
        recommendationsArtist = Spotify.getRecommendationsArtist();
        recommendationsURL = Spotify.getRecommendationsURL();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        Log.e("swipe hello", "zz");

        Log.d("ok", "oi");

        // on below line we are initializing our array list and swipe deck.
        courseModalArrayList = new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        // on below line we are adding data to our array list.
        for (int i = 0; i < Spotify.getRecommendationsSize(); i++) {
            courseModalArrayList.add(new ImagesModal(recommendationsName[i], recommendationsArtist[i], "20 Tracks", "C++ Self Paced Course", recommendationsCover[i]));
            Log.e("song", recommendationsURL[i]);
        }


        Spotify.playSong(recommendationsURL[0]);

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(courseModalArrayList, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);

        loadingDone = true;

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Spotify.playSong(recommendationsURL[position + 1]);
                // on card swipe left we are displaying a toast message.
                Toast.makeText(Swipe.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
                Log.e("song", recommendationsURL[position + 1]);

            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swipped to right we are displaying a toast message.
                Toast.makeText(Swipe.this, "Card Swiped Right" + position, Toast.LENGTH_SHORT).show();
                Spotify.playSong(recommendationsURL[position + 1]);
                Spotify.addSong(recommendationsURL[position]);
                Log.e("song", recommendationsURL[position + 1]);

            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(Swipe.this, "No more courses present", Toast.LENGTH_SHORT).show();
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
}