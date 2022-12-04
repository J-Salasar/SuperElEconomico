package com.example.supermercado;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.supermercado.databinding.ActivityUbicacionClienteBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityUbicacionCliente extends FragmentActivity implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback{
    private GoogleMap mMap;
    private ActivityUbicacionClienteBinding binding;
    private SupportMapFragment mapFragment;
    private Marker marcador;
    private String  lat = "15.505410029081524";
    private String lng = "-88.0255256498968";
    private String usuario,rango,dinero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUbicacionClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).draggable(true).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        usuario=getIntent().getStringExtra("user");
        rango=getIntent().getStringExtra("rango");
        dinero=getIntent().getStringExtra("dinero");
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(sydney,13);
        if (marcador != null) {
            marcador.remove();
        }
        mMap.animateCamera(miubicacion);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent principal = new Intent(getApplicationContext(), ActivityVerCarrito.class);
                principal.putExtra("user",usuario);
                principal.putExtra("rango",rango);
                principal.putExtra("dinero",dinero);
                principal.putExtra("latitud", lat);
                principal.putExtra("longitud", lng);
                principal.putExtra("ubicacion","aprobado");
                startActivity(principal);
                return false;
            }
        });
        //ubicacion();
        googleMap.setOnMarkerDragListener(this);
    }
    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
        lat=Double.toString(marker.getPosition().latitude);
        lng=Double.toString(marker.getPosition().longitude);
    }
    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }
    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }
    /*public void marcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,16);
        if (marcador != null) {
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Ubicacion actual"));
        mMap.animateCamera(miubicacion);
    }
    public void actualizarubicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            marcador(lat, lng);
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            actualizarubicacion(location);
        }
    };

    public void ubicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarubicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
    }*/
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
}