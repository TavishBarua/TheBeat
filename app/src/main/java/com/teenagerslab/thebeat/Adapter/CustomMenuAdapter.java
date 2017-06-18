package com.teenagerslab.thebeat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.teenagerslab.thebeat.Interfaces.ItemEventListener;
import com.teenagerslab.thebeat.R;

/**
 * Created by tavish on 6/11/2017.
 */

public class CustomMenuAdapter extends BaseAdapter implements View.OnClickListener{

  public boolean isBeatBoost = false;

  public int[] getImages() {
    return images;
  }

  public void setImages(int[] images) {
    this.images = images;
  }

  private int[] images = new int[]{R.drawable.now_playing_icn,
      R.drawable.equalizer_icn,
      R.drawable.beatdrop_icn,
      R.drawable.settings_icn,
      R.drawable.beatdrop_focussed_icn};
  private ItemEventListener listener;

  public void setListener(ItemEventListener listener) {
    this.listener = listener;
  }

  @Override
  public int getCount() {
    return 4;
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom_menu, viewGroup, false);
    view.setOnClickListener(this);
    view.setTag(i);
    ImageView img = (ImageView) view.findViewById(R.id.image_view);
    img.setImageResource(images[i]);
    return view;
  }

  @Override
  public void onClick(View view) {
    if(listener!=null){
      listener.onEventNotify(view,(int)view.getTag());
    }
  }




}
