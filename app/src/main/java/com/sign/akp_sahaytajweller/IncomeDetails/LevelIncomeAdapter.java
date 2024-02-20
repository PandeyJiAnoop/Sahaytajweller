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



public class LevelIncomeAdapter extends RecyclerView.Adapter<LevelIncomeAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;
    public LevelIncomeAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public LevelIncomeAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_levelincome, viewGroup, false);
        LevelIncomeAdapter.VH viewHolder = new LevelIncomeAdapter.VH(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull LevelIncomeAdapter.VH vh, int i) {
        vh.date_tv.setText(arrayList.get(i).get("Date"));
        vh.income_tv.setText(arrayList.get(i).get("Income"));
        vh.level_tv.setText(arrayList.get(i).get("Levels"));
        vh.id_tv.setText(arrayList.get(i).get("MemberId"));
        vh.name_tv.setText(arrayList.get(i).get("MemberName"));
        vh.package_tv.setText(arrayList.get(i).get("Package"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class VH extends RecyclerView.ViewHolder {
        TextView date_tv,name_tv,income_tv,id_tv,package_tv,level_tv;

        public VH(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            id_tv = itemView.findViewById(R.id.id_tv);
            level_tv = itemView.findViewById(R.id.level_tv);

            income_tv = itemView.findViewById(R.id.income_tv);
            package_tv = itemView.findViewById(R.id.package_tv);
        }
    }
}

