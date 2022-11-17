package com.example.generadordereportes.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.generadordereportes.models.Room;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reportes.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE rooms (roomCode TEXT PRIMARY KEY, roomName TEXT, roomDescription TEXT)");
        db.execSQL("CREATE TABLE items (itemCode TEXT PRIMARY KEY, itemName TEXT, itemRoomCode TEXT, FOREIGN KEY(itemRoomCode) REFERENCES rooms(roomCode))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    public void insertRoom(String roomCode, String roomName, String roomDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO rooms VALUES ('" + roomCode + "', '" + roomName + "', '" + roomDescription + "')");
    }

    public void insertItem(String itemCode, String itemName, String itemRoomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO items VALUES ('" + itemCode + "', '" + itemName + "', '" + itemRoomCode + "')");
    }

    public void deleteRoom(String roomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM rooms WHERE roomCode = '" + roomCode + "'");
    }

    public void deleteItem(String itemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM items WHERE itemCode = '" + itemCode + "'");
    }

    public void updateRoom(String roomCode, String roomName, String roomDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE rooms SET roomName = '" + roomName + "', roomDescription = '" + roomDescription + "' WHERE roomCode = '" + roomCode + "'");
    }

    public void updateItem(String itemCode, String itemName, String itemRoomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE items SET itemName = '" + itemName + "', itemRoomCode = '" + itemRoomCode + "' WHERE itemCode = '" + itemCode + "'");
    }

    public ArrayList<Room> getRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Room> rooms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms", null);
        if (cursor.moveToFirst()) {
            do {
                rooms.add(new Room(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return rooms;
    }

    public ArrayList<String> getItems(String roomCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE itemRoomCode = '" + roomCode + "'", null);
        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return items;
    }

}
