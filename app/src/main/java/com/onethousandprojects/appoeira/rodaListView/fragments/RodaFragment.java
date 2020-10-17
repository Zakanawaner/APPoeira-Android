package com.onethousandprojects.appoeira.rodaListView.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaListView.adapter.MyRodaListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaListView.content.RodaContent;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ServerLocationRodaResponse;

import java.util.ArrayList;
import java.util.List;

public class RodaFragment extends Fragment {

    RecyclerView recyclerView;
    MyRodaListRecyclerViewAdapter adapterRoda;
    List<RodaContent> RodaList;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public RodaFragment() {
    }
    public static RodaFragment newInstance(int columnCount) {
        RodaFragment fragment = new RodaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roda_item_list, container, false);
        RodaListActivity activity = (RodaListActivity) getActivity();
        assert activity != null;
        List<ServerLocationRodaResponse> myResponseFromActivity = activity.rodaListServer.getServerLocationRodaResponse();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            RodaList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                RodaList.add(new RodaContent(myResponseFromActivity.get(i).getId(),
                                               String.valueOf(myResponseFromActivity.get(i).getName()),
                                               String.valueOf(myResponseFromActivity.get(i).getDate()),
                                               String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerApelhido()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerRank()),
                                               myResponseFromActivity.get(i).getVerified(),
                                               myResponseFromActivity.get(i).getLatitude(),
                                               myResponseFromActivity.get(i).getLongitude(),
                                               myResponseFromActivity.get(i).getDistance(),
                                               myResponseFromActivity.get(i).getRating()));
            }
            adapterRoda = new MyRodaListRecyclerViewAdapter(getActivity(), RodaList, (RodaListActivity) getActivity());
            recyclerView.setAdapter(adapterRoda);
        }
        return view;
    }
}