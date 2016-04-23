package eu.hulsch.andreas.gpstracker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Andreas Hulsch on 4/18/2016.
 */
public class GpsGraphCustomView extends View
{
    private int width_height;

    public GpsGraphCustomView(Context context)
    {
        super(context);
        init();
    }
    public GpsGraphCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public GpsGraphCustomView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(0,0,0,0);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        if (height > width)
        {
            this.width_height = width;
        }
        else
        {
            this.width_height = height;
        }
        setMeasuredDimension(this.width_height, this.width_height);

    }
}
