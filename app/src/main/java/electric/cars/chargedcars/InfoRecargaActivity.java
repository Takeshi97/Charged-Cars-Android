package electric.cars.chargedcars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import electric.cars.chargedcars.model.Usuario;

public class InfoRecargaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView imagenRecargaInfo;
    private TextView txtNombreRecargaInfo;
    private TextView txtGrupoRecargaInfo;
    //private TextView txtDisponRecargaInfo;
    private TextView txtDireccRecargaInfo;
    private TextView txtTelefoRecargaInfo;


    private double longitud,latitud;
    private Usuario usuario;
    private String idUsuario;

    String nombre;

    private DatabaseReference reference;


    private AnimationDrawable anim_1;
    private ConstraintLayout linearlayoutAcceso_Card;

    private LinearLayout linear_layout_toast_fail;

    private FloatingActionButton floatingButtonWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_recarga);

        reference = FirebaseDatabase.getInstance().getReference();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        floatingButtonWhatsapp = findViewById(R.id.floatingButtonWhatsapp);


        imagenRecargaInfo = findViewById(R.id.imagenRecargaInfo);
        txtNombreRecargaInfo = findViewById(R.id.txtNombreRecargaInfo);
        txtGrupoRecargaInfo = findViewById(R.id.txtGrupoRecargaInfo);
        //txtDisponRecargaInfo = findViewById(R.id.txtDisponRecargaInfo);
        txtDireccRecargaInfo = findViewById(R.id.txtDireccRecargaInfo);
        txtTelefoRecargaInfo = findViewById(R.id.txtTelefoRecargaInfo);


        nombre = getIntent().getStringExtra("nombre");


        //String dispo = getIntent().getStringExtra("dispo");
        //String direct = getIntent().getStringExtra("direct");
        //String telef = getIntent().getStringExtra("telef");
        //String grupo = getIntent().getStringExtra("grupo");
        idUsuario = getIntent().getStringExtra("idUsuario");
        longitud = getIntent().getDoubleExtra("longitud",0.0);
        latitud = getIntent().getDoubleExtra("latitud",0.0);


        Picasso.get().load(getIntent().getStringExtra("urlImage")).into(imagenRecargaInfo);



        txtNombreRecargaInfo.setText(nombre);
        txtGrupoRecargaInfo.setText(getIntent().getStringExtra("grupo"));
        //txtDisponRecargaInfo.setText(dispo);
        txtDireccRecargaInfo.setText(getIntent().getStringExtra("direct"));
        txtTelefoRecargaInfo.setText(getIntent().getStringExtra("telef"));



        reference.child("usuarios").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        floatingButtonWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=" + "57" + txtTelefoRecargaInfo.getText().toString() + "&text=" + "Hola quiero solicitar información de " + txtNombreRecargaInfo.getText().toString();
                sendIntent.setData(Uri.parse(uri));
                startActivity(sendIntent);
            }
        });



        init();

        anim_1 = (AnimationDrawable) linearlayoutAcceso_Card.getBackground();
        anim_1.setEnterFadeDuration(2300);
        anim_1.setExitFadeDuration(2300);


    }


    private void init(){

        this.linearlayoutAcceso_Card = findViewById(R.id.linearlayoutAcceso_Card);
    }




    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(anim_1!=null && !anim_1.isRunning()){
            anim_1.start();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////
        /*
        if(isNetDisponible()){

            if(isOnlineNet()){

            }else{
                toastSinInternet("No hay acceso a Internet");
            }

        }else{
            toastHabilitarInternet("Internet Deshabilitado");
        }*/


    }

    @Override
    protected void onPause() {
        super.onPause();

        if(anim_1!=null && anim_1.isRunning()){
            anim_1.stop();
        }


        /////////////////////////////////////////////////////////////////////////////////////////////
        /*
        if(isNetDisponible()){

            if(isOnlineNet()){

            }else{
                toastSinInternet("No hay acceso a Internet");
            }

        }else{
            toastHabilitarInternet("Internet Deshabilitado");
        }*/

    }


    @Override
    protected void onStart() {
        super.onStart();


        /////////////////////////////////////////////////////////////////////////////////////////////
        /*
        if(isNetDisponible()){

            if(isOnlineNet()){

            }else{
                toastSinInternet("No hay acceso a Internet");
            }

        }else{
            toastHabilitarInternet("Internet Deshabilitado");
        }*/

    }














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.ItemSalir:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        try{

            LatLng centroPais = new LatLng(3.4925240237821953,-73.19000051061505);

            LatLng ubicacionPuntoRecarga = new LatLng(latitud,longitud);



            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga).title(nombre));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centroPais, 4.5F));
            UiSettings settings = googleMap.getUiSettings();
            settings.setZoomControlsEnabled(true);
            settings.setCompassEnabled(true);


            googleMap.setMyLocationEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setTrafficEnabled(true);

        }catch (Exception e){

            toastFailLargo("Error generado por: " + e + " ¡ Movil Incompatible ! ");
            toastFailLargo("Error generado por: " + e + " ¡ Movil Incompatible ! ");


        }


    }

    public void toastFailLargo(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_fail, linear_layout_toast_fail);
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_error);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    public void toastSinInternet(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_lost_con, findViewById(R.id.linear_layout_toast_lost_con));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_lost_con);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 450);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }



    public void toastHabilitarInternet(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_ethernet, findViewById(R.id.linear_layout_toast_ethernet));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_internet);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 450);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}