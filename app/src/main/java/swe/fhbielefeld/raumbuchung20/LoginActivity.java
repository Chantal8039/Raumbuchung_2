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
        final EditText editText = this.findViewById(R.id.editText_email);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String email = "";
                intent.putExtra("Extra_EMAIL",email);
                Toast.makeText(LoginActivity.this, "Sie haben sich erfolgreich angemeldet",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }
}
