package com.onethousandprojects.appoeira.groupDetailMoreView.fragments;

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
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupDetailMoreView.content.GroupCommentsContent;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupCommentsResponse;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    RecyclerView recyclerView;
    MyGroupCommentsRecyclerViewAdapter adapterGroupDetail;
    List<GroupCommentsContent> CommentList;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public CommentFragment() {
    }

    @SuppressWarnings("unused")
    public static CommentFragment newInstance(int columnCount) {
        CommentFragment fragment = new CommentFragment();
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
        View view = inflater.inflate(R.layout.fragment_comments_group_item_list, container, false);
        GroupDetailMoreActivity activity = (GroupDetailMoreActivity) getActivity();
        assert activity != null;
        List<ServerGroupCommentsResponse> myResponseFromActivity = activity.getMyResponseComments();
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
            CommentList = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.size(); i++) {
                CommentList.add(new GroupCommentsContent(myResponseFromActivity.get(i).getUserId(),
                        String.valueOf(myResponseFromActivity.get(i).getPicUrl()),
                        String.valueOf(myResponseFromActivity.get(i).getUserApelhido()),
                        String.valueOf(myResponseFromActivity.get(i).getComment()),
                        String.valueOf(myResponseFromActivity.get(i).getDate())));
            }
            adapterGroupDetail = new MyGroupCommentsRecyclerViewAdapter(getActivity(), CommentList, (GroupDetailMoreActivity) getActivity());
            recyclerView.setAdapter(adapterGroupDetail);
        }
        return view;
    }
}