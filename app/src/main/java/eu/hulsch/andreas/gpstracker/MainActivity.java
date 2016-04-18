package eu.hulsch.andreas.gpstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView gps_active_tv;
    private TextView current_speed_tv;
    private TextView average_speed_tv;
    private TextView overall_time_tv;
    private Button tracking_btn;
    private GpsGraphCustomView gpsGraphCustomView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews()
    {
        gps_active_tv = (TextView) findViewById(R.id.gps_active);
        current_speed_tv = (TextView) findViewById(R.id.current_speed);
        average_speed_tv = (TextView) findViewById(R.id.average_speed);
        overall_time_tv = (TextView) findViewById(R.id.overall_time);
        tracking_btn = (Button) findViewById(R.id.btn_tracking);
        gpsGraphCustomView = (GpsGraphCustomView) findViewById(R.id.gps_graph_custom_view);
    }
}
