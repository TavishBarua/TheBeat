package com.teenagerslab.thebeat.Adapter;

/**
 * Created by tavish on 5/28/2017.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;
import android.text.format.DateUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class songAdapter {
  // SDCard Path
  //final String MEDIA_PATH = new String("/sdcard/");

  static final String KEY_DURATION = MediaStore.Audio.Media.DURATION;
  long myNum = 0;
  private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
  Cursor cursor;
  private Context context;
  // Constructor
  public songAdapter(Context context){
    this.context = context;
  }



  /*public ArrayList<HashMap<String, String>> getPlayList(){

    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";



    String[] projection = {
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DISPLAY_NAME,
       // MediaStore.Audio.Media.DURATION
    };
    String songs_duration = "";




    ContentResolver contentResolver = context.getContentResolver();
    cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection, selection,null,null);

    myNum = Long.parseLong(KEY_DURATION);

    while(cursor.moveToNext()) {
      HashMap<String, String> song = new HashMap<String, String>();
      song.put("songID",cursor.getString(0));
      song.put("artist",cursor.getString(1));
      song.put("songTitle",cursor.getString(2));
      song.put("songPath",cursor.getString(3));
      song.put("duration1",myNum);
     // song.put("duration",cursor.getString(5));


      // Adding each song to SongList
      songsList.add(song);
    }
    // return songs list array
    return songsList;
  }

*/

  public ArrayList<HashMap<String, String>> getPlayList(Context c) {

    String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";

    /*use content provider to get beginning of database query that queries for all audio by display name, path
    and mimtype which i dont use but got it incase you want to scan for mp3 files only you can compare with RFC  mimetype for mp3's
    */
    final Cursor mCursor = c.getContentResolver().query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        new String[] { MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.MIME_TYPE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.DURATION }, null, null,
        "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

    String songs_title = "";
    String mAudioPath = "";
    String songs_artist = "";
    String songs_album = "";
    String songs_duration = "";


    if (mCursor.moveToFirst()) {
    do {

      String file_type = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));

      songs_title = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
      mAudioPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
      songs_artist = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
      songs_album = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
      songs_duration = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
      HashMap<String, String> song = new HashMap<String, String>();


      if(String.valueOf(songs_duration) != null){
        try{
          Long time = Long.valueOf(songs_duration);
          long seconds = time/1000;
          long minutes = seconds/60;
          seconds = seconds % 60;

          if(seconds<10){
            String csongs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
            song.put("songDuration", csongs_duration);
          }else{
            String ccsongs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
            song.put("songDuration", ccsongs_duration);
          }
        }catch(NumberFormatException e){
          song.put("songDuration", songs_duration);
        }
      }else{
        String nothing = "0";
        song.put("songDuration", nothing);
      }

      song.put("songTitle", songs_title);
      song.put("songPath", mAudioPath);
      song.put("songArtist", songs_artist);
      song.put("songAlbum", songs_album);

      songsList.add(song);

    } while (mCursor.moveToNext());
  }

  mCursor.close(); //cursor has been consumed so close it
  return songsList;
}


  /**
   * Function to read all mp3 files from sdcard
   * and store the details in ArrayList
   * */
 /* public ArrayList<HashMap<String, String>> getPlayList(){
    File home = new File(MEDIA_PATH);

    if (home.listFiles(new FileExtensionFilter()).length > 0) {
      for (File file : home.listFiles(new FileExtensionFilter())) {
        HashMap<String, String> song = new HashMap<String, String>();
        song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
        song.put("songPath", file.getPath());

        // Adding each song to SongList
        songsList.add(song);
      }
    }
    // return songs list array
    return songsList;
  }

  /**
   * Class to filter files which are having .mp3 extension
   *
  class FileExtensionFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
      return (name.endsWith(".mp3") || name.endsWith(".MP3"));
    }
  }*/
}