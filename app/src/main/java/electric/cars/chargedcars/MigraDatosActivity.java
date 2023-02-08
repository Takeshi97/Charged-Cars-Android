package electric.cars.chargedcars;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import electric.cars.chargedcars.fragment.UbicacionPuntoFragment;


public class MigraDatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migra_datos);

        /*

        UbicacionPuntoFragment ubicacionFragment = new UbicacionPuntoFragment();

        Bundle argumento = new Bundle();
        argumento.putInt("locationSize",18);

        ubicacionFragment.setArguments(argumento);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.migracion, ubicacionFragment,null);
        fragmentTransaction.commit();*/



        ArrayList<String> listaNombre = (ArrayList<String>) getIntent().getStringArrayListExtra("miListaNombre");
        ArrayList<String> listaGrupo = (ArrayList<String>) getIntent().getStringArrayListExtra("miListaGrupo");
        ArrayList<String> milistaLatitud = (ArrayList<String>) getIntent().getStringArrayListExtra("miListaLatitud");
        ArrayList<String> miListaLongitud = (ArrayList<String>) getIntent().getStringArrayListExtra("miListaLongitud");
        ArrayList<String> milistaImagenLogo = (ArrayList<String>) getIntent().getStringArrayListExtra("milistaImagenLogo");

        int cantidadActual = getIntent().getIntExtra("arraySize",0);

        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU: " + listaNombre);
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU: " + cantidadActual);
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU: " + listaGrupo.get(0));
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU: " + milistaImagenLogo.get(6));


        Fragment fragment = new UbicacionPuntoFragment();
        Bundle argumento = new Bundle();
        argumento.putSerializable("miListaNombre", listaNombre);
        argumento.putSerializable("miListaGrupo", listaGrupo);
        argumento.putSerializable("miListaLatitud", milistaLatitud);
        argumento.putSerializable("miListaLongitud", miListaLongitud);
        argumento.putSerializable("milistaImagenLogo", milistaImagenLogo);
        argumento.putInt("locationsize",cantidadActual);

        fragment.setArguments(argumento);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.migracion, fragment).addToBackStack(null)
                .commit();





        //Intent intent = new Intent(MigraDatosActivity.this, HomeUserActivity.class);
        //startActivity(intent);
        //finish();

    }
}



























































