package com.teenagerslab.thebeat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.teenagerslab.thebeat.R;

/**
 * Created by tavish on 6/16/2017.
 */

public class ArtistsFragment extends android.support.v4.app.Fragment {

    public ArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_artists, container, false);
        return rootView;



    }

  /*  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        TextView title = (TextView) view.findViewById(R.id.item_title);
        title.setText(String.valueOf(position));

    } */
}
