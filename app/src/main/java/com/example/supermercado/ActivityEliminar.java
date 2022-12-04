package com.example.supermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.supermercado.Admin.ActivityPerfilAdmin;
import com.example.supermercado.Repartidor.ActivityPerfilRepartidor;

public class ActivityEliminar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        if(getIntent().getStringExtra("rangouser").equals("Administrador")) {
            retornar1();
        }
        else {
            if(getIntent().getStringExtra("rangouser").equals("Repartidor")) {
                retornar2();
            }
        }
    }
    private void retornar1() {
        Intent intent=new Intent(this, ActivityPerfilAdmin.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    private void retornar2() {
        Intent intent=new Intent(this, ActivityPerfilRepartidor.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
}