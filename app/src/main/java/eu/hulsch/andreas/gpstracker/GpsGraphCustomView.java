package eu.hulsch.andreas.gpstracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Andreas Hulsch on 4/18/2016.
 */
public class GpsGraphCustomView extends View implements LocationList.ILocationDataChangedListener
{

    private LocationList locationList;

    private float width_height;
    private float[] kmh_lines_points;
    private float[] speed_graph_points;
    private float[] average_line_points;

    /* paints and so on */
    private Paint white_paint;
    private Paint black_paint;
    private Paint green_paint;
    private Paint red_paint;

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
        // 6 lines * 4
        kmh_lines_points = new float[24];
        this.speed_graph_points = new float[0];
        this.average_line_points = new float[4];

        /** paints **/
        white_paint = new Paint();
        white_paint.setColor(Color.WHITE);
        black_paint = new Paint();
        black_paint.setColor(Color.BLACK);
        green_paint = new Paint();
        green_paint.setColor(Color.GREEN);
        green_paint.setStyle(Paint.Style.STROKE);
        green_paint.setStrokeWidth(1.0f);
        red_paint = new Paint();
        red_paint.setColor(Color.RED);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(0, 0, 0, 0);

        // force the view to be square in size
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
        setMeasuredDimension((int)this.width_height, (int)this.width_height);

        float cell_width_height = this.width_height/6;
        for(int i = 0; i < this.kmh_lines_points.length; i=i+4)
        {
            // first point x, y
            this.kmh_lines_points[i] = 0f;
            this.kmh_lines_points[i+1] = cell_width_height * (i/4);
            // second point x, y
            this.kmh_lines_points[i+2] = this.width_height ;
            this.kmh_lines_points[i+3] = cell_width_height*(i/4);
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, this.width_height, this.width_height, this.black_paint);
        canvas.drawLines(this.kmh_lines_points, this.white_paint);

        if(this.speed_graph_points.length > 0)
        {
            canvas.drawLines(this.speed_graph_points, this.green_paint);
            canvas.drawLines(this.average_line_points, this.red_paint);
        }

        // canvas.drawPath(this.speed_graph, this.green_paint);

    }

    @Override
    public void onLocationDataChanged()
    {
        if(this.locationList.getCustomLocations().size() == 0)
        {
            return;
        }
        speed_graph_points = new float[(this.locationList.getCustomLocations().size()*4)-4];

        float margin_between_points;
        if(this.locationList.getCustomLocations().size() > 1)
        {
            margin_between_points = this.width_height / (this.locationList.getCustomLocations().size()-1);
        }
        else
        {
            margin_between_points = this.width_height / (this.locationList.getCustomLocations().size());
        }

        for(int i = 0; i < this.speed_graph_points.length; i = i+4)
        {
            // first point x, y
            this.speed_graph_points[i] = margin_between_points * (i/4);
            this.speed_graph_points[i + 1] = (this.width_height / 60) * (60 - locationList.getCurrentSpeed(i / 4));
            // second point x, y
            this.speed_graph_points[i+2] = margin_between_points * ((i+4)/4);
            this.speed_graph_points[i+3] = (this.width_height / 60) * (60 - locationList.getCurrentSpeed((i+4) / 4));
        }

        // average line points
            // startX, startY
        this.average_line_points[0] = 0f;
        this.average_line_points[1] = (this.width_height / 60) * (60 - locationList.getAverageSpeed());
            // endX, endY
        this.average_line_points[2] = this.width_height;
        this.average_line_points[3] = (this.width_height / 60) * (60 - locationList.getAverageSpeed());


        invalidate();



    }

    public void setLocationList(LocationList locationList)
    {
        this.locationList = locationList;
        this.locationList.addLocationDataChangedListener(this);
    }

}
