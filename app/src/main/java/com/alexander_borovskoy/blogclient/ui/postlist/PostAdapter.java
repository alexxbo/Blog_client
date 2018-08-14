package com.alexander_borovskoy.blogclient.ui.postlist;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alexander_borovskoy.blogclient.R;
import com.alexander_borovskoy.blogclient.databinding.ItemPostBinding;
import com.alexander_borovskoy.blogclient.data.Post;

import java.util.List;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPostList;

    @Nullable
    private final PostClickCallback mPostClickCallback;

    public PostAdapter(@Nullable PostClickCallback mPostClickCallback) {
        this.mPostClickCallback = mPostClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_post, parent, false);
        binding.setCallback(mPostClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setPost(mPostList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemPostBinding binding;

        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setPostList(final List<Post> postList) {
        if (mPostList == null) {
            mPostList = postList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPostList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPostList.get(oldItemPosition).getId() ==
                            postList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = postList.get(newItemPosition);
                    Post oldPost = mPostList.get(oldItemPosition);
                    return newPost.getId() == oldPost.getId()
                            && Objects.equals(newPost.getTitle(), oldPost.getTitle())
                            && Objects.equals(newPost.getText(), oldPost.getText());
                }
            });
            mPostList = postList;
            result.dispatchUpdatesTo(this);
        }
    }
}
