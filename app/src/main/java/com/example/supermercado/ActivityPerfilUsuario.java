package com.example.supermercado;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.example.supermercado.Admin.ActivityAdministrador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityPerfilUsuario extends AppCompatActivity {
    private EditText telefono1,clave1;
    private TextView nombre,correo,usuario;
    private String user,rango,telefono,clave;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        telefono1=(EditText) findViewById(R.id.txtRTelefonoM111);
        clave1=(EditText) findViewById(R.id.txtRClaveM);
        nombre=(TextView) findViewById(R.id.nombre111);
        correo=(TextView) findViewById(R.id.correo111);
        usuario=(TextView) findViewById(R.id.usuario111);
        foto=(ImageView) findViewById(R.id.imgRUsersM111);
        user=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        informacion();
    }
    private void informacion() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/privacidad.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("informacion");
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        /*comida=new productos(
                                rowcontacto.getString("id"),
                                rowcontacto.getString("nombre"),
                                rowcontacto.getString("precio"),
                                rowcontacto.getString("cantidad"),
                                rowcontacto.getString("foto")
                        );*/
                    }
                }
                catch (Throwable error){
                    Toast.makeText(getApplicationContext(), "No nada de informacion", Toast.LENGTH_LONG).show();
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
                parametros.put("usuario", user);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void volver111(View view){
        if(rango.equals("Usuario")) {
            Intent intent = new Intent(this, ActivityIniciar.class);
            intent.putExtra("user", user);
            intent.putExtra("rango", rango);
            startActivity(intent);
        }
        else {
            if(rango.equals("Administrador")) {
                Intent intent = new Intent(this, ActivityAdministrador.class);
                intent.putExtra("user", user);
                intent.putExtra("rango", rango);
                startActivity(intent);
            }
            else{
                if(rango.equals("Repartidor")){
                    Intent intent = new Intent(this, Repartidor_envio.class);
                    intent.putExtra("user", user);
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