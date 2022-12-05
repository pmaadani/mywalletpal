package com.budgetapp.app;

    import android.annotation.SuppressLint;
    import android.app.Activity;
    import android.graphics.Color;
    import android.os.Bundle;

    import android.content.Intent;
    import android.util.Log;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.TextView;

    import androidx.annotation.Nullable;

    import com.budgetapp.app.data.DatabaseHandler;
    import com.budgetapp.app.model.Category;
    import com.budgetapp.app.model.MonthlyBudget;
    import com.budgetapp.app.model.Transaction;

    import java.util.ArrayList;
    import java.util.List;

public class categorizes extends Activity {

    Button home;
    Button ins;
    Button sportevents;
    Button videogames;
    Button utilities;
    Button transportation;
    Button food;
    Button md;
    Button   mButton;
    EditText mEdit;


    List<String> loadedCatsNames=new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler((categorizes.this));



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategories);





        List<Category> loadedCats=db.getAllCategories();
        for (Category cat : loadedCats) {
            loadedCatsNames.add(cat.getName().toUpperCase());
        }


        //transactiondb.deletetransaction(MonthlyBudget);

        List<Category> categoryList = db.getAllCategories();
        for (Category category: categoryList){
            //Log.d("Main", "category name: " + category.getName());

        }



        home =(Button) findViewById(R.id.house);
        ins =(Button) findViewById(R.id.insurance);
        sportevents =(Button) findViewById(R.id.sportevents);
        videogames =(Button) findViewById(R.id.videogames);
        utilities =(Button) findViewById(R.id.utilities);
        transportation =(Button) findViewById(R.id.transportation);
        food =(Button) findViewById(R.id.food);
        md =(Button) findViewById(R.id.md);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (home.getId() == R.id.house){
                    home.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Housing = new Category();
                    Housing.setName("Housing");
                    addCatToDatabase(Housing);
                }

            }
        });

        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ins.getId() == R.id.insurance){
                    ins.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Insurance = new Category();
                    Insurance.setName("Insurance");
                    addCatToDatabase(Insurance);
                }
            }
        });

        sportevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sportevents.getId() == R.id.sportevents){
                    sportevents.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Sportevents = new Category();
                    Sportevents.setName("Sport Events");
                    addCatToDatabase(Sportevents);
                }
            }
        });

        videogames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videogames.getId() == R.id.videogames){
                    videogames.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Videogames = new Category();
                    Videogames.setName("Video Games");
                    addCatToDatabase(Videogames);
                }
            }
        });

        utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utilities.getId() == R.id.utilities){
                    utilities.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Utilities = new Category();
                    Utilities.setName("Utilities");
                    addCatToDatabase(Utilities);
                }
            }
        });

        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transportation.getId() == R.id.transportation){
                    transportation.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Transportation = new Category();
                    Transportation.setName("Transportation");
                    addCatToDatabase(Transportation);
                }
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (food.getId() == R.id.food){
                    food.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Food = new Category();
                    Food.setName("Food");
                    addCatToDatabase(Food);
                }
            }
        });

        md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (md.getId() == R.id.md){
                    md.setTextColor(Color.parseColor("#FFFF00"));
                    //add to Category name db
                    Category Medical = new Category();
                    Medical.setName("Medical");
                    addCatToDatabase(Medical);
                }
            }
        });


        mButton = (Button)findViewById(R.id.addcustom);
        mEdit   = (EditText) findViewById(R.id.editTextaddbudget);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //Log.d("Main - EditText", mEdit.getText().toString());

                        //add to Category name db
                        MonthlyBudget monthlyBudget = new MonthlyBudget();
                        monthlyBudget.setMonthlybudget(mEdit.getText().toString());
                        monthlyBudget.setMonthYear("Dec 22");
                        db.addMonthlybudget(monthlyBudget);

                        Intent intent = new Intent(categorizes.this, addcustomcategory.class);
                        startActivity(intent);
                        //delete a transaction


                    }
                });

    }

    private void addCatToDatabase(Category category) {
        if (!loadedCatsNames.contains(category.getName().toUpperCase())) {

            db.addCategory(category);
        }
    }
}
