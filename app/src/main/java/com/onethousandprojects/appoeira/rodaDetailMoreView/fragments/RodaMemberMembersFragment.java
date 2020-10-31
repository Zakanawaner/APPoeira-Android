package com.onethousandprojects.appoeira.rodaDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaDetailMoreView.adapter.MyRodaMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaDetailMoreView.content.RodaMembersContent;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ServerRodaDetailMoreResponse;

import java.util.ArrayList;
import java.util.List;

public class RodaMemberMembersFragment extends Fragment {
    RecyclerView recyclerView;
    MyRodaMembersRecyclerViewAdapter adapterRodaDetail;
    List<RodaMembersContent> MemberList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public RodaMemberMembersFragment() {
    }

    @SuppressWarnings("unused")
    public static RodaMemberMembersFragment newInstance(int columnCount) {
        RodaMemberMembersFragment fragment = new RodaMemberMembersFragment();
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
        RodaDetailMoreActivity activity = (RodaDetailMoreActivity) getActivity();
        assert activity != null;
        List<ServerRodaDetailMoreResponse> myResponseFromActivity = activity.rodaDetailMoreServer.getMyResponse();
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
                    MemberList.add(new RodaMembersContent(String.valueOf(myResponseFromActivity.get(i).getRodaSchool()),
                            myResponseFromActivity.get(i).getUserId(),
                            String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                            String.valueOf(myResponseFromActivity.get(i).getUserPicUrl()),
                            myResponseFromActivity.get(i).isUserPremium(),
                            String.valueOf(myResponseFromActivity.get(i).getUserRank()),
                            String.valueOf(myResponseFromActivity.get(i).getUserGroupRole())));
                }
            }
            adapterRodaDetail = new MyRodaMembersRecyclerViewAdapter(getActivity(), MemberList, (RodaDetailMoreActivity) getActivity());
            recyclerView.setAdapter(adapterRodaDetail);
        }
        return view;
    }
}