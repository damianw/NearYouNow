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
import android.os.AsyncTask;
import com.cartodb.*;
import com.cartodb.model.CartoDBResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListingListFragment extends ListFragment {

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

    public void updateLocation(Location location, CartoDBClientIF client) {
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        myLocation = location;
        CartoDBResponse<Map<String, Object>> res = null;
        try {
            res = client.request("select * from mytable limit 1");
        } catch (CartoDBException ex) {
            Logger.getLogger(ListingListFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(res.getTotal_rows());
        System.out.print(res.getRows().get(0).get("cartodb_id"));
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

    /*private class GetListings extends AsyncTask {

        @Override
        protected List<Map<String, Object>> doInBackground(String query, CartoDBClientIF client) {

            // params comes from the execute() call: params[0] is the url.
            return Utils.getListings(myLocation, client);
        }
        // onPostExecute displays the results of the AsyncTask.

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            textView.setText(result);
        }
    }*/
}
