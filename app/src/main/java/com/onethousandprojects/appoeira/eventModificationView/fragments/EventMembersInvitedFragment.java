package com.onethousandprojects.appoeira.eventModificationView.fragments;

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
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.eventModificationView.adapters.MyEventModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.searchView.content.SearchContent;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ServerEventDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchUserResponse;

import java.util.ArrayList;
import java.util.List;

public class EventMembersInvitedFragment extends Fragment {
    RecyclerView recyclerView;
    MyEventModificationInviteMembersRecyclerViewAdapter adapterGroupDetail;
    List<SearchContent> MemberList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public EventMembersInvitedFragment() {
    }

    @SuppressWarnings("unused")
    public static EventMembersInvitedFragment newInstance(int columnCount) {
        EventMembersInvitedFragment fragment = new EventMembersInvitedFragment();
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
        EventModificationActivity activity = (EventModificationActivity) getActivity();
        assert activity != null;
        if (activity.fromEventDetail.getBoolean("modification") && !activity.flagFirstFragment) {
            activity.flagFirstFragment = true;
            List<ServerEventDetailMoreResponse> myResponseFromActivity = activity.eventModificationServer.getMyResponse();
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
                    if (String.valueOf(myResponseFromActivity.get(i).getUserGroupRole()).equals("participant")) {
                        MemberList.add(new SearchContent(1,
                                myResponseFromActivity.get(i).getUserId(),
                                String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                                String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                                false,
                                String.valueOf(myResponseFromActivity.get(i).getUserRank()),
                                null, null, null));
                    }
                }
                adapterGroupDetail = new MyEventModificationInviteMembersRecyclerViewAdapter(getActivity(), MemberList, (EventModificationActivity) getActivity());
                recyclerView.setAdapter(adapterGroupDetail);
            }
        } else {
            List<ServerSearchUserResponse> myResponseFromActivity = activity.eventModificationServer.getSearchResponse().getUserResponses();
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
                    MemberList.add(new SearchContent(1,
                            myResponseFromActivity.get(i).getId(),
                            String.valueOf(myResponseFromActivity.get(i).getApelhido()),
                            String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                            myResponseFromActivity.get(i).isPremium(),
                            String.valueOf(myResponseFromActivity.get(i).getRank()),
                            null, null, null));
                }
                adapterGroupDetail = new MyEventModificationInviteMembersRecyclerViewAdapter(getActivity(), MemberList, (EventModificationActivity) getActivity());
                recyclerView.setAdapter(adapterGroupDetail);
            }
        }
        return view;
    }
}