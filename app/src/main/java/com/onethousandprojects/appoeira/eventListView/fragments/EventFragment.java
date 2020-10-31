package com.onethousandprojects.appoeira.eventListView.fragments;

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
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventListView.adapter.MyEventListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventListView.content.EventContent;
import com.onethousandprojects.appoeira.serverStuff.eventList.ServerLocationEventResponse;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    RecyclerView recyclerView;
    MyEventListRecyclerViewAdapter adapterEvent;
    List<EventContent> EventList;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public EventFragment() {
    }
    public static EventFragment newInstance(int columnCount) {
        EventFragment fragment = new EventFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_item_list, container, false);
        EventListActivity activity = (EventListActivity) getActivity();
        assert activity != null;
        List<ServerLocationEventResponse> myResponseFromActivity = activity.eventListServer.getServerLocationEventResponse();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            EventList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                EventList.add(new EventContent(myResponseFromActivity.get(i).getId(),
                                               String.valueOf(myResponseFromActivity.get(i).getName()),
                                               String.valueOf(myResponseFromActivity.get(i).getDate()),
                                               String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerApelhido()),
                                               String.valueOf(myResponseFromActivity.get(i).getOwnerRank()),
                                               myResponseFromActivity.get(i).getVerified(),
                                               myResponseFromActivity.get(i).getLatitude(),
                                               myResponseFromActivity.get(i).getLongitude(),
                                               myResponseFromActivity.get(i).getDistance(),
                                               myResponseFromActivity.get(i).getPlatform(),
                                               myResponseFromActivity.get(i).getKey(),
                                               myResponseFromActivity.get(i).getRating()));
            }
            adapterEvent = new MyEventListRecyclerViewAdapter(getActivity(), EventList, (EventListActivity) getActivity());
            recyclerView.setAdapter(adapterEvent);
        }
        return view;
    }
}