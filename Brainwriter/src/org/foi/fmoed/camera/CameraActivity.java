package org.foi.fmoed.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import org.foi.fmoed.R;
import org.foi.fmoed.managers.MultimediaManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

    private int CAMERA_PIC_REQUEST = 5;
    private ImageButton savePhotoButton;
    private ImageButton cancelRequestButton;
    private ImageButton retakePhotoButton;

    private ImageView imageView;
    private byte[] imageBytes = null;
    
    private MultimediaManager mm;


    public String PICTURES_DIR = "Pictures/";

    public String path = Environment.getExternalStorageDirectory() + "/";
    UUID image_id;


    /**
     * onCreate event: Initiatialize all the components needed for interaction with camera API.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);

        savePhotoButton = (ImageButton) findViewById(R.id.savePhotoButton);
        cancelRequestButton = (ImageButton) findViewById(R.id.cancelRequestButton);
        retakePhotoButton = (ImageButton) findViewById(R.id.retakePhotoButton);

        this.imageView = (ImageView) findViewById(R.id.imageView);


        Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        image_id = UUID.randomUUID();
        Uri uriSavedImage = Uri.fromFile(new File(path + image_id));

        camera.putExtra("output", uriSavedImage);
        startActivityForResult(camera, CAMERA_PIC_REQUEST);

        savePhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	String status = (mm.saveImage(Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888))) ? "Wallpaper has been setted up" : "Error Saving Image";
    			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
            }
        });

        cancelRequestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: go to previous activity
            }
        });

        retakePhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: restart this activity
            }
        });
    }

    /**
     * onActivityResult event: When return from camera intent this event gets executed.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = BitmapFactory.decodeFile(path + image_id);
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageBytes = stream.toByteArray();
        }

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_CANCELED) {
            // TODO: go to previous activity
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
