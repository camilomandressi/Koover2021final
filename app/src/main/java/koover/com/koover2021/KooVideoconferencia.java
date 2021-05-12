package koover.com.koover2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class KooVideoconferencia extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private KooDetailAdapter kooDetailAdapter;
    int pos;
    Button contratar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koo_videoconferencia);

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

        Button contratar = findViewById(R.id.btnContratarVc);

        contratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contratarVc = new Intent(v.getContext(), ContratarVideoconferencia.class);
                startActivity(contratarVc);
            }
        });


    }
}