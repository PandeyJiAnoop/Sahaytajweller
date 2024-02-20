package com.sign.akp_sahaytajweller.IncomeDetails;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sign.akp_sahaytajweller.R;

import java.util.HashMap;
import java.util.List;



public class ContractIncomeAdapter extends RecyclerView.Adapter<ContractIncomeAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;
    public ContractIncomeAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ContractIncomeAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_contactincome, viewGroup, false);
        ContractIncomeAdapter.VH viewHolder = new ContractIncomeAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContractIncomeAdapter.VH vh, int i) {

        vh.tv.setText(String.valueOf(" "+(i+1)));
        vh.tv1.setText(arrayList.get(i).get("Date"));
        vh.tv2.setText(arrayList.get(i).get("Income"));
        vh.tv3.setText(arrayList.get(i).get("Package"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class VH extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2,tv3;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);

        }
    }
}


