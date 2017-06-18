package com.teenagerslab.thebeat.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teenagerslab.thebeat.R;

/**
 * Created by tavish on 6/17/2017.
 */

public class MyFilesFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_myfiles, container, false);
        return rootView;
    }
}
