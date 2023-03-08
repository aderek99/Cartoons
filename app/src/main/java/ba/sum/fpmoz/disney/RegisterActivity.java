package ba.sum.fpmoz.disney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button finalRegisterBtn = findViewById(R.id.registerBtn);
        EditText registerEmailTxt = findViewById(R.id.registerEmailTxt);
        EditText registerPasswordTxt = findViewById(R.id.registerPasswordTxt);
        EditText registerPasswordCnfTxt = findViewById(R.id.registerPasswordCnfTxt);
        finalRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerEmailTxt.getText().toString();
                String password = registerPasswordTxt.getText().toString();
                String passwordCnf = registerPasswordCnfTxt.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Molimo unesite sve vrijednosti.", Toast.LENGTH_LONG).show();
                } else if (!password.equals(passwordCnf)) {
                    Toast.makeText(getApplicationContext(), "Molimo unesite lozinke koje se podudaraju.", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult>
                                                       task) {
                            if (task.isComplete()) {
                                Toast.makeText(getApplicationContext(),
                                        "Uspješno ste napravili račun na sustavu.", Toast.LENGTH_LONG).show();
                                registerEmailTxt.setText("");
                                registerPasswordTxt.setText("");
                                registerPasswordCnfTxt.setText("");
                            }
                        }
                    });
                }
            }
        });
    }
}