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
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserActivityRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserActivityContent;

import java.util.ArrayList;
import java.util.List;

public class UserActivityListFragment extends Fragment {
    RecyclerView recyclerView;
    UserActivityRecyclerViewAdapter adapterUserDetail;
    List<UserActivityContent> ActivityList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserActivityListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserActivityListFragment newInstance(int columnCount) {
        UserActivityListFragment fragment = new UserActivityListFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_activity_item_list, container, false);
        UserDetailActivity activity = (UserDetailActivity) getActivity();
        assert activity != null;
        List<UserActivityContent> activityContents = activity.getActivity();
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
            ActivityList = new ArrayList<>();
            for (int i = 0; i < activityContents.size(); i++) {
                ActivityList.add(new UserActivityContent(activityContents.get(i).getId(),
                        String.valueOf(activityContents.get(i).getName()),
                        String.valueOf(activityContents.get(i).getPicUrl()),
                        String.valueOf(activityContents.get(i).getDate()),
                        String.valueOf(activityContents.get(i).getDescription()),
                        activityContents.get(i).getType()));
            }
            adapterUserDetail = new UserActivityRecyclerViewAdapter(getActivity(), ActivityList, (UserDetailActivity) getActivity(), (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}