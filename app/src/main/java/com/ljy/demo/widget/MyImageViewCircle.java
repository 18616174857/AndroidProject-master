package com.ljy.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.ljy.demo.R;

public class MyImageViewCircle extends ImageView {
	private  Paint paint;
	private int mWidth;
	int mRadius;
	private int mHeight;
	String text;
	int alpha,Fstatr,Fstop,textsize;
	int bgcolor;
	int textcolor;

	private static final String TAG = "MyImageView";

	public MyImageViewCircle(Context context) {
		super(context);
		init("MyImageView(Context context)");
	}

	public MyImageViewCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init("MyImageView(Context context, AttributeSet attrs)");
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.MyYuan);
		text = mTypedArray.getString(R.styleable.MyYuan_yuan_text);
		 alpha = mTypedArray.getInt(R.styleable.MyYuan_yuan_alpha,100);
		 Fstatr= mTypedArray.getInt(R.styleable.MyYuan_yuan_Fstatr,40);
		Fstop= mTypedArray.getInt(R.styleable.MyYuan_yuan_Fstop,100);
		textsize= mTypedArray.getInt(R.styleable.MyYuan_yuan_textsize,30);
		 bgcolor= mTypedArray.getColor(R.styleable.MyYuan_yuan_bgcolor,Color.BLACK);
		textcolor=mTypedArray.getColor(R.styleable.MyYuan_yuan_bgcolor,Color.WHITE);


		mTypedArray.recycle();


	}

	public MyImageViewCircle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init("MyImageView(Context context, AttributeSet attrs, int defStyle)");
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.MyYuan);
	    text = mTypedArray.getString(R.styleable.MyYuan_yuan_text);
		alpha = mTypedArray.getInt(R.styleable.MyYuan_yuan_alpha,100);
		Fstatr= mTypedArray.getInt(R.styleable.MyYuan_yuan_Fstatr,40);
		Fstop= mTypedArray.getInt(R.styleable.MyYuan_yuan_Fstop,100);
		textsize= mTypedArray.getInt(R.styleable.MyYuan_yuan_textsize,30);
		bgcolor= mTypedArray.getColor(R.styleable.MyYuan_yuan_bgcolor,Color.BLACK);
		textcolor=mTypedArray.getColor(R.styleable.MyYuan_yuan_textcolor,Color.WHITE);
		mTypedArray.recycle();


	}

	private void init(String structName) {
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		mWidth = specSize;
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		mHeight = specSize;
		setMeasuredDimension(mWidth, mHeight);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		if (!(drawable instanceof BitmapDrawable)) {
			return;
		}
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();

		if (null == b) {
			return;
		}

		Bitmap bitmap = b.copy(Config.ARGB_8888, true);

		int min = Math.min(mWidth, mHeight);

		Bitmap roundBitmap = getCroppedBitmap(bitmap, min);
		canvas.drawBitmap(roundBitmap, 0, 0, null);


		paint=new Paint();
		paint.setColor(bgcolor);
		paint.setAlpha(alpha);
		paint.setStyle(Paint.Style.FILL);
		RectF re1= new RectF(0,0,mWidth,mWidth);
		canvas.drawArc(re1,Fstatr,Fstop,false,paint);
		paint.setAlpha(255);
		paint.setTextSize(textsize);
		paint.setColor(textcolor);
		canvas.drawText(text,(mWidth-text.length()*textsize)/2,mWidth-textsize/2,paint);

	}


	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		} else {
			sbmp = bmp;
		}
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

	private Bitmap createRoundConerImage(Bitmap source) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rect, mRadius, mRadius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}
}
