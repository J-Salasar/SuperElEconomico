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
import com.example.supermercado.configuracion.validar_sesion;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
public class ActivityIniciar extends AppCompatActivity{
    private TextView usuario,nombre,dinero;
    private ImageView imagen;
    private String rango;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);
        usuario=(TextView) findViewById(R.id.usuario_texto);
        usuario.setText(getIntent().getStringExtra("user"));
        nombre=(TextView) findViewById(R.id.nombre_texto);
        imagen=(ImageView) findViewById(R.id.foto_perfil);
        dinero=(TextView) findViewById(R.id.dinero_texto);
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
    public void productos(View view){
        Intent intent=new Intent(this,ActivityProductos.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void historial_pedidos(View view){
        Intent intent=new Intent(this,ActivityHistorialPedido.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void ver_carrito(View view){
        Intent intent=new Intent(this, ActivityVerCarrito.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        intent.putExtra("dinero",dinero.getText().toString());
        startActivity(intent);
    }
    public void perfil_usuario(View view){
        Intent intent=new Intent(this,ActivityPerfilUsuario.class);
        intent.putExtra("user",getIntent().getStringExtra("user"));
        intent.putExtra("rango",getIntent().getStringExtra("rango"));
        startActivity(intent);
    }
    public void cerrar(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}