package com.ssynhtn.viewpagertest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by huangtongnao on 2019/6/19.
 * Email: huangtongnao@gmail.com
 */
public class SimpleListFragment extends Fragment {

    private static final String EXTRA_SIZE = "EXTRA_SIZE";
    public static final String TAG = SimpleListFragment.class.getSimpleName();

    public static SimpleListFragment newInstance(int size) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_SIZE, size);
        SimpleListFragment fragment = new SimpleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        Log.d(TAG, "nested " + recyclerView.isNestedScrollingEnabled());

        int size = getArguments().getInt(EXTRA_SIZE);
        Adapter adapter = new Adapter(size);
        recyclerView.setAdapter(adapter);

    }

}
