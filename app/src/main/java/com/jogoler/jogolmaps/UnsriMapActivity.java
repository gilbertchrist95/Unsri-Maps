package com.jogoler.jogolmaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UnsriMapActivity extends TileViewActivity {
  static int move;
  public static final double NORTH_WEST_LATITUDE = -3.223789;
  public static final double NORTH_WEST_LONGITUDE = 104.665836;
  public static final double SOUTH_EAST_LATITUDE = -3.208147;
  public static final double SOUTH_EAST_LONGITUDE = 104.643003;
  private static final int GPS_TIME_INTERVAL = 100; // get gps location every 1 min 60000
  private static final int GPS_DISTANCE = 0; // set the distance value in meter
  private static final int HANDLER_DELAY = 1000 * 60 * 5;
  private double CURRENT_LATITUDE = -1;
  private double CURRENT_LONGITUDE = -1;
  private float CURRENT_SPEED = 0;
  ImageView markerMyLocation;
  LocationManager locationManager;
  protected Location location;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // we'll reference the TileView multiple times
    TileView tileView = getTileView();

    // size and geolocation
    tileView.setSize(9000, 6200);

    // we won't use a downsample here, so color it similarly to tiles
    tileView.setBackgroundColor(0xFFF3F3F4);

    tileView.addDetailLevel(0.0125f, "tiles/unsri_maps/125/%d_%d.jpg");
    tileView.addDetailLevel(0.2500f, "tiles/unsri_maps/250/%d_%d.jpg");
    tileView.addDetailLevel(0.5000f, "tiles/unsri_maps/500/%d_%d.jpg");
    tileView.addDetailLevel(1.0000f, "tiles/unsri_maps/1000/%d_%d.jpg");

    // markers should align to the coordinate along the horizontal center and vertical bottom
    tileView.setMarkerAnchorPoints(-0.5f, -1.0f);

    // provide the corner coordinates for relative positioning
    tileView.defineBounds(
            NORTH_WEST_LONGITUDE,
            NORTH_WEST_LATITUDE,
            SOUTH_EAST_LONGITUDE,
            SOUTH_EAST_LATITUDE
    );

    MapsData mapsData = new MapsData();
    ArrayList<double[]> points = mapsData.getLocationData();
    // add markers for all the points
    for (double[] point : points) {
      // any view will do...
      ImageView marker = new ImageView(this);

//      marker.getLayoutParams().height = marker.getMaxHeight()/2;
      // save the coordinate for centering and callout positioning
      marker.setTag(point);
      // marker depend on category
      marker.setImageResource((int) point[2]);


      marker.setScaleX((float) 0.5);
      marker.setScaleY((float) 0.5);
      // on tap show further information about the area indicated
      // this could be done using a OnClickListener, which is a little more "snappy", since
      // MarkerTapListener uses GestureDetector.onSingleTapConfirmed, which has a delay of 300ms to
      // confirm it's not the start of a double-tap. But this would consume the touch event and
      // interrupt dragging
      tileView.getMarkerLayout().setMarkerTapListener(markerTapListener);
      // add it to the view tree
      tileView.addMarker(marker, point[0], point[1], -0.5f, -0.8f);

    }

    //check gps
    if (!obtainLocation())
      Toast.makeText(getApplicationContext(), "Turn on your GPS", Toast.LENGTH_LONG).show();
    else {

      if ((CURRENT_LONGITUDE < NORTH_WEST_LONGITUDE && CURRENT_LONGITUDE > SOUTH_EAST_LONGITUDE) &&
              (CURRENT_LATITUDE < SOUTH_EAST_LATITUDE && CURRENT_LATITUDE > NORTH_WEST_LATITUDE)) {
        double[] position = {CURRENT_LONGITUDE, CURRENT_LATITUDE, 1};
        move = 1;
        markerMyLocation = new ImageView(this);
        markerMyLocation.setTag(position);
        markerMyLocation.setImageResource(R.drawable.mylocation);
        markerMyLocation.setScaleX((float) 0.7);
        markerMyLocation.setScaleY((float) 0.7);

        tileView.getMarkerLayout().setMarkerTapListener(markerTapListener);
        tileView.addMarker(markerMyLocation, position[0], position[1], -0.5f, -0.75f);
//        tileView.addMarker(markerMyLocation, position[0], position[1], -0.3f, -0.6f);
      } else {
        move = 0;
        Log.d(TAG, "hehe Latitude, Longitude = " + CURRENT_LATITUDE + "," + CURRENT_LONGITUDE);
//        markerMyLocation = new ImageView(this);
//        markerMyLocation = null;
        double[] position = {CURRENT_LONGITUDE, CURRENT_LATITUDE, 1};
        markerMyLocation = new ImageView(this);
        markerMyLocation.setTag(position);
        markerMyLocation.setImageResource(R.drawable.mylocation);
        markerMyLocation.setScaleX((float) 0.7);
        markerMyLocation.setScaleY((float) 0.7);

        tileView.getMarkerLayout().setMarkerTapListener(markerTapListener);
        tileView.addMarker(markerMyLocation, position[0], position[1], -0.5f, -0.75f);
        Toast.makeText(getApplicationContext(), "You are not in unsri !", Toast.LENGTH_LONG).show();
//        double[] position = {CURRENT_LONGITUDE, CURRENT_LATITUDE, 1};
//        markerMyLocation = new ImageView(this);
//        markerMyLocation.setTag(position);
//        markerMyLocation.setImageResource(R.drawable.mylocation);
//        markerMyLocation.setScaleX((float) 0.7);
//        markerMyLocation.setScaleY((float) 0.7);
//
//        tileView.getMarkerLayout().setMarkerTapListener(markerTapListener);
//        tileView.addMarker(markerMyLocation, position[0], position[1], -0.5f, -0.75f);
      }
    }

    // let's start off framed to the center of all points
    double x = 0;
    double y = 0;
    for (double[] point : points) {
      x = x + point[0];
      y = y + point[1];
    }
    int size = points.size();
    x = x / size;
    y = y / size;

    if (CURRENT_LONGITUDE == -1 || CURRENT_LATITUDE == -1) frameTo(x, y);
    else frameTo(CURRENT_LONGITUDE, CURRENT_LATITUDE);

    addView(tileView, R.drawable.label);

    // test higher than 1
    tileView.setScaleLimits(0, 2);

    // start small and allow zoom
    tileView.setScale(0.5f);

    // with padding, we might be fast enough to create the illusion of a seamless image
    tileView.setViewportPadding(256);

    // we're running from assets, should be fairly fast decodes, go ahead and render asap
    tileView.setShouldRenderWhilePanning(true);
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          while (true) {
            sleep(1000);
            //   if(markerMyLocation!=null) getTileView().moveMarker(markerMyLocation,CURRENT_LONGITUDE,CURRENT_LATITUDE);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    thread.start();




  }

  @Override
  protected void onStart() {
    super.onStart();
    obtainLocation();
  }

  private MarkerLayout.MarkerTapListener markerTapListener = new MarkerLayout.MarkerTapListener() {

    @Override
    public void onMarkerTap(View view, int x, int y) {
      // get reference to the TileView
      Log.d(TAG, "id view" + view.getId());
      TileView tileView = getTileView();
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

  private void addView(TileView tileView, int view) {
    RelativeLayout relativeLayout = new RelativeLayout(this);
    ImageView logo = new ImageView(this);
    logo.setImageResource(view);
    RelativeLayout.LayoutParams logoLayoutParams = new RelativeLayout.LayoutParams(MarkerLayout.LayoutParams.WRAP_CONTENT, MarkerLayout.LayoutParams.WRAP_CONTENT);
    logoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    relativeLayout.addView(logo, logoLayoutParams);
    tileView.addView(relativeLayout);
  }

  private boolean obtainLocation() {
    if (locationManager == null)
      locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//    if (locationManager!=null) {
      location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      Log.d(TAG, "Update Location");
      if (location != null) {
        CURRENT_LATITUDE = location.getLatitude();
        CURRENT_LONGITUDE = location.getLongitude();
        CURRENT_SPEED = location.getSpeed();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
//          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                  0, 0, GPSListener);;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                GPS_TIME_INTERVAL, GPS_DISTANCE, GPSListener);
        Log.d(TAG, "gcgc:" + CURRENT_LATITUDE + " " + CURRENT_LONGITUDE);
//        }
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
  public void onPause() {
    super.onPause();
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(getApplicationContext(), "Paused !", Toast.LENGTH_LONG).show();
      Log.d(TAG, "Destroy Listener");
      locationManager.removeUpdates(GPSListener);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(getApplicationContext(), "Resume !", Toast.LENGTH_LONG).show();
      Log.d(TAG, "Update Location");
      obtainLocation();
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
      if ((markerMyLocation != null) && (move == 1)) {
        getTileView().moveMarker(markerMyLocation, CURRENT_LONGITUDE, CURRENT_LATITUDE);
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

  @Override
  public void onBackPressed() {
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
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}