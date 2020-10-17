package com.onethousandprojects.appoeira.groupListView.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupListView.content.GroupContent;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.groupListView.adapter.MyGroupListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupFragment extends Fragment {

    RecyclerView recyclerView;
    MyGroupListRecyclerViewAdapter adapterGroup;
    List<GroupContent> GroupList;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public GroupFragment() {
    }
    public static GroupFragment newInstance(int columnCount) {
        GroupFragment fragment = new GroupFragment();
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
        View view = inflater.inflate(R.layout.fragment_group_item_list, container, false);
        GroupListActivity activity = (GroupListActivity) getActivity();
        assert activity != null;
        List<ServerLocationGroupResponse> myResponseFromActivity = activity.groupListServer.getServerLocationGroupResponse();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            GroupList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                GroupList.add(new GroupContent(myResponseFromActivity.get(i).getId(),
                                               String.valueOf(myResponseFromActivity.get(i).getName()),
                                               String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                                               String.valueOf(myResponseFromActivity.get(i).getPhone()),
                                               myResponseFromActivity.get(i).getVerified(),
                                               myResponseFromActivity.get(i).getRating(),
                                               myResponseFromActivity.get(i).getVotes(),
                                               myResponseFromActivity.get(i).getDistance()));
            }
            //Collections.sort(GroupList);
            adapterGroup = new MyGroupListRecyclerViewAdapter(getActivity(), GroupList, (GroupListActivity) getActivity());
            recyclerView.setAdapter(adapterGroup);
        }
        return view;
    }
}