package electric.cars.chargedcars;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import electric.cars.chargedcars.controlador.PagerController;


public class HomeUserActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    //private RecyclerView recyclerRecarga;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PagerController pagerController;



    private LinearLayout linearLayoutHomeUserActivity;
    //private TextView textViewLetraSpinner;
    private AnimationDrawable anim_1;
    private AnimationDrawable anim_2;
    private AnimationDrawable anim_3;


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



        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);


        drawerLayout = findViewById(R.id.drawer_layout_Home_User);
        navigationView = findViewById(R.id.navigator_HomeUser);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.primero:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono primero", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.segundo:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono segundo", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.tercero:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono tercero", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.cuarto:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono cuarto", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.quinto:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono quinto", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.sexto:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono sexto", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.septimo:
                    {
                        Toast.makeText(HomeUserActivity.this,"Selecciono septimo", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

                return false;
            }
        });



        tabLayout = findViewById(R.id.tabLayoutHomeUser);
        viewPager = findViewById(R.id.viewPagerHomeUser);


        pagerController = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerController);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                pagerController.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }



        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(1).select();











        init();

        anim_1 = (AnimationDrawable) linearLayoutHomeUserActivity.getBackground();
        anim_1.setEnterFadeDuration(2300);
        anim_1.setExitFadeDuration(2300);

        anim_2 = (AnimationDrawable) tabLayout.getBackground();
        anim_2.setEnterFadeDuration(2300);
        anim_2.setExitFadeDuration(2300);
/*
        anim_3 = (AnimationDrawable) textViewLetraSpinner.getBackground();
        anim_3.setEnterFadeDuration(2300);
        anim_3.setExitFadeDuration(2300);*/


    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();
        }
    }


    private void init(){

        this.linearLayoutHomeUserActivity = findViewById(R.id.linearLayoutHomeUserActivity);
        this.tabLayout = findViewById(R.id.tabLayoutHomeUser);
        //this.textViewLetraSpinner = findViewById(R.id.textViewLetraSpinner);
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

        if(anim_2!=null && !anim_2.isRunning()){
            anim_2.start();
        }
/*
        if(anim_3!=null && !anim_3.isRunning()){
            anim_3.start();
        }*/


    }

    @Override
    protected void onPause() {
        super.onPause();

        if(anim_1!=null && anim_1.isRunning()){
            anim_1.stop();
        }

        if(anim_2!=null && anim_2.isRunning()){
            anim_2.stop();
        }

        ///////////////////////////////////////////////////
        /*if(isNetDisponible()){

            if(isOnlineNet()){

            }else{
                toastSinInternet("No hay acceso a Internet");
            }

        }else{
            toastHabilitarInternet("Internet Deshabilitado");
        }*/



/*
        if(anim_3!=null && anim_3.isRunning()){
            anim_3.stop();
        }*/

    }


















    @Override
    protected void onStart() {

        //tabLayout.getTabAt(1).select();
        super.onStart();

        ///////////////////////////////////////////////////
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






















