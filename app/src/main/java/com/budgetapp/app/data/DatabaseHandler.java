package com.budgetapp.app.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.budgetapp.app.model.Category;
import com.budgetapp.app.model.MonthlyBudget;
import com.budgetapp.app.model.Transaction;
import com.budgetapp.app.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper  {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME,null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Util.TRANSACTIONS_TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_TEXT + " TEXT NOT NULL,"
                + Util.KEY_AMOUNT + " TEXT," + Util.KEY_TRANSACTION_DATE + " TEXT," + Util.KEY_CATEGORY_NAME + " TEXT" + ")";

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + Util.CATEGORIES_TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT" + ")";

        String CREATE_MONTHLYBUDGET_TABLE = "CREATE TABLE " + Util.MONTHLYBUDGETS_TABLE_NAME + "("
                + Util.KEY_MONTHYEAR + " TEXT PRIMARY KEY," + Util.KEY_MONTHLYBUDGET + " TEXT" + ")";


        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_MONTHLYBUDGET_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addTransaction(Transaction tr){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_TEXT, tr.getText());
        values.put(Util.KEY_AMOUNT, String.valueOf(tr.getAmount()));
        values.put(Util.KEY_TRANSACTION_DATE, String.valueOf(tr.getTransaction_date()));
        values.put(Util.KEY_CATEGORY_NAME, String.valueOf(tr.getCategory()));

        db.insert(Util.TRANSACTIONS_TABLE_NAME,null,values);
        db.close();

    }


    public void addCategory(Category cr){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, cr.getName());

        db.insert(Util.CATEGORIES_TABLE_NAME,null,values);
       //Log.d("DBHandler","addCategory: " + "item added");
        db.close();
    }

    public void addMonthlybudget(MonthlyBudget mb){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_MONTHLYBUDGET, mb.getMonthlybudget());
        values.put(Util.KEY_MONTHYEAR,mb.getMonthYear());

        db.insert(Util.MONTHLYBUDGETS_TABLE_NAME,null,values);
        ///Log.d("DBHandler","addMonthlybudget: " + "item added");
        db.close();
    }

    //get a transaction

    public MonthlyBudget getMonthlybudget(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.MONTHLYBUDGETS_TABLE_NAME, new String[]{Util.KEY_MONTHYEAR, Util.KEY_MONTHLYBUDGET},
                Util.KEY_MONTHYEAR + "=?", new String[]{yearMonth}, null,null,null);


        if(cursor != null  && cursor.getCount()>0){
            //Log.d("Main","addMonthlybudget: Has Record");
            cursor.moveToFirst();
            MonthlyBudget monthlyBudget = new MonthlyBudget();
            monthlyBudget.setMonthlybudget(cursor.getString(1));
            monthlyBudget.setMonthYear(cursor.getString(0));


            return monthlyBudget;}
        else {
            //Log.d("Main","addMonthlybudget: No Record");
            return null;
        }
    }

/*
    public boolean tableExists(){

        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null)
            Log.d("Main", "db is null");

        if (!db.isOpen())
            Log.d("Main", "db is not open");
        if(Util.MONTHLYBUDGETS_TABLE_NAME == null)
            Log.d("Main", "table is null");
        else Log.d("Main", " else "+ db + Util.MONTHLYBUDGETS_TABLE_NAME);
        return false;
    } */


    public Transaction getTransaction(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TRANSACTIONS_TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_TEXT,Util.KEY_AMOUNT,Util.KEY_TRANSACTION_DATE,Util.KEY_CATEGORY_NAME},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null,null,null);

        if(cursor != null)
            cursor.moveToFirst();
        Transaction transaction = new Transaction();
        transaction.setId(Integer.parseInt(cursor.getString(0)));
        transaction.setText(cursor.getString(1));
        transaction.setAmount(cursor.getString(2));
        transaction.setTransaction_date(cursor.getString(3));
        transaction.setCategory(cursor.getString(4));

        return transaction;
    }


    public List<Transaction> getAllTransactions(){
        List<Transaction> transList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TRANSACTIONS_TABLE_NAME;
        Cursor cursor= db.rawQuery(selectAll, null);

        //Loop through our data
        if ( cursor.moveToFirst()){
            do{
                Transaction tr=new Transaction();
                tr.setId(Integer.parseInt(cursor.getString(0)));
                tr.setText(cursor.getString(1));
                tr.setAmount(cursor.getString(2));
                tr.setTransaction_date(cursor.getString(3));
                tr.setCategory(cursor.getString(4));

                transList.add(tr);

            } while (cursor.moveToNext());
        }
        return transList;
    }

    public List<Transaction> getAllTransactionsForMonthYear(String year,String month){
        List<Transaction> transList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TRANSACTIONS_TABLE_NAME + " WHERE " + Util.KEY_TRANSACTION_DATE + " LIKE ? " ;
        String param1 =month+" "+year;
        Cursor cursor= db.rawQuery(selectAll, new String[]{param1});

        //Loop through our data
        if ( cursor.moveToFirst()){
            do{
                Transaction tr=new Transaction();
                tr.setId(Integer.parseInt(cursor.getString(0)));
                tr.setText(cursor.getString(1));
                tr.setAmount(cursor.getString(2));
                tr.setTransaction_date(cursor.getString(3));
                tr.setCategory(cursor.getString(4));

                transList.add(tr);

            } while (cursor.moveToNext());
        }
        return transList;
    }

    public List<Category> getAllCategories(){
        List<Category> catList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.CATEGORIES_TABLE_NAME;

        Cursor cursor= db.rawQuery(selectAll, null);

        if ( cursor.moveToFirst()) {
            do {
                Category tr = new Category();
                tr.setId(Integer.parseInt(cursor.getString(0)));
                tr.setName(cursor.getString(1));

                catList.add(tr);

            } while (cursor.moveToNext());
        }
        return catList;
    }

    public List<MonthlyBudget> getAllMonthlyBudgets(){
        List<MonthlyBudget> transList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.MONTHLYBUDGETS_TABLE_NAME;
        Cursor cursor= db.rawQuery(selectAll, null);

        //Loop through our data
        if ( cursor.moveToFirst()){
            do{
                MonthlyBudget tr=new MonthlyBudget();
                tr.setMonthYear(cursor.getString(0));
                tr.setMonthlybudget(cursor.getString(1));

                transList.add(tr);

            } while (cursor.moveToNext());
        }
        return transList;
    }


    //Delete

    public void deletetransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        int num=db.delete(Util.TRANSACTIONS_TABLE_NAME, "KEY_TEXT IS NULL",null);

        //Log.d("Main  num __ ", String.valueOf(num));

        db.close();
    }

    public void deletecategorie (Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        int num = db.delete(Util.CATEGORIES_TABLE_NAME,"TEXT IS NULL" ,null);

       // Log.d("Main num __", String.valueOf(num));

        db.close();
    }

    //Update transaction

    public int updateTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =  new ContentValues();
        values.put(Util.KEY_AMOUNT, transaction.getAmount());

        //update the row

        return db.update(Util.TRANSACTIONS_TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(transaction.getId())});
    }



}
