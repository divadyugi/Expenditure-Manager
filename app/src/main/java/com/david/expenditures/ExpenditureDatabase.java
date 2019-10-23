package com.david.expenditures;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenditureDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Expenditure.db";
    public static final String TABLE_NAME = "Expenditures";
    public static final String EXPENDITURE_COLUMN_ID = "ID";
    public static final String EXPENDITURE_COLUMN_AMOUNT = "ExpenditureAmount";
    public static final String EXPENDITURE_COLUMN_SUPPLIER = "ExpenditureSupplier";
    private static final String EXPENDITURE_COLUMN_TYPE = "ExpenditureType";




    public ExpenditureDatabase(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+EXPENDITURE_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXPENDITURE_COLUMN_AMOUNT+" DOUBLE,"+
                EXPENDITURE_COLUMN_SUPPLIER+" Text,"+
                EXPENDITURE_COLUMN_TYPE +" Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertExpenditureData(double amount, String supplier, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENDITURE_COLUMN_AMOUNT, amount);
        values.put(EXPENDITURE_COLUMN_SUPPLIER, supplier);
        values.put(EXPENDITURE_COLUMN_TYPE, type);

        long result = db.insert(TABLE_NAME, null, values);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getExpenditureData(String type){
        String query = "SELECT * FROM "+TABLE_NAME + " WHERE "+EXPENDITURE_COLUMN_TYPE+" = "+"'"+type+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getAmountSortedExpenditureData(String type){
        String query = "SELECT * FROM "+TABLE_NAME + " WHERE "+EXPENDITURE_COLUMN_TYPE+" = "+"'"+type+"'"+ "ORDER BY "+EXPENDITURE_COLUMN_AMOUNT+ " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getSupplierSortedExpenditureData(String type){
        String query = "SELECT * FROM "+TABLE_NAME + " WHERE "+EXPENDITURE_COLUMN_TYPE+" = "+"'"+type+"'"+ "ORDER BY "+EXPENDITURE_COLUMN_SUPPLIER+ " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

}
