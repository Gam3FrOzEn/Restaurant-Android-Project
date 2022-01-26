package com.example.restaurantproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText quantityET;
    EditText costET, priceET;
    Button button;
    Button button2;
    DBHelper dbHelper;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Spinner spinner = findViewById(R.id.food_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.foods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        quantityET = findViewById(R.id.quantityET);
        costET = findViewById(R.id.costET);
        priceET = findViewById(R.id.priceET);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        dbHelper = new DBHelper(HomeActivity.this);

        String username = getIntent().getExtras().getString("username");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantityET.getText().toString().isEmpty() || priceET.getText().toString().isEmpty()) {
                    Toast.makeText(HomeActivity.this,"Моля изберете храна и въведете количество!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String foodName = spinner.getSelectedItem().toString();
                int quantity = Integer.parseInt(quantityET.getText().toString());
                double cost = Double.parseDouble(costET.getText().toString());
                double price = Double.parseDouble(priceET.getText().toString());
                order = new Order(username, foodName, quantity, cost, price);
                dbHelper.createOrder(order);
                Toast.makeText(HomeActivity.this,"Успешно създадена поръчка!", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ListActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        quantityET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (quantityET.getText().toString().isEmpty())
                    return;
                int quantity = Integer.parseInt(quantityET.getText().toString());
                double cost = Double.parseDouble(costET.getText().toString());
                double finalPrice = quantity * cost;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                priceET.setText(String.valueOf(decimalFormat.format(finalPrice)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();

        switch (text) {
            case "Паста":
                costET.setText("4.10");
                break;
            case "Пица":
                costET.setText("4.40");
                break;
            case "Пържола":
                costET.setText("3.20");
                break;
            case "Кюфте":
                costET.setText("1.20");
                break;
            case "Тирамису":
                costET.setText("3.50");
                break;
            case "Торта":
                costET.setText("12.70");
                break;
            case "Панакота":
                costET.setText("8.60");
                break;
        }

        if (quantityET.getText().toString().isEmpty())
            return;
        int quantity = Integer.parseInt(quantityET.getText().toString());
        double cost = Double.parseDouble(costET.getText().toString());
        double finalPrice = quantity * cost;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        priceET.setText(String.valueOf(decimalFormat.format(finalPrice)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}