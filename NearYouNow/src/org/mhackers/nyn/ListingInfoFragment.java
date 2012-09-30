/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 *
 * @author damian
 */
public class ListingInfoFragment extends DialogFragment {

    private ListView listingTags;
    private Listing myListing;
    private View myView;
    private Location myLocation;
    private ImageButton typeLogo;
    private TextView description;

    public ListingInfoFragment(Listing listing, Location location) {
        myListing = listing;
        myLocation = location;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listinginfo, container, false);
        description = (TextView)inflater.inflate(R.id.listing_description, container);
        typeLogo = (ImageButton) myView.findViewById(R.id.image_button);
        handleImageButton();
        final Button mapButton = (Button) myView.findViewById(R.id.map_button);
        listingTags = (ListView) myView.findViewById(R.id.tags_list);
        ArrayList<String> tags = new ArrayList();
        listingTags.setAdapter(new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1, tags));
        //addressText.setText(String.valueOf(myParty.getLocation().getLatitude()));
        //ratioBar.setProgress(myParty.getRatio());
        this.setMenuVisibility(true);
        this.getDialog().setTitle(myListing.getName());
        final Button getitButton = (Button) myView.findViewById(R.id.getit_button);
        getitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = new GetItFragment(myListing, myLocation);
                newFragment.show(ft, "dialog");
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + myListing.getLocation().getLongitude() + "&daddr=" + myListing.getLocation().getLatitude()));
                startActivity(intent);
            }
        });
        return myView;
    }

    private void handleImageButton() {
        
    }
}
