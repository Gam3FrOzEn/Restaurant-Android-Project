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

public class EditOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edit_quantityET;
    EditText edit_costET, edit_priceET;
    Button edit_button;
    DBHelper dbHelper;
    Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        Spinner spinner = findViewById(R.id.edit_food_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.foods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        edit_quantityET = findViewById(R.id.edit_quantityET);
        edit_costET = findViewById(R.id.edit_costET);
        edit_priceET = findViewById(R.id.edit_priceET);
        edit_button = findViewById(R.id.edit_button3);
        dbHelper = new DBHelper(EditOrderActivity.this);

        int id = getIntent().getExtras().getInt("id");
        String username = getIntent().getExtras().getString("username");
        String food = getIntent().getExtras().getString("food");
        int quantity = getIntent().getExtras().getInt("quantity");
        Double cost = getIntent().getExtras().getDouble("cost");

        dbHelper = new DBHelper(EditOrderActivity.this);

        switch (food) {
            case "Паста":
                spinner.setSelection(0);
                break;
            case "Пица":
                spinner.setSelection(1);
                break;
            case "Пържола":
                spinner.setSelection(2);
                break;
            case "Кюфте":
                spinner.setSelection(3);
                break;
            case "Тирамису":
                spinner.setSelection(4);
                break;
            case "Торта":
                spinner.setSelection(5);
                break;
            case "Панакота":
                spinner.setSelection(6);
                break;
        }

        edit_quantityET.setText(String.valueOf(quantity));

        edit_quantityET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit_quantityET.getText().toString().isEmpty())
                    return;
                int quantity = Integer.parseInt(edit_quantityET.getText().toString());
                double cost = Double.parseDouble(edit_costET.getText().toString());
                double finalPrice = quantity * cost;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                edit_priceET.setText(String.valueOf(decimalFormat.format(finalPrice)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_quantityET.getText().toString().isEmpty() || edit_priceET.getText().toString().isEmpty()) {
                    Toast.makeText(EditOrderActivity.this,"Моля попълнете полетата!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String foodName = spinner.getSelectedItem().toString();
                int quantity = Integer.parseInt(edit_quantityET.getText().toString());
                double cost = Double.parseDouble(edit_costET.getText().toString());
                double price = Double.parseDouble(edit_priceET.getText().toString());
                order = new Order(id, username, foodName, quantity, cost, price);
                dbHelper.updateOrder(order);
                Intent intent = new Intent(EditOrderActivity.this, ListActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();

        switch (text) {
            case "Паста":
                edit_costET.setText("4.10");
                break;
            case "Пица":
                edit_costET.setText("4.40");
                break;
            case "Пържола":
                edit_costET.setText("3.20");
                break;
            case "Кюфте":
                edit_costET.setText("1.20");
                break;
            case "Тирамису":
                edit_costET.setText("3.50");
                break;
            case "Торта":
                edit_costET.setText("12.70");
                break;
            case "Панакота":
                edit_costET.setText("8.60");
                break;
        }

        if (edit_quantityET.getText().toString().isEmpty())
            return;
        int quantity = Integer.parseInt(edit_quantityET.getText().toString());
        double cost = Double.parseDouble(edit_costET.getText().toString());
        double finalPrice = quantity * cost;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        edit_priceET.setText(String.valueOf(decimalFormat.format(finalPrice)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}