package com.teenagerslab.thebeat.Activity;


import android.os.Bundle;

import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import com.teenagerslab.thebeat.Fragments.AlbumsFragment;
import com.teenagerslab.thebeat.Fragments.ArtistsFragment;
import com.teenagerslab.thebeat.Fragments.GenresFragment;
import com.teenagerslab.thebeat.Fragments.MyFilesFragment;
import com.teenagerslab.thebeat.Fragments.PlaylistsFragment;
import com.teenagerslab.thebeat.Fragments.SongsFragment;
import com.teenagerslab.thebeat.R;




/**
 * Created by tavish on 6/14/2017.
 */

public class BeatDropMusicLibrary extends AppCompatActivity {


    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.beatdrop_music_library);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);




       FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.demo_beatdrop_tab_artists, ArtistsFragment.class)
                .add(R.string.demo_beatdrop_tab_playlists, PlaylistsFragment.class)
                .add(R.string.demo_beatdrop_tab_albums, AlbumsFragment.class)
                .add(R.string.demo_beatdrop_tab_myfiles, MyFilesFragment.class)
                .add(R.string.demo_beatdrop_tab_genres, GenresFragment.class)
                .add(R.string.demo_beatdrop_tab_songs, SongsFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);





    }





}
