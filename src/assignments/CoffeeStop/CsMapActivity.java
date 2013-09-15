package assignments.CoffeeStop;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class CsMapActivity extends FragmentActivity 
	implements OnMyLocationButtonClickListener,
	ConnectionCallbacks,
	OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

	private GoogleMap gMap;
	private LocationClient locClient;
	
	private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         
            .setFastestInterval(16)   
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cs_map);
		setUpMapIfNeeded();
	}
	
	@Override
	protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        locClient.connect();
    }
	
	@Override
	public void onPause() {
        super.onPause();
        if (locClient != null) {
        	locClient.disconnect();
        }
    }
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
        	gMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) {
            	gMap.setMyLocationEnabled(true);
            	gMap.setOnMyLocationButtonClickListener(this);
            }
        }
    }
	
	
	
	private void setUpLocationClientIfNeeded() {
        if (locClient == null) { locClient = new LocationClient(getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cs_main, menu);
		return true;
	}
	
	public boolean onMyLocationButtonClick() {
		return false;
    }
	
	@Override
    public void onLocationChanged(Location loc) {
    	LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
    	CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
    	gMap.animateCamera(camUpdate); //Animate the camera to the location set.
    }
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		locClient.requestLocationUpdates(REQUEST,this);  
	}
	
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
