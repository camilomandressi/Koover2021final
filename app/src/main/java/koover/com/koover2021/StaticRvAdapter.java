package koover.com.koover2021;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRVViewHolder>{

    private ArrayList<StaticRvModel> items;
    int row_index = -1;
    UpdateRecyclerView updateRecyclerView;
    Activity activity;
    int pos;
    boolean check = true;
    boolean select = true;




    public StaticRvAdapter(ArrayList<StaticRvModel> items, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.items = items;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_rv_item,parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);


        return staticRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, final int position) {
        StaticRvModel currentItem = items.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.textView.setText(currentItem.getText());

        if (check){
            ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
            items.add(new DynamicRVModel("Juan", "Psicologo", R.drawable.deliveryman, R.drawable.salud, 0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Alberto", "Delivery especializado", R.drawable.deliveryman2, R.drawable.deliverymoto, 0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Juan", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto, 0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Jonny", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto,0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Christofer", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto,0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Juan Carlos", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto,0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Roberto", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto,0, new LatLng(34, 35)));
            items.add(new DynamicRVModel("Fernando", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto,0, new LatLng(34, 35)));

            updateRecyclerView.callback(position, items);

            check = false;

        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();

                if (position==0){
                    ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
                    items.add(new DynamicRVModel("Juan", "Psicologo", R.drawable.deliveryman, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Susana", "Fisioterapeuta", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Pedro", "Medico General", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Ernestina", "Pediatra", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Romulo", "Nutricionista", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Romualdo", "Enfermero", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Cristina", "Psicopedagoga", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Jorge", "Psicomotricista", R.drawable.deliveryman2, R.drawable.salud,1, new LatLng(34, 35)));


                    updateRecyclerView.callback(position, items);

                }
                else if (position==1){
                    ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
                    items.add(new DynamicRVModel("Alfredo", "Maestro Preescolar", R.drawable.deliveryman, R.drawable.educacionbg,2, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Teresita", "Profesora de Fisica", R.drawable.deliveryman2, R.drawable.educacionbg,2, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Mary", "Maestra de Primaria", R.drawable.deliveryman, R.drawable.educacionbg,2, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Elsa", "Educadora en Primera Infancia", R.drawable.deliveryman2, R.drawable.educacionbg,2, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Nicolas", "Profesor de Matematica", R.drawable.deliveryman, R.drawable.educacionbg,2, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Alberto", "Prfoesor de Literatura", R.drawable.deliveryman2, R.drawable.educacionbg,2, new LatLng(34, 35)));

                    updateRecyclerView.callback(position, items);

                }
                else if (position==2){
                    ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
                    items.add(new DynamicRVModel("Juan", "Delivery", R.drawable.deliveryman,R.drawable.deliverymoto, 3, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Jonny", "Delivery", R.drawable.deliveryman2,R.drawable.deliverymoto, 3, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Christofer", "Delivery", R.drawable.deliveryman,R.drawable.deliverymoto, 3, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Juan Carlos", "Delivery", R.drawable.deliveryman2,R.drawable.deliverymoto, 3, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Roberto", "Delivery", R.drawable.deliveryman,R.drawable.deliverymoto, 3, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Fernando", "Delivery", R.drawable.deliveryman2,R.drawable.deliverymoto, 3, new LatLng(34, 35)));

                    updateRecyclerView.callback(position, items);

                }
                else if (position==3){
                    ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
                    items.add(new DynamicRVModel("Juan Andres", "Abogado", R.drawable.deliveryman,R.drawable.profesionales, 4, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Bruno", "Contador", R.drawable.deliveryman2,R.drawable.profesionales, 4, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Lourdes", "Escribana", R.drawable.deliveryman,R.drawable.profesionales, 4, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Adriana", "Arquitecta", R.drawable.deliveryman2,R.drawable.profesionales, 4, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Diego", "Gestor", R.drawable.deliveryman,R.drawable.profesionales, 4, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Fernanda", "Ing. Agronoma", R.drawable.deliveryman2,R.drawable.profesionales, 4, new LatLng(34, 35)));

                    updateRecyclerView.callback(position, items);
                }
                else if (position==4){
                    ArrayList<DynamicRVModel> items = new ArrayList<DynamicRVModel>();
                    items.add(new DynamicRVModel("Sandra", "Acompañante Especializada", R.drawable.deliveryman, R.drawable.otrosbg, 5, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Micaela", "Cocinera", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Jessica", "Baby Sister", R.drawable.deliveryman,R.drawable.otrosbg, 5, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Martin", "Albañil", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Fernando", "Electricista", R.drawable.deliveryman,R.drawable.otrosbg, 5, new LatLng(34, 35)));
                    items.add(new DynamicRVModel("Manuel", "Jardinero", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(34, 35)));

                    updateRecyclerView.callback(position, items);
                }
            }
        });

        if (select){
            if (position==0)
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
            select=false;
        }
        else {
            if(row_index == position){
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
            }
            else{
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;

        public StaticRVViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }




}

