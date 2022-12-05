package com.budgetapp.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.model.Category;
import com.budgetapp.app.model.MonthlyBudget;
import com.budgetapp.app.model.Transaction;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class firstpage extends Activity implements RecyclerViewAdapter.OnItemClickListener {


    private DatePickerDialog datePickerDialog;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    List<Transaction> tempList=new ArrayList<>();
    Map<String,String> catAmountMap=new HashMap<>();
    List<Category> catList=new ArrayList<>();
    List<Transaction> transactionList =new ArrayList<>();

    TextView totalSpending;
    TextView remainingBalance;
    Button totalChart;
    Button savingChart;
    TextView addCategory;
    String selectedMonthYear="Dec 2022";
    EditText totalBudgetEditText;
    Button saveBudgetButton;
    Spinner spinner;
    DatabaseHandler db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);



totalSpending=findViewById(R.id.totalspendingTextView);
remainingBalance=findViewById(R.id.remainingbalance);

        db = new DatabaseHandler(firstpage.this);
        catList=db.getAllCategories();
        transactionList = db.getAllTransactionsForMonthYear(selectedMonthYear);
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
        totalBudgetEditText = (EditText) findViewById(R.id.totalmonthlybudget);

        spinner = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        selectedMonthYear= (String) adapterView.getItemAtPosition(i);
                        updateSpinnerAndViews(selectedMonthYear);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null && bundle.containsKey("monthYear")) {
            String selectedMonthYear=bundle.getString("monthYear", "Default");
            spinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.months_array)).indexOf(selectedMonthYear));
            updateSpinnerAndViews(selectedMonthYear);
        }


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




        MonthlyBudget mb=db.getMonthlybudget(selectedMonthYear);
        if (mb==null) {
            totalBudgetEditText.setText("$ 0");
        } else {
            totalBudgetEditText.setText(mb.getMonthlybudget());
        }

        saveBudgetButton=(Button) findViewById(R.id.savebudget);
        saveBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthlyBudget mb=new MonthlyBudget();
                mb.setMonthYear(selectedMonthYear);
                mb.setMonthlybudget(totalBudgetEditText.getText().toString());
                db.addMonthlybudget(mb);
                totalBudgetEditText.clearFocus();
                ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });


        String[] stats=getStats(transactionList,mb);
        totalSpending.setText(stats[1]);
        remainingBalance.setText(stats[0]);


    }


    public String[] getStats(List<Transaction> transactionList,MonthlyBudget mb) {
        double spending=0;
        for (Transaction tr: transactionList) {
            spending+=Double.valueOf(tr.getAmount());
        }
        double remaining=mb==null ? 0 : Double.valueOf(mb.getMonthlybudget())-spending;

        return new String[] {String.valueOf(remaining),String.valueOf(spending)};

    }

    public void updateSpinnerAndViews(String monthYear) {

        transactionList.clear();
        transactionList=db.getAllTransactionsForMonthYear(selectedMonthYear);
        catAmountMap.clear();

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
        recyclerViewAdapter.notifyDataSetChanged();

        MonthlyBudget mb=db.getMonthlybudget(selectedMonthYear);
        totalBudgetEditText.setText(mb == null ? "0" : mb.getMonthlybudget());

        String[] stats=getStats(transactionList,mb);
        totalSpending.setText(stats[1]);
        remainingBalance.setText(stats[0]);

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
        bundle.putString("categoryName", catList.get(adapterPosition).getName());
        bundle.putString("monthYear",selectedMonthYear);

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
