package eu.hulsch.andreas.gpstracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Andreas Hulsch on 4/18/2016.
 */
public class GpsGraphCustomView extends View
{
    private int width_height;
    private float[] kmh_lines_points;

    /* paints */
    private Paint white_paint;
    private Paint black_paint;

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

        /** paints **/
        white_paint = new Paint();
        white_paint.setColor(Color.WHITE);
        black_paint = new Paint();
        black_paint.setColor(Color.BLACK);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(0,0,0,0);

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
        setMeasuredDimension(this.width_height, this.width_height);

        int cell_width_height = this.width_height/6;
        for(int i = 0; i < this.kmh_lines_points.length; i=i+4)
        {
            // first point x, y
            this.kmh_lines_points[i] = 0f;
            this.kmh_lines_points[i+1] = (float) (cell_width_height * (i/4));
            // second point x, y
            this.kmh_lines_points[i+2] = (float) this.width_height ;
            this.kmh_lines_points[i+3] = (float) (cell_width_height*(i/4));
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, this.width_height, this.width_height, this.black_paint);
        canvas.drawLines(this.kmh_lines_points, this.white_paint);
    }
}
