package io.mapwize.mapwizesimple;

import android.app.Application;

import io.mapwize.mapwizeformapbox.AccountManager;

public class MapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.start(this, "cab8f29af2d764be17fb092b52ebf66f");
    }

}
