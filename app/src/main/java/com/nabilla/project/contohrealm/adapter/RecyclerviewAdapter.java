package com.nabilla.project.contohrealm.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nabilla.project.contohrealm.R;
import com.nabilla.project.contohrealm.TambahActivity;
import com.nabilla.project.contohrealm.model.ModelBarang;
import com.nabilla.project.contohrealm.util.OnClickedItem;

import io.realm.RealmResults;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{

    private Context context;
    private RealmResults<ModelBarang> listBarang;
    private int selectedPos;

    public RecyclerviewAdapter(Context context, RealmResults<ModelBarang> listBarang) {
        this.context = context;
        this.listBarang = listBarang;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stok_barang, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setOnClickedItem(new OnClickedItem() {
            @Override
            public void itemClickListener(int selectedPos) {
                selectedPos = selectedPos;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_nama_barang.setText(listBarang.get(position).getNamaBarang()+"("+listBarang.get(position).getJumlahBarang()+")");
        holder.tv_jumlah_barang.setText("Harga Barang : "+listBarang.get(position).getHargaBarang());

    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_nama_barang, tv_jumlah_barang;
        OnClickedItem onClickedItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_nama_barang = (TextView) itemView.findViewById(R.id.tv_nama_barang);
            tv_jumlah_barang = (TextView) itemView.findViewById(R.id.tv_jumlah_barang);

            itemView.setOnClickListener(this);
        }

        private void setOnClickedItem(OnClickedItem onClickedItem){
            this.onClickedItem = onClickedItem;
        }

        @Override
        public void onClick(View view) {
            this.onClickedItem.itemClickListener(getLayoutPosition());
            ModelBarang modelBarang = listBarang.get(selectedPos);
            int id = modelBarang.getId();

            Intent intent = new Intent(context, TambahActivity.class);
            intent.putExtra("EDIT", 1);
            intent.putExtra("ID", id);

            context.startActivity(intent);
        }
    }
}
