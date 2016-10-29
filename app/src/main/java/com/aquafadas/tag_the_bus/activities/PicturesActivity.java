package com.aquafadas.tag_the_bus.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aquafadas.tag_the_bus.R;
import com.aquafadas.tag_the_bus.fragments.ConfirmationFragment;
import com.aquafadas.tag_the_bus.fragments.PicturesStationFragment;

public class PicturesActivity extends AppCompatActivity implements PicturesStationFragment.OnFragmentInteractionListener,ConfirmationFragment.OnFragmentInteractionListener{
    FragmentTransaction fragmentTransaction;
    PicturesStationFragment picturesStationFragment;
    TextView toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
 picturesStationFragment=new PicturesStationFragment();
      Bundle  bundle = getIntent().getExtras().getBundle("station");
        toolbar=(TextView)findViewById(R.id.title) ;
        toolbar.setText(bundle.getString("name"));
picturesStationFragment.setArguments(bundle);
        fragmentTransaction= getSupportFragmentManager().beginTransaction().replace(R.id.pictures_frame,picturesStationFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
