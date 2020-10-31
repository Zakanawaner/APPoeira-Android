package com.onethousandprojects.appoeira.searchView.fragment;

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
import com.onethousandprojects.appoeira.searchView.SearchActivity;
import com.onethousandprojects.appoeira.searchView.adapters.MySearchRecyclerViewAdapter;
import com.onethousandprojects.appoeira.searchView.content.SearchContent;
import com.onethousandprojects.appoeira.serverStuff.search.ServerSearchResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    MySearchRecyclerViewAdapter adapterGroupDetail;
    List<SearchContent> searchContents;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public SearchFragment() {
    }

    @SuppressWarnings("unused")
    public static SearchFragment newInstance(int columnCount) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_item_list, container, false);
        SearchActivity activity = (SearchActivity) getActivity();
        assert activity != null;
        ServerSearchResponse myResponseFromActivity = activity.getSearchResponse();
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
            searchContents = new ArrayList<>();
            for (int i = 0; i < myResponseFromActivity.getUserResponses().size(); i++) {
                searchContents.add(new SearchContent(1,
                        myResponseFromActivity.getUserResponses().get(i).getId(),
                        String.valueOf(myResponseFromActivity.getUserResponses().get(i).getApelhido()),
                        String.valueOf(myResponseFromActivity.getUserResponses().get(i).getPicUrl()),
                        myResponseFromActivity.getUserResponses().get(i).isPremium(),
                        String.valueOf(myResponseFromActivity.getUserResponses().get(i).getRank()),
                        null, null, null));
            }
            for (int i = 0; i < myResponseFromActivity.getGroupResponses().size(); i++) {
                searchContents.add(new SearchContent(2,
                        myResponseFromActivity.getGroupResponses().get(i).getId(),
                        String.valueOf(myResponseFromActivity.getGroupResponses().get(i).getName()),
                        String.valueOf(myResponseFromActivity.getGroupResponses().get(i).getPicUrl()),
                        myResponseFromActivity.getGroupResponses().get(i).isVerified(),
                        null,
                        myResponseFromActivity.getGroupResponses().get(i).getRating(),
                        null, null));
            }
            for (int i = 0; i < myResponseFromActivity.getRodaResponses().size(); i++) {
                searchContents.add(new SearchContent(3,
                        myResponseFromActivity.getRodaResponses().get(i).getId(),
                        String.valueOf(myResponseFromActivity.getRodaResponses().get(i).getName()),
                        String.valueOf(myResponseFromActivity.getRodaResponses().get(i).getPicUrl()),
                        myResponseFromActivity.getRodaResponses().get(i).isVerified(),
                        null, null,
                        String.valueOf(myResponseFromActivity.getRodaResponses().get(i).getOwnerRank()),
                        String.valueOf(myResponseFromActivity.getRodaResponses().get(i).getOwner())));
            }
            for (int i = 0; i < myResponseFromActivity.getEventResponses().size(); i++) {
                searchContents.add(new SearchContent(4,
                        myResponseFromActivity.getEventResponses().get(i).getId(),
                        String.valueOf(myResponseFromActivity.getEventResponses().get(i).getName()),
                        String.valueOf(myResponseFromActivity.getEventResponses().get(i).getPicUrl()),
                        myResponseFromActivity.getEventResponses().get(i).isVerified(),
                        null, null,
                        String.valueOf(myResponseFromActivity.getEventResponses().get(i).getOwnerRank()),
                        String.valueOf(myResponseFromActivity.getEventResponses().get(i).getOwner())));
            }
            for (int i = 0; i < myResponseFromActivity.getOnlineResponses().size(); i++) {
                searchContents.add(new SearchContent(5,
                        myResponseFromActivity.getOnlineResponses().get(i).getId(),
                        String.valueOf(myResponseFromActivity.getOnlineResponses().get(i).getName()),
                        String.valueOf(myResponseFromActivity.getOnlineResponses().get(i).getPicUrl()),
                        myResponseFromActivity.getOnlineResponses().get(i).isVerified(),
                        null, null,
                        String.valueOf(myResponseFromActivity.getOnlineResponses().get(i).getOwnerRank()),
                        String.valueOf(myResponseFromActivity.getOnlineResponses().get(i).getOwner())));
            }
            adapterGroupDetail = new MySearchRecyclerViewAdapter(getActivity(), searchContents, (SearchActivity) getActivity());
            recyclerView.setAdapter(adapterGroupDetail);
        }
        return view;
    }
}