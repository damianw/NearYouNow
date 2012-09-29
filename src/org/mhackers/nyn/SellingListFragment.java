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

public class SellingListFragment extends ListFragment {

    PartyArrayAdapter myAdapter;
    private Location myLocation;
    private String provider;
    private ArrayList<Party> myParties;
    private ArrayList<ArrayList<UserRating>> myRatings;
    CMStore myStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRatings = new ArrayList();
        myStore = CMStore.getStore();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        Party party = (Party) l.getAdapter().getItem(position);
        ArrayList<UserRating> mratings = new ArrayList();
        for (ArrayList<UserRating> ratings : myRatings) {
            System.out.println("party: " + ratings.get(0).getPartyid() + "object: " + party.getObjectId());
            if (ratings.get(0).getPartyid().equals(party.getObjectId())) {
                mratings = ratings;
                break;
            }
        }

        DialogFragment newFragment = new PartyInfoFragment(party, mratings, myLocation);
        newFragment.show(ft, "dialog");

    }

    public void updateLocation(Location location) {
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        myLocation = location;
        myStore.loadApplicationObjectsSearch("[location near (" + lng + ", " + lat + ")]", new CMObjectResponseCallback() {
            @Override
            public void onCompletion(CMObjectResponse response) {
                List<CMObject> result = response.getObjects();
                ArrayList<Party> parties = new ArrayList();
                for (int i = 0; i < result.size(); i++) {
                    CMObject object = result.get(i);
                    System.out.println(object.getClass());
                    if (object.getClass().equals(Party.class)) {
                        parties.add((Party) object);
                    }
                }
                myParties = parties;
                //calculateAverages();
                syncRatings();
                updateList();
            }
        });
    }

    public void updateList() {
        //getView().invalidate();
        //System.out.println(myAverageRatings.size());
        //calculateAverages();
        myAdapter = new PartyArrayAdapter(
                getView().getContext(), R.layout.party_listitem, myParties);
        //if (myAverageRatings != null) {
        setListAdapter(myAdapter);
        //}
        /*for (int i = 0; i < myParties.size(); i++){
         myAdapter.setRating(i, myAverageRatings.get(i).getOvrating(), myAverageRatings.get(i).getOvrating());
         }*/


    }

    private void syncRatings() {
        System.out.println("Parties: " + myParties.size());
        for (Party party : myParties) {
            System.out.println("ITERATION");
            //System.out.println(Utils.parseKey(party.asKeyedObject()));
            String key = party.getObjectId();
            //System.out.println("[partyid=\"" +  key + "\"]");
            myStore.loadApplicationObjectsSearch("[partyid=\"" + key + "\"]", new CMObjectResponseCallback() {
                @Override
                public void onCompletion(CMObjectResponse response) {
                    List<CMObject> result = response.getObjects();
                    System.out.println("results: " + result.size());
                    ArrayList<UserRating> ratings = new ArrayList();
                    for (CMObject object : result) {
                        System.out.println(object.getClass());
                        if (object.getClass().equals(UserRating.class)) {
                            ratings.add((UserRating) object);
                        }
                    }
                    myRatings.add(ratings);
                }
            });
            //updateList();
        }

    }
}
