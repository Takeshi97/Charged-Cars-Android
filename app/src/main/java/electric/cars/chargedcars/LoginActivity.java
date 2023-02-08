package electric.cars.chargedcars;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    Handler handler = new Handler();
    TextView textView3;
    ProgressBar progressBar_3;
    boolean isActivate = false;
    int numero = 0;



    private EditText edCorreoLogin, edPassLogin;
    //private Button btnIniciar;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;





    private AnimationDrawable anim_1;
    private ConstraintLayout linearLayoutLogin;


    private TextInputLayout txtInputCorreo, txtInputPass;
    private TextView txtOlvidoPass;
    private Button btnIniciar, button18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        progressBar_3 = (ProgressBar) findViewById(R.id.progressBar3);
        textView3 = (TextView) findViewById(R.id.textView3);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(this::iniciarSesion);




        edCorreoLogin = findViewById(R.id.edCorreoLogin);
        edPassLogin = findViewById(R.id.edPassLogin);
        //btnIniciar = findViewById(R.id.btnIniciar);


        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        mDialog = new ProgressDialog(this);


        //mAuth = FirebaseAuth.getInstance();




        this.init();

        anim_1 = (AnimationDrawable) linearLayoutLogin.getBackground();
        anim_1.setEnterFadeDuration(2300);
        anim_1.setExitFadeDuration(2300);




        txtInputCorreo = findViewById(R.id.txtInputCorreo);
        txtInputPass = findViewById(R.id.txtInputPass);
        txtOlvidoPass = findViewById(R.id.txtOlvidoPass);
        button18 = findViewById(R.id.button18);





    }




    private void init(){

        this.linearLayoutLogin = findViewById(R.id.linearLayoutLogin);
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(anim_1!=null && !anim_1.isRunning()){
            anim_1.start();
        }



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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            lanzarHomeUser(currentUser);

            /*
            Intent intent = new Intent(this, HomeUserActivity.class);
            intent.putExtra("email", currentUser.getEmail());
            startActivity(intent);
            finish();                              */


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




    public void iniciarSesion(View view){


        String email = edCorreoLogin.getText().toString();
        String password = edPassLogin.getText().toString();

        try {

            if(validar()){

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LoginSuccess", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    toastOkLargo("Autenticación Correcta");
                                    lanzarHomeUser(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("LoginError", "signInWithEmail:failure", task.getException());
                                    toastFailCorto("Credenciales Incorrectas");
                                    //updateUI(null);
                                }
                            }
                        });

            }else{
                toastFailCorto("Por favor complete todas las casillas");
            }

        }catch (Exception e){

            toastFailLargo("Ha ocurrido un error al intentar iniciar sesión: " + e.getMessage());
        }

        edCorreoLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInputCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edPassLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInputPass.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }








    private void lanzarHomeUser(FirebaseUser currentUser){

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
                                textView3.setText(numero + " %");
                                progressBar_3.setVisibility(View.VISIBLE);
                                progressBar_3.setProgress(numero);
                                btnIniciar.setVisibility(View.INVISIBLE);
                                txtInputCorreo.setVisibility(View.INVISIBLE);
                                txtInputPass.setVisibility(View.INVISIBLE);
                                txtOlvidoPass.setVisibility(View.INVISIBLE);
                                button18.setVisibility(View.INVISIBLE);
                                textView3.setVisibility(View.VISIBLE);

                                if(numero == 100){

                                    //progressBar_3.setVisibility(View.INVISIBLE);

                                    //textView3.setText(0 + " %");

                                }
                            }
                        });

                        try {
                            Thread.sleep(18);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(numero == 100){

                            Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                            intent.putExtra("email", currentUser.getEmail());
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

        ////////////////////////////////////////////////////////////////////////

    }



    public void registrarse(View view){

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
                                textView3.setText(numero + " %");
                                progressBar_3.setVisibility(View.VISIBLE);
                                progressBar_3.setProgress(numero);
                                btnIniciar.setVisibility(View.INVISIBLE);
                                txtInputCorreo.setVisibility(View.INVISIBLE);
                                txtInputPass.setVisibility(View.INVISIBLE);
                                txtOlvidoPass.setVisibility(View.INVISIBLE);
                                button18.setVisibility(View.INVISIBLE);
                                textView3.setVisibility(View.VISIBLE);

                                if(numero == 100){

                                    progressBar_3.setVisibility(View.INVISIBLE);
                                    textView3.setVisibility(View.INVISIBLE);
                                    textView3.setText(0 + " %");

                                    btnIniciar.setVisibility(View.VISIBLE);
                                    txtInputCorreo.setVisibility(View.VISIBLE);
                                    txtInputPass.setVisibility(View.VISIBLE);
                                    txtOlvidoPass.setVisibility(View.VISIBLE);
                                    button18.setVisibility(View.VISIBLE);

                                }
                            }
                        });

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(numero == 100){

                            Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
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



    }



    public void olvidoContraseña(View view){

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
                                textView3.setText(numero + " %");
                                progressBar_3.setVisibility(View.VISIBLE);
                                progressBar_3.setProgress(numero);
                                btnIniciar.setVisibility(View.INVISIBLE);
                                txtInputCorreo.setVisibility(View.INVISIBLE);
                                txtInputPass.setVisibility(View.INVISIBLE);
                                txtOlvidoPass.setVisibility(View.INVISIBLE);
                                button18.setVisibility(View.INVISIBLE);
                                textView3.setVisibility(View.VISIBLE);

                                if(numero == 100){

                                    progressBar_3.setVisibility(View.INVISIBLE);
                                    textView3.setVisibility(View.INVISIBLE);
                                    textView3.setText(0 + " %");

                                    btnIniciar.setVisibility(View.VISIBLE);
                                    txtInputCorreo.setVisibility(View.VISIBLE);
                                    txtInputPass.setVisibility(View.VISIBLE);
                                    txtOlvidoPass.setVisibility(View.VISIBLE);
                                    button18.setVisibility(View.VISIBLE);

                                }
                            }
                        });

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(numero == 100){

                            Intent intent = new Intent(LoginActivity.this, RecuperarPassActivity.class);
                            intent.putExtra("correo_recu", edCorreoLogin.getText().toString());
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

    }


    public void hackerLogin(View view){

        Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
        startActivity(intent);
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
        String e_mail, password;
        e_mail = edCorreoLogin.getText().toString();
        password = edPassLogin.getText().toString();
        if(e_mail.isEmpty()){
            txtInputCorreo.setError("El Correo Electronico es Requerido");
            retorno = false;
        }else{
            txtInputCorreo.setErrorEnabled(false);
        }

        if(password.isEmpty()){
            txtInputPass.setError("La contraseña es Requerida");
            retorno = false;
        }else{
            txtInputPass.setErrorEnabled(false);
        }

        return retorno;
    }


}























