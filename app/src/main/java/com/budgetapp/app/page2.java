package com.budgetapp.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.util.Util;

public class page2 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Button btn;

        DatabaseHandler db = new DatabaseHandler((page2.this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        btn =(Button) findViewById(R.id.btn4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(page2.this, Signup.class);
                startActivity(intent);
            }
        });

        Button loginbtn;
        loginbtn =(Button) findViewById(R.id.btn2);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.getMonthlybudget("Dec 2022") == null ) {
                    Intent intent = new Intent(page2.this, categorizes.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(page2.this, firstpage.class);
                    startActivity(intent);
                }

            }
        });
    }

}
