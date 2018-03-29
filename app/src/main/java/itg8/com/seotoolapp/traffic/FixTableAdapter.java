package itg8.com.seotoolapp.traffic;

import android.widget.TextView;

import java.util.List;

import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;
import itg8.com.seotoolapp.external_links.model.Liveurlmaster;
import itg8.com.seotoolapp.keyword.model.KeyWordModel;
import itg8.com.seotoolapp.social_media.model.SocialMediaModel;
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
        else if(data.get(position) instanceof Liveurlmaster)
        {
          Liveurlmaster socialMediaModel= (Liveurlmaster) data.get(position);

          bindViews.get(0).setText("");
            bindViews.get(1).setText(String.valueOf(socialMediaModel.getLiveurl()));
            bindViews.get(2).setText(String.valueOf(socialMediaModel.getStatus()));
            bindViews.get(3).setText(String.valueOf(socialMediaModel.getSession()));
        }

        else if(data.get(position) instanceof KeyWordModel)
        {
          KeyWordModel socialMediaModel= (KeyWordModel) data.get(position);

          bindViews.get(0).setText("");
            bindViews.get(1).setText(String.valueOf(socialMediaModel.getKeywordstatusmaster().getKeyword()));
            bindViews.get(2).setText(String.valueOf(socialMediaModel.getKeywordstatusmaster().getPageno()));
            bindViews.get(3).setText(String.valueOf(socialMediaModel.getKeywordstatusmaster().getRank()));
        }



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

        else if(data.get(position) instanceof KeyWordModel)
        {
           KeyWordModel tempYearHashMap = (KeyWordModel) data.get(position);

            bindView.setText(CommonMethod.convertStringDateToDDMM(tempYearHashMap.getKeywordstatusmaster().getDateof()) );
        }

    }
}
