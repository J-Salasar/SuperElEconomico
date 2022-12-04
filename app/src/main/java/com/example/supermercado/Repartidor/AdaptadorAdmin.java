package com.example.supermercado.Repartidor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supermercado.ActivityEliminar;
import com.example.supermercado.Admin.ActivityAdministrador;
import com.example.supermercado.R;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorAdmin extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<String> nombre;
    ArrayList<String> user;
    ArrayList<String> fotos;
    String cuentauser,cuentarango,rangouser;
    private ImageView foto;
    private TextView txtnombre, txtuser;
    private Button eliminar;

    public AdaptadorAdmin(Context contexto, ArrayList<String> nombre, ArrayList<String> user, ArrayList<String> fotos, String cuentauser, String cuentarango, String rangouser) {
        this.contexto = contexto;
        this.nombre = nombre;
        this.user = user;
        this.fotos = fotos;
        this.cuentauser=cuentauser;
        this.cuentarango=cuentarango;
        this.rangouser=rangouser;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento_lista1, null);
        foto = (ImageView) vista.findViewById(R.id.foto2020);
        txtnombre = (TextView) vista.findViewById(R.id.txtnombre2020);
        txtuser = (TextView) vista.findViewById(R.id.txtuser2020);
        eliminar=(Button) vista.findViewById(R.id.eliminaradmin);
        txtnombre.setText(nombre.get(i));
        txtuser.setText("Usuario: " +user.get(i));
        byte[] bytes= Base64.getDecoder().decode(fotos.get(i));
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        foto.setImageBitmap(bitmap);
        foto.setTag(i);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        eliminar.setTag(i);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rangouser.equals("Administrador")) {
                    eliminarusuarioadmin(user.get((Integer) view.getTag()));
                }
                else{
                    if(rangouser.equals("Repartidor")){
                        eliminarusuariorepartidor(user.get((Integer)view.getTag()));
                    }
                }
            }
        });
        return vista;
    }
    public void eliminarusuarioadmin(String usuario){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/eliminar_admin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto, "Eliminado", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(contexto, ActivityEliminar.class);
                intent.putExtra("user",cuentauser);
                intent.putExtra("rango",cuentarango);
                intent.putExtra("rangouser",rangouser);
                contexto.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", usuario);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        requestQueue.add(stringRequest);
    }
    public void eliminarusuariorepartidor(String usuario){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/eliminar_repartidor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto, "Eliminado", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(contexto, ActivityEliminar.class);
                intent.putExtra("user",cuentauser);
                intent.putExtra("rango",cuentarango);
                intent.putExtra("rangouser",rangouser);
                contexto.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", usuario);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        requestQueue.add(stringRequest);
    }
    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
