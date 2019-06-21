package com.ssynhtn.viewpagertest;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangtongnao on 2019/6/19.
 * Email: huangtongnao@gmail.com
 */
class Adapter extends RecyclerView.Adapter {

    private int size;
    private int count;

    public Adapter(int size) {
        this.size = Math.max(size, 0);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        count++;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        Log.d(SimpleListFragment.TAG, "creating " + count + "th view holder for a list of size " + size);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.textView.setText("Hello " + i);
        if (i % 2 == 0) {
            holder.textView.setTextColor(Color.WHITE);
            holder.textView.setBackgroundColor(Color.BLACK);
        } else {
            holder.textView.setTextColor(Color.BLACK);
            holder.textView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
