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
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailFollowResponse.ServerUserDetailFollowResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserFollowerRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserFollowersContent;

import java.util.ArrayList;
import java.util.List;

public class UserFollowedListFragment extends Fragment {
    RecyclerView recyclerView;
    UserFollowerRecyclerViewAdapter adapterUserDetail;
    List<UserFollowersContent> FollowList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserFollowedListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserFollowedListFragment newInstance(int columnCount) {
        UserFollowedListFragment fragment = new UserFollowedListFragment();
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
        List<ServerUserDetailFollowResponse> myResponseFromActivity = activity.getFollowed();
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
            FollowList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                FollowList.add(new UserFollowersContent(myResponseFromActivity.get(i).getUserId(),
                        String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                        String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                        String.valueOf(myResponseFromActivity.get(i).getUserDate())));
            }
            adapterUserDetail = new UserFollowerRecyclerViewAdapter(getActivity(), FollowList, (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}