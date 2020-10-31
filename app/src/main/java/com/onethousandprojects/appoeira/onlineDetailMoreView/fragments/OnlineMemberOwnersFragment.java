package com.onethousandprojects.appoeira.onlineDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.onlineDetailMoreView.OnlineDetailMoreActivity;
import com.onethousandprojects.appoeira.onlineDetailMoreView.adapter.MyOnlineMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineDetailMoreView.content.OnlineMembersContent;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ServerOnlineDetailMoreResponse;

import java.util.ArrayList;
import java.util.List;

public class OnlineMemberOwnersFragment extends Fragment {
    RecyclerView recyclerView;
    MyOnlineMembersRecyclerViewAdapter adapterOnlineDetail;
    List<OnlineMembersContent> MemberList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public OnlineMemberOwnersFragment() {
    }

    @SuppressWarnings("unused")
    public static OnlineMemberOwnersFragment newInstance(int columnCount) {
        OnlineMemberOwnersFragment fragment = new OnlineMemberOwnersFragment();
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
        OnlineDetailMoreActivity activity = (OnlineDetailMoreActivity) getActivity();
        assert activity != null;
        List<ServerOnlineDetailMoreResponse> myResponseFromActivity = activity.onlineDetailMoreServer.getMyResponse();
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
                if (String.valueOf(myResponseFromActivity.get(i).getUserGroupRole()).equals("organizer")) {
                    MemberList.add(new OnlineMembersContent(String.valueOf(myResponseFromActivity.get(i).getOnlineSchool()),
                            myResponseFromActivity.get(i).getUserId(),
                            String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                            String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                            myResponseFromActivity.get(i).isUserPremium(),
                            String.valueOf(myResponseFromActivity.get(i).getUserRank()),
                            String.valueOf(myResponseFromActivity.get(i).getUserGroupRole())));
                }
            }
            adapterOnlineDetail = new MyOnlineMembersRecyclerViewAdapter(getActivity(), MemberList, (OnlineDetailMoreActivity) getActivity());
            recyclerView.setAdapter(adapterOnlineDetail);
        }
        return view;
    }
}