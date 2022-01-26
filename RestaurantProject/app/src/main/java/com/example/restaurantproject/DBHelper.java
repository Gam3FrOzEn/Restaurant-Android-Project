package com.example.restaurantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table orders(order_id INTEGER primary key autoincrement, username TEXT ,food_name TEXT, food_quantity INTEGER, " +
                "cost INTEGER, price INTEGER, foreign key (username) references users(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public boolean createOrder(Order order) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", order.getUsername());
        contentValues.put("food_name", order.getFoodName());
        contentValues.put("food_quantity", order.getFoodQuantity());
        contentValues.put("cost", order.getCost());
        contentValues.put("price", order.getPrice());
        long result = MyDB.insert("orders", null, contentValues);
        if(result==-1) return false;
        else return true;
    }

    public ArrayList<Order> getOrders (String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from orders where username = ?", new String[] {username});
        ArrayList<Order> orders = new ArrayList<>();
        Order order;
        if(cursor.getCount()>0) {
            for(int i=0; i<cursor.getCount(); i++) {
                cursor.moveToNext();
                order = new Order();
                order.setOrderId(cursor.getInt(0));
                order.setUsername(cursor.getString(1));
                order.setFoodName(cursor.getString(2));
                order.setFoodQuantity(cursor.getInt(3));
                order.setCost(cursor.getDouble(4));
                order.setPrice(cursor.getDouble(5));
                orders.add(order);
            }
        }
        cursor.close();
        MyDB.close();
        return orders;
    }

    public Order getOrder(int orderId) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from orders where order_id = ?" , new String[]{String.valueOf(orderId)});
        Order order = new Order();
        if(cursor.getCount()>0) {
            cursor.moveToNext();
            order.setOrderId(cursor.getInt(0));
            order.setUsername(cursor.getString(1));
            order.setFoodName(cursor.getString(2));
            order.setFoodQuantity(cursor.getInt(3));
            order.setCost(cursor.getDouble(4));
            order.setPrice(cursor.getDouble(5));
        }
        cursor.close();
        MyDB.close();
        return order;
    }

    public Boolean updateOrder(Order order) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_id", order.getOrderId());
        contentValues.put("username", order.getUsername());
        contentValues.put("food_name", order.getFoodName());
        contentValues.put("food_quantity", order.getFoodQuantity());
        contentValues.put("cost", order.getCost());
        contentValues.put("price", order.getPrice());
        long result = MyDB.update("orders", contentValues, "order_id = ?", new String[]{String.valueOf(order.getOrderId())});
        if(result==-1) return false;
        else return true;

    }

    public void deleteOrder(Order order) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Delete from orders where order_id = ?"                 , new String[]{String.valueOf(order.getOrderId())});
        cursor.moveToFirst();
        cursor.close();
        MyDB.close();
    }
}
