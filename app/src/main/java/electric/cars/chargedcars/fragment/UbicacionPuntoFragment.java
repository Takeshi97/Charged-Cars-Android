package electric.cars.chargedcars.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import electric.cars.chargedcars.R;


public class UbicacionPuntoFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    private double longitud,latitud;

    private int locationSize;

    private LinearLayout linear_layout_toast_fail;


    ArrayList<String> nombreLista, grupoLista, latitudLista, longitudLista, imagenLogoLista;


    private final int CODE_PERMISSION_GPS = 4;

    double x = 10.2222222222222;


    public UbicacionPuntoFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getArguments() != null){


            locationSize = getArguments().getInt("locationsize",0);

            nombreLista = new ArrayList<String>();
            nombreLista = (ArrayList<String>) getArguments().getSerializable("miListaNombre");

            grupoLista = new ArrayList<String>();
            grupoLista = (ArrayList<String>) getArguments().getSerializable("miListaGrupo");

            latitudLista = new ArrayList<String>();
            latitudLista = (ArrayList<String>) getArguments().getSerializable("miListaLatitud");

            longitudLista = new ArrayList<String>();
            longitudLista = (ArrayList<String>) getArguments().getSerializable("miListaLongitud");

            imagenLogoLista = new ArrayList<String>();
            imagenLogoLista = (ArrayList<String>) getArguments().getSerializable("milistaImagenLogo");


            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY: " + latitudLista);
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY: " + nombreLista.get(12));
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY: " + locationSize);




        }




    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_ubicacion_punto, container,false);



        return mView;
    }


    private boolean verificarPermisosGps(){
        boolean permisosFine = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        boolean permisosCoarse = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        if(permisosCoarse || permisosFine){
            return true;
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},CODE_PERMISSION_GPS);
            return false;
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mMapView = (MapView) mView.findViewById(R.id.all_points);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);


        }



    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


            MapsInitializer.initialize(getContext());

            mGoogleMap = googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
/*
        googleMap.addMarker(new MarkerOptions().position(new LatLng(4.552284, -75.67568879999999)).title("Hola").snippet("Shit"));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(4.552284, -75.67568879999999)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));*/

            UiSettings settings = googleMap.getUiSettings();
            settings.setZoomControlsEnabled(true);
            settings.setCompassEnabled(true);


            if(verificarPermisosGps()){

                googleMap.setMyLocationEnabled(true);

            }

            googleMap.setBuildingsEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setTrafficEnabled(true);


            CameraPosition Liberty = CameraPosition.builder().target(new LatLng(3.4925240237821953, -72.80000051061505)).zoom(5.2F).bearing(0).tilt(45).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

            //locationSize -= 1;

          /*  while (locationSize <= 0){ */

        try{

            LatLng ubicacionPuntoRecarga_1 = new LatLng(4.75262788917162,-74.05879696551985);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_charged__car_mapp))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_1).title("Toyota Charged").snippet("Grupo Toyota"));


            LatLng ubicacionPuntoRecarga_2 = new LatLng(6.25318444965326,-75.5926764677333);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_charged__car_mapp))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_2).title("Cars Charged").snippet("Grupo Ferrer"));

            LatLng ubicacionPuntoRecarga_3 = new LatLng(7.114742636789934,-73.10732782220754);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_3).title("Renault Electric").snippet("Grupo Renault"));

            LatLng ubicacionPuntoRecarga_4 = new LatLng(4.122556677071713,-73.63055541889781);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_charged__car_mapp))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_4).title("Subaru Electronic").snippet("Grupo Toyota"));

            LatLng ubicacionPuntoRecarga_5 = new LatLng(5.33034746096869,-72.39093468621512);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_5).title("Mitsubishi Electronic").snippet("Grupo Mitsubishi "));

            LatLng ubicacionPuntoRecarga_6 = new LatLng(10.96279704460426,-74.805324210842);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_6).title("Tesla Charged").snippet("Tesla Group"));

            LatLng ubicacionPuntoRecarga_7 = new LatLng(3.3830885709303056,-76.53178810888629);
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                    .anchor(0.0f, 1.0f).position(ubicacionPuntoRecarga_7).title("Recharged Plus").snippet("Grupo Plus"));

        }catch (Exception e){

            try{

                LatLng ubicacionPuntoRecarga_1 = new LatLng(4.75262788917162,-74.05879696551985);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_1).title("Toyota Charged").snippet("Grupo Toyota"));


                LatLng ubicacionPuntoRecarga_2 = new LatLng(6.25318444965326,-75.5926764677333);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_2).title("Cars Charged").snippet("Grupo Ferrer"));

                LatLng ubicacionPuntoRecarga_3 = new LatLng(7.114742636789934,-73.10732782220754);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_3).title("Renault Electric").snippet("Grupo Renault"));

                LatLng ubicacionPuntoRecarga_4 = new LatLng(4.122556677071713,-73.63055541889781);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_4).title("Subaru Electronic").snippet("Grupo Toyota"));

                LatLng ubicacionPuntoRecarga_5 = new LatLng(5.33034746096869,-72.39093468621512);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_5).title("Mitsubishi Electronic").snippet("Grupo Mitsubishi "));

                LatLng ubicacionPuntoRecarga_6 = new LatLng(10.96279704460426,-74.805324210842);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_6).title("Tesla Charged").snippet("Tesla Group"));

                LatLng ubicacionPuntoRecarga_7 = new LatLng(3.3830885709303056,-76.53178810888629);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car_electric_logo_mapa))
                        .anchor(0.0f, 0.0f).position(ubicacionPuntoRecarga_7).title("Recharged Plus").snippet("Grupo Plus"));

            }catch (Exception b){

                toastFailLargo("Error generado por: " + b + " ¡ Movil Incompatible ! ");
                toastFailLargo("Error generado por: " + b + " ¡ Movil Incompatible ! ");

            }


        }




                //x+=20;
                //locationSize-=1;
           /* }*/



    }


    public void toastFailLargo(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_fail, linear_layout_toast_fail);
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_error);
        txtMessage.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


}