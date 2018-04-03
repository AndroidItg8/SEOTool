package itg8.com.seotoolapp.traffic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.common.CommonMethod;
import itg8.com.seotoolapp.common.UtilSnackbar;
import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.traffic.controller.HomeController;
import itg8.com.seotoolapp.traffic.model.TrafficModel;
import itg8.com.seotoolapp.traffic.model.Trafficcategorymaster;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrafficFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrafficFragment extends Fragment implements TrafficAdapter.OnItemClickedListener, HomeController.TrafficFragmentListener<TrafficModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    HashMap<Trafficcategorymaster, List<TrafficModel>> hashMapCategory = new HashMap<>();
    private static final String TAG = "TrafficFragment";
    @BindView(R.id.img_no)
    ImageView imgNo;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<TrafficModel> listTraffic;
    private boolean isViewCreated=false;
    private Context mContext;


    public TrafficFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrafficFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrafficFragment newInstance(String param1, String param2) {
        TrafficFragment fragment = new TrafficFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        unbinder = ButterKnife.bind(this, view);
        isViewCreated=true;
        setData();
        return view;
    }

    private void setData() {
        if(isViewCreated) {
            if (listTraffic != null && listTraffic.size() > 0) {
                CommonMethod.showHideView(recyclerView, rlNoItem);
                hashMapCategory.clear();
                generateCategoryList(listTraffic);
            } else
                CommonMethod.showHideView(rlNoItem, recyclerView);
            UtilSnackbar.showSnakbarTypeThree(recyclerView, new UtilSnackbar.OnSnackbarActionClickListener() {
                @Override
                public void onRetryClicked() {
                    ((HomeActivity) mContext).setTrafficFragmentDownloadListener();

                }
            });

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        mContext = context;
        ((HomeActivity) mContext).setTrafficFragmentListener(this);


    }

    private void init(HashMap<Trafficcategorymaster, List<TrafficModel>> hashMapCategory, List<Trafficcategorymaster> trafficcategorymasterList) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new TrafficAdapter(getActivity(), hashMapCategory, trafficcategorymasterList, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isViewCreated=false;
    }

    @Override
    public void onItemClicked(HashMap<Trafficcategorymaster, List<TrafficModel>> list) {


        ((HomeActivity) getActivity()).startTrafficDetail(list);

    }


    @Override
    public void onListOfCategoryAvailable(List<TrafficModel> list) {

//        init(list);
        listTraffic = list;
        generateCategoryList(list);
    }

    private void generateCategoryList(List<TrafficModel> list) {
        if (isViewCreated) {
            if (list.size() > 0) {
                CommonMethod.showHideView(recyclerView, rlNoItem);

                List<Trafficcategorymaster> trafficcategorymasterList = new ArrayList<>();
                for (TrafficModel model : list) {

                    if (!hashMapCategory.containsKey(model.getTrafficcategorymaster())) {
                        List<TrafficModel> trafficModelList = new ArrayList<>();
                        trafficModelList.add(model);

                        trafficcategorymasterList.add(model.getTrafficcategorymaster());
                        hashMapCategory.put(model.getTrafficcategorymaster(), trafficModelList);
                    } else {
                        hashMapCategory.get(model.getTrafficcategorymaster()).add(model);
                    }


                    Log.d(TAG, "generateCategoryList: " + hashMapCategory.toString());

                }

                init(hashMapCategory, trafficcategorymasterList);

            } else {
                CommonMethod.showHideView(recyclerView, rlNoItem);
            }
        }
    }

    @Override
    public void onListDownloadFail() {
        UtilSnackbar.showSnakbarTypeTwo(rlNoItem,getString(R.string.error));


    }
}
