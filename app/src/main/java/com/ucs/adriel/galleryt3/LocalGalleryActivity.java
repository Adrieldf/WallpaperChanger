package com.ucs.adriel.galleryt3;

import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class LocalGalleryActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnLocalGalleryApplyWP;
    private final int GALERIA_IMAGENS = 1;
    private File filePhoto = null;
    WallpaperManager wallpaperManager;
    Bitmap photoBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_gallery);
        this.imageView = findViewById(R.id.pvLocalGallery);
        this.btnLocalGalleryApplyWP = findViewById(R.id.btnLocalGalleryApplyWP);
        this.btnLocalGalleryApplyWP.setEnabled(false);

        wallpaperManager  = WallpaperManager.getInstance(getApplicationContext());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null,
                    null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            filePhoto = new File(picturePath);
            setPhoto(filePhoto.getAbsolutePath());
        }
    }
    private void setPhoto(String path) {
        this.photoBmp = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(this.photoBmp);
    }

    public void searchImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALERIA_IMAGENS);
        this.btnLocalGalleryApplyWP.setEnabled(true);
    }
    public void onClickLocalGallerySetWP(View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Bitmap bmp = Bitmap.createScaledBitmap(this.photoBmp, width, height, false);

        wallpaperManager = WallpaperManager.getInstance(this);

        try {
            wallpaperManager.setBitmap(bmp);
            wallpaperManager.suggestDesiredDimensions(width, height);
            Toast.makeText(view.getContext(),"Papel de parede aplicado com sucesso",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
