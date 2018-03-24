package itg8.com.seotoolapp.keyword;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itg8.com.seotoolapp.R;

/**
 * Created by Android itg 8 on 3/24/2018.
 */

public class KeyWordStatusAdapter extends RecyclerView.Adapter<KeyWordStatusAdapter.KeyWordStatusViewHolder> {
    public KeyWordStatusAdapter(FragmentActivity activity) {

    }

    @Override
    public KeyWordStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_keystatus, parent, false);
        return new KeyWordStatusViewHolder(view) ;












    }

    @Override
    public void onBindViewHolder(KeyWordStatusViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class KeyWordStatusViewHolder extends RecyclerView.ViewHolder {
        public KeyWordStatusViewHolder(View itemView) {
            super(itemView);
        }
    }
}
