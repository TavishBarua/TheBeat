package com.teenagerslab.thebeat.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tavish on 6/3/2017.
 */

public class TextLatoThin extends TextView {

  public TextLatoThin(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public TextLatoThin(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TextLatoThin(Context context) {
    super(context);
    init();
  }

  private void init() {
    Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
        "fonts/Lato-Thin.ttf");
    setTypeface(tf);
  }

}
