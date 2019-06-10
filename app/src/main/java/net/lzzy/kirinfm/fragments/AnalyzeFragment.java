package net.lzzy.kirinfm.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.Region;
import net.lzzy.kirinfm.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class AnalyzeFragment extends BaseFragment {
    public static final String ARG_REGIONS = "arg_regions";
    public static final String ARG_RADIOS = "arg_radios";
    private List<Region> regions = new ArrayList<>();
    private Radio radios;
    private PieChart pieChart;
    private BarChart barProgram;
    private BarChart barStation;
    private LineChart lineChart;
    private Chart[] charts;
    private float touchX1;
    private int chartIndex = 0;
    private String[] titles = new String[]{"地区访问比例(单位:%)",
            "app访问次数", "最受欢迎的节目", "最受欢迎的电台", "电台类型健身"};
    private TextView tvRegion;

    public static AnalyzeFragment newInstance(List<Region> regions, Radio radios) {
        AnalyzeFragment fragment = new AnalyzeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_REGIONS, (ArrayList<? extends Parcelable>) regions);
        args.putParcelable(ARG_RADIOS, (Parcelable) radios);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            regions = getArguments().getParcelableArrayList(ARG_REGIONS);
            radios = getArguments().getParcelable(ARG_RADIOS);
        }
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_analyze;
    }

    @Override
    protected void populate() {

        tvRegion = find(R.id.fragment_analyze_tv_region);

        initCharts();
        //饼图显示
        displayPieChart();
        //配置饼图
        configPieChart();
        //显示折线图
        displayLineChart();
        //配置折线图
        configBarLineChart(lineChart);

        configBarLineChart(barStation);
        //显示柱状图
        displayBarChart();

        pieChart.setVisibility(View.VISIBLE);
        View dot1 = find(R.id.fragment_chart_dot1);
        View dot2 = find(R.id.fragment_chart_dot2);
        View dot3 = find(R.id.fragment_chart_dot3);
        View dot4 = find(R.id.fragment_chart_dot4);
        View dot5 = find(R.id.fragment_chart_dot5);
        View[] dots = new View[]{dot1, dot2, dot3, dot4, dot5};
        find(R.id.fragment_analyze_pie).setOnTouchListener(new ViewUtils.AbstractTouchHandler() {
            private static final float MIN_DISTANCE = 100;

            @Override
            public boolean handleTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX1 = event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchX2 = event.getX();
                    if (Math.abs(touchX2 - touchX1) > MIN_DISTANCE) {
                        if (touchX2 < touchX1) {
                            if (chartIndex < charts.length - 1) {
                                chartIndex++;
                            } else {
                                chartIndex = 0;
                            }
                        } else {
                            if (chartIndex > 0) {
                                chartIndex--;
                            } else {
                                chartIndex = charts.length - 1;
                            }
                        }
                        switchChart();
                    }
                }
                return true;
            }

            private void switchChart() {
                for (int i = 0; i < charts.length; i++) {
                    if (chartIndex == i) {
                        tvRegion.setText(titles[i]);
                        charts[i].setVisibility(View.VISIBLE);
                        dots[i].setBackgroundResource(R.drawable.dot_fill_style);
                    } else {
                        charts[i].setVisibility(View.GONE);
                        dots[i].setBackgroundResource(R.drawable.dot_style);
                    }
                }
            }
        });

    }

    private void displayBarChart() {
        ValueFormatter xFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "广西交通广播";
            }
        };
        barStation.getXAxis().setValueFormatter(xFormatter);


        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1));
        entries.add(new BarEntry(1, 2));
        entries.add(new BarEntry(2, 6));
        entries.add(new BarEntry(3, 3));
        entries.add(new BarEntry(4, 5));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(Color.GREEN, Color.GRAY, Color.DKGRAY, Color.LTGRAY, Color.MAGENTA);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.8f);
        barStation.setData(data);
        barStation.invalidate();
    }

    private void configBarLineChart(BarLineChartBase chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f);

        /**  Y 轴 **/
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setLabelCount(8, false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(11f);
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0);

        /** chart属性 **/
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setPinchZoom(false);
    }

    private void displayLineChart() {
        ValueFormatter xFrmatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Q." + (int) value;
            }
        };
        lineChart.getXAxis().setValueFormatter(xFrmatter);
        List<Entry> entries = new ArrayList<>();

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(1f);
        dataSet.setDrawCircles(true);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setValueTextSize(9f);
        LineData data = new LineData(dataSet);
        lineChart.setData(data);
    }

    private void displayPieChart() {
        int reght = 1;
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Region region : regions) {
            entries.add(new PieEntry(reght, region.getTitle()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(Color.GREEN, Color.RED, Color.MAGENTA, Color.DKGRAY, Color.LTGRAY, Color.CYAN);
        dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);

        pieChart.highlightValues(null);
        pieChart.invalidate();

    }

    private void configPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 10);
    }

    private void initCharts() {
        pieChart = find(R.id.fragment_charts_pie);
        barProgram = find(R.id.fragment_charts_bar_program);
        barStation = find(R.id.fragment_charts_bar_station);
        lineChart = find(R.id.fragment_charts_line);

        charts = new Chart[]{pieChart, lineChart, barProgram, barStation};

        for (Chart chart : charts) {
            chart.setTouchEnabled(false);
            chart.setVisibility(View.GONE);
            Description desc = new Description();
            chart.setDescription(desc);
            chart.setNoDataText("数据获取中......");
            chart.setExtraOffsets(5, 10, 5, 25);
        }
    }

    @Override
    public void search(String kw) {

    }
}
