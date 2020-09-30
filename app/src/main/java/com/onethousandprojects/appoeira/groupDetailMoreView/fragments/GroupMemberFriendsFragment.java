package com.onethousandprojects.appoeira.groupDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.groupDetailMoreView.content.GroupMembersContent;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberFriendsFragment extends Fragment {
    RecyclerView recyclerView;
    MyGroupMembersRecyclerViewAdapter adapterGroupDetail;
    List<GroupMembersContent> MemberList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public GroupMemberFriendsFragment() {
    }

    @SuppressWarnings("unused")
    public static GroupMemberFriendsFragment newInstance(int columnCount) {
        GroupMemberFriendsFragment fragment = new GroupMemberFriendsFragment();
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
        View view = inflater.inflate(R.layout.fragment_group_detail_item_list, container, false);
        GroupDetailMoreActivity activity = (GroupDetailMoreActivity) getActivity();
        List<ServerGroupDetailMoreResponse> myResponseFromActivity = activity.getMyResponse();
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
            MemberList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                if (String.valueOf(myResponseFromActivity.get(i).getUserGroupRole()).equals("friend")) {
                    MemberList.add(new GroupMembersContent(String.valueOf(myResponseFromActivity.get(i).getGroupSchool()),
                            myResponseFromActivity.get(i).getUserId(),
                            String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                            String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                            myResponseFromActivity.get(i).isUserPremium(),
                            String.valueOf(myResponseFromActivity.get(i).getUserRank()),
                            String.valueOf(myResponseFromActivity.get(i).getUserGroupRole())));
                }
            }
            adapterGroupDetail = new MyGroupMembersRecyclerViewAdapter(getActivity(), MemberList, (GroupDetailMoreActivity) getActivity());
            recyclerView.setAdapter(adapterGroupDetail);
        }
        return view;
    }
}