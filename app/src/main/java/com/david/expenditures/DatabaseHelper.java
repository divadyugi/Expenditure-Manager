package com.david.expenditures;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String Database_Name = "Income.db";
    public static final String Table_Name = "Income";
    public static final String Column_ID = "ID";
    public static final String Column_Amount = "IncomeAmount";
    public static final String Column_Supplier = "IncomeSupplier";




    public DatabaseHelper(Context context){
        super(context, Table_Name, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table_Name+"("+Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                Column_Amount+" DOUBLE,"+
                Column_Supplier+" Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Name);
        onCreate(db);
    }

    public boolean insertIncomeData(double amount, String supplier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column_Amount, amount);
        values.put(Column_Supplier, supplier);
        long result = db.insert(Table_Name, null, values);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getIncomeData(){
        String query = "SELECT * FROM "+Table_Name;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getAmountSortedIncomeData(){
        String query = "SELECT * FROM "+Table_Name + " ORDER BY "+Column_Amount +" ASC";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getSupplierSortedIncomeData(){
        String query = "SELECT * FROM "+Table_Name + " ORDER BY "+Column_Supplier +" DESC";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

}
