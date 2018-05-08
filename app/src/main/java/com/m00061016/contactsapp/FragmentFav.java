package com.m00061016.contactsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentFav extends Fragment {

    public RecyclerView myrecyclerview;
    View v;

    public FragmentFav() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.contact_fragment,container,false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.contact_recyclerview);
        FavoriteRecyclerViewAdapter recyclerAdapter = new FavoriteRecyclerViewAdapter(getContext(),MainActivity.contactList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }

    public void updateData(){
        FavoriteRecyclerViewAdapter recyclerAdapter = new FavoriteRecyclerViewAdapter(getContext(),MainActivity.contactList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
