package org.mhackers.nyn;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MainActivity extends Activity implements LocationListener
{
    private LocationManager locationManager;
    private Location myLocation;
    private String provider;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        
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
        partyListener = new TabListener<PartyListFragment>(
                this, "partylist", PartyListFragment.class);

        ActionBar.Tab tab = actionBar.newTab()
                .setText("Parties")
                .setTabListener(partyListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab()
                .setText("Friends")
                .setTabListener(new TabListener<FriendListFragment>(
                this, "friendlist", FriendListFragment.class));
        actionBar.addTab(tab);
        myPartyFrag = (PartyListFragment)partyListener.getFragment();
        myStore = CMStore.getStore();
    }
}
