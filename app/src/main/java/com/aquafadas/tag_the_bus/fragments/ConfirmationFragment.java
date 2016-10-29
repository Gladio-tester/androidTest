package com.aquafadas.tag_the_bus.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aquafadas.tag_the_bus.R;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConfirmationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View view;
    Bundle bundle;
    Button saveBtn;
    ProgressDialog mProgressDialog;
    EditText title;

    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Parse.initialize(new Parse.Configuration.Builder(getActivity())
                .applicationId("3E9cBfYImTUZCYEUp7kxEnM7ZL7N2DcQrqlNAgFG")
                .clientKey("UkLKfWA6MiGHgNXT7cCdxpPZGuzY0rKBrcsaSXoR")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        bundle = this.getArguments();

        final ImageView imgView = (ImageView) view.findViewById(R.id.imageStation);
        // Set the Image in ImageView after decoding the String
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
        System.out.println(bundle.getString("img"));
        imgView.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("img")));
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "On click", Toast.LENGTH_SHORT).show();
                title = (EditText) view.findViewById(R.id.ImageTitle);
               /* Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString("img"));


                ByteArrayOutputStream baos = new ByteArrayOutputStream();//and then I convert that image into a byteArray
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();*/

                //android.util.Base64.encodeToString(b, Base64.DEFAULT);
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setTitle("enregistrement des données ");
                mProgressDialog.setMessage("patientez...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
                ParseFile file = new ParseFile("station.png",bundle.getByteArray("img"));
                ParseObject picture = new ParseObject("Pictures");


                picture.put("Title", title.getText());
                picture.put("UserId", "1234");
                picture.put("Station_Name", "barcelone");
                picture.put("Station_Id", "01");
                picture.put("Image", file);
                picture.saveInBackground(new SaveCallback() {
                                             @Override
                                             public void done(ParseException e) {
                                                 mProgressDialog.hide();
                                             }
                                         }


                );
            }
        });
        return view;
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

}
