package com.example.generadordereportes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.generadordereportes.R;
import com.example.generadordereportes.utilities.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


    }
}