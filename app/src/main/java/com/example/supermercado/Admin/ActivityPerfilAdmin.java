package com.example.supermercado.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.supermercado.R;

public class ActivityPerfilAdmin extends AppCompatActivity {
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEFOTO=101;
    private String currentPhotoPath;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_admin);
    }



    public void volverMenuAdmin(View view)
    {
        startActivity(new Intent(ActivityPerfilAdmin.this,ActivityAdministrador.class));
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takePictureIntent, REQUESTTAKEFOTO);
        }
    }

    public void permisos_camara_admin(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUESTCODECAMARA);
        }
        else {
            dispatchTakePictureIntent();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODECAMARA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(getApplicationContext(), "Permiso Denegado", Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            finishAffinity();
        }
        return super.onKeyDown(keyCode,event);
    }

}