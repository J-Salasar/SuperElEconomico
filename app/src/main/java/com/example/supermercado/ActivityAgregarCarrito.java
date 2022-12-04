package com.example.supermercado;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.configuracion.productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ActivityAgregarCarrito extends AppCompatActivity {
    private TextView nombre,cantidad,precio,cantidad_cliente;
    private Button sumar, restar;
    private ImageView foto;
    private String id,usuario,rango;
    private int resultado;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carrito);
        nombre=(TextView) findViewById(R.id.nombre22);
        cantidad=(TextView) findViewById(R.id.cantidad22);
        precio=(TextView) findViewById(R.id.precio22);
        sumar=(Button) findViewById(R.id.sumar22);
        restar=(Button) findViewById(R.id.restar22);
        cantidad_cliente=(TextView) findViewById(R.id.cliente_cantidad22);
        foto=(ImageView) findViewById(R.id.foto22);
        id=getIntent().getStringExtra("id");
        nombre.setText(getIntent().getStringExtra("nombre"));
        cantidad.setText("Cantidad: "+getIntent().getStringExtra("cantidad"));
        precio.setText("L. "+getIntent().getStringExtra("precio"));
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(getIntent().getStringExtra("cantidad")) > Integer.parseInt(cantidad_cliente.getText().toString())){
                    resultado=Integer.parseInt(cantidad_cliente.getText().toString()) + 1;
                    cantidad_cliente.setText(String.valueOf(resultado));
                }
            }
        });
        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(cantidad_cliente.getText().toString()) > 1){
                    resultado=Integer.parseInt(cantidad_cliente.getText().toString()) - 1;
                    cantidad_cliente.setText(String.valueOf(resultado));
                }
            }
        });
        byte[] bytes= Base64.getDecoder().decode(getIntent().getStringExtra("foto"));
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        foto.setImageBitmap(bitmap);
    }
    public void salir(View view){
        Intent intent=new Intent(this,ActivityProductos.class);
        intent.putExtra("user",usuario);
        intent.putExtra("rango",rango);
        startActivity(intent);
    }
    public void agregar_carrito(View view){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/agregar_carrito.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Agregado al carrito", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),ActivityProductos.class);
                intent.putExtra("user",usuario);
                intent.putExtra("rango",rango);
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
                parametros.put("id",id);
                parametros.put("nombre", getIntent().getStringExtra("nombre"));
                parametros.put("precio", getIntent().getStringExtra("precio"));
                parametros.put("foto",getIntent().getStringExtra("foto"));
                parametros.put("usuario",getIntent().getStringExtra("user"));
                parametros.put("cantidad", String.valueOf(Integer.parseInt(getIntent().getStringExtra("cantidad"))-Integer.parseInt(cantidad_cliente.getText().toString())));
                parametros.put("cantidad_usuario",cantidad_cliente.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}