package com.example.generadordereportes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generadordereportes.R;
import com.example.generadordereportes.activities.EditRoomActivity;
import com.example.generadordereportes.activities.ItemListActivity;
import com.example.generadordereportes.activities.RoomListActivity;
import com.example.generadordereportes.models.Room;
import com.example.generadordereportes.utilities.Database;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    Context context;
    List<Room> roomList;

    public RoomListAdapter(Context ct, List<Room> r) {
        context = ct;
        roomList = r;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.room_list_row, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.tvRoomName.setText(roomList.get(position).getRoomName());
        holder.tvRoomDescription.setText(roomList.get(position).getRoomDescription());
        holder.tvRoomCode.setText(roomList.get(position).getRoomCode());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        Database db = new Database(itemView.getContext());
        CardView cardView;
        Button btnDeleteRoom;
        Button btnEditRoom;
        TextView tvRoomName;
        TextView tvRoomDescription;
        TextView tvRoomCode;
        public RoomViewHolder(View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomDescription);
            tvRoomCode = itemView.findViewById(R.id.tvRoomCode);
            btnDeleteRoom = itemView.findViewById(R.id.btnDeleteRoom);
            btnEditRoom = itemView.findViewById(R.id.btnEditRoom);
            cardView = itemView.findViewById(R.id.cardView);
            btnEditRoom.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), EditRoomActivity.class);
                intent.putExtra("roomCode", tvRoomCode.getText().toString());
                itemView.getContext().startActivity(intent);
            });
            btnDeleteRoom.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle("Eliminar dependencia");
                builder.setMessage("¿Está seguro de que desea eliminar esta dependencia?");
                builder.setPositiveButton("Sí", (dialog, which) -> {
                    if (db.deleteRoom(tvRoomCode.getText().toString())) {
                        Toast.makeText(itemView.getContext(), "Dependencia eliminada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(itemView.getContext(), RoomListActivity.class);
                        itemView.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(itemView.getContext(), "Error al eliminar dependencia", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
            cardView.setOnClickListener(v -> {
            });
        }
    }

}



