package eu.hulsch.andreas.gpstracker;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Hulsch on 23.04.2016.
 */
public class LocationList
{
    private List<CustomLocation> customLocations;
    private int maxListSize;
    private float averageSpeed;

    private List<ILocationDataChangedListener> locationDataChangedListener_list;


    public LocationList(int list_size)
    {
        this.customLocations = new ArrayList<CustomLocation>();
        this.maxListSize = list_size;
        this.locationDataChangedListener_list = new ArrayList<ILocationDataChangedListener>();
    }

    public void reset()
    {
        this.customLocations = new ArrayList<CustomLocation>();
    }

    public void addLocation(Location location)
    {
        if(customLocations.size()>= maxListSize)
        {
            customLocations.remove(0);
        }
        if(customLocations.size() == 0)
        {
            CustomLocation customLocation = new CustomLocation(location);
            customLocations.add(customLocation);
        }
        else
        {
            CustomLocation customLocation = new CustomLocation(location, customLocations.get(customLocations.size()-1).getLocation());
            customLocations.add(customLocation);
        }
        this.averageSpeed = calculateAvarageSpeed();

        // notify Listener
        for(ILocationDataChangedListener listener : this.locationDataChangedListener_list)
        {
            listener.onLocationDataChanged();
        }
    }

    private float calculateAvarageSpeed()
    {
        float overall=0;
        for(int i = 0; i < this.customLocations.size(); i++)
        {
            overall+= customLocations.get(i).getCurrent_speed();
        }
        return overall/customLocations.size();
    }

    public float getCurrentSpeed(int at)
    {
        return customLocations.get(at).getCurrent_speed();
    }

    public List<CustomLocation> getCustomLocations()
    {
        return customLocations;
    }
    public float getAverageSpeed()
    {
        return averageSpeed;
    }

    /*** interface to notify the customview ***/
    public interface ILocationDataChangedListener
    {
        void onLocationDataChanged();
    }
    public void addLocationDataChangedListener(ILocationDataChangedListener listener)
    {
        this.locationDataChangedListener_list.add(listener);
    }
    public void removeLocationDataChangedListener(ILocationDataChangedListener listener)
    {
        this.locationDataChangedListener_list.remove(listener);
    }

    /*** CustomLocation class including the speed ***/
    private class CustomLocation
    {
        private float current_speed;
        private Location location;

        public CustomLocation(Location current_location)
        {
            this.location = current_location;
            this.current_speed = 0f;
        }
        public CustomLocation(Location current_location, Location last_locLocation)
        {
            this.location = current_location;
            this.current_speed = calculateCurrentSpeed(current_location, last_locLocation);
        }
        private float calculateCurrentSpeed(Location current_location, Location last_Location)
        {
            float speed =
                    (float) Math.abs(
                    (last_Location.distanceTo(current_location) / (last_Location.getTime() - current_location.getTime()))
                    * 1000 * 3.6);

            return speed;
        }

        public Location getLocation()
        {
            return location;
        }
        public float getCurrent_speed()
        {
            return current_speed;
        }
    }
}



