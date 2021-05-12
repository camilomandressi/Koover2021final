package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class KooDetailActivity extends AppCompatActivity implements UpdateRecyclerView2 {

    private RecyclerView recyclerView2;
    private KooDetailAdapter kooDetailAdapter;
    Activity context;
    int pos;
    Button contratar;
    FloatingActionButton btnAtras;
    String nameRes;
    int backGroundRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_koo_detail);

        final Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);
        //Creo nuevas variables para llamar los datos parceables
        DynamicRVModel datosKooveters = intent.getParcelableExtra("Item Ejemplo"); // variable datos Kooveters

        String nameRes = datosKooveters.getName(); // Llamo a la variable del nombre
        TextView nombreKoo = findViewById(R.id.kooveer_nombre); // Le digo a que elemento le voy a pegar
        nombreKoo.setText(nameRes); //Macheo los datos variable y elemento.

        int backGroundRecibido = datosKooveters.getImageFondo();
        ImageView background = findViewById(R.id.imageView4);
        background.setImageResource(backGroundRecibido);



        ArrayList<KooDetailModel> kooDetailModels = new ArrayList<>();
        kooDetailModels.add(new KooDetailModel("Luis Gutierrez", "Buen servicio llego a tiempo", pos));
        kooDetailModels.add(new KooDetailModel("Analia Perez", "Excelente servicio", pos));
        kooDetailModels.add(new KooDetailModel("Martin Gonzalez", "Aceptable servicio se demoro en la entrega", pos));
        kooDetailModels.add(new KooDetailModel("Matilde Quiroga", "Super recomendable Muy amable", pos));
        kooDetailModels.add(new KooDetailModel("Lucia Machado", "100 recomendable", pos));
        kooDetailModels.add(new KooDetailModel("David Terans", "Excelente servicio muy amable", pos));

        recyclerView2 = findViewById(R.id.rv_comentarios);
        kooDetailAdapter = new KooDetailAdapter(kooDetailModels);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView2.setAdapter(kooDetailAdapter);




        Button contratar = findViewById(R.id.contratar);

        contratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContratar = new Intent(v.getContext(), Contratar.class);
                startActivity(intentContratar);
            }
        });


        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(KooDetailActivity.this, MainActivity.class);
                finish();
                startActivity(atras);
            }
        });
    }

    @Override
    public void callback2(int position, ArrayList<KooDetailModel> items) {
        kooDetailAdapter = new KooDetailAdapter(items);
        kooDetailAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(kooDetailAdapter);
    }
}