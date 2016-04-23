package eu.hulsch.andreas.gpstracker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private TextView gps_active_tv;
    private TextView current_speed_tv;
    private TextView average_speed_tv;
    private TextView overall_time_tv;
    private Button tracking_btn;
    private GpsGraphCustomView gpsGraphCustomView;

    private LocationManager locationManager;
    private boolean location_updates_active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListener();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location_updates_active = false;


    }

    private void initViews() {
        gps_active_tv = (TextView) findViewById(R.id.gps_active);
        current_speed_tv = (TextView) findViewById(R.id.current_speed);
        average_speed_tv = (TextView) findViewById(R.id.average_speed);
        overall_time_tv = (TextView) findViewById(R.id.overall_time);
        tracking_btn = (Button) findViewById(R.id.btn_tracking);
        gpsGraphCustomView = (GpsGraphCustomView) findViewById(R.id.gps_graph_custom_view);
    }

    private void initListener() {
        tracking_btn.setOnClickListener(this);
    }


    // OnClick Listener Tracking btn
    @Override
    public void onClick(View v) {
        if (location_updates_active)
        {
            stop_tracking();
        }
        else
        {
            start_tracking();
        }
    }

    private void start_tracking()
    {
        location_updates_active = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this);
        tracking_btn.setText(R.string.stop_tracking);
        gps_active_tv.setText(R.string.gps_active);
    }

    private void stop_tracking()
    {
        location_updates_active = false;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
        tracking_btn.setText(R.string.start_tracking);
        gps_active_tv.setText(R.string.gps_inactive);
    }


    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location)
    {
        // testing
        Toast.makeText(this,":" +location.getLongitude() + " " + location.getLatitude(), Toast.LENGTH_SHORT).show();


    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p/>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p/>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider)
    {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider)
    {
        if(provider == LocationManager.GPS_PROVIDER)
        {

        }
    }
}
