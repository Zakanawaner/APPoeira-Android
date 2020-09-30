package com.onethousandprojects.appoeira.userDetailView.fragments;

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
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupDetailMoreView.content.GroupMembersContent;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ServerUserDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupsResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserGroupRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserGroupContent;

import java.util.ArrayList;
import java.util.List;

public class UserGroupListFragment extends Fragment {
    RecyclerView recyclerView;
    UserGroupRecyclerViewAdapter adapterUserDetail;
    List<UserGroupContent> GroupList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserGroupListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserGroupListFragment newInstance(int columnCount) {
        UserGroupListFragment fragment = new UserGroupListFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_detail_item_list, container, false);
        UserDetailActivity activity = (UserDetailActivity) getActivity();
        assert activity != null;
        List<ServerUserDetailGroupsResponse> myResponseFromActivity = activity.getMyResponse().getGroups();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Lista de miembros del grupo
            GroupList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                GroupList.add(new UserGroupContent(myResponseFromActivity.get(i).getGroupId(),
                              String.valueOf(myResponseFromActivity.get(i).getGroupName()),
                              String.valueOf(myResponseFromActivity.get(i).getGroupPicUrl()),
                              myResponseFromActivity.get(i).getGroupRole()));
                }
            adapterUserDetail = new UserGroupRecyclerViewAdapter(getActivity(), GroupList, (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}