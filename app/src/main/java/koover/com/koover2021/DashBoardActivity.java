package koover.com.koover2021;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.DynamicLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import koover.com.koover2021.DRVinterface.LoadMore;

public class DashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;

    List<DynamicRVModel> items = new ArrayList();
    DynamicRVAdapter dynamicRVAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        final ArrayList<StaticRvModel> item = new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.doctor,"Salud"));
        item.add(new StaticRvModel(R.drawable.teacher,"Educación"));
        item.add(new StaticRvModel(R.drawable.delivery,"Delivery"));
        item.add(new StaticRvModel(R.drawable.lawyer,"Profesionales"));
        item.add(new StaticRvModel(R.drawable.worker,"Otros servicios"));

    }
}