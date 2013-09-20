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
	
	public static String imagePath;
	
	/**
	 * Helper method for saving image from canvas to .png on filesystem
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
				imagePath = "0";
				return true;
			} else {
				FileOutputStream out = new FileOutputStream(file);
				b.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				imagePath = file.getPath();
				return true;
			}
		} catch (Exception ex) {
			Log.i("SAVE ex", ex.toString());
			imagePath = "";
			return false;
		}
	}
	
	/**
	 * Helper function for creating directory path
	 * @return File object
	 */
	
	private File makeFile() {
		String path = Environment.getExternalStorageDirectory().toString();
	    File dir = new File(path + "/Brainwriter/Drawings");    
	    dir.mkdirs();
	    String fname = "BW" + UUID.randomUUID() + ".png";
	    File file = new File (dir, fname);
	    if (file.exists ()) file.delete ();
	    return file;
	}
	
}
