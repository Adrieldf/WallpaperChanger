package com.ucs.adriel.galleryt3;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnCameraApplyWP;

    private final int CAMERA = 3;
    private File filePhoto = null;

    WallpaperManager wallpaperManager;
    Bitmap photoBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageView = findViewById(R.id.pvCamera);
        this.btnCameraApplyWP = findViewById(R.id.btnCameraApplyWP);
        this.btnCameraApplyWP.setEnabled(false);

        wallpaperManager  = WallpaperManager.getInstance(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA) {
            sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(filePhoto)));
            setPhoto(filePhoto.getAbsolutePath());
        }
    }
    private void setPhoto(String path) {
        this.photoBmp = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(this.photoBmp);
    }
    public void openSystemCamera(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                filePhoto = createFile();
            } catch (IOException ex) {
                showAlert("Erro", "Erro salvando foto");
            }
            if (filePhoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".provider", filePhoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }
        btnCameraApplyWP.setEnabled(true);
    }
    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_Hhmmss").format(new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(pasta.getPath() + File.separator + "JPG_" + timeStamp + ".jpg");
    }
    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public void onClickCameraSetWP(View view) {

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
