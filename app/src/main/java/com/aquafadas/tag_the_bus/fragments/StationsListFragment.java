package com.aquafadas.tag_the_bus.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.aquafadas.tag_the_bus.activities.PicturesActivity;
import com.aquafadas.tag_the_bus.entities.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StationsListFragment extends Fragment implements LocationListener {

    private OnFragmentInteractionListener mListener;
    private LocationManager locationManager;
    private String provider;
    private static final long MIN_DISTANCE = 2;
    private static final long MIN_TIME = 1000 * 60;
    StationAdapter adapter;
    ListView listView;
    List<Station> stationList;
    ProgressDialog mProgressDialog;

    public StationsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);

        listView = (ListView) view.findViewById(R.id.listStations);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }

        locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
       // provider="network";
        locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);
        Location location = locationManager.getLastKnownLocation(provider);
        System.out.println("my provider " + provider);

        // Initialize the location fields
        if (location != null) {

            System.out.println("Provider " + provider + " has been selected.");
            Toast.makeText(getActivity(), "Provider " + provider + " has been selected. ", Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        } else {

            Toast.makeText(getActivity(), "Location not available ! Turn GPS on !" , Toast.LENGTH_LONG).show();
           // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //startActivity(intent);
        }


        return view;
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

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        System.out.println(" location xxx : " + lat + " , lon " + lng);
        Toast.makeText(getActivity(), " location xxx : " + lat + " , lon " + lng, Toast.LENGTH_LONG).show();
        updateStation(location);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateStation(Location location) {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("chargement des données ");
        mProgressDialog.setMessage("patientez...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0)
              //     System.out.println("xxxxxxxxxx "+addresses.get(0).getAddressLine(2));
            System.out.println("xxxxxxxxxx "+addresses.get(0).getSubAdminArea());
            //System.out.println("xxxxxxxxxx "+addresses.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


       // String url = "http://barcelonaapi.marcpous.com/bus/nearstation/latlon/" + location.getLatitude() + "/" + location.getLongitude() + "/1.json";
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
                            stationList = new ArrayList<>();
                            for (int i = 0; i < listJson.length(); i++) {
                                JSONObject station = listJson.getJSONObject(i);


                                stationList.add(new Station(station.getString("id"), station.getString("street_name")
                                        , station.getString("buses"), station.getDouble("lat"), station.getDouble("lon"), station.getDouble("distance")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new StationAdapter(getActivity(), R.layout.station_one_row, stationList);
                        adapter.notifyDataSetChanged();
                        mProgressDialog.hide();

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                Bundle bundle = new Bundle();
                                bundle.putString("id",stationList.get(i).getIdStaion());
                                bundle.putString("name",stationList.get(i).getStreet());
                                /*PicturesStationFragment fragment=new PicturesStationFragment();
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.choice_frame, fragment, "Pictures Fragment");
                                ft.commit();
*/
                                Intent intent=new Intent(getActivity(), PicturesActivity.class);
                                intent.putExtra("station",bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.hide();

                Toast.makeText(getActivity(),"Check your network status !",Toast.LENGTH_LONG);
            }
        });
        queue.add(stringRequest);


    }

}
