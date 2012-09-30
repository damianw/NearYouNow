package org.mhackers.nyn;

import android.app.*;
import android.app.ActionBar.*;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import android.widget.TextView;
import com.cartodb.*;
import com.cartodb.impl.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity implements LocationListener
{
    private LocationManager locationManager;
    private Location myLocation;
    private String provider;
    TabListener<ListingListFragment> listingListener;
    private final String CARTO_API_KEY = "71396cdae0d97a3b4f1f1618f6e6d0dc9f53e684";
    private ActionBar actionBar;
    private ListingListFragment myListingFrag;
    CartoDBClientIF myClient;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        initCartoDB();
        initTabs();
        initLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        myListingFrag.updateLocation(location, myClient);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onProviderEnabled(String provider) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onProviderDisabled(String provider) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            //latituteField.setText("Location not available");
            //longitudeField.setText("Location not available");
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    
    private void initTabs() {
        listingListener = new TabListener<ListingListFragment>(
                this, "listinglist", ListingListFragment.class);
        ActionBar.Tab tab = actionBar.newTab()
                .setText("Listings")
                .setTabListener(listingListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab()
                .setText("Requests")
                .setTabListener(new TabListener<RequestListFragment>(
                this, "requestlist", RequestListFragment.class));
        actionBar.addTab(tab);
        myListingFrag = (ListingListFragment)listingListener.getFragment();
        myListingFrag.setClient(myClient);
    }

    private void initCartoDB() {
        myClient = null;
        try {
            myClient = new ApiKeyCartoDBClient("lucaspa@umich.edu", CARTO_API_KEY);
        } catch (CartoDBException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
