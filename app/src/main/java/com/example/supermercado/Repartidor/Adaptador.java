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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.supermercado.R;

import java.util.ArrayList;
import java.util.Base64;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<String> id;
    ArrayList<String> nombre;
    ArrayList<String> precio;
    ArrayList<String> cantidad;
    ArrayList<String> fotos;
    private ImageView foto;
    private TextView txtnombre, txtprecio, txtcantidad;

    public Adaptador(Context contexto, ArrayList<String> id, ArrayList<String> nombre, ArrayList<String> precio, ArrayList<String> cantidad, ArrayList<String> fotos) {
        this.contexto = contexto;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fotos = fotos;
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
                /*Intent ubicacion=new Intent(contexto,ActivityUbicacion.class);
                ubicacion.putExtra("etiqueta",nombre.get((Integer)view.getTag()));
                ubicacion.putExtra("Y",latitud.get((Integer)view.getTag()));
                ubicacion.putExtra("X",longitud.get((Integer)view.getTag()));
                contexto.startActivity(ubicacion);*/
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
