package com.teenagerslab.thebeat.Activity;

import static com.teenagerslab.thebeat.R.id.duration;
import static com.teenagerslab.thebeat.R.id.songTotalDurationLabel;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.teenagerslab.thebeat.Adapter.songAdapter;
import com.teenagerslab.thebeat.R;
import com.teenagerslab.thebeat.Views.DurationView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tavish on 5/29/2017.
 */

public class PlaylistActivity extends ListActivity{

  public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
  TextView tx;
  private MediaPlayer mp1;
  private ListView lv;
  //public int len=0;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.playlist);

    ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

    songAdapter plm = new songAdapter(PlaylistActivity.this);
    // get all songs from sdcard
    this.songsList = plm.getPlayList(this);

   // getSongLength();



    // looping through playlist
    for (int i = 0; i < songsList.size(); i++) {
      // creating new HashMap
      HashMap<String, String> song = songsList.get(i);

      // adding HashList to ArrayList
      songsListData.add(song);

    }




      // Adding menuItems to ListView
    ListAdapter adapter = new SimpleAdapter(this, songsListData,
        R.layout.item_playlist, new String[] { "songTitle","songArtist","songDuration" }, new int[] {
        R.id.songTitle,R.id.artist, duration });

    setListAdapter(adapter);

    // selecting single ListView item
    lv = getListView();


   int len=lv.getAdapter().getCount();

   Toast.makeText(getApplicationContext(), "Total number of Items are:" + lv.getAdapter().getCount() , Toast.LENGTH_LONG).show();
    // listening to single listitem click
    lv.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view,
          int position, long id) {
        // getting listitem index
        int songIndex = position;

        // Starting new intent
        Intent in = new Intent(getApplicationContext(),
            MainActivity.class);
        // Sending songIndex to PlayerActivity
        in.putExtra("songIndex", songIndex);
        setResult(100, in);
        // Closing PlayListView
        finish();
      }
    });

  }

  Runnable run1 = new Runnable() {
    @Override
    public void run() {

      long totalDuration = mp1.getDuration();


    }
  };

 /* public int getSongLength(){
     len=lv.getCount();
    return len;

  } */


}
