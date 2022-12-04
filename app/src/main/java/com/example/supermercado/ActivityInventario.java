package com.example.supermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.Admin.ActivityAdministrador;
import com.example.supermercado.Repartidor.Adaptador;
import com.example.supermercado.configuracion.productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityInventario extends AppCompatActivity {
    private ArrayList<productos> productoslista;
    private ArrayList<String> id;
    private ArrayList<String> nombre;
    private ArrayList<String> precio;
    private ArrayList<String> cantidad;
    private ArrayList<String> foto;
    private ListView lista;
    private String usuario,rango;
    private EditText buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        lista=(ListView) findViewById(R.id.lista1_basedatos906);
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        buscar=(EditText) findViewById(R.id.buscador_texto906);
        ObtenerLista1();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent modificar=new Intent(view.getContext(),ActivityModificarInventario.class);
                modificar.putExtra("id",id.get(i));
                modificar.putExtra("nombre",nombre.get(i));
                modificar.putExtra("precio",precio.get(i));
                modificar.putExtra("cantidad",cantidad.get(i));
                modificar.putExtra("foto",foto.get(i));
                modificar.putExtra("user",usuario);
                modificar.putExtra("rango",rango);
                startActivity(modificar);
            }
        });
    }
    public void buscar_producto906(View view){
        if(buscar.getText().toString()==null){
            ObtenerLista1();
        }
        else{
            if(buscar.getText().toString()!=null){
                ObtenerLista2();
            }
        }
    }
    private void ObtenerLista1() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://apk.salasar.xyz:25565/inventario_productos.php?codigo=0123456789", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("productos");
                    productos comida=null;
                    productoslista=new ArrayList<productos>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new productos(
                                rowcontacto.getString("id"),
                                rowcontacto.getString("nombre"),
                                rowcontacto.getString("precio"),
                                rowcontacto.getString("cantidad"),
                                rowcontacto.getString("foto")
                        );
                        productoslista.add(comida);
                    }
                    fllList();
                }
                catch (Throwable error){
                    Toast.makeText(getApplicationContext(), "No nada", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No nada", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ObtenerLista2() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/inventario_productos_buscar.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("productos");
                    productos comida=null;
                    productoslista=new ArrayList<productos>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new productos(
                                rowcontacto.getString("id"),
                                rowcontacto.getString("nombre"),
                                rowcontacto.getString("precio"),
                                rowcontacto.getString("cantidad"),
                                rowcontacto.getString("foto")
                        );
                        productoslista.add(comida);
                    }
                    fllList();
                }
                catch (Throwable error){
                    Toast.makeText(getApplicationContext(), "No nada con esa letra", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("buscar", buscar.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void fllList() {
        id=new ArrayList<String>();
        nombre=new ArrayList<String>();
        precio=new ArrayList<String>();
        cantidad=new ArrayList<String>();
        foto=new ArrayList<String>();
        for(int i=0;i<productoslista.size();i++){
            id.add(
                    productoslista.get(i).getId()
            );
            nombre.add(
                    productoslista.get(i).getNombre()
            );
            precio.add(
                    productoslista.get(i).getPrecio()
            );
            cantidad.add(
                    productoslista.get(i).getCantidad()
            );
            foto.add(
                    productoslista.get(i).getFoto()
            );
        }
        lista.setAdapter(new Adaptador(this,id,nombre,precio,cantidad,foto));

    }
    public void volver906(View view){
        if(rango.equals("Usuario")) {
            Intent intent = new Intent(this, ActivityIniciar.class);
            intent.putExtra("user", usuario);
            intent.putExtra("rango", rango);
            startActivity(intent);
        }
        else {
            if(rango.equals("Administrador")) {
                Intent intent = new Intent(this, ActivityAdministrador.class);
                intent.putExtra("user", usuario);
                intent.putExtra("rango", rango);
                startActivity(intent);
            }
            else{
                if(rango.equals("Repartidor")){
                    Intent intent = new Intent(this, Repartidor_envio.class);
                    intent.putExtra("user", usuario);
                    intent.putExtra("rango", rango);
                    startActivity(intent);
                }
            }
        }
    }
    public void crear(View view){
        Intent intent = new Intent(this, ActivityCrearProducto.class);
        intent.putExtra("user", usuario);
        intent.putExtra("rango", rango);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}