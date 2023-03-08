package ba.sum.fpmoz.disney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import ba.sum.fpmoz.disney.model.UserProfile;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://disney-415ce-default-rtdb.europe-west1.firebasedatabase.app/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        EditText profileFirstnameTxt = findViewById(R.id.profileFirstnameTxt);
        EditText profileLastnameTxt = findViewById(R.id.profileLastnemeTxt);
        EditText profileEmailTxt = findViewById(R.id.profileEmailTxt);
        EditText profileDateOfBirth = findViewById(R.id.profileDateOfBith);
        Button profileSaveBtn = findViewById(R.id.profileSaveBtn);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_profile:
                        return true;

                    case R.id.navigation_cartoons:
                        startActivity(new Intent(getApplicationContext(), CartoonsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigation_sign:
                        startActivity(new Intent(getApplicationContext(), SingoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        if (currentUser != null) {
            DatabaseReference profileRef = database.getReference("profile/").child(currentUser.getUid());
            profileEmailTxt.setText(currentUser.getEmail());

            profileRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    UserProfile profile = task.getResult().getValue(UserProfile.class);
                    if (profile != null) {
                        profileFirstnameTxt.setText(profile.getFirstname());
                        profileLastnameTxt.setText(profile.getLastname());
                        profileDateOfBirth.setText(profile.getDateOfBirth().toString());
                    }
                }
            });


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            profileSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String firstname = profileFirstnameTxt.getText().toString();
                    String lastname = profileLastnameTxt.getText().toString();
                    String email = profileEmailTxt.getText().toString();
                    try {
                        Date dateOfBirth = sdf.parse(profileDateOfBirth.getText().toString());
                        UserProfile profile = new UserProfile(firstname, lastname, email, dateOfBirth);
                        profileRef.setValue(profile);
                    } catch (ParseException e) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Morate unijeti ispravan datum.",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            });
        }
    }
}