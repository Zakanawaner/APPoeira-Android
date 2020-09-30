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
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse.ServerUserDetailGroupsResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse.ServerUserDetailRodasResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserGroupRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserRodaRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserGroupContent;
import com.onethousandprojects.appoeira.userDetailView.content.UserRodaContent;

import java.util.ArrayList;
import java.util.List;

public class UserRodaListFragment extends Fragment {
    RecyclerView recyclerView;
    UserRodaRecyclerViewAdapter adapterUserDetail;
    List<UserRodaContent> RodaList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public UserRodaListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserRodaListFragment newInstance(int columnCount) {
        UserRodaListFragment fragment = new UserRodaListFragment();
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
        List<ServerUserDetailRodasResponse> myResponseFromActivity = activity.getMyResponse().getRodas();
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
            RodaList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                RodaList.add(new UserRodaContent(myResponseFromActivity.get(i).getRodaId(),
                              String.valueOf(myResponseFromActivity.get(i).getRodaName()),
                              String.valueOf(myResponseFromActivity.get(i).getRodaPicUrl()),
                              myResponseFromActivity.get(i).getRodaRole()));
                }
            adapterUserDetail = new UserRodaRecyclerViewAdapter(getActivity(), RodaList, (UserDetailActivity) getActivity());
            recyclerView.setAdapter(adapterUserDetail);
        }
        return view;
    }
}