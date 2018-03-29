package itg8.com.seotoolapp.keyword;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.keyword.model.Keywordstatusmaster;


/**
 * Created by Android itg 8 on 3/24/2018.
 */

public class KeyWordStatusAdapter extends RecyclerView.Adapter<KeyWordStatusAdapter.KeyWordStatusViewHolder> {


    private final Context context;
    private final List<Keywordstatusmaster> list;


    public KeyWordStatusAdapter(Context context, List<Keywordstatusmaster> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public KeyWordStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_keyword_status, parent, false);
        return new KeyWordStatusViewHolder(view);

    }

    @Override
    public void onBindViewHolder(KeyWordStatusViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class KeyWordStatusViewHolder extends RecyclerView.ViewHolder {


        public KeyWordStatusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
