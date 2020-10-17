package com.onethousandprojects.appoeira.eventDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.eventDetailMoreView.adapter.MyEventMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventDetailMoreView.content.EventMembersContent;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ServerEventDetailMoreResponse;

import java.util.ArrayList;
import java.util.List;

public class EventMemberConvidedFragment extends Fragment {
    RecyclerView recyclerView;
    MyEventMembersRecyclerViewAdapter adapterEventDetail;
    List<EventMembersContent> MemberList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public EventMemberConvidedFragment() {
    }

    @SuppressWarnings("unused")
    public static EventMemberConvidedFragment newInstance(int columnCount) {
        EventMemberConvidedFragment fragment = new EventMemberConvidedFragment();
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
        EventDetailMoreActivity activity = (EventDetailMoreActivity) getActivity();
        assert activity != null;
        List<ServerEventDetailMoreResponse> myResponseFromActivity = activity.eventDetailMoreServer.getMyResponse();
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
                if (String.valueOf(myResponseFromActivity.get(i).getUserGroupRole()).equals("invited")) {
                    MemberList.add(new EventMembersContent(String.valueOf(myResponseFromActivity.get(i).getEventSchool()),
                            myResponseFromActivity.get(i).getUserId(),
                            String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                            String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                            myResponseFromActivity.get(i).isUserPremium(),
                            String.valueOf(myResponseFromActivity.get(i).getUserRank()),
                            String.valueOf(myResponseFromActivity.get(i).getUserGroupRole())));
                }
            }
            adapterEventDetail = new MyEventMembersRecyclerViewAdapter(getActivity(), MemberList, (EventDetailMoreActivity) getActivity());
            recyclerView.setAdapter(adapterEventDetail);
        }
        return view;
    }
}