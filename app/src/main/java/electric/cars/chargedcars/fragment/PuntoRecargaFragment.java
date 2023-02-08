package electric.cars.chargedcars.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

import electric.cars.chargedcars.R;
import electric.cars.chargedcars.adapter.Puntos_recarga_Adapter;
import electric.cars.chargedcars.model.Sitios_recarga;


public class PuntoRecargaFragment extends Fragment {

    private DatabaseReference mDatabase;

    private Spinner spinnerFragmentPuntoRecarga;
    private RecyclerView recyclerViewRecarga;
    private Puntos_recarga_Adapter puntosRecargaAdapter;

    private RadioButton rbDisponiblePuntoRecargaFragment,rbNoDisponiblePuntoRecargaFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_punto_recarga, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        spinnerFragmentPuntoRecarga = view.findViewById(R.id.spinnerFragmentPuntoRecarga);
        recyclerViewRecarga = view.findViewById(R.id.recyclerViewRecarga);

        rbDisponiblePuntoRecargaFragment = view.findViewById(R.id.rbDisponiblePuntoRecargaFragment);
        rbNoDisponiblePuntoRecargaFragment = view.findViewById(R.id.rbNoDisponiblePuntoRecargaFragment);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.tipo_punto_recarga, R.layout.spinner_item_modificacion);
        spinnerFragmentPuntoRecarga.setAdapter(adapter);



        rbDisponiblePuntoRecargaFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViews();
            }
        });

        rbNoDisponiblePuntoRecargaFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViews();
            }
        });


        spinnerFragmentPuntoRecarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicion, long l) {

                switch (posicion){
                    case 0:
                        cardViews();
                        break;

                    case 1:
                        cardViews();
                        break;

                    case 2:
                        cardViews();
                        break;

                    case 3:
                        cardViews();
                        break;

                    case 4:
                        cardViews();
                        break;

                    case 5:
                        cardViews();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        cardViews();

        return view;
    }





    private void cardViews(){
        LinkedList<Sitios_recarga> sitiosRecargasList = new LinkedList<>();

        sitiosRecargasList.clear();
        String tipoPuntoRecarga = spinnerFragmentPuntoRecarga.getSelectedItem().toString();
        DatabaseReference reference;
        if(rbDisponiblePuntoRecargaFragment.isChecked()){
            reference = mDatabase.child("puntos_recarga").child(tipoPuntoRecarga).child("disponible");
        }else{
            reference = mDatabase.child("puntos_recarga").child(tipoPuntoRecarga).child("no_disponible");
        }

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if(task.getResult().getValue()==null){
                        //ReciclerViewMascotas = view.findViewById(R.id.ReciclerViewMascotas);
                        puntosRecargaAdapter = new Puntos_recarga_Adapter(sitiosRecargasList);
                        recyclerViewRecarga.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewRecarga.setAdapter(puntosRecargaAdapter);

                    }
                }
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sitios_recarga sitios_recarga = snapshot.getValue(Sitios_recarga.class);
                sitiosRecargasList.add(sitios_recarga);
                //ReciclerViewMascotas = view.findViewById(R.id.ReciclerViewMascotas);
                puntosRecargaAdapter = new Puntos_recarga_Adapter(sitiosRecargasList);
                recyclerViewRecarga.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewRecarga.setAdapter(puntosRecargaAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println("onChildChanged");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                System.out.println("onChildRemoved");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println("onChildMoved");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("onCancelled");
            }

        });

    }



    /*
    // Todo a futuro esta lista sera con los elementos de la bd
    private LinkedList<Sitios_recarga> listarPuntosRecarga(){
        LinkedList<Sitios_recarga> puntosRecarga = new LinkedList<Sitios_recarga>();

        puntosRecarga.add(new Sitios_recarga("Mitsubishi Charged","Cr 9 # 36 - 77","10547632","Disponible","Grupo Mitsubishi",R.drawable.mitsubishi_logo));
        puntosRecarga.add(new Sitios_recarga("Subaru Electric","Cr 75 # 18 - 42","810348916","Disponible","Grupo Toyota",R.drawable.subaru_logo));
        puntosRecarga.add(new Sitios_recarga("Tesla Charged","Cr 21 # 24 - 67","7013249851","Disponible","Grupo Tesla",R.drawable.tesla_logo));
        puntosRecarga.add(new Sitios_recarga("Toyota Electric","Cr 7 # 12 - 75","451205478","Disponible","Grupo Toyota",R.drawable.toyota_logo));
        puntosRecarga.add(new Sitios_recarga("Renault Electric","Tr 34 # 37 - 97","624589137","Disponible","Grupo Renault",R.drawable.renault_logo));
        puntosRecarga.add(new Sitios_recarga("Ultra Carga","Cr 85 # 37 - 95","378154601","Disponible","Grupo General Electric",R.drawable.ultra_carga_logo));
        puntosRecarga.add(new Sitios_recarga("Volter Car","Cr 110 # 97 - 79","2514873645","No Disponible","Grupo Volter",R.drawable.volter_car_logo));





        return puntosRecarga;

    }        */



}