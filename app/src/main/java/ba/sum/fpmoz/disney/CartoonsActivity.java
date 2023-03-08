package ba.sum.fpmoz.disney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.disney.adapter.CartoonsAdapter;
import ba.sum.fpmoz.disney.model.Cartoons;

public class CartoonsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    CartoonsAdapter cartoonsAdapter;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://disney-415ce-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoons);
        this.recyclerView = findViewById(R.id.cartoonsRv);
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        FirebaseRecyclerOptions<Cartoons> options = new FirebaseRecyclerOptions.Builder<Cartoons>().setQuery(
                this.mDatabase.getReference("cartoons"),
                Cartoons.class
        ).build();

        this.cartoonsAdapter = new CartoonsAdapter(options);
        this.recyclerView.setAdapter(this.cartoonsAdapter);



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cartoons);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_cartoons:
                        return true;

                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigation_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.cartoonsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.cartoonsAdapter.stopListening();
    }
}