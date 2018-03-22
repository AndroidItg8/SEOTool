package itg8.com.seotoolapp.traffic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;

/**
 * Created by Android itg 8 on 3/22/2018.
 */

class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder> {
    private final Context context;
    private final OnItemClickedListener listener;


    public TrafficAdapter(Context context, OnItemClickedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_traffic, parent, false);
        return new TrafficViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
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



        public TrafficViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked();
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked();
    }
}
