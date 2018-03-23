package itg8.com.seotoolapp.traffic;

import android.widget.TextView;

import java.util.List;

import itg8.com.seotoolapp.traffic.model.DataBean;
import itg8.com.seotoolapp.widget.fixtablelayout.inter.IDataAdapter;

/**
 * Created by feng on 2017/4/4.
 */

public class FixTableAdapter implements IDataAdapter {

    public String[] titles;

    public List<DataBean> data;

    public FixTableAdapter(String[] titles, List<DataBean> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<DataBean> data) {
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
        DataBean dataBean = data.get(position);
        bindViews.get(0)
                .setText(dataBean.id);
        for (int i =1; i<=titles.length-1; i++)
        {
            bindViews.get(i)
                    .setText(dataBean.data1);


        }

//        bindViews.get(titles.length)
//                .setText(dataBean.id);
//        bindViews.get(1)
//                .setText(dataBean.data1);
//        bindViews.get(2)
//                .setText(dataBean.data2);
//        bindViews.get(3)
//                .setText(dataBean.data3);
//        bindViews.get(4)
//                .setText(dataBean.data4);
//        bindViews.get(5)
//                .setText(dataBean.data5);
//        bindViews.get(6)
//                .setText(dataBean.data6);
//        bindViews.get(7)
//                .setText(dataBean.data7);
//        bindViews.get(8)
//                .setText(dataBean.data8);

    }

    @Override
    public void convertLeftData(int position, TextView bindView) {
        bindView.setText(data.get(position).id);
    }
}
