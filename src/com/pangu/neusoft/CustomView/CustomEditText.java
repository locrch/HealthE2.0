package com.pangu.neusoft.CustomView;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText
{
	public CustomEditText(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CustomEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override  
    protected void onDraw(Canvas canvas) {  
        Paint paint = new Paint();  
        paint.setTextSize(20);  
        paint.setColor(Color.BLACK);  
        
        Paint line = new Paint();
        
        line.setColor(Color.rgb(14,85,114));
        
        canvas.drawText("题头:", 10, getHeight() / 2 + 5, paint);  
        
        canvas.drawLine(2, 3, getWidth()-2, getWidth()-3, line);
        
        super.onDraw(canvas);  
    }  
	
	
}
