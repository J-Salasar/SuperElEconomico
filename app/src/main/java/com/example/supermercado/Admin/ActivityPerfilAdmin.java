package com.example.supermercado.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.ActivityEliminar;
import com.example.supermercado.R;
import com.example.supermercado.Repartidor.AdaptadorAdmin;
import com.example.supermercado.Repartidor.AdaptadorCarrito;
import com.example.supermercado.configuracion.carrito;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityPerfilAdmin extends AppCompatActivity {
    private ArrayList<validar_sesion> productoslista;
    private ArrayList<String> nombre;
    private ArrayList<String> userdb;
    private ArrayList<String> foto;
    private ListView lista;
    private EditText user;
    private String usuario,rango,rangouser="Administrador";;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_admin);
        lista=(ListView) findViewById(R.id.lista2020);
        user=(EditText) findViewById(R.id.user2020);
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        ObtenerLista1();
    }
    public void salir2020(View view){
        Intent intent=new Intent(this,ActivityAdministrador.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    private void ObtenerLista1() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://apk.salasar.xyz:25565/usuarios_admin.php?codigo=0123456789", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("usuarios");
                    validar_sesion comida=null;
                    productoslista=new ArrayList<validar_sesion>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new validar_sesion(
                                rowcontacto.getString("nombre")+" "+rowcontacto.getString("apellido"),
                                rowcontacto.getString("usuario"),
                                rowcontacto.getString("foto")
                        );
                        productoslista.add(comida);
                    }
                    fllList();
                }
                catch (Throwable error){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void fllList() {
        nombre=new ArrayList<String>();
        userdb=new ArrayList<String>();
        foto=new ArrayList<String>();
        for(int i=0;i<productoslista.size();i++){
            nombre.add(
                    productoslista.get(i).getValidar()
            );
            userdb.add(
                    productoslista.get(i).getEstado()
            );
            foto.add(
                    productoslista.get(i).getRango()
            );
        }
        lista.setAdapter(new AdaptadorAdmin(this,nombre,userdb,foto,usuario,rango,rangouser));
    }
    public void agregarusuario(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/agregar_admin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(), ActivityEliminar.class);
                intent.putExtra("user",usuario);
                intent.putExtra("rango",rango);
                intent.putExtra("rangouser", rangouser);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", user.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}