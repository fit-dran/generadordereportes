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

public class AddItemActivity extends AppCompatActivity {

    static final String ADD_ITEM_ACTIVITY = "AddItemActivity";
    EditText etItemName;
    EditText etItemCode;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Intent originalIntent = result.getOriginalIntent();
            if (originalIntent == null) {
                Log.d(ADD_ITEM_ACTIVITY, "Cancelled scan");
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d(ADD_ITEM_ACTIVITY, "Cancelled scan due to missing camera permission");
            }
        } else {
            etItemCode.setText(result.getContents());
            Log.d(ADD_ITEM_ACTIVITY, "Scanned");
        }
    });
    Button btnAddItem;
    FloatingActionButton fabItemScanBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Database db = new Database(this);
        etItemName = findViewById(R.id.etItemName);
        etItemCode = findViewById(R.id.etItemCode);
        btnAddItem = findViewById(R.id.btnAddItem);
        Intent intent = getIntent();
        String roomCode = intent.getStringExtra("roomCode");
        btnAddItem.setOnClickListener(v -> {
            String itemName = etItemName.getText().toString();
            String itemCode = etItemCode.getText().toString();
            if (itemName.isEmpty() || itemCode.isEmpty()) {
                Toast.makeText(AddItemActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                db.insertItem(itemCode, itemName, roomCode);
                Toast.makeText(AddItemActivity.this, "Item agregado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        fabItemScanBarCode = findViewById(R.id.fabItemScanBarCode);
        fabItemScanBarCode.setOnClickListener(v -> scanBarcodeCustomLayout());
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