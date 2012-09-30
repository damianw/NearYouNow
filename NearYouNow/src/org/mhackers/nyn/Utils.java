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

    public static CartoDBResponse<Map<String, Object>> makeQuery(String query, CartoDBClientIF client){
        CartoDBResponse<Map<String, Object>> res = null;
        try {
            res = client.request(query);
        } catch (CartoDBException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
            ex.printStackTrace();
            System.err.println(ex);
        }
        return res;
    }
    
    public static CartoDBResponse<Map<String, Object>> getListingsQuery(Location location, CartoDBClientIF client){
        ArrayList<Map<String, Object>> result = null;
        //CartoDBResponse<Map<String, Object>> res = makeQuery("SELECT * FROM table WHERE ST_DWithin(ST_GeogFromText('SRID=4326;POINT(" + location.getLatitude() + "," + location.getLongitude() + ")'), geography(latlon), 12070)", client);
        CartoDBResponse<Map<String, Object>> res = makeQuery("select * from listings", client);
        if (res == null){
            System.out.println("There is nothing in the table");
        }
        //12070 meters is roughly five miles
        /*List<Map<String, Object>> rows = res.getRows();
        for (Map map: rows){
            result.add(map);
        }*/
        System.out.println(res.getTotal_rows());
        return res;
    }
    
    public static ArrayList<Listing> getListings(Location location, CartoDBClientIF client){
        List<Listing> result = null;
        CartoDBResponse<Map<String, Object>> res = getListingsQuery(location, client);
        List<Map<String, Object>> rows = res.getRows();
        for (int i = 0; i <= ){
            System.out.println("getting map");
            Object get = map.get("location");
            System.out.println(get.getClass());
            //Listing listing = new Listing((String)map.get("name"), (String)map.get("price"), );
            //result.add();
        }
        return result;
    }
}


