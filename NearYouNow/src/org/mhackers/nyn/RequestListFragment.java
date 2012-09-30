/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListFragment;
import android.content.Context;
import android.location.Location;
import java.util.ArrayList;
import java.util.List;

public class RequestListFragment extends ListFragment {

    ListingArrayAdapter myAdapter;
    private Location myLocation;
    private String provider;
    private ArrayList<Listing> myListings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        Listing listing = (Listing) l.getAdapter().getItem(position);
        
        DialogFragment newFragment = new ListingInfoFragment(listing, myLocation);
        newFragment.show(ft, "dialog");

    }

    public void updateLocation(Location location) {
        
    }

    public void updateList() {
        //getView().invalidate();
        //System.out.println(myAverageRatings.size());
        //calculateAverages();
        myAdapter = new ListingArrayAdapter(
                getView().getContext(), R.layout.listing_listitem, myListings);
        //if (myAverageRatings != null) {
        setListAdapter(myAdapter);
        //}
        /*for (int i = 0; i < myParties.size(); i++){
         myAdapter.setRating(i, myAverageRatings.get(i).getOvrating(), myAverageRatings.get(i).getOvrating());
         }*/


    }
}
