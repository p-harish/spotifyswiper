package me.harishpartha.spotifyswiper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.Context;

import com.bumptech.glide.Glide;

public class SongStack extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<ImagesModal> songs;
    private Context context;

    // on below line we have created constructor for our variables.
    public SongStack(ArrayList<ImagesModal> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        ((TextView) v.findViewById(R.id.cardSongName)).setText(songs.get(position).getsongName());
        ((TextView) v.findViewById(R.id.cardAlbumName)).setText(songs.get(position).getalbumName());
        ((TextView) v.findViewById(R.id.cardArtistName)).setText(songs.get(position).getartistName());

        Glide.with(context).load(songs.get(position).getImgUrl()).into((ImageView) v.findViewById(R.id.cardImage));
        return v;
    }
}