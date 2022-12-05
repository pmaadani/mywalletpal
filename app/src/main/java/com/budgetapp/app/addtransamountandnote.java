package com.budgetapp.app;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.model.Transaction;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class addtransamountandnote extends AppCompatActivity {

    Button   mButton;
    EditText mEditAmount;
    EditText mNote;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtransamountandnote);

        DatabaseHandler db = new DatabaseHandler((addtransamountandnote.this));


        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("categoryName", "Default");
        String selectedMonthYear=bundle.getString("monthYear", "Default");

        //Log.d("Main", "title cat: " + title);




        mButton = (Button) findViewById(R.id.addamountnote);
        TextView catTextView=findViewById(R.id.categoryNameTextView);
        catTextView.setText(title);
        mEditAmount = (EditText) findViewById(R.id.transamount);
        mNote = (EditText) findViewById(R.id.trannote);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //Log.d("Main - addAmountTrans", mEditAmount.getText().toString());
                        //Log.d("Main - addamountnote", mNote.getText().toString());
                        //Log.d("Main - title", title);


                        Transaction newTransaction = new Transaction();

                        newTransaction.setAmount(mEditAmount.getText().toString());
                        newTransaction.setText(mNote.getText().toString());
                        newTransaction.setCategory(title);
                        newTransaction.setTransaction_date(selectedMonthYear);
                        db.addTransaction(newTransaction);

                        Intent intent = new Intent(addtransamountandnote.this, firstpage.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
    }
}
