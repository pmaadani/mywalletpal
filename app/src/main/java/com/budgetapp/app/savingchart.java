package com.budgetapp.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class savingchart extends Activity {

    PieChart pieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savingchart);

        pieChart = (PieChart) findViewById(R.id.savingsbarchart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);


        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(34f,""));
        yValues.add(new PieEntry(24f,""));
        yValues.add(new PieEntry(14f,""));
        yValues.add(new PieEntry(39f,""));
        yValues.add(new PieEntry(44f,""));
        yValues.add(new PieEntry(34f,""));
        yValues.add(new PieEntry(34f,""));
        yValues.add(new PieEntry(40f,""));
        yValues.add(new PieEntry(21f,""));
        yValues.add(new PieEntry(10f,""));
        yValues.add(new PieEntry(20f,""));
        yValues.add(new PieEntry(33f,""));

        PieDataSet dataSet = new PieDataSet(yValues,"Months");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

    }
}
