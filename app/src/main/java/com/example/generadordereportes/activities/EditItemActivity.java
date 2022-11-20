package com.example.generadordereportes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.generadordereportes.R;
import com.example.generadordereportes.models.Item;
import com.example.generadordereportes.utilities.Database;
import com.example.generadordereportes.utilities.Utilities;

public class EditItemActivity extends AppCompatActivity {
    EditText etItemName;
    EditText etItemCode;
    Button btnAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Database db = new Database(this);
        etItemName = findViewById(R.id.etItemName);
        etItemCode = findViewById(R.id.etItemCode);
        btnAddItem = findViewById(R.id.btnSaveItem);
        Utilities.disableEditText(etItemCode);
        String intem = getIntent().getStringExtra("itemCode");
        Item item = db.getItemByCode(intem);
        etItemName.setText(item.getItemName());
        etItemCode.setText(item.getItemCode());
        btnAddItem.setOnClickListener(v -> {
            String itemName = etItemName.getText().toString();
            String itemCode = etItemCode.getText().toString();
            String itemRoomCode = item.getItemRoomCode();
            if (itemName.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                Item updatedItem = new Item(itemCode, itemName, itemRoomCode);
                db.updateItem(updatedItem.getItemCode(), updatedItem.getItemName(), updatedItem.getItemRoomCode());
                Toast.makeText(this, "Se ha actualizado el item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ItemListActivity.class);
                intent.putExtra("roomCode", itemRoomCode);
                startActivity(intent);
            }
        });


    }
}