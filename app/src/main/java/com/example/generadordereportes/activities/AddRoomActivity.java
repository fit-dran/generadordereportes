package com.example.generadordereportes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.generadordereportes.R;
import com.example.generadordereportes.utilities.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AddRoomActivity extends AppCompatActivity {

    static final String ACTIVITY = "AddRoomActivity";
    EditText etRoomName;
    EditText etRoomBarCode;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Intent originalIntent = result.getOriginalIntent();
            if (originalIntent == null) {
                Log.d(ACTIVITY, "Cancelled scan");
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d(ACTIVITY, "Cancelled scan due to missing camera permission");
            }
        } else {
            etRoomBarCode.setText(result.getContents());
            Log.d(ACTIVITY, "Scanned");
        }
    });
    EditText etRoomDescription;
    Button btnAddRoom;
    FloatingActionButton fabRoomScanBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Database db = new Database(this);
        etRoomName = findViewById(R.id.etRoomName);
        etRoomBarCode = findViewById(R.id.etRoomBarCode);
        etRoomDescription = findViewById(R.id.etRoomDescription);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        btnAddRoom.setOnClickListener(v -> {
            String roomName = etRoomName.getText().toString();
            String roomCode = etRoomBarCode.getText().toString();
            String roomDescription = etRoomDescription.getText().toString();
            if (roomName.isEmpty() || roomCode.isEmpty() || roomDescription.isEmpty()) {
                Toast.makeText(AddRoomActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
            if (db.getRoomByCode(roomCode) != null) {
                Toast.makeText(AddRoomActivity.this, "Ya existe una habitación con ese código", Toast.LENGTH_SHORT).show();
            } else {
                if (db.insertRoom(roomCode, roomName, roomDescription )) {
                    Toast.makeText(AddRoomActivity.this, "Habitación agregada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddRoomActivity.this, "Error al agregar habitación", Toast.LENGTH_SHORT).show();
                }
            }
            //TODO: Mejorar if nesting
        });
        fabRoomScanBarCode = findViewById(R.id.fabRoomScanBarCode);
        fabRoomScanBarCode.setOnClickListener(v -> scanBarcodeCustomLayout());
    }

    public void scanBarcodeCustomLayout() {
        ScanOptions options = new ScanOptions();
        options.setCaptureActivity(AnyOrientationCaptureActivity.class);
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        options.setPrompt("Escanea el código de barras");
        options.setOrientationLocked(false);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
}