package com.example.generadordereportes.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.generadordereportes.models.Item;
import com.example.generadordereportes.models.Room;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reportes.db";
    private static final int DATABASE_VERSION = 7;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE rooms (roomCode TEXT PRIMARY KEY, roomName TEXT, roomDescription TEXT)");
        db.execSQL("CREATE TABLE items (itemCode TEXT, itemName TEXT, itemRoomCode TEXT, FOREIGN KEY(itemRoomCode) REFERENCES rooms(roomCode))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rooms");
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    public boolean insertRoom(String roomCode, String roomName, String roomDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO rooms VALUES ('" + roomCode + "', '" + roomName + "', '" + roomDescription + "')");
        return true;
    }

    public boolean insertItem(String itemCode, String itemName, String itemRoomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO items VALUES ('" + itemCode + "', '" + itemName + "', '" + itemRoomCode + "')");
        return true;
    }

    public boolean deleteRoom(String roomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM rooms WHERE roomCode = '" + roomCode + "'");
        return true;
    }

    public boolean deleteItem(String itemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM items WHERE itemCode = '" + itemCode + "'");
        return true;
    }

    public boolean updateRoom(String roomCode, String roomName, String roomDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE rooms SET roomName = '" + roomName + "', roomDescription = '" + roomDescription + "' WHERE roomCode = '" + roomCode + "'");
        return true;
    }

    public boolean updateItem(String itemCode, String itemName, String itemRoomCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE items SET itemName = '" + itemName + "', itemRoomCode = '" + itemRoomCode + "' WHERE itemCode = '" + itemCode + "'");
        return true;
    }

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms", null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                rooms.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rooms;
    }

    public List<Item> getItems(String roomCode) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE itemRoomCode = '" + roomCode + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public Room getRoomByCode(String roomCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rooms WHERE roomCode = '" + roomCode + "'", null);
        if (cursor.moveToFirst()) {
            Room room = new Room(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            return room;
        }
        cursor.close();
        return null;
    }

    public List<Item> getItemsByCode(String roomCode) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE itemRoomCode = '" + roomCode + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public String getRoomCodeByItemCode(String itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE itemCode = '" + itemCode + "'", null);
        if (cursor.moveToFirst()) {
            String roomCode = cursor.getString(2);
            cursor.close();
            return roomCode;
        }
        cursor.close();
        return null;
    }

    public Item getItemByCode(String itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items WHERE itemCode = '" + itemCode + "'", null);
        if (cursor.moveToFirst()) {
            Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            return item;
        }
        cursor.close();
        return null;
    }
}
