package com.david.expenditures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class IncomeListAdapter extends ArrayAdapter<Income> {

    private Context context;
    private int resource;
    private ArrayList<Income> objects;

    public IncomeListAdapter(Context context, int resource, ArrayList<Income> objects){
        super(context,resource,objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        double amount = getItem(position).getAmount();
        String supplier = getItem(position).getSupplier();

        Income income = new Income(amount, supplier);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvAmount = (TextView)convertView.findViewById(R.id.incomeAmountText);
        TextView tvSupplier = (TextView)convertView.findViewById(R.id.incomeSupplierText);

        tvAmount.setText("£ "+amount);
        tvSupplier.setText(supplier);

        return convertView;
    }

}
