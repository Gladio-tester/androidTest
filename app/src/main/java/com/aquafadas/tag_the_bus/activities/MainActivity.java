package com.aquafadas.tag_the_bus.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aquafadas.tag_the_bus.R;
import com.aquafadas.tag_the_bus.fragments.StationsListFragment;
import com.aquafadas.tag_the_bus.fragments.StationsMapFragment;

import info.hoang8f.android.segmented.SegmentedGroup;

public class MainActivity extends AppCompatActivity implements StationsMapFragment.OnFragmentInteractionListener,StationsListFragment.OnFragmentInteractionListener {
    FragmentTransaction fragmentTransaction;
    StationsListFragment stationsListFragment;
    StationsMapFragment stationsMapFragment;
    SegmentedGroup segmentedChoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission_group.STORAGE,Manifest.permission_group.CAMERA,Manifest.permission_group.LOCATION},1);
        stationsListFragment=new StationsListFragment();
        stationsMapFragment=new StationsMapFragment();
        fragmentTransaction= getSupportFragmentManager().beginTransaction().replace(R.id.choice_frame,stationsListFragment);
        fragmentTransaction.commit();

        segmentedChoise = (SegmentedGroup) findViewById(R.id.segmentedChoice);
        segmentedChoise.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    if(rb.getText().equals("Liste"))
                    {
                        fragmentTransaction= getSupportFragmentManager().beginTransaction().replace(R.id.choice_frame,stationsListFragment);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        fragmentTransaction= getSupportFragmentManager().beginTransaction().replace(R.id.choice_frame,stationsMapFragment);
                        fragmentTransaction.commit();

                    }
                }

            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
