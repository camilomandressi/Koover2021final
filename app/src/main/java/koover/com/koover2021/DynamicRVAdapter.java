package koover.com.koover2021;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicRVAdapter extends RecyclerView.Adapter<DynamicRVAdapter.DynamicRvHolder>{

    public ArrayList<DynamicRVModel> dynamicRVModels;
    private OnItemClickListner mListner;
    private List<DynamicRVModel> originalItems;

    public interface OnItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner mListner){
        this.mListner = mListner;
    }

    public DynamicRVAdapter(ArrayList<DynamicRVModel> dynamicRVModels){
        this.dynamicRVModels = dynamicRVModels;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(dynamicRVModels);
    }

    public class DynamicRvHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name;
        public TextView details;
        ConstraintLayout constraintLayout;

        public DynamicRvHolder(@NonNull View itemView, final OnItemClickListner mListner) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.perfil);
            name = (TextView) itemView.findViewById(R.id.nombre);
            details = (TextView) itemView.findViewById(R.id.details);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout2);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (mListner!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public DynamicRVAdapter.DynamicRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_rv_item_layout,parent,false);
        DynamicRvHolder dynamicRvHolder = new DynamicRvHolder(view, mListner);
        return dynamicRvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicRVAdapter.DynamicRvHolder holder, int position) {

        DynamicRVModel currentItem = dynamicRVModels.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.name.setText(currentItem.getName());
        holder.details.setText(currentItem.getDetails());

    }

    @Override
    public int getItemCount() {
        return dynamicRVModels.size();
    }


    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            dynamicRVModels.clear();
            dynamicRVModels.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dynamicRVModels.clear();
                List<DynamicRVModel> collect = originalItems.stream()
                        .filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                dynamicRVModels.addAll(collect);
            }
            else {
                dynamicRVModels.clear();
                for (DynamicRVModel i : originalItems) {
                    if (i.getName().toLowerCase().contains(strSearch)) {
                        dynamicRVModels.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


};