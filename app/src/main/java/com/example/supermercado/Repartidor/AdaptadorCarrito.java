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
import com.example.supermercado.ActivityPago;
import com.example.supermercado.R;
import com.example.supermercado.configuracion.validar_sesion;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorCarrito extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<validar_sesion> productoslista;
    Context contexto;
    ArrayList<String> id;
    ArrayList<String> nombre;
    ArrayList<String> precio;
    ArrayList<String> cantidad;
    ArrayList<String> fotos;
    ArrayList<String> cantidad_actual;
    String usuario,rango,dinero;
    Button borrar;
    private ImageView foto;
    private TextView txtnombre, txtprecio, txtcantidad;

    public AdaptadorCarrito(Context contexto, ArrayList<String> id, ArrayList<String> nombre, ArrayList<String> precio, ArrayList<String> cantidad, ArrayList<String> fotos, ArrayList<String> cantidad_actual, String usuario, String rango, String dinero) {
        this.contexto = contexto;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fotos = fotos;
        this.cantidad_actual=cantidad_actual;
        this.usuario=usuario;
        this.rango=rango;
        this.dinero=dinero;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento_lista2, null);
        foto = (ImageView) vista.findViewById(R.id.foto666);
        txtnombre = (TextView) vista.findViewById(R.id.nombre666);
        txtprecio = (TextView) vista.findViewById(R.id.precio666);
        txtcantidad = (TextView) vista.findViewById(R.id.cantidad666);
        borrar=(Button) vista.findViewById(R.id.borrarproducto);
        txtnombre.setText(nombre.get(i));
        txtprecio.setText("L. " +precio.get(i));
        txtcantidad.setText("Cantidad: "+cantidad.get(i));
        byte[] bytes= Base64.getDecoder().decode(fotos.get(i));
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        foto.setImageBitmap(bitmap);
        foto.setTag(i);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        borrar.setTag(i);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_producto(id.get((Integer)view.getTag()));
            }
        });
        return vista;
    }
    public void eliminar_producto(String idproducto){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://apk.salasar.xyz:25565/eliminar_producto.php", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto,"Producto eliminado",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(contexto,ActivityPago.class);
                intent.putExtra("user",usuario);
                intent.putExtra("rango",rango);
                intent.putExtra("dinero",dinero);
                contexto.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", idproducto);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(contexto);
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

