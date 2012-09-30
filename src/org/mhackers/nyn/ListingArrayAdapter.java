package org.mhackers.nyn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListingArrayAdapter extends ArrayAdapter<Listing> {
	private static final String tag = "ListingArrayAdapter";
	private static final String ASSETS_DIR = "images/";
	private Context context;
	private ImageView listingIcon;
	private TextView listingName;
	private String[] comments;
	private List<Listing> listings = new ArrayList<Listing>();

	public ListingArrayAdapter(Context context, int textViewResourceId,
			List<Listing> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.listings = objects;
	}

	public int getCount() {
		return this.listings.size();
	}

	public Listing getItem(int index) {
		return this.listings.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// ROW INFLATION
			Log.d(tag, "Starting XML Row Inflation ... ");
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listing_listitem, parent, false);
			Log.d(tag, "Successfully completed XML Row Inflation!");
		}

		// Get item
		/*Party party = getItem(position);
                //AverageRating rating = myAverageRatings.get(position);
		
		// Get reference to ImageView 
		partyIcon = (ImageView) row.findViewById(R.id.party_icon);
		
		// Get reference to TextView - party_name
		partyName = (TextView) row.findViewById(R.id.party_name);
		
		// Get reference to TextView - party_rating
		partyRating = (TextView) row.findViewById(R.id.party_rating);
                partyRatio = (TextView) row.findViewById(R.id.party_ratio);

		//Set party name
		partyName.setText(party.getName().toString());
		
		// Set party icon usign File path
		/*String imgFilePath = ASSETS_DIR + party.resourceId;
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets()
					.open(imgFilePath));
			partyIcon.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set party ratingiation
		partyRating.setText("Rating: " + party.getOvrating());
                partyRatio.setText("Ratio: " + party.getMfratio());
                String partyType = party.getPartytype();
                if (partyType.equals("Electronic")){
                    partyIcon.setImageResource(R.drawable.electronic);
                }
                else if (partyType.equals("Foam")){
                    partyIcon.setImageResource(R.drawable.bubbles);
                }
                else if (partyType.equals("Chill")){
                    partyIcon.setImageResource(R.drawable.friends);
                }
                else if (partyType.equals("Greek")){
                    partyIcon.setImageResource(R.drawable.greece);
                }
                else if (partyType.equals("House")){
                    partyIcon.setImageResource(R.drawable.house);
                }
                else if (partyType.equals("Wine")){
                    partyIcon.setImageResource(R.drawable.wine);
                }*/
                
                
                //FIX THIS SHIT
                
		return row;
	}
        
}