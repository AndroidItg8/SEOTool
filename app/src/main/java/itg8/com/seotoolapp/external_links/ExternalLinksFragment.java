package itg8.com.seotoolapp.external_links;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itg8.com.seotoolapp.home.HomeActivity;
import itg8.com.seotoolapp.R;
import itg8.com.seotoolapp.traffic.controller.HomeController;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExternalLinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExternalLinksFragment extends Fragment implements HomeController.ExternalLinksFragmentListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ExternalLinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExternalLinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExternalLinksFragment newInstance(String param1, String param2) {
        ExternalLinksFragment fragment = new ExternalLinksFragment();
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
        return inflater.inflate(R.layout.fragment_external_links, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity)getActivity()).setExternalLinksFragmentListener(this);
    }

    @Override
    public void onExtLinkAvail(List t) {

    }

    @Override
    public void onDownloadFail() {

    }
}