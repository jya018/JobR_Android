package com.capstone.JobR.view.avgSpec;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class AvgSpecChartAdapter extends RecyclerView.Adapter<AvgSpecChartAdapter.ViewHolder> {
    private ArrayList<String> specList = new ArrayList<>();
    private ArrayList<Float> specAvg = new ArrayList<>();
    private ArrayList<int[]> dataList = new ArrayList<>();
    private ArrayList<String[]> distList = new ArrayList<>();

    @NonNull
    @Override
    public AvgSpecChartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spec_chart, parent, false);

        return new AvgSpecChartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvgSpecChartAdapter.ViewHolder holder, int position) {
        holder.onBind(specList.get(position), specAvg.get(position), dataList.get(position), distList.get(position));
    }

    @Override
    public int getItemCount() {
        return specList.size();
    }

    public void addItem(String spec, float avg, ArrayList<Integer> specData, String[] distribute) {
        specList.add(spec);
        specAvg.add(avg);
        int[] tmp = new int[specData.size()];
        for (int i = 0; i < specData.size(); i++) {
            tmp[i] = specData.get(i);
        }
        dataList.add(tmp);
        distList.add(distribute);
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = true;
        private boolean hasLabelForSelected = false;

        //스펙 data List
        private int[] dataList;

        //스펙 분배
        private String[] distributionLabel;
        private int[] distData;

        private TextView specTitle;
        private TextView specAvg;
        private ImageView arrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specTitle = itemView.findViewById(R.id.specTitle);
            specAvg = itemView.findViewById(R.id.specAvg);

            arrow = itemView.findViewById(R.id.ic_arrow);
            chart = (ColumnChartView) itemView.findViewById(R.id.chart);

            //클릭 시, 차트 펼치기
            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                if (chart.getVisibility() == View.VISIBLE) {
                    chart.setVisibility(View.GONE);
                    arrow.animate().setDuration(200).rotation(0f);
                } else {
                    chart.setVisibility(View.VISIBLE);
                    arrow.animate().setDuration(200).rotation(90f);
                }
            });
        }

        public void onBind(String specTitle, float specAvg, int[] dataList, String[] distribution) {
            this.specTitle.setText(specTitle);
            this.specAvg.setText(String.valueOf(specAvg));
            this.dataList = dataList;   //데이터 값들 모여있는 것
            this.distributionLabel = distribution;  //밑에 점수판

            //점수 배분
            distributeData();

            //차트 생성
            generateDefaultData();
        }

        //점수 배분
        private void distributeData() {
            distData = new int[distributionLabel.length];
            switch (specTitle.getText().toString()) {
                case "TOEIC":
                    toeic();
                    break;
                case "TOEFL":
                    toefl();
                    break;
                case "TEPS":
                    teps();
                    break;
                case "OPIC":
                    opic();
                    break;
                case "TOEIC SPEAKING":
                    tos();
                    break;
                default:
                    break;
            }
        }

        public void toeic() {
            for (int i = 0; i < dataList.length; i++) {
                //950,900,850,800,750,700,700미만
                if (dataList[i] >= 950) {
                    distData[0]++;
                } else if (dataList[i] >= 900) {
                    distData[1]++;
                } else if (dataList[i] >= 850) {
                    distData[2]++;
                } else if (dataList[i] >= 800) {
                    distData[3]++;
                } else if (dataList[i] >= 750) {
                    distData[4]++;
                } else if (dataList[i] >= 700) {
                    distData[5]++;
                } else
                    distData[6]++;
            }
        }

        public void toefl() {
            for (int i = 0; i < dataList.length; i++) {
                //110,100,90,80,70,70미만
                if (dataList[i] >= 110) {
                    distData[0]++;
                } else if (dataList[i] >= 100) {
                    distData[1]++;
                } else if (dataList[i] >= 90) {
                    distData[2]++;
                } else if (dataList[i] >= 80) {
                    distData[3]++;
                } else if (dataList[i] >= 70) {
                    distData[4]++;
                } else
                    distData[5]++;
            }
        }

        public void teps() {
            for (int i = 0; i < dataList.length; i++) {
                //550,500,450,400,350,350미만
                if (dataList[i] >= 550) {
                    distData[0]++;
                } else if (dataList[i] >= 500) {
                    distData[1]++;
                } else if (dataList[i] >= 450) {
                    distData[2]++;
                } else if (dataList[i] >= 400) {
                    distData[3]++;
                } else if (dataList[i] >= 350) {
                    distData[4]++;
                } else
                    distData[5]++;
            }
        }

        public void opic() {
            for (int i = 0; i < dataList.length; i++) {
                //AL(8),IH(7),IM3(6),IM2(5),IM1(4),IM1(4)미만
                if (dataList[i] >= 8) {
                    distData[0]++;
                } else if (dataList[i] >= 7) {
                    distData[1]++;
                } else if (dataList[i] >= 6) {
                    distData[2]++;
                } else if (dataList[i] >= 5) {
                    distData[3]++;
                } else if (dataList[i] >= 4) {
                    distData[4]++;
                } else
                    distData[5]++;
            }
        }

        public void tos() {
            for (int i = 0; i < dataList.length; i++) {
                //190,180,170,160,150,140,130,130미만
                if (dataList[i] >= 190) {
                    distData[0]++;
                } else if (dataList[i] >= 180) {
                    distData[1]++;
                } else if (dataList[i] >= 170) {
                    distData[2]++;
                } else if (dataList[i] >= 160) {
                    distData[3]++;
                } else if (dataList[i] >= 150) {
                    distData[4]++;
                } else if (dataList[i] >= 140) {
                    distData[5]++;
                } else if (dataList[i] >= 130) {
                    distData[6]++;
                } else
                    distData[7]++;
            }
        }

        public void score() {
            for (int i = 0; i < dataList.length; i++) {
                //"4","3.5","3","2.5","2.5미만"
                if (dataList[i] >= 4) {
                    distData[0]++;
                } else if (dataList[i] >= 3.5) {
                    distData[1]++;
                } else if (dataList[i] >= 3) {
                    distData[2]++;
                } else if (dataList[i] >= 2.5) {
                    distData[3]++;
                } else
                    distData[4]++;
            }
        }

        //차트 생성
        private void generateDefaultData() {
            int numSubcolumns = 1;
            int numColumns = distData.length;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    //데이터 출력
//                    values.add(new SubcolumnValue(distData[i], Color.parseColor("#222222")));
                    values.add(new SubcolumnValue(distData[i], ChartUtils.pickColor()));
                }

                //X축 라벨
                axisValues.add(new AxisValue(i).setLabel(distributionLabel[i]));

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes) {
                Axis axisX = new Axis(axisValues);
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("점수");
                    axisY.setName("인원 수");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);
        }
    }
}