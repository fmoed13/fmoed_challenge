package org.foi.fmoed.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;

/**
 * 
 * 
 *
 */

public class MultimediaManager extends Activity {
	
	/**
	 * 
	 * @param finalBitmap
	 */
	
	public boolean saveImage(Bitmap finalBitmap) {

		File file = makeFile();
		
	    try {
	           FileOutputStream out = new FileOutputStream(file);
	           finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
	           out.flush();
	           out.close();
	           return true;

	    } catch (Exception e) {
	           e.printStackTrace();
	           return false;
	    }
	}
	
	/**
	 * 
	 * @param v
	 * @param setWallpaper
	 * @return
	 */
	public boolean saveImage(View v, boolean setWallpaper) {

		File file = makeFile();
		
		try {
			Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
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
				return true;
			} else {
				FileOutputStream out = new FileOutputStream(file);
				b.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
				return true;
			}
		} catch (Exception ex) {
			Log.i("SAVE ex", ex.toString());
			return false;
		}
	}
	
	private File makeFile() {
		String path = Environment.getExternalStorageDirectory().toString();
	    File dir = new File(path + "/Images");    
	    dir.mkdirs();
	    String fname = "BW" + UUID.randomUUID() + ".png";
	    File file = new File (dir, fname);
	    if (file.exists ()) file.delete ();
	    return file;
	}
	
}
