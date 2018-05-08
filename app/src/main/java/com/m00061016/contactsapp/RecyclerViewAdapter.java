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
import android.widget.EditText;
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
    String phone_n, name;
    int position;
    public RecyclerViewAdapter.onItemClickListener onItemClick;

    /*Definiendo el constructor para el adaptador del RecyclewView
    *
    * */

    public interface onItemClickListener{
        void onItemClick(RecyclerViewAdapter contact);

    }

    public RecyclerViewAdapter(Context mContext, List<Contact> mData,onItemClickListener onItemClick) {
        this.mContext = mContext;
        this.mData = mData;
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        final RecyclerViewAdapter r= this;

        //Inicializando el cuadro de dialogo

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_contact);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //Mostrando la informacion de cada contacto

        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                final TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.dialog_img_id);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
                dialog_contact_img.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());

                position = vHolder.getAdapterPosition();

                name = mData.get(vHolder.getAdapterPosition()).getName();
                phone_n = mData.get(vHolder.getAdapterPosition()).getPhone();
                Toast.makeText(mContext, String.valueOf(mData.get(vHolder.getAdapterPosition()).getName()), Toast.LENGTH_SHORT).show();
                myDialog.show();

                //Editando un contacto

                myDialog.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog editD = new Dialog(mContext);
                        editD.setContentView(R.layout.dialog_edit_contact);
                        editD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        myDialog.dismiss();
                        editD.show();

                        editD.findViewById(R.id.editdialog_btn_save).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String e_name, e_phone;
                                EditText e_new_name,e_new_number;
                                e_new_name = (EditText) editD.findViewById(R.id.editdialog_new_name_id);
                                e_new_number = (EditText) editD.findViewById(R.id.editdialog_new_phone_id);

                                e_name = e_new_name.getText().toString();
                                e_phone = e_new_number.getText().toString();

                                TextView name_e,phone_e;
                                name_e = (TextView) vHolder.item_contact.findViewById(R.id.name_contact);
                                phone_e = (TextView) vHolder.item_contact.findViewById(R.id.phone_contact);

                                mData.get(vHolder.getAdapterPosition()).setName(e_name);
                                mData.get(vHolder.getAdapterPosition()).setPhone(e_phone);

                                name_e.setText(e_name);
                                phone_e.setText(e_phone);

                                Toast.makeText(mContext,("Contacto editado" ),Toast.LENGTH_SHORT).show();

                                editD.dismiss();

                            }
                        });
                    }
                });


            }
        });

        //Share desde el cuadro de dialogo
        myDialog.findViewById(R.id.dialog_share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT, "Name: " + name + "\nPhone: " + phone_n);
                v.getContext().startActivity(i.createChooser(i, "Share with..."));
                myDialog.dismiss();
            }
        });

        //Lanzando la llamada desde el cuadro de dialogo
        myDialog.findViewById(R.id.contact_call_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + phone_n));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                v.getContext().startActivity(call);
                myDialog.dismiss();
            }
        });

//Eliminando un contacto
        myDialog.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mData.remove(position);
                MainActivity.contactList = mData;
                FragmentContact.lstContact = mData;
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mData.size());

                Toast.makeText(mContext,("Contacto eliminado: "+name ),Toast.LENGTH_SHORT).show();

                myDialog.dismiss();
            }
        });

        //Enviando a Favoritos
        myDialog.findViewById(R.id.btn_fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.contactList.get(position).toggle();
                Toast.makeText(mContext,("Añadido a favoritos " ),Toast.LENGTH_SHORT).show();
                onItemClick.onItemClick(r);
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


    /*Contando la longitud del ArrayList
    *
    * */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /*Definiendo el ViewHolder
    *
    * */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item_contact;
        private TextView tv_name, tv_phone;
        private ImageView img;
        private Button share;
        private ImageButton fav;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_contact);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
            fav = (ImageButton) itemView.findViewById(R.id.btn_fav);
        }
    }

}
