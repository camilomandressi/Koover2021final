package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContratarVideoconferencia extends AppCompatActivity {

    FloatingActionButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar_videoconferencia);

       FloatingActionButton atras = findViewById(R.id.btnAtras);

       atras.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent atrasVc = new Intent(ContratarVideoconferencia.this, KooVideoconferencia.class);
               startActivity(atrasVc);
           }
       });


    }
}