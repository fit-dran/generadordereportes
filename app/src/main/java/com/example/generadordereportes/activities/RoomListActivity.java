package com.example.generadordereportes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generadordereportes.R;
import com.example.generadordereportes.adapters.RoomListAdapter;
import com.example.generadordereportes.utilities.Database;

public class RoomListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button addRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Database db = new Database(this);
        recyclerView = findViewById(R.id.recyclerView);
        addRoomButton = findViewById(R.id.addRoomButton);
        RoomListAdapter adapter = new RoomListAdapter(this, db.getRooms());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addRoomButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRoomActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(this);
        RoomListAdapter adapter = new RoomListAdapter(this, db.getRooms());
        recyclerView.setAdapter(adapter);
    }
}