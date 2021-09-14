package me.harishpartha.spotifyswiper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Spotify {

    public static void setmSpotifyAppRemote(SpotifyAppRemote mSpotifyAppRemote) {
        Spotify.mSpotifyAppRemote = mSpotifyAppRemote;
    }

    private static SpotifyAppRemote mSpotifyAppRemote;

    private static OkHttpClient client = new OkHttpClient();

    private static boolean apiConnection = false;
    private static boolean sdkConnection = false;
    private static String userId = "";
    private static String playListId = "";

    private static String accessToken = "";

    private static JSONObject jObject;
    private static String jString = "";

    private static Queue<String> queue = new PriorityQueue<String>();

    public static boolean isSdkConnection() {
        return sdkConnection;
    }

    public static void setSdkConnection(boolean sdkConnection) {
        Spotify.sdkConnection = sdkConnection;
    }

    public static boolean isApiConnection() {
        return apiConnection;
    }

    public static void setApiConnection(boolean apiConnection) {
        Spotify.apiConnection = apiConnection;
    }


    public static SpotifyAppRemote getmSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    public static void setAccessToken(String s) {
        accessToken = s;
    }

    public static void getUser() {
        Log.d("spotify", "getting user");
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url("https://api.spotify.com/v1/me")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("smoge","smoge");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    jString = response.body().string();
                    Log.d("yay0", "id" + jString);
                    try {
                        jObject = new JSONObject(jString);
                        userId = jObject.getString("id");
                        Log.d("JSON", "id " + userId);

                    } catch (JSONException e) {
                        Log.e("JSON", "failed to make json" + e);
                    }
                }
            }
        });

    }

    public static void createPlaylist(String s) {

        Log.d("spotify", "creating playlist");

        String postBody = "{\n" +
                " \"name\": \"" + s + "\"\n" +
                "}";
        Log.d("postBody", postBody);


        RequestBody body = RequestBody.create(postBody, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url("https://api.spotify.com/v1/users/" + userId + "/playlists")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("smoge","smoge");
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    jString = response.body().string();
                    Log.d("yay0", "id" + jString);
                    try {
                        jObject = new JSONObject(jString);
                        playListId = jObject.getString("id");
                        Log.d("JSON", "id " + playListId);

                    } catch (JSONException e) {
                        Log.e("JSON", "failed to make json" + e);
                    }
                }
            }
        });



    }

    public static void searchSongs(String s, Context context) {

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url("https://api.spotify.com/v1/search?q=" + s + "&type=track&limit=10")
                .build();

        Log.d("request", request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("smoge","smoge");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    jString = response.body().string();
                    try {
                        jObject = new JSONObject(jString);
                        NewPlaylist.setImages(jObject, context);
                      //  NewPlaylist.changeImages();

                    } catch (JSONException e) {
                        Log.e("JSON", "search failed to make json" + e);
                    }

                }
            }
        });
        Log.d("yay4", "search" + jObject.toString());

    }

    public static void addSong(String track, Context context) {

        RequestBody body = RequestBody.create("", null);

        String trackSplice = track.substring(14);

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url("https://api.spotify.com/v1/playlists/" + playListId + "/tracks?uris=spotify%3Atrack%3A" + trackSplice)
                .post(body)
                .build();

        Log.e("addSong", request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("smoge","smoge");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    jString = response.body().string();
                    try {
                        jObject = new JSONObject(jString);
                        Log.d("JSON", "addSong " + jString);
                        queue.add(trackSplice);
                        getRecommendations();
                    } catch (JSONException e) {
                        Log.e("JSON", "search failed to make json" + e);
                    }

                }
            }
        });
    }

    public static void getRecommendations() {

        String url = "https://api.spotify.com/v1/recommendations?limit=100&seed_tracks=";

        boolean first = true;

        Iterator iterator = queue.iterator();
        while(iterator.hasNext()) {
            if (first) {
                url += iterator.next();
                first = false;
            }
            else
                url += "%2C" + iterator.next();
        }

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("smoge","smoge");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    jString = response.body().string();
                    try {
                        jObject = new JSONObject(jString);
                        Log.d("JSON", "recommendations " + jString);
                    } catch (JSONException e) {
                        Log.e("JSON", "search failed to make json" + e);
                    }

                }
            }
        });

    }
}

