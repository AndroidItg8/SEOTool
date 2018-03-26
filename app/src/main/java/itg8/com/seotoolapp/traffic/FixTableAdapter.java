package itg8.com.seotoolapp.traffic;

import android.widget.TextView;

import java.util.List;

import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.widget.fixtablelayout.inter.IDataAdapter;

/**
 * Created by feng on 2017/4/4.
 */

public class FixTableAdapter implements IDataAdapter {

    public String[] titles;

    public List<Object> data;

    public FixTableAdapter(String[] titles, List<Object> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public String getTitleAt(int pos) {

        return titles[pos];
    }

    @Override
    public int getTitleCount() {
        return titles.length;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void convertData(int position, List<TextView> bindViews) {
        if(data.get(position) instanceof  TrafficModel)
        {
            TrafficModel TrafficModel = (itg8.com.seotoolapp.traffic.model.TrafficModel) data.get(position);
            bindViews.get(0)
                    .setText(TrafficModel.getTrafficmaster().getDateof());
            for (int i =1; i<=titles.length-1; i++)
            {
                bindViews.get(i)
                        .setText(TrafficModel.getTrafficmaster().getContof());


            }
        }else if(data.get(position) instanceof CommonMethod.TempYearHashMap)
        {
            CommonMethod.TempYearHashMap  tempYearHashMap = (CommonMethod.TempYearHashMap) data.get(position);
            bindViews.get(0).setText(tempYearHashMap.getMonth() + "-" +tempYearHashMap.getYear());

            bindViews.get(1).setText(String.valueOf(tempYearHashMap.getValue()));
        }


//        bindViews.get(titles.length)
//                .setText(TrafficModel.id);
//        bindViews.get(1)
//                .setText(TrafficModel.data1);
//        bindViews.get(2)
//                .setText(TrafficModel.data2);
//        bindViews.get(3)
//                .setText(TrafficModel.data3);
//        bindViews.get(4)
//                .setText(TrafficModel.data4);
//        bindViews.get(5)
//                .setText(TrafficModel.data5);
//        bindViews.get(6)
//                .setText(TrafficModel.data6);
//        bindViews.get(7)
//                .setText(TrafficModel.data7);
//        bindViews.get(8)
//                .setText(TrafficModel.data8);

    }

    @Override
    public void convertLeftData(int position, TextView bindView) {

        if(data.get(position) instanceof  TrafficModel) {
            TrafficModel TrafficModel = (itg8.com.seotoolapp.traffic.model.TrafficModel) data.get(position);

            bindView.setText(TrafficModel.getTrafficmaster().getDateof());
        }else if(data.get(position) instanceof CommonMethod.TempYearHashMap )
        {
            CommonMethod.TempYearHashMap  tempYearHashMap = (CommonMethod.TempYearHashMap) data.get(position);

            bindView.setText(tempYearHashMap.getMonth() + "-" +tempYearHashMap.getYear());

        }
    }
}
