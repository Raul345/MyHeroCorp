package com.herocorp.ui.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularImageView extends ImageView
{

	public CircularImageView(Context context)
	{
		super(context);
	}

	public CircularImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();

		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return; 
		}
	
		int w = getWidth();
		
		//if(((BitmapDrawable)drawable).getBitmap() != null)
			canvas.drawBitmap(getCroppedBitmap(((BitmapDrawable)drawable).getBitmap().copy(Config.ARGB_8888, true), w), 0,0, null);
	}

	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {

		if(bmp.getWidth() != radius || bmp.getHeight() != radius) {

			int width = Bitmap.createScaledBitmap(bmp, radius, radius, false).getWidth();
			int height = Bitmap.createScaledBitmap(bmp, radius, radius, false).getHeight();
			
			Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, width, height);

			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setDither(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.parseColor("#BAB399"));
			canvas.drawCircle(width / 2+0.7f, height / 2+0.7f,	width / 2+0.1f, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, radius, radius, false), rect, rect, paint);

			return output;

		} else {

			int width = Bitmap.createScaledBitmap(bmp, radius, radius, false).getWidth();
			int height = Bitmap.createScaledBitmap(bmp, radius, radius, false).getHeight();
			
			Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, width, height);

			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setDither(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.parseColor("#BAB399"));
			canvas.drawCircle(width / 2+0.7f, height / 2+0.7f,	width / 2+0.1f, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, radius, radius, false), rect, rect, paint);

			return output;
		}
	}
}
