package com.example.supermercado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.supermercado.Admin.ActivityListadoPedidos;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_detalle_pedido_repartidor extends AppCompatActivity {
    private String usuario,rango,idfactura,telefono,latitud,longitud;
    private TextView id,nombre,fecha,hora,total;
    private ListView lista;
    private ArrayList<validar_sesion> facturalista;
    private ArrayList<String> arreglofactura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido_repartidor);
        id=(TextView) findViewById(R.id.factura_numero777);
        nombre=(TextView) findViewById(R.id.cliente_factura777);
        fecha=(TextView) findViewById(R.id.fecha_factura777);
        hora=(TextView) findViewById(R.id.hora_factura777);
        total=(TextView) findViewById(R.id.total_factura777);
        idfactura=getIntent().getStringExtra("id");
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        telefono=getIntent().getStringExtra("telefono");
        latitud=getIntent().getStringExtra("latitud");
        longitud=getIntent().getStringExtra("longitud");
        id.setText("Factura No: "+idfactura);
        nombre.setText("Cliente: "+getIntent().getStringExtra("nombre"));
        fecha.setText("Fecha: "+getIntent().getStringExtra("fecha"));
        hora.setText("Hora: "+getIntent().getStringExtra("hora"));
        total.setText("Total: L. "+getIntent().getStringExtra("total"));
        lista=(ListView)findViewById(R.id.lista_factura2022777);
        ObtenerLista();
    }
    private void ObtenerLista() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/historial_productos.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("producto");
                    validar_sesion comida=null;
                    facturalista=new ArrayList<validar_sesion>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new validar_sesion(
                                rowcontacto.getString("nombre"),
                                rowcontacto.getString("cantidad"),
                                rowcontacto.getString("precio")
                        );
                        facturalista.add(comida);
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
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", idfactura);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void fllList() {
        arreglofactura=new ArrayList<String>();
        for(int i=0;i<facturalista.size();i++){
            arreglofactura.add
                    (
                            facturalista.get(i).getValidar()+"\n"+
                                    "Cantidad: "+facturalista.get(i).getEstado()+"   L. "+facturalista.get(i).getRango()+"   L. "+String.valueOf(Double.parseDouble(facturalista.get(i).getEstado()) * Double.parseDouble(facturalista.get(i).getRango()))
                    );
        }
        ArrayAdapter adp=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglofactura);
        lista.setAdapter(adp);
    }
    public void retroceder2022777(View view){
        Intent intent = new Intent(this, ActivityListadoEntrega.class);
        intent.putExtra("user", usuario);
        intent.putExtra("rango", rango);
        startActivity(intent);
    }
    public void completar_pedido777(View view){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/entrega_completado.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Pedido completado",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ActivityListadoEntrega.class);
                intent.putExtra("user", usuario);
                intent.putExtra("rango", rango);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", idfactura);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void ubicacion111(View view){
        Intent intent=new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+latitud+","+longitud+"&mode=d"
                ));
        intent.setPackage("com.google.android.apps.maps");
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    public void llamar111(View view){
        AlertDialog.Builder alerta=new AlertDialog.Builder(Activity_detalle_pedido_repartidor.this);
        alerta.setMessage("Deseas llamar a: "+getIntent().getStringExtra("nombre"))
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int y) {
                        Intent callintent=new Intent(Intent.ACTION_DIAL);
                        callintent.setData(Uri.parse("tel:"+telefono));
                        startActivity(callintent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Llamada");
        titulo.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}