package electric.cars.chargedcars;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import electric.cars.chargedcars.model.Usuario;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText edCorreoRegistro, edPassRegistro;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText edFechaCumple, edNombreRegistro, edApellidoRegistro, edNewPassRegistro, edTelefonoRegistro;


    private AnimationDrawable anim_1;
    private ConstraintLayout LinearLayoutRegistro;


    private final int CODE_PERMISSION_GPS = 4;
    Switch switchGps;



    private TextInputLayout txtInputLayoutNombreRegistro, txtInputLayoutApellidoRegistro,
            txtInputLayoutFechaRegistro, txtInputLayoutTelefonoRegistro,
            txtInputLayoutCorreoRegistro, txtInputLayoutPassEnterRegistro, txtInputLayoutPassRepeatRegistro;




    Button btnRegistrarRegistro;


    int numero = 0;
    boolean isActivate = false;
    Handler handler = new Handler();
    TextView textViewCircle;
    ProgressBar progressBarCircle;
    //ImageView imageViewRegistro;


    //Dialog customDialog = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);




        //imageViewRegistro = findViewById(R.id.imageView5);
        progressBarCircle = (ProgressBar) findViewById(R.id.Circular_Bar_Circle_Register);
        textViewCircle = (TextView) findViewById(R.id.textViewCircle_Register);


        edCorreoRegistro = findViewById(R.id.edCorreoRegistro);
        edPassRegistro = findViewById(R.id.edPassRegistro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edFechaCumple = findViewById(R.id.edFechaCumpleRegistro);

        edNombreRegistro = findViewById(R.id.edNombreRegistro);
        edApellidoRegistro = findViewById(R.id.edApellidoRegistro);
        edNewPassRegistro = findViewById(R.id.edNewPassRegistro);

        edTelefonoRegistro = findViewById(R.id.edTelefonoRegistro);


        btnRegistrarRegistro = findViewById(R.id.btnRegistrarRegistro);
        btnRegistrarRegistro.setOnClickListener(this::clickEnRegistro);

        switchGps = (Switch) findViewById(R.id.switch1);



        init();

        anim_1 = (AnimationDrawable) LinearLayoutRegistro.getBackground();
        anim_1.setEnterFadeDuration(2300);
        anim_1.setExitFadeDuration(2300);







        txtInputLayoutNombreRegistro = findViewById(R.id.txtInputLayoutNombreRegistro);
        txtInputLayoutApellidoRegistro = findViewById(R.id.txtInputLayoutApellidoRegistro);
        txtInputLayoutFechaRegistro = findViewById(R.id.txtInputLayoutFechaRegistro);
        txtInputLayoutTelefonoRegistro = findViewById(R.id.txtInputLayoutTelefonoRegistro);
        txtInputLayoutCorreoRegistro = findViewById(R.id.txtInputLayoutCorreoRegistro);
        txtInputLayoutPassEnterRegistro = findViewById(R.id.txtInputLayoutPassEnterRegistro);
        txtInputLayoutPassRepeatRegistro = findViewById(R.id.txtInputLayoutPassRepeatRegistro);

/*
        findViewById(R.id.btnRegistrarRegistro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoPersonalizado();
            }
        });      */


    }


    private void init(){

        this.LinearLayoutRegistro = findViewById(R.id.LinearLayoutRegistro);
    }


    public void clickSwitch(View view){



        if (switchGps.isChecked()){

            boolean permisosFine = ContextCompat.checkSelfPermission(RegisterUserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
            boolean permisosCoarse = ContextCompat.checkSelfPermission(RegisterUserActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
            if(permisosCoarse || permisosFine){
                toastOkCorto("Permisos GPS Concedidos");
            }

        }else{

            ActivityCompat.requestPermissions(RegisterUserActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},CODE_PERMISSION_GPS);
            toastFailCorto("La aplicacion requiere permisos GPS");
        }
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

        //tabLayout.getTabAt(1).select();
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








    private void mostrarDialogoPersonalizado(){

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUserActivity.this)
                .setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_presonalizado,null);

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



        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView txt = view.findViewById(R.id.text_dialog);
        txt.setText("¿Desea registrar la información suministrada?");


        Button btnPositive = view.findViewById(R.id.btnPositive);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuarios();
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












    public void clickEnRegistro(View view){

        String nombre, apellido, fecha, telefono, e_mail, password, password_repeat;
        nombre = edNombreRegistro.getText().toString();
        apellido = edApellidoRegistro.getText().toString();
        fecha = edFechaCumple.getText().toString();
        telefono = edTelefonoRegistro.getText().toString();
        e_mail = edCorreoRegistro.getText().toString();
        password = edPassRegistro.getText().toString();
        password_repeat = edNewPassRegistro.getText().toString();


        try{

            if(validar()){

                mostrarDialogoPersonalizado();




            }else if(nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty() || telefono.isEmpty() || e_mail.isEmpty() || password.isEmpty() || password_repeat.isEmpty()){

                toastFailCorto("Por favor complete todas las casillas");

            }

        }catch(Exception e){

            toastFailLargo("Ha ocurrido un error al intentar Registrarse: " + e.getMessage());
        }

        edNombreRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutNombreRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edApellidoRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutApellidoRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edFechaCumple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutFechaRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edTelefonoRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutTelefonoRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edCorreoRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutCorreoRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edPassRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutPassEnterRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edNewPassRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutPassRepeatRegistro.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }





    public void obtenerFechaCumple(View view){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                System.out.println(year + "/" + (month+1) + "/" + dayOfMonth);

                edFechaCumple.setText(dayOfMonth + "/" + (month+1) + "/" + year);

            }
        }, year,month,dayofmonth);

        datePickerDialog.show();
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


    public void toastOkLargo(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_ok, findViewById(R.id.linear_layout_toast_ok));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_1);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void toastOkCorto(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_ok, findViewById(R.id.linear_layout_toast_ok));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_1);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void toastFailLargo(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_fail, findViewById(R.id.linear_layout_toast_fail));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_error);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void toastFailCorto(String message){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.linear_l_toast_fail, findViewById(R.id.linear_layout_toast_fail));
        TextView txtMessage = view.findViewById(R.id.txtMessageToast_error);
        txtMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }










    private boolean validar(){

        boolean retorno = true;
        String nombre, apellido, fecha, telefono, e_mail, password, password_repeat;
        nombre = edNombreRegistro.getText().toString();
        apellido = edApellidoRegistro.getText().toString();
        fecha = edFechaCumple.getText().toString();
        telefono = edTelefonoRegistro.getText().toString();
        e_mail = edCorreoRegistro.getText().toString();
        password = edPassRegistro.getText().toString();
        password_repeat = edNewPassRegistro.getText().toString();



        if(nombre.isEmpty()){
            txtInputLayoutNombreRegistro.setError("El Nombre es Requerido");
            retorno = false;
        }else{
            txtInputLayoutNombreRegistro.setErrorEnabled(false);
        }

        if(apellido.isEmpty()){
            txtInputLayoutApellidoRegistro.setError("El Apellido es Requerido");
            retorno = false;
        }else{
            txtInputLayoutApellidoRegistro.setErrorEnabled(false);
        }

        if(fecha.isEmpty()){
            txtInputLayoutFechaRegistro.setError("La Fecha de nacimiento es Requerida");
            retorno = false;
        }else{
            txtInputLayoutFechaRegistro.setErrorEnabled(false);
        }

        if(telefono.isEmpty()){
            txtInputLayoutTelefonoRegistro.setError("El número de teléfono es Requerido");
            retorno = false;
        }else{
            txtInputLayoutTelefonoRegistro.setErrorEnabled(false);
        }

        if(e_mail.isEmpty()){
            txtInputLayoutCorreoRegistro.setError("El Correo Electronico es Requerido");
            retorno = false;
        }else{
            txtInputLayoutCorreoRegistro.setErrorEnabled(false);
        }

        if(password.isEmpty()){
            txtInputLayoutPassEnterRegistro.setError("La Contraseña es Requerida");
            retorno = false;
        }else{
            txtInputLayoutPassEnterRegistro.setErrorEnabled(false);
        }

        if(password_repeat.isEmpty()){
            txtInputLayoutPassRepeatRegistro.setError("La Confirmacion de Contraseña es Requerida");
            retorno = false;
        }else{
            txtInputLayoutPassRepeatRegistro.setErrorEnabled(false);
        }

        if(!password.equals(password_repeat)){
            txtInputLayoutPassEnterRegistro.setError("Las contraseñas ingresadas no coinciden");
            txtInputLayoutPassRepeatRegistro.setError("Las contraseñas ingresadas no coinciden");
            retorno = false;
        }else if(!password.isEmpty() && !password_repeat.isEmpty()){

            txtInputLayoutPassEnterRegistro.setErrorEnabled(false);
            txtInputLayoutPassRepeatRegistro.setErrorEnabled(false);
        }


        return retorno;
    }


    private void registrarUsuarios(){

        String nombre = edNombreRegistro.getText().toString();
        String apellido = edApellidoRegistro.getText().toString();
        String fecha = edFechaCumple.getText().toString();
        String telefono = edTelefonoRegistro.getText().toString();
        String email = edCorreoRegistro.getText().toString();
        String password = edPassRegistro.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                /*          // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);                                                         */

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String id = currentUser.getUid();
                            Usuario usuario = new Usuario(nombre,apellido,fecha,telefono,email);
                            mDatabase.child("usuarios").child(id).setValue(usuario);


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
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void run() {

                                                    textViewCircle.setVisibility(View.VISIBLE);
                                                    textViewCircle.setText(numero + " %");
                                                    progressBarCircle.setVisibility(View.VISIBLE);
                                                    progressBarCircle.setProgress(numero);
                                                    btnRegistrarRegistro.setVisibility(View.INVISIBLE);
                                                    switchGps.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutNombreRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutApellidoRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutFechaRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutTelefonoRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutCorreoRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutPassEnterRegistro.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutPassRepeatRegistro.setVisibility(View.INVISIBLE);



                                                    if(numero == 100){

                                                        /*
                                                        progressBarCircle.setVisibility(View.INVISIBLE);
                                                        textViewCircle.setVisibility(View.INVISIBLE);
                                                        textViewCircle.setText(" "+ 0 + " %");

                                                        txtInputLayoutCorreoRecuperacion.setVisibility(View.VISIBLE);
                                                        txtInputLayoutFechaRecuperar.setVisibility(View.VISIBLE);
                                                        btnEnviarRecuperar.setVisibility(View.VISIBLE);
                                                        imageViewRecuperar.setVisibility(View.VISIBLE);*/


                                                    }
                                                }
                                            });

                                            try {
                                                Thread.sleep(20);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if(numero == 100){

                                                Intent intent = new Intent(RegisterUserActivity.this, HomeUserActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                            numero++;
                                            isActivate = false;
                                        }

                                    }
                                });
                                hilo.start();
                            }

                            toastOkLargo("Se ha Registrado Satisfactoriamente");

                            ////////////////////////////////////////////////////////////////////////



                        } else {
                /*          // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);                                                         */

                            toastFailCorto("Error al registrar usuario");

                        }
                    }
                });

    }










}



