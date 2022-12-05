package com.budgetapp.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.budgetapp.app.model.Category;
import com.budgetapp.app.model.Transaction;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Map<String,String> mCategoriesMap;
    private Context context;
    private OnItemClickListener itemClickListener;
    private List<Category> mCatList;

    public RecyclerViewAdapter(Map<String,String> categoriesMap, List<Category> catList, Context context, OnItemClickListener itemClickListener) {
        this.mCategoriesMap =categoriesMap;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.mCatList=catList;

    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listtemplate,parent,false);

        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        String catName=mCatList.get(position).getName();
        holder.categoryTextView.setText(catName);
        float val=mCategoriesMap.get(catName)!=null ? Float.valueOf(mCategoriesMap.get(catName)) : 0f;
        holder.mchart.setData(getChartData(val));
    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener onItemClickListener;

        public TextView categoryTextView;
        public TextView addManualTextView;
        public TextView scanReciptTextView;
        public TextView removeCategoryImageView;
        public HorizontalBarChart mchart;


        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            addManualTextView = itemView.findViewById(R.id.addManualTextView);
            scanReciptTextView = itemView.findViewById(R.id.scanReciptTextView);
            removeCategoryImageView = itemView.findViewById(R.id.deleteTextView);
            mchart=itemView.findViewById(R.id.chart1);
            mchart.getDescription().setEnabled(false);
            mchart.getLegend().setEnabled(false);
            //mchart.getXAxis().setLabelCount(0,true);
            mchart.getAxisLeft().setDrawLabels(false);
            //mchart.getAxisRight().setDrawLabels(false);
            mchart.getAxisRight().setAxisMaximum(1000);
            mchart.getAxisRight().setAxisMinimum(0);
            mchart.getAxisLeft().setAxisMinimum(0);
            mchart.getAxisLeft().setAxisMaximum(1000);

            mchart.getXAxis().setDrawLabels(false);
            mchart.getAxisLeft().setDrawGridLines(false);
            mchart.getXAxis().setDrawGridLines(false);
            mchart.getXAxis().setEnabled(false);
            mchart.getAxisRight().setDrawGridLines(false);

            addManualTextView.setOnClickListener(this);
            scanReciptTextView.setOnClickListener(this);
            removeCategoryImageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id=view.getId();
            if (id==R.id.addManualTextView) {
                onItemClickListener.onAddManualClicked(getAdapterPosition());
            } else if (id==R.id.scanReciptTextView) {
                onItemClickListener.onscanReciptClicked(getAdapterPosition());
            }else if (id==R.id.deleteTextView) {
                onItemClickListener.onCategoryRemoveClicked(getAdapterPosition());
            }

        }

    }

    public interface OnItemClickListener {
        void onAddManualClicked(int adapterPosition);
        void onscanReciptClicked(int adapterPosition);
        void onCategoryRemoveClicked(int adapterPosition);

    }

    private BarData getChartData (float ydata){
//
        BarEntry yval=new BarEntry(0f,ydata);
        List<BarEntry> barList=new ArrayList<>();
        barList.add(yval);

        BarDataSet set1 = new BarDataSet(barList,"Data Set1");
        set1.setColor(Color.rgb(132, 78, 207));
        set1.getBarShadowColor();

        BarData data = new BarData(set1);

        return data;
    }

}
