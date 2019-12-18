package swe.fhbielefeld.raumbuchung20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import swe.fhbielefeld.raumbuchung20.modules.User;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = this.findViewById(R.id.button_login);
        final EditText editText = this.findViewById(R.id.editText_username);


        button.setOnClickListener(new View.OnClickListener() {


            // Method to check whether the user input matches the (hard-coded) database.
            private User UserCheck(){
                EditText username = findViewById(R.id.editText_username);
                EditText password = findViewById(R.id.editText_password);

                User u1 = new User("admin","admin");
                User u2 = new User("chantal","chantal");
                User u3 = new User("fouad","fouad");
                User u4 = new User("marko","marko");
                User u5 = new User("alwin","alwin");

                if(username.getText().toString().equals(u1.getName()) && password.getText().toString().equals(u1.getPasswort())) {
                    return u1;
                }else if(username.getText().toString().equals(u2.getName()) && password.getText().toString().equals(u2.getPasswort())){
                    return u2;
                }else if(username.getText().toString().equals(u3.getName()) && password.getText().toString().equals(u3.getPasswort())){
                    return u3;
                }else if(username.getText().toString().equals(u4.getName()) && password.getText().toString().equals(u4.getPasswort())){
                    return u4;
                }else if(username.getText().toString().equals(u5.getName()) && password.getText().toString().equals(u5.getPasswort())){
                    return u5;
                }
                return null;
            }

            @Override
            public void onClick(View view){
                // Check if username/pw match and login -> home screen
                User user = UserCheck() ;
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ANGEMELDETER_USER", user);
                    Toast.makeText(LoginActivity.this, "Sie haben sich erfolgreich angemeldet",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    // if wrong, force user to write again
                    Toast.makeText(getApplicationContext(), "Falscher Username oder Passwort",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
