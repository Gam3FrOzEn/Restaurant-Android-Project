package com.example.restaurantproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    Adapter adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String username = getIntent().getExtras().getString("username");
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(ListActivity.this);

        ArrayList<Order> orders = dbHelper.getOrders(username);
        adapter = new Adapter(ListActivity.this,orders);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("МЕНЮ");
                builder.setItems(new CharSequence[]{"ИЗТРИЙ", "РЕДАКТИРАЙ"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                new AlertDialog.Builder(ListActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("ИЗТРИВАНЕ НА ПОРЪЧКА")
                                        .setMessage("Сигурни ли сте, че искате да изтриете тази поръчка?")
                                        .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Order clickedOrder = (Order) parent.getItemAtPosition(position);
                                                dbHelper.deleteOrder(clickedOrder);
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(ListActivity.this, "Изтрито!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("НЕ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(ListActivity.this, "Добре", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                                break;
                            case 1:
                                new AlertDialog.Builder(ListActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("РЕДАКТИРАНЕ НА ПОРЪЧКА")
                                        .setMessage("Сигурни ли сте, че искате да редактирате тази поръчка?")
                                        .setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Order clickedOrder = (Order) parent.getItemAtPosition(position);
                                                String food = clickedOrder.getFoodName();
                                                int quantity = clickedOrder.getFoodQuantity();
                                                Double cost = clickedOrder.getCost();
                                                Double price = clickedOrder.getPrice();
                                                Intent intent = new Intent(ListActivity.this, EditOrderActivity.class);
                                                intent.putExtra("id", clickedOrder.getOrderId());
                                                intent.putExtra("food",food);
                                                intent.putExtra("quantity",quantity);
                                                intent.putExtra("cost",cost);
                                                intent.putExtra("price",price);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                                finish();

                                            }
                                        })
                                        .setNegativeButton("НЕ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(ListActivity.this, "Добре", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                });
                builder.create().show();

            }
        });



    }
}