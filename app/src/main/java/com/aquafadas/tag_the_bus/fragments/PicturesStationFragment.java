package com.aquafadas.tag_the_bus.fragments;


import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aquafadas.tag_the_bus.R;
import com.google.android.gms.plus.PlusOneButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment with a Google +1 button.
 */
public class PicturesStationFragment extends Fragment {

    ImageView gallerie,testView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bundle bundle ;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    String imgPath;
    Bitmap bitmap;
    View view;
    public PicturesStationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     view = inflater.inflate(R.layout.fragment_pictures_station, container, false);
        Parse.initialize(new Parse.Configuration.Builder(getActivity())
                .applicationId("3E9cBfYImTUZCYEUp7kxEnM7ZL7N2DcQrqlNAgFG")
                .clientKey("UkLKfWA6MiGHgNXT7cCdxpPZGuzY0rKBrcsaSXoR")
                .server("https://parseapi.back4app.com/")
                .build()
        );
        bundle= this.getArguments();
gallerie=(ImageView)view.findViewById(R.id.gallerie);
        testView=(ImageView)view.findViewById(R.id.listView);
        gallerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);*/
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });




        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pictures");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> pictures, ParseException e) {
                if (e == null) {

                 //   pictures.get(0).getString("Title");
                    System.out.println("0000 "+pictures.get(0).getParseFile("Image").getUrl());

                } else {
                    // handle Parse Exception here
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      try {


       if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           Bundle extras = data.getExtras();
           Bitmap bitmap = (Bitmap) extras.get("data");


testView.setImageBitmap(bitmap);
           // System.out.println("name "+bundle.getString("name"));
           // System.out.println("id "+bundle.getString("id"));
           ByteArrayOutputStream baos = new ByteArrayOutputStream();//and then I convert that image into a byteArray
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
           byte[] b = baos.toByteArray();
           bundle.putByteArray("img", b);

           ConfirmationFragment fragment = new ConfirmationFragment();
           fragment.setArguments(bundle);
           FragmentTransaction ft = getFragmentManager().beginTransaction();
           ft.replace(R.id.pictures_frame, fragment, "Pictures Fragment");
           ft.commit();
               /*
               ImageView imgView = (ImageView) view.findViewById(R.id.imgView);
               // Set the Image in ImageView after decoding the String
               imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));*/


       }
           }
        catch (Exception e) {
           Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                   .show();
       }
    }

}
