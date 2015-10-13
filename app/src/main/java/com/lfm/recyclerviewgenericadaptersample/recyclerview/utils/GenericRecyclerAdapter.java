package com.lfm.recyclerviewgenericadaptersample.recyclerview.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.lfm.recyclerviewgenericadaptersample.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mogwai on 13/10/15.
 */
public class GenericRecyclerAdapter<E> extends RecyclerView.Adapter<ViewPresenterHolder<E>> {

    private View.OnClickListener onClickListener;
    private List<E> items;
    private Class<? extends ViewPresenter<E>> classPresenter;
    private Context context;
    private Bundle params;

    public GenericRecyclerAdapter(Context context, Collection<E> items, Class<? extends ViewPresenter<E>> classPresenter){
        this(context,items,classPresenter, null, null);
    }

    public GenericRecyclerAdapter(Context context, Collection<E> items, Class<? extends ViewPresenter<E>> classPresenter, View.OnClickListener onClickListener){
        this(context,items,classPresenter, onClickListener, null);
    }
    public GenericRecyclerAdapter(Context context, Collection<E> items, Class<? extends ViewPresenter<E>> classPresenter, View.OnClickListener onClickListener,  Bundle params) {
        this.items = new ArrayList<>(items);
        this.classPresenter = classPresenter;
        this.context = context;
        this.params = params;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewPresenterHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {
            return newPresenter(parent);
    }

    @Override
    public void onBindViewHolder(ViewPresenterHolder<E> holder, int position) {
        holder.swapData(items.get(position));
        holder.setTag(R.id.tag_content, items.get(position));
        holder.setTag(R.id.tag_position, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ViewPresenterHolder<E> newPresenter(ViewGroup parent) {
        try {
            ViewPresenter<E> viewPresenter = classPresenter.newInstance();
            viewPresenter.initViewPresenter(context, parent, params);
            viewPresenter.setOnClickListener(onClickListener);
            return new ViewPresenterHolder<>(viewPresenter);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        notifyDataSetChanged();
    }

    public void setItems(Collection<E> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }
}