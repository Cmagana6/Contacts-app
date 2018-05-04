package com.m00061016.contactsapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Contact> mData;
    Dialog myDialog;
    String phone_n,name;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //Inicializando el cuadro de dialogo

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_contact);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                final TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.dialog_img_id);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
                dialog_contact_img.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());

                name = mData.get(vHolder.getAdapterPosition()).getName();
                phone_n = mData.get(vHolder.getAdapterPosition()).getPhone();
                Toast.makeText(mContext, String.valueOf(mData.get(vHolder.getAdapterPosition()).getName()), Toast.LENGTH_SHORT).show();
                myDialog.show();

            }
        });

        //Share desde el cuadro de dialogo
        myDialog.findViewById(R.id.dialog_share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT, "Name: "+name+"\nPhone: "+phone_n);
                v.getContext().startActivity(i.createChooser(i,"Share with..."));
                myDialog.dismiss();
            }
        });

        //Lanzando la llamada desde el cuadro de dialogo
        myDialog.findViewById(R.id.contact_call_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" +phone_n));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                v.getContext().startActivity(call);
                myDialog.dismiss();
            }
        });



        return vHolder;
    }

    //Lanzando la llamada desde el RecyclewView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.img.setImageResource(mData.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item_contact;
        private TextView tv_name, tv_phone;
        private ImageView img;
        private Button share;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_contact);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
        }
    }

}
