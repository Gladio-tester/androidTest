package com.aquafadas.tag_the_bus.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aquafadas.tag_the_bus.Adapters.StationAdapter;
import com.aquafadas.tag_the_bus.R;
import com.aquafadas.tag_the_bus.activities.MainActivity;
import com.aquafadas.tag_the_bus.entities.Station;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;


public class StationsMapFragment extends Fragment implements
        OnMapReadyCallback, LocationListener{


    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private static final long MIN_DISTANCE = 2;
    private static final long MIN_TIME = 1000 * 60;
    public StationsMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stations_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;

        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //    mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }


        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                }


                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                //provider="network";

                Location location = locationManager.getLastKnownLocation(provider);
                System.out.println("my provider " + provider);

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

           //     mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                // Initialize the location fields
                if (location != null) {

                    System.out.println("Provider " + provider + " has been selected.");
                    Toast.makeText(getActivity(), "Provider " + provider + " has been selected. ", Toast.LENGTH_SHORT).show();
                    onLocationChanged(location);
                } else {

                    Toast.makeText(getActivity(), "Location not available ! Turn on GPS " + provider, Toast.LENGTH_SHORT).show();
                }



                return false;
            }
        });



    }


    @Override
    public void onLocationChanged(Location location) {
mMap.clear();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

       // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(41.3985182,2.1917991)));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        //String url = "http://barcelonaapi.marcpous.com/bus/nearstation/latlon/" + location.getLatitude() + "/" + location.getLongitude() + "/1.json";
        String url = "http://barcelonaapi.marcpous.com/bus/nearstation/latlon/41.3985182/2.1917991/1.json";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject myResponse = new JSONObject();
                        try {
                            myResponse = new JSONObject(response);
                            System.out.println("111 xxx " + response);
                            System.out.println("xxx " + myResponse.getJSONObject("data").getJSONArray("nearstations").getJSONObject(0).get("street_name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            //mTextView.setText("Response is: " + myResponse.getJSONObject("data").getJSONArray("nearstations").getJSONObject(0).get("street_name"));
                            JSONArray listJson = myResponse.getJSONObject("data").getJSONArray("nearstations");

                            for (int i = 0; i < listJson.length(); i++) {
                                JSONObject station = listJson.getJSONObject(i);


                                //stationList.add(new Station(station.getString("id"), station.getString("street_name")
                                        //, station.getString("buses"), station.getDouble("lat"), station.getDouble("lon"), station.getDouble("distance")));
                                LatLng latLng = new LatLng(station.getDouble("lat"), station.getDouble("lon"));

                                mMap.addMarker(new MarkerOptions().position(latLng).title(station.getString("street_name")));

                            }
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
