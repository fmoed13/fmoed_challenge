package org.foi.fmoed.canvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import org.foi.fmoed.R;
import org.foi.fmoed.canvas.ColorPicker.OnColorChangedListener;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Drawer extends Activity implements OnColorChangedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.drawer);
		
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}

		float[] outerR = new float[] { 12, 12, 12, 12, 0, 0, 0, 0 };
		RectF inset = new RectF(6, 6, 6, 6);
		float[] innerR = new float[] { 12, 12, 0, 0, 12, 12, 0, 0 };

		mDrawables = new ShapeDrawable[2];

		mDrawables[0] = new ShapeDrawable(new RoundRectShape(outerR, inset,
				null));
		mDrawables[0].getPaint().setShader(makeSweep());

		mDrawables[1] = new ShapeDrawable(new RoundRectShape(outerR, inset,
				innerR));
		mDrawables[1].getPaint().setShader(makeLinear());

		PathEffect mPathEffect1 = new DiscretePathEffect(10, 4);
		PathEffect mPathEffect2 = new CornerPathEffect(4);

		mDrawables[0].getPaint().setPathEffect(
				new ComposePathEffect(mPathEffect2, mPathEffect1));
		LinearLayout layout = (LinearLayout) findViewById(R.id.myDrawing);
		layout.setBackgroundDrawable(mDrawables[0]);
		activity = this;
		mView = new DrawingView(this);
		layout.addView(mView, new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		init();

		mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
		mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}

	private static Shader makeLinear() {
		return new LinearGradient(0, 0, 50, 50, new int[] { 0xFFFF0000,
				0xFF00FF00, 0xFF0000FF }, null, Shader.TileMode.MIRROR);
	}

	private static Shader makeSweep() {
		return new SweepGradient(150, 25, new int[] { 0xFFFF0000, 0xFF00FF00,
				0xFF0000FF, 0xFFFF0000 }, null);
	}

	private ShapeDrawable[] mDrawables;
	private MaskFilter mEmboss;
	private MaskFilter mBlur;
	private Paint mPaint;
	Activity activity;
	View mView;
	int penColor = Color.DKGRAY;
	int penSize = 7;

	private void init() {
		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(penColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(penSize);
	}

	class DrawingView extends View {
		private Path path;
		private Bitmap mBitmap;
		private Canvas mCanvas;
		int width, height;

		public DrawingView(Context context) {
			super(context);
			path = new Path();
			mBitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			this.setBackgroundColor(Color.WHITE);
		}

		private ArrayList<PathWithPaint> _graphics1 = new ArrayList<PathWithPaint>();

		public boolean onTouchEvent(MotionEvent event) {
			PathWithPaint pp = new PathWithPaint();
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				path.moveTo(event.getX(), event.getY());
				path.lineTo(event.getX(), event.getY());
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				path.lineTo(event.getX(), event.getY());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				path.lineTo(event.getX(), event.getY());
				mCanvas.drawPath(path, mPaint);
				pp.setPath(path);
				pp.setmPaint(mPaint);
				_graphics1.add(pp);
			}
			invalidate();
			return true;
		}

		@Override
		public void onDraw(Canvas canvas) {

			canvas.drawBitmap(mBitmap, 0, 0, mPaint);

			if (_graphics1.size() > 0) {
				canvas.drawPath(
						_graphics1.get(_graphics1.size() - 1).getPath(),
						_graphics1.get(_graphics1.size() - 1).getmPaint());
			}

			Display display = getWindowManager().getDefaultDisplay();
			int width = 300;
			int height = 425;
			try {
				Method mGetRawH = Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				width = (Integer) mGetRawW.invoke(display);
				height = (Integer) mGetRawH.invoke(display);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			int x = 0;
			int y = 0;
			if (curveDrawable == 1) {
				mDrawables[0].setBounds(x, y, x + width, (y + height) - 35);
				mDrawables[0].draw(canvas);
			}
			if (curveDrawable == 2) {
				mDrawables[1].setBounds(x, y, x + width, (y + height) - 35);
				mDrawables[1].draw(canvas);
			}
		}

		public void clearView() {
			_graphics1.removeAll(_graphics1);
			mBitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			path = new Path();
			invalidate();
		}

		@Override
		public void invalidate() {
			super.invalidate();
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			width = w;
			height = h;
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}

		public void colorChange() {
			path = new Path();
			invalidate();
		}
		
		public void saveAsJpg(File file, View v, boolean setWallpaper) {

			try {
				Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
						Bitmap.Config.ARGB_8888);
				FileOutputStream out = new FileOutputStream(file);
				Canvas c = new Canvas(b);
				v.draw(c);
		           
				if (setWallpaper) {
					WallpaperManager myWallpaperManager = WallpaperManager
							.getInstance(getApplicationContext());
					try {
						myWallpaperManager.setBitmap(b);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Toast.makeText(activity, "Wallpaper has been setted up", Toast.LENGTH_SHORT).show();
				} else {
					b.compress(Bitmap.CompressFormat.JPEG, 100, out);
			           out.flush();
			           out.close();
					Toast.makeText(activity, "Image Saved", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception ex) {
				Toast.makeText(activity, "Error Saving Image", Toast.LENGTH_SHORT).show();
				Log.i("SAVE ex", ex.toString());
			}
		}
	}

	private boolean isDrawBorder = true;
	int bgColor = Color.WHITE;

	public void colorChanged(String key, int color) {

		if (key.equals("Pen Color")) {
			mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(color);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(penSize);
			penColor = color;
		} else if (key.equals("Bg Color")) {
			if (isDrawBorder)
				((DrawingView) mView).invalidate();
			else
				((DrawingView) mView).clearView();
			((DrawingView) mView).setBackgroundColor(color);
			bgColor = color;
			isDrawBorder = false;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, 0, 0, "Color").setShortcut('3', 'c');
		menu.add(0, 1, 0, "BG Color").setShortcut('3', 'c');
		menu.add(0, 2, 0, "Emboss").setShortcut('4', 's');
		menu.add(0, 3, 0, "Blur").setShortcut('5', 'z');
		menu.add(0, 4, 0, "Erase").setShortcut('5', 'z');
		menu.add(0, 5, 0, "SrcATop").setShortcut('5', 'z');

		SubMenu border = menu.addSubMenu(0, 9, 0, "Border");

		menu.add(0, 6, 0, "Set as Wallpaper").setShortcut('5', 'z');
		menu.add(0, 7, 0, "Save").setShortcut('5', 'z');
		menu.add(0, 8, 0, "Clear").setShortcut('5', 'z');

		border.add(0, 10, 0, "Curve").setShortcut('5', 'z');
		border.add(0, 11, 0, "Rectangle").setShortcut('5', 'z');
		border.add(0, 12, 0, "No Border").setShortcut('5', 'z');

		return true;
	}

	private int curveDrawable = 0;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/Images");    
	    myDir.mkdirs();
	    String fname = "image" + UUID.randomUUID() + ".jpg";
	    File file = new File (myDir, fname);
	    if (file.exists ()) file.delete (); 

		switch (item.getItemId()) {

		case 0:
			((DrawingView) mView).colorChange();
			new ColorPicker(activity, this, "Pen Color", penColor, Color.DKGRAY).show();
			return true;

		case 1:
			new ColorPicker(activity, this, "Bg Color", bgColor, Color.WHITE)
					.show();
			return true;

		case 2:
			if (mPaint.getMaskFilter() != mEmboss) {
				mPaint.setMaskFilter(mEmboss);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;

		case 3:
			if (mPaint.getMaskFilter() != mBlur) {
				mPaint.setMaskFilter(mBlur);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;

		case 4:
			((DrawingView) mView).colorChange();
			mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(bgColor);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(15);
			return true;

		case 5:
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
			mPaint.setAlpha(0x80);
			return true;

		case 6:
			((DrawingView) mView).saveAsJpg(file, mView, true);
			return true;

		case 7:
			((DrawingView) mView).saveAsJpg(file, mView, false);
			return true;

		case 8:
			((DrawingView) mView).clearView();
			return true;

		case 10:
			isDrawBorder = true;
			colorChanged("Bg Color", bgColor);
			curveDrawable = 1;
			return true;

		case 11:
			isDrawBorder = true;
			colorChanged("Bg Color", bgColor);
			curveDrawable = 2;
			return true;

		case 12:
			isDrawBorder = true;
			colorChanged("Bg Color", bgColor);
			curveDrawable = 0;
			return true;

		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		super.onBackPressed();
	}

}