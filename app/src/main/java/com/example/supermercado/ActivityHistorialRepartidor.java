package com.example.supermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.Admin.ActivityAdministrador;
import com.example.supermercado.configuracion.factura;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityHistorialRepartidor extends AppCompatActivity {
    private String usuario,rango;
    private ListView lista;
    private ArrayList<factura> facturalista;
    private ArrayList<String> arreglofactura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_repartidor);
        lista=(ListView) findViewById(R.id.lista999);
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        ObtenerLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(), ActivityDetallePedidoHistorialAdmin.class);
                intent.putExtra("id",facturalista.get(i).getId());
                intent.putExtra("nombre",facturalista.get(i).getNombre());
                intent.putExtra("fecha",facturalista.get(i).getFecha());
                intent.putExtra("hora",facturalista.get(i).getHora());
                intent.putExtra("total",facturalista.get(i).getTotal());
                intent.putExtra("user",usuario);
                intent.putExtra("rango",rango);
                startActivity(intent);
            }
        });
    }
    private void ObtenerLista() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://apk.salasar.xyz:25565/historial_factura_repartidor.php?codigo=0123456789", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("factura");
                    factura comida=null;
                    facturalista=new ArrayList<factura>();
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        comida=new factura(
                                rowcontacto.getString("id"),
                                rowcontacto.getString("nombre")+" "+rowcontacto.getString("apellido"),
                                rowcontacto.getString("fecha"),
                                rowcontacto.getString("hora"),
                                rowcontacto.getString("latitud"),
                                rowcontacto.getString("longitud"),
                                rowcontacto.getString("telefono"),
                                rowcontacto.getString("total")
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
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void fllList() {
        arreglofactura=new ArrayList<String>();
        for(int i=0;i<facturalista.size();i++){
            arreglofactura.add
                    (
                            "============================================\n"+
                                    "Factura No: "+facturalista.get(i).getId()+"\n"+
                                    "Fecha: "+facturalista.get(i).getFecha()+"   Hora: "+facturalista.get(i).getHora()+"\n"+
                                    "Cliente: "+facturalista.get(i).getNombre()+"\n\n\n"+
                                    "Total: L. "+facturalista.get(i).getTotal()+"\n"+
                                    "============================================"
                    );
        }
        ArrayAdapter adp=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglofactura);
        lista.setAdapter(adp);
    }
    public void atras2022999(View view){
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
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}