package swe.fhbielefeld.raumbuchung20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import swe.fhbielefeld.raumbuchung20.modules.Client;
import swe.fhbielefeld.raumbuchung20.modules.User;

public class MainActivity extends AppCompatActivity {
    public User angemeldeterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder( R.id.navigation_home, R.id.navigation_neu, R.id.navigation_buchungen).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if(extras==null){
                finish();
            }else{
                Client.angemeldeterUser=(User)extras.getSerializable("ANGEMELDETER_USER");
            }
        }else{
            Client.angemeldeterUser = (User)savedInstanceState.getSerializable("ANGEMELDETER_USER");
        }
        /*new AlertDialog.Builder(this)
                .setTitle("Sie haben sich erfolgreich angemeldet")
                .setMessage(email)
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/


    }
}
