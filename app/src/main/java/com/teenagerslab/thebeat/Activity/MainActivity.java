package com.teenagerslab.thebeat.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.teenagerslab.thebeat.Adapter.songAdapter;
import com.teenagerslab.thebeat.Helpers.Utilities;
import com.teenagerslab.thebeat.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

  private ImageButton btnPlay;
  private ImageButton btnPrev;
  private ImageButton btnNext;
  private ImageView imageViewRound;
  Handler seekHandler = new Handler();
  private MediaPlayer mp;
  private songAdapter songAdapter;
  private TextView songCurrentDurationLabel;
  private TextView songTotalDurationLabel;
  private TextView songNameLabel;
  private Utilities utils;
  private SeekBar seek_bar;
  private ImageButton btnPlaylist;

  private int currentSongIndex = 0;

  static final String KEY_ARTIST = "artist";
  static final String KEY_SONG = "song";
  private static final int PERMISSION_REQUEST_CODE = 1;



  private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*songParser parser = new songParser();
    String xml = parser.getXmlFromUrl(URL);
    Document doc = parser.getDomElement(xml);

    NodeList nl = doc.getElementsByTagName(KEY_SONG); */




    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.default_img);

   // imageViewRound.setImageBitmap(icon);

    getInit();

    mp = new MediaPlayer();
    songAdapter = new songAdapter(MainActivity.this);
    utils = new Utilities();

    seek_bar.setOnSeekBarChangeListener(this);

    songsList = songAdapter.getPlayList(this);


    songNameLabel.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    songNameLabel.setMarqueeRepeatLimit(500);
    songNameLabel.setSelected(true);

    playSong(0);

    btnPlay.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // check for already playing
        if(mp.isPlaying()){
          if(mp!=null){
            mp.pause();
            // Changing button image to play button
            btnPlay.setImageResource(R.drawable.btn_play);
          }
        }else{
          // Resume song
          if(mp!=null){
            mp.start();
            // Changing button image to pause button
            btnPlay.setImageResource(R.drawable.btn_pause);
          }
        }

      }
    });


    btnNext.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        // check if next song is there or not
        if(currentSongIndex < (songsList.size() - 1)){
          playSong(currentSongIndex + 1);
          currentSongIndex = currentSongIndex + 1;
        }else{
          // play first song
          playSong(0);
          currentSongIndex = 0;
        }

      }
    });
    btnPrev.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if(currentSongIndex > 0){
          playSong(currentSongIndex - 1);
          currentSongIndex = currentSongIndex - 1;
        }else{
          // play last song
          playSong(songsList.size() - 1);
          currentSongIndex = songsList.size() - 1;
        }

      }
    });

    btnPlaylist.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
        startActivityForResult(i, 100);
      }
    });






  }


  public void getInit() {
    seek_bar = (SeekBar) findViewById(R.id.seekBar1);
   // player = MediaPlayer.create(this, R.raw.sample);
    btnNext=(ImageButton) findViewById(R.id.btnNext);
    btnPrev=(ImageButton) findViewById(R.id.btnPrev);
    imageViewRound = (ImageView) findViewById(R.id.imageView_round);
    btnPlay = (ImageButton) findViewById(R.id.btnPlay);
    songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
    songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
    songNameLabel = (TextView) findViewById(R.id.songName);
    btnPlaylist = (ImageButton) findViewById(R.id.settings);
    seek_bar.setOnSeekBarChangeListener(this);
   // seek_bar.setMax(player.getDuration());


  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == 100) {
      currentSongIndex = data.getExtras().getInt("songIndex");
      // play selected song
      playSong(currentSongIndex);
    }
  }

  public void  playSong(int songIndex){
    // Play song
    try {
      mp.reset();
      mp.setDataSource(songsList.get(songIndex).get("songPath"));
      mp.prepare();
      mp.start();
      // Displaying Song title
      String songTitle = songsList.get(songIndex).get("songTitle");
      songNameLabel.setText(songTitle);

      // Changing Button Image to pause image
      btnPlay.setImageResource(R.drawable.btn_pause);

      // set Progress bar values
      seek_bar.setProgress(0);
      seek_bar.setMax(100);

      // Updating progress bar
      updateProgressBar();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  public void updateProgressBar() {
    seekHandler.postDelayed(run, 100);
  }

  Runnable run = new Runnable() {

    @Override
    public void run() {

      long totalDuration = mp.getDuration();
      long currentDuration = mp.getCurrentPosition();

      // Displaying Total Duration time
      songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
      // Displaying time completed playing
      songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

      // Updating progress bar
      int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
      //Log.d("Progress", ""+progress);
      seek_bar.setProgress(progress);
      seekHandler.postDelayed(this, 100);
    }

  };


  /*private void requestPermission() {

    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
      Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
    } else {
      ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Log.e("value", "Permission Granted, Now you can use local drive .");
          // Code for Below 23 API Oriented Device
          this.songsList = plm.getPlayList();
        } else {
          Log.e("value", "Permission Denied, You cannot use local drive .");
        }
        break;
    }
  }*/


  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    seekHandler.removeCallbacks(run);
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    seekHandler.removeCallbacks(run);
    int totalDuration = mp.getDuration();
    int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

    // forward or backward to certain seconds
    mp.seekTo(currentPosition);

    // update timer progress again
    updateProgressBar();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    seekHandler.removeCallbacks(run);
    mp.release();
  }
}
