package com.example.generadordereportes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generadordereportes.R;
import com.example.generadordereportes.adapters.ItemListAdapter;
import com.example.generadordereportes.models.Item;
import com.example.generadordereportes.utilities.Database;

import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    TextView tvRoomName;
    RecyclerView recyclerView;
    Button btnAddItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Database db = new Database(this);
        tvRoomName = findViewById(R.id.tvRoomName);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddItem = findViewById(R.id.btnAddItem);
        Intent intent = getIntent();
        String roomCode = intent.getStringExtra("roomCode");
        tvRoomName.setText(db.getRoomByCode(roomCode).getRoomCode());
        List<Item> itemList = db.getItemsByCode(roomCode);
        ItemListAdapter adapter = new ItemListAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddItem.setOnClickListener(v -> {
            Intent addItemIntent = new Intent(this, AddItemActivity.class);
            addItemIntent.putExtra("roomCode", roomCode);
            startActivity(addItemIntent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(this);
        Intent intent = getIntent();
        String roomCode = intent.getStringExtra("roomCode");
        recyclerView.setAdapter(new ItemListAdapter(this, db.getItems(roomCode)));
    }
}