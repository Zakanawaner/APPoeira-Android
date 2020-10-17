package com.onethousandprojects.appoeira.onlineListView.fragments;

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
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineListView.adapter.MyOnlineListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineListView.content.OnlineContent;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ServerLocationOnlineResponse;

import java.util.ArrayList;
import java.util.List;

public class OnlineFragment extends Fragment {

    RecyclerView recyclerView;
    MyOnlineListRecyclerViewAdapter adapterOnline;
    List<OnlineContent> OnlineList;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public OnlineFragment() {
    }
    public static OnlineFragment newInstance(int columnCount) {
        OnlineFragment fragment = new OnlineFragment();
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
        View view = inflater.inflate(R.layout.fragment_online_item_list, container, false);
        OnlineListActivity activity = (OnlineListActivity) getActivity();
        assert activity != null;
        List<ServerLocationOnlineResponse> myResponseFromActivity = activity.onlineListServer.getServerLocationOnlineResponse();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            OnlineList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                OnlineList.add(new OnlineContent(myResponseFromActivity.get(i).getId(),
                                               String.valueOf(myResponseFromActivity.get(i).getName()),
                                               String.valueOf(myResponseFromActivity.get(i).getDate()),
                                               String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerApelhido()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerRank()),
                                               myResponseFromActivity.get(i).getVerified(),
                                               myResponseFromActivity.get(i).getPlatform(),
                                               myResponseFromActivity.get(i).getKey(),
                                               myResponseFromActivity.get(i).getRating()));
            }
            adapterOnline = new MyOnlineListRecyclerViewAdapter(getActivity(), OnlineList, (OnlineListActivity) getActivity());
            recyclerView.setAdapter(adapterOnline);
        }
        return view;
    }
}