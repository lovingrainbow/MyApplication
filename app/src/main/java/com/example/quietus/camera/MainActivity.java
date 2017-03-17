package com.example.quietus.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10;
    private String APP_TAG = "IMet";
    private ImageView ivPreview;
    private String photoFileName = "TestPhoto.jpg";
    private TextView tvFilePath;
    private String mCurrentPhotoPath;
    private Bitmap mImageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPreview = (ImageView)findViewById(R.id.ivPreview);
    }

    public void TakePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");        }
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }
        return null;
    }


    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                ivPreview.setImageBitmap(takenImage);
        }

    }
}
