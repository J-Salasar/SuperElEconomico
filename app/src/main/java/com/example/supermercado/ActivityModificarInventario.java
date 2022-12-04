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
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ActivityModificarInventario extends AppCompatActivity {
    private TextView cantidad;
    private EditText nombre,precio,cantidad_cliente;
    private Button sumar, restar;
    private ImageView foto;
    private String id,usuario,rango;
    private int resultado;
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEFOTO=101;
    private String currentPhotoPath;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_inventario);
        nombre=(EditText) findViewById(R.id.nombre2200);
        cantidad=(TextView) findViewById(R.id.cantidad2200);
        precio=(EditText) findViewById(R.id.precio2200);
        sumar=(Button) findViewById(R.id.sumar2200);
        restar=(Button) findViewById(R.id.restar2200);
        cantidad_cliente=(EditText) findViewById(R.id.cliente_cantidad2200);
        foto=(ImageView) findViewById(R.id.foto2200);
        id=getIntent().getStringExtra("id");
        currentPhotoPath=getIntent().getStringExtra("foto");
        nombre.setText(getIntent().getStringExtra("nombre"));
        cantidad.setText("Cantidad: "+getIntent().getStringExtra("cantidad"));
        precio.setText(getIntent().getStringExtra("precio"));
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(cantidad_cliente.getText().toString(),4)){
                    resultado = Integer.parseInt(cantidad_cliente.getText().toString()) + 1;
                    cantidad_cliente.setText(String.valueOf(resultado));
                }
                else {
                    cantidad_cliente.setText("0");
                }
            }
        });
        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(cantidad_cliente.getText().toString(),4)){
                    if (Integer.parseInt(cantidad_cliente.getText().toString()) > 0) {
                        resultado = Integer.parseInt(cantidad_cliente.getText().toString()) - 1;
                        cantidad_cliente.setText(String.valueOf(resultado));
                    }
                }
                else {
                    cantidad_cliente.setText("0");
                }
            }
        });
        byte[] bytes= Base64.getDecoder().decode(currentPhotoPath);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        foto.setImageBitmap(bitmap);
    }
    public void salir66(View view){
        Intent intent=new Intent(this,ActivityInventario.class);
        intent.putExtra("user",usuario);
        intent.putExtra("rango",rango);
        startActivity(intent);
    }
    public boolean validar(String dato, int numero){
        String opcion1="[a-z,A-Z,Ñ,ñ,/,_/,' ',-]{3,100}";
        String opcion2="[0-9]{1,11}[.][0-9]{1,2}";
        String opcion3="[0-9]{1,11}";
        String opcion4="[0-9]{1,11}";
        String opcion5="[0-9]{1,11}";
        switch (numero){
            case 1:{
                return dato.matches(opcion1);
            }
            case 2:{
                return dato.matches(opcion2+"|"+opcion3);
            }
            case 3:{
                return dato.matches(opcion4);
            }
            case 4:{
                return dato.matches(opcion5);
            }
            default:{
                return false;
            }
        }
    }
    public void verificar(View view){
        if(validar(nombre.getText().toString(),1)){
            if(validar(precio.getText().toString(),2)){
                if(validar(cantidad_cliente.getText().toString(),3)) {
                    actualizar_inventario();
                }
                else{
                    Toast.makeText(this,"Cantidad no valida",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this,"Precio no valido",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Nombre no valido",Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar_inventario(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/actualizar_inventario.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Actualizado", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),ActivityInventario.class);
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
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("precio", precio.getText().toString());
                parametros.put("foto",currentPhotoPath);
                parametros.put("cantidad", String.valueOf(Integer.parseInt(getIntent().getStringExtra("cantidad"))+Integer.parseInt(cantidad_cliente.getText().toString())));
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
            currentPhotoPath = Base64.getEncoder().encodeToString(bytes);
        }
    }
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(takePictureIntent, REQUESTTAKEFOTO);
        }
    }
    public void permisos_camara66(View view) {
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