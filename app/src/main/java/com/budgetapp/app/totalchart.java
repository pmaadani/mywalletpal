package com.budgetapp.app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budgetapp.app.data.DatabaseHandler;
import com.budgetapp.app.model.MonthlyBudget;
import com.budgetapp.app.model.Transaction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class totalchart extends AppCompatActivity {

    BarChart barChart;
    float barWidth;
    float barSpace;
    float groupSpace;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.totalchart);

        DatabaseHandler db = new DatabaseHandler(this);

        List<MonthlyBudget> mbList= db.getAllMonthlyBudgets();


        if (mbList!=null) {
            barWidth = 0.3f;
            barSpace = 0f;
            groupSpace = 0.4f;
            barChart = (BarChart) findViewById(R.id.barchart);

            barChart.setDrawBarShadow(false);
            barChart.setDescription(null);
            barChart.setDrawValueAboveBar(true);
            barChart.setMaxVisibleValueCount(50);
            barChart.setPinchZoom(false);
            barChart.setDrawGridBackground(false);
            barChart.setScaleEnabled(false);

            int groupCount = mbList.size();

            List xVals = new ArrayList();
            List<BarEntry> earnings = new ArrayList<>();
            List<BarEntry> spendings = new ArrayList<>();


            int counter=1;
            for (MonthlyBudget mb : mbList) {
                xVals.add(mb.getMonthYear());

                earnings.add(new BarEntry(counter,Float.valueOf(mb.getMonthlybudget())));
                List<Transaction> allTr= db.getAllTransactionsForMonthYear(mb.getMonthYear());
                float spending=0;
                for (Transaction tr : allTr) {
                    spending += Float.valueOf(tr.getAmount());
                }
                spendings.add(new BarEntry(counter,spending));
                counter++;
            }


            BarDataSet set1, set2;
            set1 = new BarDataSet(earnings, "Earning");
            set1.setColor(Color.rgb(111, 51, 242));
            set2 = new BarDataSet(spendings, "Spending");
            set2.setColor(Color.rgb(146, 59, 209));
            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            barChart.setData(data);
            barChart.getBarData().setBarWidth(barWidth);
            barChart.getXAxis().setAxisMinimum(0);
            barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            barChart.groupBars(0, groupSpace, barSpace);
            barChart.getData().setHighlightEnabled(false);
            barChart.invalidate();



            Legend l = barChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setYOffset(20f);
            l.setXOffset(0f);
            l.setYEntrySpace(0f);
            l.setTextSize(8f);
            l.setXEntrySpace(1f);
            l.setFormSize(9f);



            //X-axis
            XAxis xAxis = barChart.getXAxis();
            //xAxis.setLabelCount(groupCount,true);
            xAxis.setGranularity(1f);
            //xAxis.setGranularityEnabled(true);
            //xAxis.setCenterAxisLabels(true);
            xAxis.setDrawGridLines(false);
            //xAxis.setAxisMaximum(12);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setCenterAxisLabels(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

            //Y-axis
            barChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setDrawGridLines(true);
            //leftAxis.setSpaceTop(35f);
            //leftAxis.setAxisMinimum(0f);

        }







    }


}
