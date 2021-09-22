package me.harishpartha.spotifyswiper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LaunchPage extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 420;
    private static final String CLIENT_ID = "452852792dc248aaaa6a3c9b25301e46";
    private static final String REDIRECT_URI = "https://me.harishpartha.spotifyswiper/callback";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page);

        Button spotSignIn = findViewById(R.id.spotSignIn);
        spotSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sdkConnect();
    }

    public void sdkConnect() {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        Spotify.setmSpotifyAppRemote(spotifyAppRemote);
                        Log.d("MainActivity", "Connected! Yay!");
                        apiConnect();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);
                    }
                });
    }

    public void apiConnect() {
        Log.d("spotuf", "in apiconnect");
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming playlist-modify-public "});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Log.d("on", "activityresulkt");
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.d("Main Activity", "Yay logged in" + response.getAccessToken());
                    Spotify.setAccessToken(response.getAccessToken());
                    Spotify.setApiConnection(true);
                    Spotify.getUser();
                    Intent intent2 = new Intent(this, ActionSelection.class);
                    startActivity(intent2);
                    finish();

                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.e("Main Activity", "Could not log in");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }
}