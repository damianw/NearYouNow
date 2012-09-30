/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import android.location.Location;
import com.cartodb.CartoDBClientIF;
import com.cartodb.CartoDBException;
import com.cartodb.model.CartoDBResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damian
 */
public abstract class Utils {

    public CartoDBResponse<Map<String, Object>> makeQuery(String query, CartoDBClientIF client){
        CartoDBResponse<Map<String, Object>> res = null;
        try {
            res = client.request(query);
        } catch (CartoDBException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public ArrayList<Listing> getListings(Location location, CartoDBClientIF client){
        ArrayList<Listing> result = null;
        CartoDBResponse<Map<String, Object>> res = makeQuery("SELECT * FROM table WHERE ST_DWithin(ST_GeogFromText('SRID=4326;POINT(" + location.getLatitude() + "," + location.getLongitude() + ")'), geography(latlon), 12070)", client);
        //12070 meters is roughly five miles
        List<Map<String, Object>> rows = res.getRows();
        for (Map map: rows){
            
        }
    }
}


