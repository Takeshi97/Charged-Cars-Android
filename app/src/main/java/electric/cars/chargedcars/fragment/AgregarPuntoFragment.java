package electric.cars.chargedcars.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

import electric.cars.chargedcars.HomeUserActivity;
import electric.cars.chargedcars.R;
import electric.cars.chargedcars.model.Sitios_recarga;

public class AgregarPuntoFragment extends Fragment {

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FusedLocationProviderClient providerClient;

    private ImageView imgCamaraAgregar;
    private Spinner categoriaPuntoRecarga;
    private EditText edNombrePuntoRecargaAgregar,edGrupoPuntoRecargaAgregar,edDireccionPuntoRecargaAgregar,edTelefonoPuntoRecargaAgregar;
    private RadioButton rbDisponiblePuntoRecargaAgregar,rbNoDisponiblePuntoRecargaAgregar;
    private Button btnEnviarPuntoRecarga;

    private final int CODE_IMAGE_CAPTURE = 1;
    private final int CODE_MEDIA_STORE = 2;
    private final int CODE_PERMISSION_CAMARA = 3;
    private final int CODE_PERMISSION_GPS = 4;

    private String uriImageStorage;

    /*

    public AgregarFragment() {
        // Required empty public constructor
    }
*/

    private TextInputLayout txtNombreInputFragmentAgregar, txtGrupoInputFragmentAgregar, txtDireccionInputFragmentAgregar, txtTelefonoInputFragmentAgregar;
    private LinearLayout linear_layout_toast_ok, linear_layout_toast_fail;


    int numero = 0;
    boolean isActivate = false;
    Handler handler = new Handler();
    TextView textViewCircleFragment, textViewFragmenAgregar;
    ProgressBar progressBarCircle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewFragment = inflater.inflate(R.layout.fragment_agregar_punto, container, false);



        progressBarCircle = viewFragment.findViewById(R.id.Circular_Bar_Circle_Fragment);
        textViewCircleFragment = viewFragment.findViewById(R.id.textViewCircleFragment);


        verificarPermisosGps();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        providerClient = LocationServices.getFusedLocationProviderClient(getContext());

        imgCamaraAgregar = viewFragment.findViewById(R.id.imgCamaraAgregar);
        categoriaPuntoRecarga = viewFragment.findViewById(R.id.categoriaPuntoRecarga);
        edNombrePuntoRecargaAgregar = viewFragment.findViewById(R.id.edNombrePuntoRecargaAgregar);
        edDireccionPuntoRecargaAgregar = viewFragment.findViewById(R.id.edDireccionPuntoRecargaAgregar);
        edGrupoPuntoRecargaAgregar = viewFragment.findViewById(R.id.edGrupoPuntoRecargaAgregar);
        edTelefonoPuntoRecargaAgregar = viewFragment.findViewById(R.id.edTelefonoPuntoRecargaAgregar);
        rbDisponiblePuntoRecargaAgregar = viewFragment.findViewById(R.id.rbDisponiblePuntoRecargaAgregar);
        rbNoDisponiblePuntoRecargaAgregar = viewFragment.findViewById(R.id.rbNoDisponiblePuntoRecargaAgregar);
        btnEnviarPuntoRecarga = viewFragment.findViewById(R.id.btnEnviarPuntoRecarga);
        textViewFragmenAgregar = viewFragment.findViewById(R.id.textViewFragmenAgregar);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.tipo_punto_recarga, R.layout.spinner_item_modificacion);
        categoriaPuntoRecarga.setAdapter(adapter);



        txtNombreInputFragmentAgregar = viewFragment.findViewById(R.id.txtNombreInputFragmentAgregar);
        txtGrupoInputFragmentAgregar = viewFragment.findViewById(R.id.txtGrupoInputFragmentAgregar);
        txtDireccionInputFragmentAgregar = viewFragment.findViewById(R.id.txtDireccionInputFragmentAgregar);
        txtTelefonoInputFragmentAgregar = viewFragment.findViewById(R.id.txtTelefonoInputFragmentAgregar);

        linear_layout_toast_ok = viewFragment.findViewById(R.id.linear_layout_toast_ok);
        linear_layout_toast_fail = viewFragment.findViewById(R.id.linear_layout_toast_fail);




        imgCamaraAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seleccionarFoto();
            }
        });


        btnEnviarPuntoRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //seleccionarFoto();
                enviarDatosPuntoDb();
            }
        });



        return viewFragment;

    }


    private boolean verificarPermisosGps(){
        boolean permisosFine = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        boolean permisosCoarse = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        if(permisosCoarse || permisosFine){
            return true;
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},CODE_PERMISSION_GPS);
            return false;
        }
    }




    private void mostrarAlertaDeRegistroPunto(){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext())
                .setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_alerta_ingreso_punto,null);

        builder.setView(view);

        // TODO BOTONES POR DEFECTO
        /*
        builder.setView(inflater.inflate(R.layout.dialog_presonalizado, null))
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Conectando...", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Cancel...", Toast.LENGTH_SHORT).show();
                    }
                });                      */



        final android.app.AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView txt = view.findViewById(R.id.text_dialog);
        txt.setText("¿Desea Inscribir un Nuevo Punto de Recarga?");


        Button btnPositive = view.findViewById(R.id.btnPositive);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarPuntosRecarga();
                dialog.dismiss();
            }
        });

        // TODO set btn Cancel onClickListener

        Button btnNegative = view.findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }


    @SuppressLint("MissingPermission")
    private void registrarPuntosRecarga(){

        if(verificarPermisosGps()){

            providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location!=null){

                        //Agregar la lagica para guardar imagen en storage
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String idUser = currentUser.getUid();

                        String nombre = edNombrePuntoRecargaAgregar.getText().toString();
                        String grupo = edGrupoPuntoRecargaAgregar.getText().toString();
                        String direccion = edDireccionPuntoRecargaAgregar.getText().toString();
                        String telefono = edTelefonoPuntoRecargaAgregar.getText().toString();
                        String tipoPuntoRecarga = categoriaPuntoRecarga.getSelectedItem().toString();

                        double longitud = location.getLongitude();
                        double latitud = location.getLatitude();


                        Sitios_recarga sitios_recarga = new Sitios_recarga(nombre,grupo,direccion,telefono,uriImageStorage,idUser,longitud,latitud);
                        if(rbDisponiblePuntoRecargaAgregar.isChecked()){
                            mDatabase.child("puntos_recarga").child(tipoPuntoRecarga).child("disponible").push().setValue(sitios_recarga);

                            loadingCircleChecked();

                        }else{
                            mDatabase.child("puntos_recarga").child(tipoPuntoRecarga).child("no_disponible").push().setValue(sitios_recarga);

                            loadingCircleChecked();


                        }
                    }else{
                        toastFailLargo("No logramos acceder a la localización del dispositivo");
                    }
                }
            });


        }else{

            toastFailLargo("No se puede registrar el punto de recarga por permisos de gps");
        }


    }



    private void loadingCircleChecked(){


        ////////////////////////////////////////////////////////////////////////

        if(numero >= 100){

            numero = 0;

        }


        if(!isActivate){
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(numero <= 100){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                textViewCircleFragment.setVisibility(View.VISIBLE);
                                textViewCircleFragment.setText(numero + " %");
                                progressBarCircle.setVisibility(View.VISIBLE);
                                progressBarCircle.setProgress(numero);
                                imgCamaraAgregar.setVisibility(View.INVISIBLE);
                                categoriaPuntoRecarga.setVisibility(View.INVISIBLE);
                                rbDisponiblePuntoRecargaAgregar.setVisibility(View.INVISIBLE);
                                rbNoDisponiblePuntoRecargaAgregar.setVisibility(View.INVISIBLE);
                                btnEnviarPuntoRecarga.setVisibility(View.INVISIBLE);
                                txtNombreInputFragmentAgregar.setVisibility(View.INVISIBLE);
                                txtGrupoInputFragmentAgregar.setVisibility(View.INVISIBLE);
                                txtDireccionInputFragmentAgregar.setVisibility(View.INVISIBLE);
                                txtTelefonoInputFragmentAgregar.setVisibility(View.INVISIBLE);
                                textViewFragmenAgregar.setVisibility(View.INVISIBLE);



                                if(numero == 100){


                                    progressBarCircle.setVisibility(View.INVISIBLE);
                                    textViewCircleFragment.setVisibility(View.INVISIBLE);
                                    textViewCircleFragment.setText(" "+ 0 + " %");

                                    imgCamaraAgregar.setVisibility(View.VISIBLE);
                                    categoriaPuntoRecarga.setVisibility(View.VISIBLE);
                                    rbDisponiblePuntoRecargaAgregar.setVisibility(View.VISIBLE);
                                    rbNoDisponiblePuntoRecargaAgregar.setVisibility(View.VISIBLE);
                                    btnEnviarPuntoRecarga.setVisibility(View.VISIBLE);
                                    txtNombreInputFragmentAgregar.setVisibility(View.VISIBLE);
                                    txtGrupoInputFragmentAgregar.setVisibility(View.VISIBLE);
                                    txtDireccionInputFragmentAgregar.setVisibility(View.VISIBLE);
                                    txtTelefonoInputFragmentAgregar.setVisibility(View.VISIBLE);
                                    textViewFragmenAgregar.setVisibility(View.VISIBLE);


                                }
                            }
                        });

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(numero == 100){

                            Intent intent = new Intent(getContext(), HomeUserActivity.class);
                            startActivity(intent);

                        }
                        numero++;
                        isActivate = false;
                    }

                }
            });
            hilo.start();
        }

        ////////////////////////////////////////////////////////////////////////



        toastOkCorto("Punto de Recarga Ingresado");


    }





    private void enviarDatosPuntoDb() {

        try{

            if(validar()){

                mostrarAlertaDeRegistroPunto();

            }else{

                toastFailCorto("Por favor complete todas las casillas");
            }

        }catch(Exception e){

            toastFailLargo("Ha ocurrido un error al intentar iniciar sesión: " + e.getMessage());
        }

        edNombrePuntoRecargaAgregar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtNombreInputFragmentAgregar.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edGrupoPuntoRecargaAgregar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtGrupoInputFragmentAgregar.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edDireccionPuntoRecargaAgregar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtDireccionInputFragmentAgregar.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edTelefonoPuntoRecargaAgregar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtTelefonoInputFragmentAgregar.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }






    private void seleccionarFoto(){

        boolean permisoCamara = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED;
        //boolean permisoGpsCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        //boolean permisoGpsFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
        boolean permisoAlmacenamiento = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED;


        if(permisoCamara && permisoAlmacenamiento){

            mostrarAlertaCapturaImagen();



        }else{

            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},CODE_PERMISSION_CAMARA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null){
            String nameImage = nameImageCamera();
            UploadTask uploadTask;
            switch (requestCode){
                case CODE_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    imgCamaraAgregar.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] dataImagen = baos.toByteArray();
                    uploadTask = mStorage.child("imagenes").child(nameImage).putBytes(dataImagen);
                    obtenerUriImageStorage(uploadTask,nameImage);

                    break;
                case CODE_MEDIA_STORE:
                    Uri uriImagen = data.getData();
                    imgCamaraAgregar.setImageURI(uriImagen);

                    uploadTask = mStorage.child("imagenes").child(nameImage).putFile(uriImagen);
                    obtenerUriImageStorage(uploadTask,nameImage);

                    break;
            }
        }
    }


    private void obtenerUriImageStorage(UploadTask uploadTask,String nameImage) {
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return mStorage.child("imagenes").child(nameImage).getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    uriImageStorage = downloadUri.toString();
                } else {
                    toastFailLargo("Fallo en el cargue de la imagen");
                }
            }
        });
    }


    private String nameImageCamera() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        Date date = new Date();
        Random random = new Random();
        int numRandom = random.nextInt(100000);
        return id+"-"+date+"-"+numRandom+".jpeg";
    }







    private void mostrarAlertaCapturaImagen(){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext())
                .setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_alerta_seleccion_imagen,null);

        builder.setView(view);

        // TODO BOTONES POR DEFECTO
        /*
        builder.setView(inflater.inflate(R.layout.dialog_presonalizado, null))
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Conectando...", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Cancel...", Toast.LENGTH_SHORT).show();
                    }
                });                      */



        final android.app.AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView txt = view.findViewById(R.id.text_dialog);
        txt.setText("¡ Seleccione el medio para Cargar la Imagen !");


        Button btnPositive = view.findViewById(R.id.btnPositive);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ACCION POSITIVA ALERT DIALOG
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CODE_IMAGE_CAPTURE);

                dialog.dismiss();
            }
        });

        // TODO set btn Cancel onClickListener

        Button btnNegative = view.findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_MEDIA_STORE);

                dialog.dismiss();
            }
        });

    }


















    public void toastOkLargo(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_ok, linear_layout_toast_ok);
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_1);
        txtMessage.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    public void toastOkCorto(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_ok, linear_layout_toast_ok);
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_1);
        txtMessage.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
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

    public void toastFailCorto(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_fail, linear_layout_toast_fail);
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_error);
        txtMessage.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }



    private boolean validar(){
        boolean retorno = true;
        String nombre, direccion, grupo, telefono;
        nombre = edNombrePuntoRecargaAgregar.getText().toString();
        direccion = edDireccionPuntoRecargaAgregar.getText().toString();
        grupo = edGrupoPuntoRecargaAgregar.getText().toString();
        telefono = edTelefonoPuntoRecargaAgregar.getText().toString();


        if(nombre.isEmpty()){
            txtNombreInputFragmentAgregar.setError("El Nombre del punto es Requerido");
            retorno = false;
        }else{
            txtNombreInputFragmentAgregar.setErrorEnabled(false);
        }

        if(direccion.isEmpty()){
            txtDireccionInputFragmentAgregar.setError("La Direccion del punto es Requerida");
            retorno = false;
        }else{
            txtDireccionInputFragmentAgregar.setErrorEnabled(false);
        }


        if(grupo.isEmpty()){
            txtGrupoInputFragmentAgregar.setError("El Grupo del punto es Requerido");
            retorno = false;
        }else{
            txtGrupoInputFragmentAgregar.setErrorEnabled(false);
        }

        if(telefono.isEmpty()){
            txtTelefonoInputFragmentAgregar.setError("El Telefono del punto es Requerido");
            retorno = false;
        }else{
            txtTelefonoInputFragmentAgregar.setErrorEnabled(false);
        }

        return retorno;
    }
}

