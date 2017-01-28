package com.mentionedittext.suggestion;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.farshid.mentionableedittext.R;
import com.mentionedittext.model.MentionModel;
import com.mentionedittext.view.OnRecyclerViewClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MentionModel> inputList;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerAdapter() {
    }

    public void setInputAdapter(ArrayList<MentionModel> inputList) {
        this.inputList = inputList;
    }

    public void setOnClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public void removeAll() {
        if (inputList == null)
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
        viewHolder.textView.setText(inputList.get(position).getName());
        if (inputList.get(position).getAvatarUrl() != null) {
            Glide.with(viewHolder.itemView.getContext())
                    .load(inputList.get(position).getAvatarUrl())
                    .into(viewHolder.imgAvatar);
        }
    }

    @Override
    public int getItemCount() {
        if (inputList == null)
            return 0;
        return inputList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        CircleImageView imgAvatar;

        OnRecyclerViewClickListener onRecyclerViewClickListener;

        public ViewHolder(View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSuggestionWord);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);

            this.onRecyclerViewClickListener = onRecyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewClickListener != null) {
                onRecyclerViewClickListener.onItemClickListener(inputList.get(getLayoutPosition()).getName());
            }
        }
    }
}
