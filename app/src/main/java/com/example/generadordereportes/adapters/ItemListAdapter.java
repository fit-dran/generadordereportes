package com.example.generadordereportes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generadordereportes.R;
import com.example.generadordereportes.activities.EditItemActivity;
import com.example.generadordereportes.activities.ItemListActivity;
import com.example.generadordereportes.models.Item;
import com.example.generadordereportes.utilities.Database;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    Context context;
    List<Item> itemList;

    public ItemListAdapter(Context ct, List<Item> i) {
        context = ct;
        itemList = i;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvItemName.setText(itemList.get(position).getItemName());
        holder.tvItemCode.setText(itemList.get(position).getItemCode());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        Database db = new Database(itemView.getContext());
        CardView cardView;
        Button btnDeleteItem;
        Button btnEditItem;
        TextView tvItemName;
        TextView tvItemCode;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
            btnEditItem = itemView.findViewById(R.id.btnEditItem);
            cardView = itemView.findViewById(R.id.cardView);
            btnEditItem.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), EditItemActivity.class);
                intent.putExtra("itemCode", tvItemCode.getText().toString());
                itemView.getContext().startActivity(intent);
            });
            btnDeleteItem.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle("Eliminar item");
                builder.setMessage("¿Está seguro que desea eliminar el item?");
                builder.setPositiveButton("Si", (dialog, which) -> {
                    Intent intent = new Intent(itemView.getContext(), ItemListActivity.class);
                    intent.putExtra("roomCode", db.getRoomCodeByItemCode(tvItemCode.getText().toString()));
                    db.deleteItem(tvItemCode.getText().toString());
                    itemView.getContext().startActivity(intent);
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
        }
    }
}
