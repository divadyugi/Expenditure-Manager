package com.david.expenditures;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText addIncomeAmount, addIncomeSupplier, addExpenditureSupplier, addExpenditureAmount;
    ListView incomeListView, expenditureListView;
    Spinner expenditureType, expenditureSearchType;
    Button incomeAmountSort, incomeSupplierSort, expenditureAmountSortBtn, expenditureSupplierSortBtn;

    DatabaseHelper myDB;
    ArrayList<Income> incomes;
    private boolean amountSort;
    private boolean supplierSort;

    ExpenditureDatabase expenditureDB;
    ArrayList<Expenditure> expenditures;
    private boolean expenditureAmountSort;
    private boolean expenditureSupplierSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Expenditure Manager");


        myDB = new DatabaseHelper(this);

        incomes = new ArrayList<>();

        expenditureDB = new ExpenditureDatabase(this);

        expenditures = new ArrayList<>();

    }

    public void addData(View view){
        try {
            addIncomeAmount = (EditText) findViewById(R.id.addIncomeAmount);
            addIncomeSupplier = (EditText) findViewById(R.id.addIncomeSupplier);

            if(addIncomeSupplier.getText().length()<=0){
                throw new NumberFormatException();
            }

            boolean isInserted = myDB.insertIncomeData(Double.parseDouble(addIncomeAmount.getText().toString()),
                    addIncomeSupplier.getText().toString());
            if (isInserted) {
                Toast.makeText(this, "Data was added to the database", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to add data to the database. Try again", Toast.LENGTH_LONG).show();
            }

            addIncomeAmount.setHint("Amount");
            addIncomeSupplier.setHint("Supplier");
        }catch(NumberFormatException ex){
            Toast.makeText(this,"Please enter valid data to every data field",Toast.LENGTH_LONG).show();
        }
    }

    public void showIncomeData(View view){
        readIncomeData();
        moveToIncomeData(view);

        incomeListView = (ListView)findViewById(R.id.incomeList);
        IncomeListAdapter adapter = new IncomeListAdapter(this, R.layout.income_data_layout, incomes);

        incomeListView.setAdapter(adapter);


    }

    private void readIncomeData() {
        removeListElements();
        Cursor res;
        if(amountSort){
            res = myDB.getAmountSortedIncomeData();
        }else if(supplierSort) {
            res = myDB.getSupplierSortedIncomeData();
        }else{
            res = myDB.getIncomeData();
        }
        if(res.getCount()==0){
            Toast.makeText(this,"There is no data in the database", Toast.LENGTH_LONG).show();
            return;
        }

        double amount;
        String supplier;
        while(res.moveToNext()){

            amount = res.getDouble(1);
            supplier = res.getString(2);

            System.out.println("Amount: "+amount);

            incomes.add(new Income(amount,supplier));

        }
    }


    public void sortByIncomeAmount(View view){
        amountSort=!amountSort;
        showIncomeData(view);

        incomeAmountSort = (Button)findViewById(R.id.incomeAmountSortBtn);
        System.out.println(incomeAmountSort.getBackground());
        if(amountSort){
            incomeAmountSort.setBackgroundColor(getResources().getColor(R.color.colorActive));
        }else{
            incomeAmountSort.setBackgroundColor(getResources().getColor(R.color.colorInActive));
        }
    }

    public void sortByIncomeSupplier(View view){
        supplierSort= !supplierSort;
        showIncomeData(view);
        incomeSupplierSort = (Button)findViewById(R.id.incomeSupplierSortBtn);
        if(supplierSort){
            incomeSupplierSort.setBackgroundColor(getResources().getColor(R.color.colorActive));
        }else{
            incomeSupplierSort.setBackgroundColor(getResources().getColor(R.color.colorInActive));
        }
    }

    public void addExpenditureData(View view){
        try {
        addExpenditureAmount = (EditText)findViewById(R.id.addExpenditureAmount);
        addExpenditureSupplier  = (EditText)findViewById(R.id.addExpenditureSupplier);
        expenditureType = (Spinner)findViewById(R.id.addType);

            if(addExpenditureSupplier.getText().length()<=0){
                throw new NumberFormatException();
            }

            boolean expenditureInserted = expenditureDB.insertExpenditureData(Double.parseDouble(addExpenditureAmount.getText().toString()),
                    addExpenditureSupplier.getText().toString(),
                    expenditureType.getSelectedItem().toString());
            if (expenditureInserted) {
                Toast.makeText(this, "Data was added to the database", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to add data to the database. Try again", Toast.LENGTH_LONG).show();
            }

            addExpenditureAmount.setHint("Amount");
            addExpenditureSupplier.setHint("Supplier");
        }catch(NumberFormatException ex){
            Toast.makeText(this,"Please enter valid data to every data field",Toast.LENGTH_LONG).show();
        }
    }

    public void showExpenditure(View view){

        readExpenditure();

        expenditureListView = (ListView)findViewById(R.id.expenditureList);
        ExpenditureListAdapter expenditureAdapter = new ExpenditureListAdapter(this, R.layout.income_data_layout, expenditures);

        expenditureListView.setAdapter(expenditureAdapter);

    }

    private void readExpenditure(){
        removeExpenditureListElements();
        expenditureSearchType = (Spinner)findViewById(R.id.findType);
        Cursor res;
        if(expenditureAmountSort) {
            res = expenditureDB.getAmountSortedExpenditureData(expenditureSearchType.getSelectedItem().toString());
        }else if(expenditureSupplierSort){
            res = expenditureDB.getSupplierSortedExpenditureData(expenditureSearchType.getSelectedItem().toString());

        }else{
            res = expenditureDB.getExpenditureData(expenditureSearchType.getSelectedItem().toString());
        }

        if(res.getCount()==0){
            Toast.makeText(this,"There is no data in the database", Toast.LENGTH_LONG).show();
            return;
        }

        double amount;
        String supplier;
        String type;
        while(res.moveToNext()){

            amount = res.getDouble(1);
            supplier = res.getString(2);
            type = res.getString(3);

            expenditures.add(new Expenditure(amount,supplier,type));
//            System.out.println("Type: "+type+" Spinner"+expenditureListView.getSelectedItem().toString());

        }
    }

    public void sortByExpenditureAmount(View view){
        expenditureAmountSort= !expenditureAmountSort;
        showExpenditure(view);
        expenditureAmountSortBtn = (Button)findViewById(R.id.expenditureAmountSortBtn);
        if(supplierSort){
            expenditureAmountSortBtn.setBackgroundColor(getResources().getColor(R.color.colorActive));
        }else{
            expenditureAmountSortBtn.setBackgroundColor(getResources().getColor(R.color.colorInActive));
        }
    }

    public void sortByExpenditureSupplier(View view){
        expenditureSupplierSort= !expenditureSupplierSort;
        showExpenditure(view);
        expenditureSupplierSortBtn = (Button)findViewById(R.id.expenditureSupplierSortBtn);
        if(supplierSort){
            expenditureSupplierSortBtn.setBackgroundColor(getResources().getColor(R.color.colorActive));
        }else{
            expenditureSupplierSortBtn.setBackgroundColor(getResources().getColor(R.color.colorInActive));
        }
    }

    private void removeExpenditureListElements(){
        for(int i =expenditures.size()-1; i>=0; i--){
            expenditures.remove(i);
        }
    }

    private void removeListElements(){
        for(int i =incomes.size()-1; i>=0;i--){
            incomes.remove(i);
        }
    }

    public void moveToAdditionChoice(View view){
        setContentView(R.layout.additionchoice);
    }

    public void moveToExpenditureAddition(View view){
        setContentView(R.layout.expenditureaddition);
    }

    public void moveToIncomeAddition(View view){
        setContentView(R.layout.incomeaddition);
    }

    public void moveToDataExtraction(View view){
        setContentView(R.layout.dataextraction);
    }

    public void moveToIncomeData(View view){
        setContentView(R.layout.incomedata);
    }


    public void moveToExpenditureData(View view){
        setContentView(R.layout.expendituredata);
    }

    public void moveToMainScreen(View view){
        setContentView(R.layout.activity_main);
    }
}
