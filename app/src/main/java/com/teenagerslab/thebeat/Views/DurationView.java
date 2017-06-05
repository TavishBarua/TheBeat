package com.teenagerslab.thebeat.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.teenagerslab.thebeat.R;
import javax.xml.datatype.Duration;

/**
 * Created by tavish on 6/5/2017.
 */

public class DurationView extends View {

  private View mValue;

  public DurationView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.TextLatoThin, 0, 0);
    //String titleText = a.getString(R.styleable.TextLatoThin);

  }

  public DurationView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

  }
  public DurationView(Context context) {
    super(context);
  }

}
