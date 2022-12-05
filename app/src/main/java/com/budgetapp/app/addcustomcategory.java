package com.budgetapp.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.model.Category;
import com.budgetapp.app.model.MonthlyBudget;
import com.budgetapp.app.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class addcustomcategory extends Activity {

    private ListView listView;
    private ArrayList<String> categoriesArrayList;
    private ArrayAdapter<String> arrayAdapter;

    Button addcustomcat;
    EditText mEdit;
    Button gotn;

    Category customcat = new Category();
    Transaction transCustomcat = new Transaction();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomcategory);

        gotn =(Button) findViewById(R.id.gooobtn);
        gotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addcustomcategory.this, firstpage.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listviewone);
        categoriesArrayList = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler((addcustomcategory.this));

        List<Category> categoryList = db.getAllCategories();
        List<Transaction> transactionList = db.getAllTransactions();

        for (Transaction transaction: transactionList){
            //Log.d("Main - CustomCat", "trans category: " + transaction.getText());
        }


        for (Category category: categoryList){
            //Log.d("Main - CustomCat", "onCreate: " + category.getName());
            categoriesArrayList.add(category.getName());

            //create array adaptor

            arrayAdapter  = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,//xml
                    categoriesArrayList
            ){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    if (position %2 == 1 ){
                        view.setBackgroundColor(ResourcesCompat.getColor(
                                getResources(), R.color.rectangle_14_color, null
                        ));

                    } else {

                        view.setBackgroundColor(getResources().getColor(
                                android.R.color.white
                        ));

                    }

                    return view;
                }
            };

            //add to our listview

            listView.setAdapter(arrayAdapter);

        }

        addcustomcat = (Button) findViewById(R.id.addcategory);
        mEdit   = (EditText) findViewById(R.id.editTextaddbudget);

        addcustomcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Main - EditText", mEdit.getText().toString());

                if (mEdit.getText().toString() != "") {

                    List<Category> allCats=db.getAllCategories();
                    customcat.setName(mEdit.getText().toString());
                    if (!allCats.contains(customcat)) {
                        db.addCategory(customcat);
                    }

                    Intent intent = new Intent(addcustomcategory.this, addcustomcategory.class);
                    startActivity(intent);
                }



            }
        });


    }
}
