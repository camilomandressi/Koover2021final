package koover.com.koover2021;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KooDetailAdapter extends RecyclerView.Adapter<KooDetailAdapter.KooRVViewHolder> {

    public ArrayList<KooDetailModel> kooDetailModels;

    public KooDetailAdapter(ArrayList<KooDetailModel> kooDetailModels){
        this.kooDetailModels = kooDetailModels;
    }


    public class KooRVViewHolder extends RecyclerView.ViewHolder {

        public TextView comentarioUsuario;
        public TextView detalleComentario;
        ConstraintLayout constraintLayout;

        public KooRVViewHolder(@NonNull View itemView) {
            super(itemView);
            comentarioUsuario = (TextView) itemView.findViewById(R.id.cometarioUsuario);
            detalleComentario = (TextView) itemView.findViewById(R.id.detalleComentario);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout4);
        }
    }

    @NonNull
    @Override
    public KooDetailAdapter.KooRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.koo_comentario_item, parent, false);
        KooRVViewHolder kooRVViewHolder = new KooRVViewHolder(view);
        return kooRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KooDetailAdapter.KooRVViewHolder holder, int position) {
        KooDetailModel actualItem = kooDetailModels.get(position);
        holder.comentarioUsuario.setText(actualItem.getComentarioUsuario());
        holder.detalleComentario.setText(actualItem.getDetalleComentario());

    }

    @Override
    public int getItemCount() {
        return kooDetailModels.size();
    }


}
