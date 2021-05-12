package koover.com.koover2021;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import koover.com.koover2021.DRVinterface.LoadMore;

import static koover.com.koover2021.R.layout.dashboard_fragment;


public class DashBoardFragment<perfil> extends Fragment implements UpdateRecyclerView, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView, recyclerView2;
    private StaticRvAdapter staticRvAdapter;

    ArrayList<DynamicRVModel> items = new ArrayList();
    DynamicRVAdapter dynamicRVAdapter;
    int pos;

    ImageButton botonPerfil;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView nombreUser;

    //Inicialzo la variables
    private SearchView svBuscar;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(dashboard_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ArrayList<StaticRvModel> item = new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.doctor,"Salud"));
        item.add(new StaticRvModel(R.drawable.teacher,"Educación"));
        item.add(new StaticRvModel(R.drawable.delivery,"Delivery"));
        item.add(new StaticRvModel(R.drawable.lawyer,"Profesionales"));
        item.add(new StaticRvModel(R.drawable.worker,"Otros servicios"));

        recyclerView = root.findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(staticRvAdapter);

        items = new ArrayList<>();
        recyclerView2 = root.findViewById(R.id.rv_2);
        dynamicRVAdapter = new DynamicRVAdapter(items);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView2.setAdapter(dynamicRVAdapter);



        //BOTON DE ACCESO AL PERFIL
        ImageButton botonPerfil = (ImageButton)root.findViewById(R.id.ibProfileBtn);

        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irPerfilIntent = new Intent(getActivity(), UserProfile.class);
                startActivity(irPerfilIntent);
            }
        });


        nombreUser = (TextView) root.findViewById(R.id.nombreUser);
        svBuscar = (SearchView) root.findViewById(R.id.svBuscar);

        getUserInfo();
        initListener();

        return root;
    }


    @Override
    public void callback(int position, final ArrayList<DynamicRVModel> items) {
        dynamicRVAdapter = new DynamicRVAdapter(items);
        dynamicRVAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(dynamicRVAdapter);

        dynamicRVAdapter.setOnItemClickListner(new DynamicRVAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                pos = items.get(position).getPos();

                Intent intent = new Intent(getActivity(), KooDetailActivity.class);
                intent.putExtra("pos", pos);
                intent.putExtra("Item Ejemplo", items.get(position));
                startActivity(intent);

            }
        });

    }


    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nombreMostarPrincipal = dataSnapshot.child("name").getValue().toString();
                    nombreUser.setText(nombreMostarPrincipal);
                }
                else{
                    Toast.makeText(getContext(), "No funciona temporalmente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Se cran las funciones con la implementación
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        dynamicRVAdapter.filter(newText);
        return false;
    }

    private void initListener(){
        svBuscar.setOnQueryTextListener(DashBoardFragment.this);
    }
}
