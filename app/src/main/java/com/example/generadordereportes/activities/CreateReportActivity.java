package com.example.generadordereportes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.generadordereportes.BuildConfig;
import com.example.generadordereportes.R;
import com.example.generadordereportes.models.Item;
import com.example.generadordereportes.models.Room;
import com.example.generadordereportes.utilities.Database;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class CreateReportActivity extends AppCompatActivity {
    TextView tvRoomName;
    EditText etEmail;
    Button btnCreateReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        Database db = new Database(this);
        Intent intent = getIntent();
        String roomCode = intent.getStringExtra("roomCode");
        Room room = db.getRoomByCode(roomCode);
        List<Item> itemList = db.getItemsByCode(roomCode);
        tvRoomName = findViewById(R.id.tvRoomName);
        etEmail = findViewById(R.id.etEmail);
        btnCreateReport = findViewById(R.id.btnCreateReport);
        tvRoomName.setText(room.getRoomCode());
        btnCreateReport.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(this, "El campo de correo electrónico no puede estar vacío", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    createReport(room, itemList, email);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createReport(Room room, List<Item> itemList,String email) throws FileNotFoundException {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, room.getRoomCode() + ".pdf");
        OutputStream os = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Reporte de la dependencia: " + room.getRoomCode()));
        document.add(new Paragraph("Nombre de la dependencia: " + room.getRoomName()));
        document.add(new Paragraph("Descripción de la dependencia: " + room.getRoomDescription()));
        document.add(new Paragraph("Número de Items: " + itemList.size()));
        document.add(new Paragraph("Items: "));
        for (Item item : itemList) {
            document.add(new Paragraph("Nombre del Item: " + item.getItemName()));
            document.add(new Paragraph("Codigo del Item: " + item.getItemCode()));
            document.add(new Paragraph(" "));
        }
        document.close();
        Uri fromFile = FileProvider.getUriForFile(this, "com.example.generadordereportes.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de la dependencia: " + room.getRoomCode());
        intent.putExtra(Intent.EXTRA_TEXT, "Reporte de la dependencia: " + room.getRoomCode());
        intent.putExtra(Intent.EXTRA_STREAM, fromFile);
        startActivity(Intent.createChooser(intent, "Enviar correo"));



    }

}