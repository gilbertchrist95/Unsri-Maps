package com.jogoler.jogolmaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.location.*;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MapsResultActivity extends AppCompatActivity {

    Intent intentSearchResult;
    int id;
    double longitude;
    double latitude;
    String convertToDouble;

    public static final double NORTH_WEST_LATITUDE = -3.223789;
    public static final double NORTH_WEST_LONGITUDE = 104.665836;
    public static final double SOUTH_EAST_LATITUDE = -3.208147;
    public static final double SOUTH_EAST_LONGITUDE = 104.643003;
    private static final int GPS_TIME_INTERVAL = 5000; // get gps location every 1 min 60000
    private static final int GPS_DISTANCE = 0; // set the distance value in meter
    FloatingActionButton fab;
    public TileView tileView;
    Paint paint;
    ArrayList<double[]> home;
    private static double CURRENT_LATITUDE = -1;
    private static double CURRENT_LONGITUDE = -1;
    private float CURRENT_SPEED = 0;
    ImageView markerMyLocation;
    LocationManager locationManager;
    protected android.location.Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_maps_result);

        tileView = new TileView(this);

        // size and geolocation
        tileView.setSize(9000, 6200);

        // setbackgound
        tileView.setBackgroundColor(0xFFF3F3F4);

        tileView.addDetailLevel(0.5000f, "tiles/unsri_maps/500/%d_%d.jpg");
        tileView.addDetailLevel(0.2500f, "tiles/unsri_maps/250/%d_%d.jpg");
        tileView.addDetailLevel(0.5000f, "tiles/unsri_maps/500/%d_%d.jpg");
        tileView.addDetailLevel(1.0000f, "tiles/unsri_maps/1000/%d_%d.jpg");

        // markers should align to the coordinate along the horizontal center and vertical bottom
        tileView.setMarkerAnchorPoints(-0.5f, -1.0f);

        tileView.defineBounds(
                NORTH_WEST_LONGITUDE,
                NORTH_WEST_LATITUDE,
                SOUTH_EAST_LONGITUDE,
                SOUTH_EAST_LATITUDE
        );

        if (!obtainLocation()) {
            Toast.makeText(getApplicationContext(), "Turn on your GPS", Toast.LENGTH_LONG).show();
        } else {
            if ((CURRENT_LONGITUDE < NORTH_WEST_LONGITUDE && CURRENT_LONGITUDE > SOUTH_EAST_LONGITUDE) &&
                    (CURRENT_LATITUDE < SOUTH_EAST_LATITUDE && CURRENT_LATITUDE > NORTH_WEST_LATITUDE)) {
                double[] position = {CURRENT_LONGITUDE, CURRENT_LATITUDE, 1};

                markerMyLocation = new ImageView(this);
                markerMyLocation.setTag(position);
                markerMyLocation.setImageResource(R.drawable.mylocation);

                tileView.getMarkerLayout().setMarkerTapListener(markerTapListerner);
                tileView.addMarker(markerMyLocation, position[0], position[1], -0.5f, -1.0f);
            } else {
                Log.d(TAG, "hehe Latitude, Longitude = " + CURRENT_LATITUDE + "," + CURRENT_LONGITUDE);
                Toast.makeText(getApplicationContext(), "You are not in unsri !", Toast.LENGTH_LONG).show();
            }
        }

        intentSearchResult = getIntent();
        // Get the results of id
        convertToDouble = intentSearchResult.getStringExtra("id");
        id = Integer.parseInt(convertToDouble.trim());
        // Get the results of longitude
        convertToDouble = intentSearchResult.getStringExtra("longitude");
        longitude = Double.parseDouble(convertToDouble);
        // Get the results of latitude
        convertToDouble = intentSearchResult.getStringExtra("latitude");
        latitude = Double.parseDouble(convertToDouble);

        double[] point = new double[]{longitude,latitude,R.drawable.targetlocation};
        ImageView marker = new ImageView(this);
        marker.setTag(point);
        marker.setImageResource(R.drawable.targetlocation);

        // on tap show further information about the area indicated
        // this could be done using a OnClickListener, which is a little more "snappy", since
        // MarkerTapListener uses GestureDetector.onSingleTapConfirmed, which has a delay of 300ms to
        // confirm it's not the start of a double-tap. But this would consume the touch event and
        // interrupt dragging
        // on tap show further information about the area indicated
        // this could be done using a OnClickListener, which is a little more "snappy", since
        // MarkerTapListener uses GestureDetector.onSingleTapConfirmed, which has a delay of 300ms to
        // confirm it's not the start of a double-tap. But this would consume the touch event and
        // interrupt dragging
        tileView.getMarkerLayout().setMarkerTapListener(markerTapListerner);
        // add it to the view tree
        tileView.addMarker(marker, point[0], point[1], -0.5f, -1.0f);

        home = new ArrayList<>();
        home.add(new double[]{CURRENT_LONGITUDE, CURRENT_LATITUDE});
        home.add(new double[]{longitude, latitude});

        // get the default paint and style it.  the same effect could be achieved by passing a custom Paint instnace
        paint = tileView.getDefaultPathPaint();
        paint.setColor(Color.MAGENTA);
        paint.setStrokeWidth(8);
        paint.setShadowLayer(4, 2, 2, Color.WHITE);
        paint.setPathEffect(new CornerPathEffect(5));

        // draw some of the points
        tileView.drawPath(home, paint);



        frameTo(longitude, latitude);

        // test higher than 1
        tileView.setScaleLimits(0, 2);

        // start small and allow zoom
        tileView.setScale(0.5f);

        // with padding, we might be fast enough to create the illusion of a seamless image
        tileView.setViewportPadding(256);

        // we're running from assets, should be fairly fast decodes, go ahead and render asap
        tileView.setShouldRenderWhilePanning(true);

        fab = (FloatingActionButton) findViewById(R.id.fabMyLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if ((CURRENT_LONGITUDE < NORTH_WEST_LONGITUDE && CURRENT_LONGITUDE > SOUTH_EAST_LONGITUDE) &&
                    (CURRENT_LATITUDE < SOUTH_EAST_LATITUDE && CURRENT_LATITUDE > NORTH_WEST_LATITUDE)) {
                tileView.moveToMarker(markerMyLocation, true);
            } else {
                Snackbar.make(view, "You're not in UNSRI", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            }
        });

        //add tileview
        ((RelativeLayout) findViewById(R.id.content_maps_result)).addView(tileView);
    }

    public TileView getTileView() {
        return tileView;
    }

    public void frameTo( final double x, final double y ) {
        getTileView().post( new Runnable() {
            @Override
            public void run() {
                getTileView().scrollToAndCenter( x, y );
            }
        });
    }

    private boolean obtainLocation() {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//    if (locationManager!=null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "Update Location");
            if (location != null) {
                CURRENT_LATITUDE = location.getLatitude();
                CURRENT_LONGITUDE = location.getLongitude();
                CURRENT_SPEED = location.getSpeed();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE

                        }, 1);
                    }
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        GPS_TIME_INTERVAL, GPS_DISTANCE, GPSListener);
                Log.d(TAG, "gcgc:" + CURRENT_LATITUDE + " " + CURRENT_LONGITUDE);
            } else {
                //Toast.makeText(getApplicationContext(), "Location can't be retrieved", Toast.LENGTH_LONG).show();
            }
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Provider not Found!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    obtainLocation();
                    return;
                }
        }
    }

    private LocationListener GPSListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            // update location
            if (loc != null) {
                location = loc;
                CURRENT_LATITUDE = location.getLatitude();
                CURRENT_LONGITUDE = location.getLongitude();
                CURRENT_SPEED = location.getSpeed();
            }
            if ((markerMyLocation != null)) {
                getTileView().moveMarker(markerMyLocation, CURRENT_LONGITUDE, CURRENT_LATITUDE);

//                double[] baru = new double[]{CURRENT_LONGITUDE, CURRENT_LATITUDE};
//                home.add(0,baru);
//                // draw some of the points
//                tileView.drawPath(home, paint);
            }
            Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + CURRENT_LATITUDE + ", lon=" + CURRENT_LONGITUDE);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Disabled new provider, Turn it on ! " + provider,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Enabled new provider " + provider,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private MarkerLayout.MarkerTapListener markerTapListerner = new MarkerLayout.MarkerTapListener() {
        @Override
        public void onMarkerTap(View view, int x, int y) {
            Log.d(TAG, "id view" + view.getId());
            // we saved the coordinate in the marker's tag
            double[] position = (double[]) view.getTag();
            // lets center the screen to that coordinate
            tileView.slideToAndCenter(position[0], position[1]);
            // create a simple callout
            SampleCallout callout = new SampleCallout(view.getContext());
            // add it to the view tree at the same position and offset as the marker that invoked it
            tileView.addCallout(callout, position[0], position[1], -0.5f, -1.0f);
            // a little sugar
            callout.transitionIn();
            // stub out some text
            callout.setTitle("MAP CALLOUT");
            callout.setSubtitle("Lat,Long :\n" + position[1] + ", " + position[0]);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(GPSListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(GPSListener);
        finish();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
