package com.example.supermercado.Admin;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
import com.example.supermercado.ActivityHistorialPedido;
import com.example.supermercado.ActivityHistorialPedidoClientes;
import com.example.supermercado.ActivityInventario;
import com.example.supermercado.ActivityPerfilUsuario;
import com.example.supermercado.ActivityProductos;
import com.example.supermercado.ActivityVerCarrito;
import com.example.supermercado.MainActivity;
import com.example.supermercado.R;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ActivityAdministrador extends AppCompatActivity {
    private TextView usuario,nombre,dinero;
    private ImageView imagen;
    private String rango;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        usuario=(TextView) findViewById(R.id.usuario_texto1);
        usuario.setText(getIntent().getStringExtra("user"));
        nombre=(TextView) findViewById(R.id.nombre_texto1);
        imagen=(ImageView) findViewById(R.id.foto_perfil1);
        dinero=(TextView) findViewById(R.id.dinero_texto1);
        rango=getIntent().getStringExtra("rango");
        informacion();
    }
    public void informacion(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/pagina_usuario.php", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("usuario");
                    validar_sesion cuenta=null;
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        cuenta=new validar_sesion(
                                rowcontacto.getString("nombre")+" "+rowcontacto.getString("apellido"),
                                rowcontacto.getString("foto"),
                                rowcontacto.getString("dinero")
                        );
                    }
                    nombre.setText(cuenta.getValidar());
                    byte[] bytes= Base64.getDecoder().decode(cuenta.getEstado());
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imagen.setImageBitmap(bitmap);
                    dinero.setText(cuenta.getRango());
                }
                catch (Throwable error){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
                parametros.put("usuario", getIntent().getStringExtra("user"));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void productos1(View view){
        Intent intent=new Intent(this, ActivityProductos.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void historial_pedidos1(View view){
        Intent intent=new Intent(this, ActivityHistorialPedido.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void ver_carrito1(View view){
        Intent intent=new Intent(this, ActivityVerCarrito.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        intent.putExtra("dinero",dinero.getText().toString());
        startActivity(intent);
    }
    public void perfil_usuario1(View view){
        /*Intent intent=new Intent(this, ActivityPerfilUsuario.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);*/
    }
    public void cerrar1(View view){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void inventario1(View view){
        Intent intent=new Intent(this, ActivityInventario.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void crear_repartidor(View view){
        /*Intent intent=new Intent(this, ActivityRegistrarRepartidor.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);*/
    }
    public void lista_pedidos(View view){
        Intent intent=new Intent(this,ActivityListadoPedidos.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void crear_admin(View view){
        /*Intent intent=new Intent(this,ActivityListadoPedidos.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);*/
    }
    public void historial_pedidos_clientes(View view){
        Intent intent=new Intent(this, ActivityHistorialPedidoClientes.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}