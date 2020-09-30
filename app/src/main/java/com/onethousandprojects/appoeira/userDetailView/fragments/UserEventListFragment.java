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
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse.ServerUserDetailEventsResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupsResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserEventRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserGroupRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserEventContent;
import com.onethousandprojects.appoeira.userDetailView.content.UserGroupContent;

import java.util.ArrayList;
import java.util.List;

public class UserEventListFragment extends Fragment {
    RecyclerView recyclerView;
    UserEventRecyclerViewAdapter adapterUserDetail;
    List<UserEventContent> EventList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserEventListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserEventListFragment newInstance(int columnCount) {
        UserEventListFragment fragment = new UserEventListFragment();
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
        List<ServerUserDetailEventsResponse> myResponseFromActivity = activity.getMyResponse().getEvents();
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
            EventList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                EventList.add(new UserEventContent(myResponseFromActivity.get(i).getEventId(),
                              String.valueOf(myResponseFromActivity.get(i).getEventName()),
                              String.valueOf(myResponseFromActivity.get(i).getEventPicUrl()),
                              myResponseFromActivity.get(i).getEventRole()));
                }
            adapterUserDetail = new UserEventRecyclerViewAdapter(getActivity(), EventList, (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}