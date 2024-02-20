package com.sign.akp_sahaytajweller.Adapterclass;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sign.akp_sahaytajweller.R;

import java.util.HashMap;
import java.util.List;



public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;
    public ReferralAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public ReferralAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_referal_list, viewGroup, false);
        ReferralAdapter.VH viewHolder = new ReferralAdapter.VH(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ReferralAdapter.VH vh, int i) {
        vh.tv.setText(arrayList.get(i).get("ActiveDate"));
        vh.tv1.setText(arrayList.get(i).get("CustomerId"));
        vh.tv2.setText(arrayList.get(i).get("CustomerName"));
        vh.tv3.setText(arrayList.get(i).get("mobileno"));

        vh.tv4.setText(arrayList.get(i).get("Package_Cost"));
        vh.tv5.setText(arrayList.get(i).get("RegDate"));
        if (arrayList.get(i).get("ActiveStatus").equalsIgnoreCase("Active")){
            vh.tv6.setText(arrayList.get(i).get("ActiveStatus"));
            vh.tv6.setTextColor(Color.GREEN);
        }
        else {
            vh.tv6.setText(arrayList.get(i).get("ActiveStatus"));
            vh.tv6.setTextColor(Color.RED);
        }
        vh.tv7.setText(arrayList.get(i).get("teamBussiness"));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class VH extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2, tv3,tv4,tv5,tv6,tv7;
        //        CircleImageView img_icon;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            tv6 = itemView.findViewById(R.id.tv6);
            tv7 = itemView.findViewById(R.id.tv7);
        }
    }
}
