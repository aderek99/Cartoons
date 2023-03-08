package ba.sum.fpmoz.disney.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.disney.R;
import ba.sum.fpmoz.disney.mala_sirena;
import ba.sum.fpmoz.disney.model.Cartoons;


public class CartoonsAdapter extends FirebaseRecyclerAdapter<Cartoons, CartoonsAdapter.CartoonsViewHolder>{

    Context context;
    public static final String TAG = "CARTOONS_ADAPTER";
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://disney-415ce-default-rtdb.europe-west1.firebasedatabase.app/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public CartoonsAdapter(@NonNull FirebaseRecyclerOptions <Cartoons> options) {
        super(options);
    }

    @NonNull
    @Override
    public CartoonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartoons_view, parent, false);
        return new CartoonsAdapter.CartoonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartoonsViewHolder holder, int position, @NonNull Cartoons model) {
        String title = model.getTitle();
        String author = model.getAuthor();
        String description = model.getDescription();
        String image = model.getImage();


        holder.titleTv.setText(title);
        holder.authorTv.setText(author);
        holder.descriptionTv.setText(description);
        Picasso
                .get()
                .load(image)
                .into(holder.imageIv);
        holder.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(model.getTitle().equals("Mala sirena")){
                    Intent mala_sirenaIntent = new Intent(context, mala_sirena.class);
                    context.startActivity(mala_sirenaIntent);
                }



            }
        });

    }



    class CartoonsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv, authorTv, descriptionTv;
        ImageView imageIv;
        ImageButton videoBtn;

        public CartoonsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.titleTv);
            authorTv = itemView.findViewById(R.id.authorTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            imageIv = itemView.findViewById(R.id.imageIv);
            videoBtn = itemView.findViewById(R.id.videoBtn);
        }

    }

}