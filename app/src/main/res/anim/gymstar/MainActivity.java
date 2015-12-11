package anim.gymstar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    GoogleMap map;
    LocationManager mLocationManager;
    Location networkLocation;
    Location GPSlocation;
    LatLng selectedloc;
    Circle circle;
    ViewPager viewPager;
    customeswipeadaptor customeswipeadaptor;
    RelativeLayout Banner;
    Animation slide_up, slide_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Banner = (RelativeLayout) findViewById(R.id.picslayout);
        Banner.setVisibility(View.GONE);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_down);

        if (map == null)
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.getUiSettings().isCompassEnabled();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.container);

        customeswipeadaptor = new customeswipeadaptor(this);

        viewPager.setAdapter(customeswipeadaptor);

        viewPager.setOffscreenPageLimit(3);

        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        //   getSupportActionBar().show();
        //AIzaSyC9Kkd2EMAPMbsDn7PQQ9Ixmi_PihD4u8I
        // changing the opsition of my location button

        View mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getView();
        View btnMyLocation = ((View) mapView.findViewById(1).getParent()).findViewById(2);

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
        networkLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        GPSlocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(networkLocation!=null||GPSlocation==null){
        selectedloc = new LatLng(networkLocation.getLatitude(),networkLocation.getLongitude());
        }
       /* if(networkLocation!=null||GPSlocation!=null){
            selectedloc = new LatLng(GPSlocation.getLatitude(),GPSlocation.getLongitude());
        }*/

        addMapCircle(1000);
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.73102085, 77.72681949), 10));

       /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80); // size of button in dp
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.setMargins(0, 0, 20, 5);
        btnMyLocation.setLayoutParams(params);*/

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_down);
                Banner.setAnimation(slide_down);
                Banner.setVisibility(View.VISIBLE);
                return false;
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);
                Banner.setAnimation(slide_up);
                Banner.setVisibility(View.GONE);
            }
        });

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.info_layout, null);
                return  v;
            }
        });

    }

    public void addMapCircle(int radius) {


        MarkerOptions marker = new MarkerOptions().position(selectedloc).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));
        // adding marker
        map.addMarker(marker);
        CircleOptions circle1 = new CircleOptions();
        circle1.center(selectedloc);
        circle1.radius(radius);
        circle1.strokeColor(Color.parseColor("#1A237E"));
        circle1.fillColor(0x2000ff00);
        circle1.strokeWidth(3f);
        circle = map.addCircle(circle1);
    }
}
