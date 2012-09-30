/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mhackers.nyn;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.RadioGroup.OnCheckedChangeListener;

import org.mhackers.nyn.VenmoSDK.VenmoResponse;

/**
 *
 * @author Jonathan
 */
public class tryVemnoAct extends Activity {

    public String app_id = "1171";
    public String app_name = "nearyounow";
    public String recipient = "2484957981";
    public String amount = "1";
    public String note = "test1";
    public String txn = "charge";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        try {
            Intent venmoIntent = VenmoSDK.openVenmoPayment(app_id, app_name, recipient, amount, note, txn);
            startActivityForResult(venmoIntent, 1); //1 is the requestCode we are using for Venmo. Feel free to change this to another number. 
        } catch (android.content.ActivityNotFoundException e) //Venmo native app not install on device, so let's instead open a mobile web version of Venmo in a WebView
        {
            Intent venmoIntent = new Intent(MainActivity.this, VenmoWebViewActivity.class);
            String venmo_uri = VenmoSDK.openVenmoPaymentInWebView(app_id, app_name, recipient, amount, note, txn);
            venmoIntent.putExtra("url", venmo_uri);
            startActivityForResult(venmoIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: { //1 is the requestCode we picked for Venmo earlier when we called startActivityForResult
                if (resultCode == RESULT_OK) {
                    String signedrequest = data.getStringExtra("signedrequest");
                    if (signedrequest != null) {
                        VenmoResponse response = (new VenmoSDK()).validateVenmoPaymentResponse(signedrequest, app_secret);
                        if (response.getSuccess().equals("1")) {
                            //Payment successful.  Use data from response object to display a success message
                            String note = response.getNote();
                            String amount = response.getAmount();
                        }
                    } else {
                        String error_message = data.getStringExtra("error_message");
                        //An error ocurred.  Make sure to display the error_message to the user
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    //The user cancelled the payment
                }
                break;
            }
        }
    }
}
