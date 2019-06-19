package com.ssynhtn.viewpagertest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by huangtongnao on 2019/6/19.
 * Email: huangtongnao@gmail.com
 */
class ViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.text);
    }
}
