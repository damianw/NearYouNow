/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damian
 */
public class GetItFragment extends DialogFragment {

    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private static final int ACTION_TAKE_VIDEO = 3;
    private SeekBar ratingBar;
    private SeekBar ratioBar;
    private View myView;
    private Listing myListing;
    private Location myLocation;
    private String photoId = "";
    private String videoId = "";
    private File[] myFiles = new File[3];
    private Button myPhotoButton;
    private EditText myCommentBox;
    private Button myVideoButton;;

    public GetItFragment() {
        super();
    }

    public GetItFragment(Listing listing, Location location) {
        this();
        
        myListing = listing;
        myLocation = location;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.getit, container, false);
        /*
        myPhotoButton = (Button) myView.findViewById(R.id.take_photo);
        myCommentBox = (EditText) myView.findViewById(R.id.message_box);
        myVideoButton = (Button) myView.findViewById(R.id.take_video);
        ratingBar = (SeekBar) myView.findViewById(R.id.rating_slider);
        ratioBar = (SeekBar) myView.findViewById(R.id.ratio_slider);
        ratingBar.setMax(10);
        ratioBar.setMax(10);
        myVideoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                String _path = Environment.getExternalStorageDirectory() + File.separator + "video.3gp";
                File file = new File(_path);
                Uri outputFileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);;
                startActivityForResult(intent, ACTION_TAKE_VIDEO);
            }
        });
        myPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String _path = Environment.getExternalStorageDirectory() + File.separator + "photo.jpg";
                File file = new File(_path);
                Uri outputFileUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);;
                startActivityForResult(intent, ACTION_TAKE_PHOTO_B);
            }
        });
        final Button submitButton = (Button) myView.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CMGeoPoint2 geopoint = new CMGeoPoint2(myLocation.getLatitude(), myLocation.getLongitude());
                UserRating newRating = new org.megatron.wtpa.UserRating("somefbuser", "", "",myCommentBox.getText().toString(), geopoint, ratioBar.getProgress(), ratingBar.getProgress(), 0, null);
                newRating.save();
                if (myFiles[0] != null) {
                    photoId = uploadFile(myFiles[0], "somefbuser_" + newRating.getObjectId() + "_photo", "image/jpeg");
                    newRating.setPhotoId(photoId);
                }
                if (myFiles[1] != null) {
                    videoId = uploadFile(myFiles[1], "somefbuser_" + newRating.getObjectId() + "_video", "video/3gpp");
                    newRating.setVideoid(videoId);
                }
                if (myFiles[2] != null) {
                    uploadFile(myFiles[2], "somefbuser_" + myParty.getObjectId() + "_audio", "audio/mp3");
                }
                if (myParty != null) {
                    newRating.setPartyid(myParty.getObjectId());
                    newRating.setPhotoId(photoId);
                    newRating.save();
                    dismiss();
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment newFragment = new NewPartyFragment(myParty, myLocation, newRating);
                    newFragment.show(ft, "dialog");

                    //FIX THIS SHIT
                }
            }
        });

        ratingBar.setProgress(5);
        ratioBar.setProgress(5);
        if (myParty != null) {
            this.getDialog().setTitle("Check-In: " + myParty.getName());
        } else {
            this.getDialog().setTitle("Check-In to New Party");
        }
        //LocationService service = new LocationService();*/
        return myView;
    }

    private String uploadFile(File myFile, String id, String mime) {
        try {
            CMFile file = new CMFile(new FileInputStream(myFile), id, mime);
            file.save(new FileCreationResponseCallback() {
                public void onCompletion(FileCreationResponse response) {
                    System.out.println("all done?: " + response.wasSuccess());
                }
            });
            return file.getObjectId();
        } catch (FileNotFoundException ex) {
            System.err.println("whyyyy");
            Logger.getLogger(CheckInFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public File convertImageUriToFile(Uri imageUri, Activity activity) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
            cursor = activity.managedQuery(imageUri,
                    proj, // Which columns to return
                    null, // WHERE clause; which rows to return (all rows)
                    null, // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
            if (cursor.moveToFirst()) {
                String orientation = cursor.getString(orientation_ColumnIndex);
                return new File(cursor.getString(file_ColumnIndex));
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}