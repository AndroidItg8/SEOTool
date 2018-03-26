package itg8.com.seotoolapp.traffic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;

/**
 * Created by Android itg 8 on 3/22/2018.
 */

class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder> {
    private final Context context;
    private List<Trafficcategorymaster> trafficcategorymasterList;
    private final OnItemClickedListener listener;
    private final HashMap<Trafficcategorymaster, List<TrafficModel>> list;


    public TrafficAdapter(Context context, HashMap<Trafficcategorymaster, List<TrafficModel>> list,List<Trafficcategorymaster> trafficcategorymasterList,  OnItemClickedListener listener) {
        this.context = context;
        this.list = list;
        this.trafficcategorymasterList = trafficcategorymasterList;
        this.listener = listener;
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_traffic, parent, false);
        return new TrafficViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int position) {
        holder.lblTitle.setText(trafficcategorymasterList.get(position).getTraffic());
       String count =  findCountOf(list.get(trafficcategorymasterList.get(position)));
       holder.lblValue.setText(count);
       holder.lblDate.setText(CommonMethod.getCurrentDateString());

    }

    private String findCountOf(List<TrafficModel> trafficModels) {
       int i = 0;
        for (TrafficModel model:trafficModels) {
            i=+ Integer.parseInt(model.getTrafficmaster().getContof());
        }
        return String.valueOf(i);
    }

    @Override
    public int getItemCount() {
        return trafficcategorymasterList.size();
    }

    public class TrafficViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lbl_title)
        TextView lblTitle;
        @BindView(R.id.lbl_date)
        TextView lblDate;
        @BindView(R.id.lbl_value)
        TextView lblValue;
        @BindView(R.id.lbl_value_sign)
        TextView lblValueSign;
        TrafficModel model;



        public TrafficViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(list);
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(HashMap<Trafficcategorymaster, List<TrafficModel>> list);
    }
}
