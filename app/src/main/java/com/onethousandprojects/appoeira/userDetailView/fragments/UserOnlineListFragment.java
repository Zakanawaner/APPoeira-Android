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
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse.ServerUserDetailOnlinesResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserOnlineRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserOnlineContent;

import java.util.ArrayList;
import java.util.List;

public class UserOnlineListFragment extends Fragment {
    RecyclerView recyclerView;
    UserOnlineRecyclerViewAdapter adapterUserDetail;
    List<UserOnlineContent> OnlineList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserOnlineListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserOnlineListFragment newInstance(int columnCount) {
        UserOnlineListFragment fragment = new UserOnlineListFragment();
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
        List<ServerUserDetailOnlinesResponse> myResponseFromActivity = activity.getMyResponse().getOnlines();
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
            OnlineList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                OnlineList.add(new UserOnlineContent(myResponseFromActivity.get(i).getOnlineId(),
                              String.valueOf(myResponseFromActivity.get(i).getOnlineName()),
                              String.valueOf(myResponseFromActivity.get(i).getOnlinePicUrl()),
                              myResponseFromActivity.get(i).getOnlineRole()));
                }
            adapterUserDetail = new UserOnlineRecyclerViewAdapter(getActivity(), OnlineList, (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}