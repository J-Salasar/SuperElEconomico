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
import com.example.supermercado.ActivityIniciar;
import com.example.supermercado.ActivityPago;
import com.example.supermercado.R;
import com.example.supermercado.configuracion.carrito;
import com.example.supermercado.configuracion.validar_sesion;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private ImageView foto;
    private TextView txtnombre, txtprecio, txtcantidad;

    public AdaptadorCarrito(Context contexto, ArrayList<String> id, ArrayList<String> nombre, ArrayList<String> precio, ArrayList<String> cantidad, ArrayList<String> fotos, ArrayList<String> cantidad_actual) {
        this.contexto = contexto;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fotos = fotos;
        this.cantidad_actual=cantidad_actual;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        foto = (ImageView) vista.findViewById(R.id.foto90);
        txtnombre = (TextView) vista.findViewById(R.id.txtnombre90);
        txtprecio = (TextView) vista.findViewById(R.id.txtprecio90);
        txtcantidad = (TextView) vista.findViewById(R.id.txtcantidad90);
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
        return vista;
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

