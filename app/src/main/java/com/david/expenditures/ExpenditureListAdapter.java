package com.david.expenditures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ExpenditureListAdapter extends ArrayAdapter<Expenditure> {

    private Context context;
    private int resource;
    private ArrayList<Expenditure> objects;

    public ExpenditureListAdapter(Context context, int resource, ArrayList<Expenditure> objects){
        super(context,resource,objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        double amount = getItem(position).getAmount();
        String supplier = getItem(position).getSupplier();
        String type = getItem(position).getType();

        Expenditure expenditure = new Expenditure(amount,supplier,type);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView tvAmount = (TextView)convertView.findViewById(R.id.incomeAmountText);
        TextView tvSupplier = (TextView)convertView.findViewById(R.id.incomeSupplierText);

        tvAmount.setText("£ "+amount);
        tvSupplier.setText(supplier);

        return convertView;
    }
}
