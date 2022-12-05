package com.budgetapp.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.model.Category;
import com.budgetapp.app.model.Transaction;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class firstpage extends Activity implements RecyclerViewAdapter.OnItemClickListener {

    Button total;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    List<Transaction> tempList=new ArrayList<>();
    Map<String,String> catAmountMap=new HashMap<>();
    List<Category> catList=new ArrayList<>();

    TextView mEdit;
    TextView totalBudget;
    Button totalChart;
    Button savingChart;
    TextView addCategory;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());


        totalChart =(Button) findViewById(R.id.total);
        savingChart =(Button) findViewById(R.id.saving);
        totalChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstpage.this, totalchart.class);
                startActivity(intent);

            }

        });

        addCategory= (TextView)findViewById(R.id.backtoaddcategory);
        addCategory.setOnClickListener(v -> {
            Intent intent = new Intent(firstpage.this, addcustomcategory.class);
            startActivity(intent);
        });

        savingChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstpage.this, savingchart.class);
                startActivity(intent);
            }
        });

        DatabaseHandler db = new DatabaseHandler(firstpage.this);
        List<Transaction> transactionList = db.getAllTransactions();
        catList=db.getAllCategories();

        for (Transaction transaction: transactionList){
            String catAmount=catAmountMap.get(transaction.getCategory());
            double catVal=0;
            if (catAmount!=null) {
                catVal=Double.parseDouble(catAmount);
                catVal+=Double.parseDouble(transaction.getAmount());
            } else {
                catVal=Double.parseDouble(transaction.getAmount());
            }
            catAmountMap.put(transaction.getCategory(),String.valueOf(catVal));
        }

        recyclerView=findViewById(R.id.categorRecyclerView);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter=new RecyclerViewAdapter(catAmountMap,catList,firstpage.this,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        totalBudget = (TextView) findViewById(R.id.totalmonthlybudget);
        totalBudget.setText("$ " + db.getMonthlybudget("Dec 22").getMonthlybudget() + ".00");



    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(1, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, 1);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    @Override
    public void onAddManualClicked(int adapterPosition) {
        Toast.makeText(getApplicationContext(),"Add Manual is Clicked " + adapterPosition,Toast.LENGTH_SHORT);
        Log.d("Main - CustomCat", "clicked! " + adapterPosition + "-" + Toast.LENGTH_SHORT );



        Log.d("Main - TextView", catList.get(adapterPosition).getName());

        Intent intent = new Intent(firstpage.this, addtransamountandnote.class);

        //Create the bundle
        Bundle bundle = new Bundle();
        bundle.putString("first", catList.get(adapterPosition).getName());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onscanReciptClicked(int adapterPosition) {
        Toast.makeText(getApplicationContext(),"Scan Recipt is Clicked " + adapterPosition,Toast.LENGTH_SHORT);

    }

    @Override
    public void onCategoryRemoveClicked(int adapterPosition) {
        Toast.makeText(getApplicationContext(),"On Category Remove is Clicked " + adapterPosition,Toast.LENGTH_SHORT);

    }



}
