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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = this.findViewById(R.id.button_login);
        final EditText editText = this.findViewById(R.id.editText_username);


        button.setOnClickListener(new View.OnClickListener() {


            // Method to check whether the user input matches the (hard-coded) database.
            private boolean UserCheck(){
                EditText username = findViewById(R.id.editText_username);
                EditText password = findViewById(R.id.editText_password);

                String u1 = "admin", p1 = "admin";
                String u2 = "chantal", p2 = "chantal";
                String u3 = "fouad", p3 = "fouad";
                String u4 = "marko", p4 = "marko";
                String u5 = "alwin", p5 = "alwin";

                if(username.getText().toString().equals(u1) && password.getText().toString().equals(p1)) {
                    return true;
                }else if(username.getText().toString().equals(u2) && password.getText().toString().equals(p2)){
                    return true;
                }else if(username.getText().toString().equals(u3) && password.getText().toString().equals(p3)){
                    return true;
                }else if(username.getText().toString().equals(u4) && password.getText().toString().equals(p4)){
                    return true;
                }else return username.getText().toString().equals(u5) && password.getText().toString().equals(p5);
            }

            @Override
            public void onClick(View view){
                // Check if username/pw match and login -> home screen
                if(UserCheck()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    String email = "";
                    intent.putExtra("Extra_EMAIL",email);
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
