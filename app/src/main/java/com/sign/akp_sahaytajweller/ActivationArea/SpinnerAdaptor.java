package com.sign.akp_sahaytajweller.ActivationArea;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.sign.akp_sahaytajweller.R;

import java.util.ArrayList;
import java.util.HashMap;


public class SpinnerAdaptor extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    public Resources res;
    private LayoutInflater inflater;

    public SpinnerAdaptor(
            Activity activity,
            int textViewResourceId,
            ArrayList<HashMap<String, String>> spList,
            Resources resLocal
    ) {
        super(activity, textViewResourceId, spList);
        data = spList;
        res = resLocal;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.custom_spinner_items, parent, false);

        TextView label = (TextView) row.findViewById(R.id.tvSpinner);
        label.setText(data.get(position).get("name"));
        if (position == 0) {
            label.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        } else {
            label.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        }
        return row;
    }
}

