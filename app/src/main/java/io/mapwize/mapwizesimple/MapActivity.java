package io.mapwize.mapwizesimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;

import io.mapwize.mapwizeformapbox.MapOptions;
import io.mapwize.mapwizeformapbox.MapwizePlugin;
import io.mapwize.mapwizeformapbox.UISettings;
import io.mapwize.mapwizeformapbox.model.Venue;

public class MapActivity extends AppCompatActivity {

    final String TAG = "MapActivity";
    MapView mapView;
    MapwizePlugin mapwizePlugin;
    FrameLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap();
    }

    void initMap() {

        Mapbox.getInstance(this, "pk.mapwize");
        containerLayout = findViewById(R.id.map_container);
        MapboxMapOptions opts = new MapboxMapOptions();
        opts = opts.styleUrl("http://outdoor.mapwize.io/styles/mapwize/style.json?key=cab8f29af2d764be17fb092b52ebf66f");
        opts = opts.attributionEnabled(false);
        opts = opts.logoEnabled(false);
        CameraPosition cm = new CameraPosition.Builder().target(new LatLng(50.632978, 3.020182)).zoom(16).build();
        opts = opts.camera(cm);
        mapView = new MapView(this, opts);
        containerLayout.addView(mapView);
        MapOptions mapwizeOpts = new MapOptions.Builder().build();
        UISettings uiSettings = new UISettings.Builder(this).logoEnabled(true).mapwizeCompassEnabled(true).showFloorControl(true).showUserPositionControl(true).build();
        mapwizePlugin = new MapwizePlugin(mapView, mapwizeOpts, uiSettings);

        mapwizePlugin.setOnDidLoadListener(new MapwizePlugin.OnDidLoadListener() {
            @Override
            public void didLoad(MapwizePlugin mapwizePlugin) {
                onMapwizeDidLoad();
            }
        });

        mapwizePlugin.setOnVenueClickListener(new MapwizePlugin.OnVenueClickListener() {
            @Override
            public boolean onVenueClick(Venue venue) {
                return onMapwizeVenueClick(venue);
            }
        });

    }

    boolean onMapwizeVenueClick(Venue venue) {
        mapwizePlugin.centerOnVenue(venue);
        return false;
    }

    void onMapwizeDidLoad() {
        Log.i(TAG, "Plugin loaded");
    }
}
