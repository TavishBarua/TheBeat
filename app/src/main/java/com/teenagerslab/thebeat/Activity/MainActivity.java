package com.teenagerslab.thebeat.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidadvance.topsnackbar.TSnackbar;
import com.rey.material.widget.SnackBar;

import com.teenagerslab.thebeat.Adapter.CustomMenuAdapter;
import com.teenagerslab.thebeat.Adapter.songAdapter;
import com.teenagerslab.thebeat.CustomMethods.CustomMenu.SmartMenu;
import com.teenagerslab.thebeat.CustomMethods.TextLatoThin;
import com.teenagerslab.thebeat.Helpers.RetrieveImageTask;
import com.teenagerslab.thebeat.Helpers.Utilities;
import com.teenagerslab.thebeat.Interfaces.ItemEventListener;
import com.teenagerslab.thebeat.R;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.habets.lastfmfetcher.Artist;
import io.habets.lastfmfetcher.LastFM;

public class MainActivity extends AppCompatActivity implements OnCompletionListener,SeekBar.OnSeekBarChangeListener {

  private ImageButton btnPlay;
  private ImageButton btnPrev;
  private ImageButton btnNext;
  private ImageButton btnRepeat;
  private ImageButton btnShuffle;
  private ImageView imageViewRound;
  Handler seekHandler = new Handler();
  private MediaPlayer mp;
  private songAdapter songAdapter;
  public PlaylistActivity songLength;
  private TextView songCurrentDurationLabel;
  private TextView songTotalDurationLabel;
  private TextView songNameLabel;
  private Utilities utils;
  private SeekBar seek_bar;
  private ImageButton btnPlaylist;
  private TextLatoThin textLato;
  SnackBar mSnackBar;
  private ImageButton beatDropBoost;


  private LinearLayout rooView;

  private RelativeLayout activitymain_relativeLayout;

  private int currentSongIndex = 0;
  //public int songLen=0;
  private boolean isShuffle = false;
  private boolean isRepeat = false;
  private boolean isBeatBoost = false;
  private boolean isRepeatAll = false;
  //public static final int REPEAT_ALL = 2;

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




   activitymain_relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);


   // mSnackBar = (SnackBar)findViewById(R.id.main_sn);






  //  Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.default_img);


   // imageViewRound.setImageBitmap(icon);

    getInit();

    mp = new MediaPlayer();
    songAdapter = new songAdapter(MainActivity.this);
    utils = new Utilities();
    textLato = new TextLatoThin(this);
    mSnackBar= new SnackBar(this);



    //songLength = new PlaylistActivity();


    seek_bar.setOnSeekBarChangeListener(this);
    mp.setOnCompletionListener(this);


    songsList = songAdapter.getPlayList(this);


   // songlen();


    songNameLabel.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    songNameLabel.setMarqueeRepeatLimit(1000);
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
        Intent i = new Intent(getApplicationContext(), BeatDropMusicLibrary.class);
        startActivityForResult(i, 100);
      }
    });

    btnRepeat.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if(isRepeat) {
          isRepeat = false;
        //  Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
          TSnackbar snackbar = TSnackbar
              .make(activitymain_relativeLayout, "No-repeat selected", TSnackbar.LENGTH_LONG);
          View snackbarView = snackbar.getView();
          Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
              R.anim.alerter_slide_in_from_top);
          snackbarView.startAnimation(animation);
          snackbarView.setBackgroundColor(Color.parseColor("#252525"));
          snackbarView.getBackground().setAlpha(110);
          snackbar.setMaxWidth(2000);
          TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
          textView.setTextColor(Color.WHITE);
          textView.setGravity(Gravity.CENTER_HORIZONTAL);
          Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
          textView.setTypeface(font);
          snackbar.show();
         /* Alerter.create(MainActivity.this).setContentGravity(1)
              .setText("No-repeat selected").hideIcon().setBackgroundColor(R.color.transparent_color_black)
              .show(); */
          btnRepeat.setImageResource(R.drawable.btn_repeat);
        }


        else{
          // make repeat to true
          isRepeat = true;
         // Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
          // make shuffle to false

          TSnackbar snackbar = TSnackbar
              .make(activitymain_relativeLayout, "Repeat-one Selected", TSnackbar.LENGTH_LONG);
          View snackbarView = snackbar.getView();
          Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
              R.anim.alerter_slide_in_from_top);
          snackbarView.startAnimation(animation);
          snackbarView.setBackgroundColor(Color.parseColor("#252525"));
          snackbarView.getBackground().setAlpha(110);
          snackbar.setMaxWidth(2000);
          TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
          textView.setTextColor(Color.WHITE);
          textView.setGravity(Gravity.CENTER_HORIZONTAL);
          Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
          textView.setTypeface(font);
          snackbar.show();

          /*Alerter.create(MainActivity.this).setContentGravity(1)
              .setText("Repeat-one Selected").hideIcon().setBackgroundColor(R.color.transparent_color_black)
              .show(); */
          isShuffle = false;
          btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
          btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
      }
    });

    btnShuffle.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if(isShuffle){
          isShuffle = false;
        //  Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();

         /* mSnackBar
              .actionText(R.string.ShuffleOff)
              .textColor(android.R.color.white)
              .show(); */


         /* Alerter.create(MainActivity.this).setContentGravity(1)
              .setText("Shuffle OFF").hideIcon().setBackgroundColor(R.color.transparent_color_black)
              .show(); */

          TSnackbar snackbar = TSnackbar
              .make(activitymain_relativeLayout, "Shuffle OFF", TSnackbar.LENGTH_LONG);
          View snackbarView = snackbar.getView();
          Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
              R.anim.alerter_slide_in_from_top);
          snackbarView.startAnimation(animation);
          snackbarView.setBackgroundColor(Color.parseColor("#252525"));
          snackbarView.getBackground().setAlpha(110);
          snackbar.setMaxWidth(2000);
          TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
          textView.setTextColor(Color.WHITE);
          textView.setGravity(Gravity.CENTER_HORIZONTAL);
          Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
          textView.setTypeface(font);
          snackbar.show();

          btnShuffle.setImageResource(R.drawable.btn_shuffle);
          /*mSnackBar=SnackBar.make(context).applyStyle(R.style.SnackBarShuffleOff);
          mSnackBar.singleLine(false).backgroundColor(Color.parseColor("#5A5A5A")).textColor(Color.parseColor("#ABABAB")).duration(5000);
        */

        //  initSnackBar(getBaseContext());


         /* Snackbar mSnackBar = Snackbar.make(activitymain_relativeLayout, "Shuffle is OFF", Snackbar.LENGTH_LONG);
          View view = mSnackBar.getView();
          FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
          params.gravity = Gravity.TOP;
          view.setLayoutParams(params);
          TextView mainTextView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
// To Apply Custom Fonts for Message and Action
          Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Thin.ttf");
          mainTextView.setTypeface(font);
          mSnackBar.show(); */
         // mSnackBar.applyStyle(R.style.SnackBarShuffleOff);
        //  mSnackBar.show();


        }else{
          // make repeat to true
          isShuffle= true;
         // Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
          // make shuffle to false

          /*Alerter.create(MainActivity.this).setContentGravity(1)
              .setText("Shuffle ON").hideIcon().setBackgroundColor(R.color.transparent_color_black)
              .show(); */

          TSnackbar snackbar = TSnackbar
              .make(activitymain_relativeLayout, "Shuffle ON", TSnackbar.LENGTH_LONG);
          View snackbarView = snackbar.getView();
          Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
              R.anim.alerter_slide_in_from_top);
          snackbarView.startAnimation(animation);
          snackbarView.setBackgroundColor(Color.parseColor("#252525"));
          snackbarView.getBackground().setAlpha(110);
          snackbar.setMaxWidth(2000);
          TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
          textView.setTextColor(Color.WHITE);
          textView.setGravity(Gravity.CENTER_HORIZONTAL);
          Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
          textView.setTypeface(font);
          snackbar.show();
          isRepeat = false;
          btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
          btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
      }
    });

    SmartMenu smartMenu = (SmartMenu) findViewById(R.id.smart_menu);
    final CustomMenuAdapter adapter = new CustomMenuAdapter();

    adapter.setListener(new ItemEventListener() {
      @Override
      public void onEventNotify(View view, int position, Object... data) {
        switch (position) {
          case 0:
           // toast("ALBUM");
            Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
            startActivityForResult(i, 100);
            break;
          case 1:
            toast("COMMENT");
            break;
          case 2:
            int[] img = adapter.getImages();
            ImageView imag = (ImageView) view.findViewById(R.id.image_view);


            if(isBeatBoost) {
              isBeatBoost=false;

              TSnackbar snackbar = TSnackbar
                  .make(activitymain_relativeLayout, "BEATBOOST Deactivated", TSnackbar.LENGTH_LONG);
              View snackbarView = snackbar.getView();
              Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                  R.anim.alerter_slide_in_from_top);
              snackbarView.startAnimation(animation);
              snackbarView.setBackgroundColor(Color.parseColor("#252525"));
              snackbarView.getBackground().setAlpha(110);
              snackbar.setMaxWidth(2000);
              TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
              textView.setTextColor(Color.WHITE);
              textView.setGravity(Gravity.CENTER_HORIZONTAL);
              Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
              textView.setTypeface(font);
              snackbar.show();
              imag.setImageResource(img[2]);
            }
            else{
              isBeatBoost=true;

              TSnackbar snackbar = TSnackbar
                  .make(activitymain_relativeLayout, "BEATBOOST Activated", TSnackbar.LENGTH_LONG);
              View snackbarView = snackbar.getView();
              Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                  R.anim.alerter_slide_in_from_top);
              snackbarView.startAnimation(animation);
              snackbarView.setBackgroundColor(Color.parseColor("#252525"));
              snackbarView.getBackground().setAlpha(110);
              snackbar.setMaxWidth(2000);
              TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
              textView.setTextColor(Color.WHITE);
              textView.setGravity(Gravity.CENTER_HORIZONTAL);
              Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
              textView.setTypeface(font);
              snackbar.show();
              imag.setImageResource(img[4]);

            }
            break;
          case 3:
            toast("LIKE");
            break;
        }
      }
    });
    smartMenu.setAdapter(adapter);




  }

  private void toast(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }


  public void getInit() {
    seek_bar = (SeekBar) findViewById(R.id.seekBar1);
   // player = MediaPlayer.create(this, R.raw.sample);
    btnNext=(ImageButton) findViewById(R.id.btnNext);
    btnPrev=(ImageButton) findViewById(R.id.btnPrev);
    btnRepeat=(ImageButton) findViewById(R.id.btnRepeat);
    btnShuffle=(ImageButton) findViewById(R.id.btnShuffle);
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

 /* public void showAlbumArt(int index){
    try {

     String AlbumArt = songsList.get(index).get("albumArt");
      Drawable img = Drawable.createFromPath(AlbumArt);
      imageViewRound.setImageDrawable(img);

    }catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  } */

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





     // showAlbumArt(songIndex);

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



 /*public void songlen(){

    songLen=songLength.getSongLength();

    Toast.makeText(getApplicationContext(), "Total number of Items are:" + songLen , Toast.LENGTH_LONG).show();
  } */




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
  public void onCompletion(MediaPlayer mp) {
    if(isRepeat){
      // repeat is on play same song again
      playSong(currentSongIndex);
    } else if(isShuffle){
      // shuffle is on - play a random song
      Random rand = new Random();
      currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
      playSong(currentSongIndex);
    } else{
      // no repeat or shuffle ON - play next song
      if(currentSongIndex < (songsList.size() - 1)){
        playSong(currentSongIndex + 1);
        currentSongIndex = currentSongIndex + 1;
      }else{
        // play first song
        playSong(0);
        currentSongIndex = 0;
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    seekHandler.removeCallbacks(run);
    mp.release();
  }
}
