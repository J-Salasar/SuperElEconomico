package com.example.supermercado;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.Admin.ActivityAdministrador;
import com.example.supermercado.Repartidor.AdaptadorCarrito;
import com.example.supermercado.configuracion.carrito;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ActivityVerCarrito extends AppCompatActivity {
    private ArrayList<carrito> productoslista;
    private ArrayList<validar_sesion> productoslista1;
    private ArrayList<String> id;
    private ArrayList<String> nombre;
    private ArrayList<String> precio;
    private ArrayList<String> cantidad;
    private ArrayList<String> foto;
    private ArrayList<String> cantidad_actual;
    private ListView lista;
    private String usuario,rango,dinero;
    private TextView compra;
    private float total=0;
    private double lat,lon;
    private static final int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carrito);
        lista=(ListView) findViewById(R.id.lista2_basedatos);
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        dinero=getIntent().getStringExtra("dinero");
        compra=(TextView) findViewById(R.id.total80);
        ObtenerLista1();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
    private void ObtenerLista1() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://apk.salasar.xyz:25565/ver_carrito.php?codigo=0123456789", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("productos");
                    carrito comida=null;
                    productoslista=new ArrayList<carrito>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new carrito(
                                rowcontacto.getString("id"),
                                rowcontacto.getString("nombre"),
                                rowcontacto.getString("precio"),
                                rowcontacto.getString("cantidad"),
                                rowcontacto.getString("foto"),
                                rowcontacto.getString("cantidad_actual")
                        );
                        total+=(Float.parseFloat(comida.getCantidad())*(Float.parseFloat(comida.getPrecio())));
                        productoslista.add(comida);
                    }
                    compra.setText("Total: L. "+total);
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
        id=new ArrayList<String>();
        nombre=new ArrayList<String>();
        precio=new ArrayList<String>();
        cantidad=new ArrayList<String>();
        foto=new ArrayList<String>();
        cantidad_actual=new ArrayList<String>();
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
            cantidad_actual.add(
                    productoslista.get(i).getCantidad_actual()
            );
        }
        lista.setAdapter(new AdaptadorCarrito(this,id,nombre,precio,cantidad,foto,cantidad_actual,usuario,rango,dinero));
    }
    public void volver80(View view){
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
    public void pagar_compra(View view) {
        if (total > 0) {
            if (getIntent().getStringExtra("ubicacion") != null) {
                if (Float.parseFloat(dinero) >= total) {
                    lat = Double.parseDouble(getIntent().getStringExtra("latitud"));
                    lon = Double.parseDouble(getIntent().getStringExtra("longitud"));
                    cobrar();
                } else {
                    Toast.makeText(this, "Te falta dinero", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Ubicacion por favor", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Agrega algo para comprar",Toast.LENGTH_SHORT).show();
        }
    }
    public void cobrar(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/cobrar_pedido.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Pago realizado",Toast.LENGTH_SHORT).show();
                total=0;
                pago();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", usuario);
                parametros.put("dinero", String.valueOf(Float.parseFloat(dinero)-total));
                parametros.put("latitud",String.valueOf(lat));
                parametros.put("longitud",String.valueOf(lon));
                parametros.put("total",String.valueOf(total));
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void ubicacion(){
        Intent intent=new Intent(this,ActivityUbicacionCliente.class);
        intent.putExtra("user",usuario);
        intent.putExtra("rango",rango);
        intent.putExtra("dinero",dinero);
        startActivity(intent);
    }
    public void pago(){
        Intent intent=new Intent(this,ActivityPago.class);
        intent.putExtra("user",usuario);
        intent.putExtra("rango",rango);
        intent.putExtra("dinero",dinero);
        startActivity(intent);
    }
    public void permisos_gps3(View view){
        if (total > 0) {
            ubicacion();
        }
        else{
            Toast.makeText(this,"Agrega algo para comprar",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){return false;}
}