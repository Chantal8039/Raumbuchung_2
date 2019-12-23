package swe.fhbielefeld.raumbuchung20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import swe.fhbielefeld.raumbuchung20.modules.Server;
import swe.fhbielefeld.raumbuchung20.modules.User;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPasswort;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }
    private void initViews(){
        edtUsername = findViewById(R.id.editText_username);
        edtPasswort = findViewById(R.id.editText_password);
        button = this.findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Check if username/pw match and login -> home screen
                login();
            }
        });
    }
    private void login(){
        User user = userCheck() ;
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
    // Method to check whether the user input matches the (hard-coded) database.
    private User userCheck(){
        User u = Server.getInstance().findUser(edtUsername.getText().toString());
        if(u != null){
            if(u.getPasswort().equals(edtPasswort.getText().toString())){
                return u;
            }
        }else{
            return null;
        }
        return null;
    }
}
