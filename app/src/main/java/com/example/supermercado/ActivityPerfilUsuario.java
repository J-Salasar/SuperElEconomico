package com.example.supermercado;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ActivityPerfilUsuario extends AppCompatActivity {
    private EditText telefono1,clave1;
    private TextView nombre,correo,usuario;
    private String user,rango,telefono,clave,fotos;
    private ImageView foto;
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEFOTO=101;
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
    public boolean verificar(String dato, int numero){
        String opcion1="[a-z,0-9,A-Z,%,&,$,#,@,!,?,¡,¿,ñ,Ñ,+,/]{8,50}";
        String opcion2="[0-9]{8}";
        String opcion3="";
        switch (numero){
            case 1:{
                return dato.matches(opcion1);
            }
            case 2:{
                return dato.matches(opcion2);
            }
            case 3:{
                return dato.matches(opcion3);
            }
            default:{
                return false;
            }
        }
    }
    public void validar111(View view){
        if(verificar(clave1.getText().toString(),1)){
            if(verificar(telefono1.getText().toString(),2)){
                actualizar111();
            }
            else {
                if(verificar(telefono1.getText().toString(),3)){
                    actualizar1112();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Telefono no valido", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            if(verificar(clave1.getText().toString(),3)){
                if(verificar(telefono1.getText().toString(),2)){
                    actualizar113();
                }
                else {
                    if(verificar(telefono1.getText().toString(),3)){
                        actualizar1114();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Telefono no valido", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Clave no valida", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void actualizar1114() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/actualizar_privacidad.php", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Actualizada", Toast.LENGTH_SHORT).show();
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
                parametros.put("telefono", telefono);
                parametros.put("foto", fotos);
                parametros.put("clave", clave);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizar113() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/actualizar_privacidad.php", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Actualizada", Toast.LENGTH_SHORT).show();
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
                parametros.put("telefono", telefono1.getText().toString());
                parametros.put("foto", fotos);
                parametros.put("clave", clave);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizar1112() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/actualizar_privacidad.php", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Actualizada", Toast.LENGTH_SHORT).show();
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
                parametros.put("telefono", telefono);
                parametros.put("foto", fotos);
                parametros.put("clave", clave1.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizar111() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/actualizar_privacidad.php", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Actualizada", Toast.LENGTH_SHORT).show();
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
                parametros.put("telefono", telefono1.getText().toString());
                parametros.put("foto", fotos);
                parametros.put("clave", clave1.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void informacion() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/privacidad.php", new Response.Listener<String>(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray contactoarray=jsonObject.getJSONArray("informacion");
                    for(int i=0;i<contactoarray.length();i++){
                        JSONObject rowcontacto=contactoarray.getJSONObject(i);
                        nombre.setText(rowcontacto.getString("nombre"));
                        correo.setText(rowcontacto.getString("correo"));
                        fotos=rowcontacto.getString("foto");
                        usuario.setText("Usuario: "+user);
                        clave=rowcontacto.getString("clave");
                        telefono=rowcontacto.getString("telefono");
                        byte[] bytes= Base64.getDecoder().decode(fotos);
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        foto.setImageBitmap(bitmap);
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
    //Agrega la foto al cuadro
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTTAKEFOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
            fotos = Base64.getEncoder().encodeToString(bytes);
        }
    }
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takePictureIntent, REQUESTTAKEFOTO);
        }
    }
    public void permisos_camara111(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUESTCODECAMARA);
        }
        else {
            dispatchTakePictureIntent();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODECAMARA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(getApplicationContext(), "Permiso Denegado", Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}