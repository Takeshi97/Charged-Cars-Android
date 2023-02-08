package electric.cars.chargedcars;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RecuperarPassActivity extends AppCompatActivity {

    private EditText edCorreoRecuperar, edFechaCumpleRecuperacion;

    private FirebaseAuth mAuth;
    //private ProgressDialog mDialog;



    private AnimationDrawable anim_1;
    private ConstraintLayout linearLayoutRecovery;



    private TextInputLayout txtInputLayoutCorreoRecuperacion, txtInputLayoutFechaRecuperar;



    int numero = 0;
    boolean isActivate = false;
    Handler handler = new Handler();
    TextView textViewCircle;
    ProgressBar progressBarCircle;

    ImageView imageViewRecuperar;
    Button btnEnviarRecuperar;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);


        edCorreoRecuperar = findViewById(R.id.edCorreoRecuperar);
        edFechaCumpleRecuperacion = findViewById(R.id.edFechaCumpleRecuperacion);


        String correo_recu = getIntent().getStringExtra("correo_recu");
        edCorreoRecuperar.setText(correo_recu);



        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        //mDialog = new ProgressDialog(this);


        //mAuth = FirebaseAuth.getInstance();






        txtInputLayoutCorreoRecuperacion = findViewById(R.id.txtInputLayoutCorreoRecuperacion);
        txtInputLayoutFechaRecuperar = findViewById(R.id.txtInputLayoutFechaRecuperar);


        btnEnviarRecuperar = findViewById(R.id.btnEnviarRecuperar);
        imageViewRecuperar = findViewById(R.id.imageViewRecuperar);
        progressBarCircle = (ProgressBar) findViewById(R.id.Circular_Bar_Circle);
        textViewCircle = (TextView) findViewById(R.id.textViewCircle);




        edFechaCumpleRecuperacion.setOnClickListener(this::obtenerFechaCumple);





        init();

        anim_1 = (AnimationDrawable) linearLayoutRecovery.getBackground();
        anim_1.setEnterFadeDuration(2300);
        anim_1.setExitFadeDuration(2300);

    }

    private void init(){

        this.linearLayoutRecovery = findViewById(R.id.linearLayoutRecovery);
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












    public void obtenerFechaCumple(View view){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                System.out.println(year + "/" + (month+1) + "/" + dayOfMonth);

                edFechaCumpleRecuperacion.setText(dayOfMonth + "/" + (month+1) + "/" + year);

            }
        }, year,month,dayofmonth);

        datePickerDialog.show();
    }















    public void envioCorreoRecuperar(View view){

        String email = edCorreoRecuperar.getText().toString();
        /*
        mDialog.setMessage("Estamos trabajando para usted ...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();*/

        try{

            if(validar()){

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            // Aqui va si se envio el correo
                            //mDialog.dismiss();

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

                                                    textViewCircle.setVisibility(View.VISIBLE);
                                                    textViewCircle.setText(numero + " %");
                                                    progressBarCircle.setVisibility(View.VISIBLE);
                                                    progressBarCircle.setProgress(numero);
                                                    txtInputLayoutCorreoRecuperacion.setVisibility(View.INVISIBLE);
                                                    txtInputLayoutFechaRecuperar.setVisibility(View.INVISIBLE);
                                                    btnEnviarRecuperar.setVisibility(View.INVISIBLE);
                                                    imageViewRecuperar.setVisibility(View.INVISIBLE);



                                                    if(numero == 100){


                                                        progressBarCircle.setVisibility(View.INVISIBLE);
                                                        textViewCircle.setVisibility(View.INVISIBLE);
                                                        textViewCircle.setText(" "+ 0 + " %");

                                                        txtInputLayoutCorreoRecuperacion.setVisibility(View.VISIBLE);
                                                        txtInputLayoutFechaRecuperar.setVisibility(View.VISIBLE);
                                                        btnEnviarRecuperar.setVisibility(View.VISIBLE);
                                                        imageViewRecuperar.setVisibility(View.VISIBLE);


                                                    }
                                                }
                                            });

                                            try {
                                                Thread.sleep(20);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if(numero == 100){

                                                Intent intent = new Intent(RecuperarPassActivity.this, LoginActivity.class);
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

                            toastOkLargo("Se esta Enviando el Correo de Recuperacion de Contraseña");

                        }else{

                            Log.w("ErrorSendEmail", task.getException().toString());
                            //mDialog.dismiss();
                            toastFailLargo("Fallo al enviar el Correo");
                        }


                    }
                });

            }else{

                toastFailCorto("Por favor complete todas las casillas");
               // mDialog.dismiss();
            }

        }catch (Exception e){

            toastFailLargo("Ha ocurrido un error al Recuperar la Contraseña: " + e.getMessage());
           // mDialog.dismiss();
        }

        edCorreoRecuperar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutCorreoRecuperacion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edFechaCumpleRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtInputLayoutFechaRecuperar.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });






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
        String e_mail, date;
        e_mail = edCorreoRecuperar.getText().toString();
        date = edFechaCumpleRecuperacion.getText().toString();
        if(e_mail.isEmpty()){
            txtInputLayoutCorreoRecuperacion.setError("El Correo Electronico es Requerido");
            retorno = false;
        }else{
            txtInputLayoutCorreoRecuperacion.setErrorEnabled(false);
        }

        if(date.isEmpty()){
            txtInputLayoutFechaRecuperar.setError("La Fecha de Nacimiento es Requerida");
            retorno = false;
        }else{
            txtInputLayoutFechaRecuperar.setErrorEnabled(false);
        }

        return retorno;
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