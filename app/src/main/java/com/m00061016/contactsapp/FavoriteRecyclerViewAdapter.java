package com.m00061016.contactsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Contact> lstContact;
    public FavoriteRecyclerViewAdapter.onItemClickListener onItemClick;

    public interface onItemClickListener {
        void itemClick(FavoriteRecyclerViewAdapter fav);

    }

    public FavoriteRecyclerViewAdapter(Context m, List<Contact> mlist) {
        lstContact = mlist;
        context = m;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(this.context).inflate(R.layout.item_contact, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        position = getCurrentPosition(position);
        final Contact contact = MainActivity.contactList.get(position);
        final FavoriteRecyclerViewAdapter f = this;

        holder.tv_name.setText(contact.getName());
        holder.tv_phone.setText(contact.getPhone());



    }

    @Override
    public int getItemCount() {
        int counter=0;
        for(int i=0; i<MainActivity.contactList.size();i++){
            if(MainActivity.contactList.get(i).getFavstatus() == true){
                counter++;
            }
        }
        return counter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item_contact;
        private TextView tv_name, tv_phone;
        private ImageView img;
        private Button share;
        private ImageButton fav;
        private boolean status;

        public MyViewHolder(View itemView) {
            super(itemView);

            fav = (ImageButton) itemView.findViewById(R.id.btn_fav);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_contact);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
        }
    }

    private int getCurrentPosition (int top){
        int aux=0, count =0;

        for(int i=0; i<MainActivity.contactList.size();i++){
            if(MainActivity.contactList.get(i).getFavstatus()){
                count++;
            }
            if(count <= top){
                aux++;
            }else{
                break;
            }

        }
        return aux;
    }


}
