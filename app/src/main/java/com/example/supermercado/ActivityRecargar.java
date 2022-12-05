package com.example.supermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.Admin.ActivityAdministrador;

import java.util.HashMap;
import java.util.Map;

public class ActivityRecargar extends AppCompatActivity {
    private EditText usuario,recargar;
    private String user,rango;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recargar);
        user=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        usuario=(EditText) findViewById(R.id.usuariotext222);
        recargar=(EditText) findViewById(R.id.creditotext222);
    }
    public boolean validar(String dato, int numero){
        String opcion1="";
        switch (numero){
            case 1:{
                return dato.matches(opcion1);
            }
            default:{
                return false;
            }
        }
    }
    public void verficar222(View view){
        if(validar(usuario.getText().toString(),1)){
            Toast.makeText(this,"Usuario no valido",Toast.LENGTH_SHORT).show();
        }
        else{
            if(validar(recargar.getText().toString(),1)){
                Toast.makeText(this,"Pon un valor en lempiras",Toast.LENGTH_SHORT).show();
            }
            else{
                credito();
            }
        }
    }
    public void credito(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/credito.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Enviado", Toast.LENGTH_LONG).show();
                recargar.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", usuario.getText().toString());
                parametros.put("recargar",recargar.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void salir222(View view){
        Intent intent=new Intent(this, ActivityAdministrador.class);
        intent.putExtra("user",user);
        intent.putExtra("rango",rango);
        startActivity(intent);
    }
}