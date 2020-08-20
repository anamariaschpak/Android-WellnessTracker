package com.example.wellnesstracker;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(13);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng WorldClass = new LatLng(44.406591, 26.124532);
        LatLng DristorKebap = new LatLng(44.416877, 26.104182);

        mMap.addMarker(new MarkerOptions().position(WorldClass).title("World Class Gym"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(WorldClass));

        PolylineOptions polyline = new PolylineOptions();
        polyline.add(WorldClass);
        polyline.add(DristorKebap);
        polyline.color(Color.MAGENTA);
        polyline.geodesic(true);
        polyline.startCap(new RoundCap());
        polyline.width(20);
        polyline.jointType(JointType.BEVEL);

        mMap.addPolyline(polyline);

        MarkerOptions markerOptionsGym = new MarkerOptions();
        markerOptionsGym.position(WorldClass)
                .title("World Class Gym")
                .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN));

        MarkerOptions markerOptionsDristor = new MarkerOptions();
        markerOptionsDristor.position(DristorKebap)
                .title("Dristor Doner Kebap")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        Marker mDristor = mMap.addMarker(markerOptionsDristor);
        mDristor.showInfoWindow();

        Marker mGym = mMap.addMarker(markerOptionsGym);
        mGym.showInfoWindow();

    }
}
