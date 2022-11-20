package com.example.generadordereportes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.generadordereportes.R;
import com.example.generadordereportes.models.Room;
import com.example.generadordereportes.utilities.Database;
import com.example.generadordereportes.utilities.Utilities;

public class EditRoomActivity extends AppCompatActivity {

    EditText etRoomName;
    EditText etRoomBarCode;
    EditText etRoomDescription;
    Button btnAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Database db = new Database(this);
        etRoomName = findViewById(R.id.etRoomName);
        etRoomBarCode = findViewById(R.id.etRoomBarCode);
        etRoomDescription = findViewById(R.id.etRoomDescription);
        Utilities.disableEditText(etRoomBarCode);
        btnAddRoom = findViewById(R.id.btnSaveRoom);
        String roomCode = getIntent().getStringExtra("roomCode");
        Room room = db.getRoomByCode(roomCode);
        etRoomName.setText(room.getRoomName());
        etRoomBarCode.setText(room.getRoomCode());
        etRoomDescription.setText(room.getRoomDescription());
        btnAddRoom.setOnClickListener(v -> {
            String roomName = etRoomName.getText().toString();
            String roomBarCode = etRoomBarCode.getText().toString();
            String roomDescription = etRoomDescription.getText().toString();
            if (roomName.isEmpty() || roomDescription.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                Room updatedRoom = new Room(roomBarCode, roomName, roomDescription);
                db.updateRoom(updatedRoom.getRoomCode(), updatedRoom.getRoomName(), updatedRoom.getRoomDescription());
                Toast.makeText(this, "Se ha actualizado la dependencia", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RoomListActivity.class);
                startActivity(intent);
            }
        });
    }
}