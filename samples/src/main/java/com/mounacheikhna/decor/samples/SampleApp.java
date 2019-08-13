package com.mounacheikhna.decor.samples;

import android.app.Application;
import android.content.Context;
import com.mounacheikhna.decor.DecorContextWrapper;
import com.mounacheikhna.decorators.Decorators;

public class SampleApp extends Application {

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(DecorContextWrapper.wrap(newBase)
        .with(Decorators.getAll()));
  }

}
