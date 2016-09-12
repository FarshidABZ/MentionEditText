package com.farshid.mentionableedittext.suggestion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farshid.mentionableedittext.R;

import java.util.ArrayList;

import com.farshid.mentionableedittext.view.OnRecyclerViewClickListener;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> inputList;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerAdapter() {
    }

    public void setInputAdapter(ArrayList<String> inputList) {
        this.inputList = inputList;
    }

    public void setOnClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public void removeAll()
    {
        if(inputList == null)
            return;

        inputList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_suggestion_list, parent, false);

        return new ViewHolder(view, onRecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(inputList.get(position));
    }

    @Override
    public int getItemCount() {
        if (inputList == null)
            return 0;
        return inputList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        OnRecyclerViewClickListener onRecyclerViewClickListener;

        public ViewHolder(View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvWord);

            this.onRecyclerViewClickListener = onRecyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewClickListener != null) {
                onRecyclerViewClickListener.onItemClickListener(inputList.get(getLayoutPosition()));
            }
        }
    }
}
