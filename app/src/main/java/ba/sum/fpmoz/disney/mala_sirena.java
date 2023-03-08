package ba.sum.fpmoz.disney;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class mala_sirena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mala_sirena);

        VideoView mala_sirena_View = (VideoView) findViewById(R.id.mala_sirenaView);
        mala_sirena_View.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mala_sirena));

        MediaController mediaController = new MediaController(this);
        mala_sirena_View.setMediaController(mediaController);
        mala_sirena_View.start();
    }
}