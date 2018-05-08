package com.m00061016.contactsapp;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentContact extends Fragment {

    View v;
    private Context context;
    public RecyclerView myrecyclerview;
    public static List<Contact> lstContact;
    private StringBuilder wbuilder,wbuilder2;
    private RecyclerViewContactInterface sendData;

    public FragmentContact() {

    }

    public interface RecyclerViewContactInterface{
        void updateFavAdapter();

    }


    /*Sobreescribiendo el metodo onCreateView del recyclerView
    *
    * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.contact_fragment,container,false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), MainActivity.contactList, new RecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewAdapter contact) {
                contact.notifyDataSetChanged();
                sendData.updateFavAdapter();
            }
        });
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);

        //Buscando un contacto
        EditText ets = (EditText) v.findViewById(R.id.et_search);
        ets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                List<Contact> filterList = new ArrayList<>();

                for(Contact item : lstContact){
                    if (item.getName().toLowerCase().contains(s.toString().toLowerCase())){
                        filterList.add(item);
                    }

                }

                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), filterList, new RecyclerViewAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerViewAdapter contact) {
                        contact.notifyDataSetChanged();
                        sendData.updateFavAdapter();
                    }
                });
                myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                myrecyclerview.setAdapter(recyclerViewAdapter);
            }
        });

        return v;
    }

    /*Creando el fragmento y cargando los contactos
    *
    * */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       Context mContext;

            mContext = (Context) getContext();


            lstContact = new ArrayList<>();
            //Para que el metodo loadContacts funcione deberemos otorgarle el permiso de acceder a los contactos a la aplicacion
            loadContacts();


    }



    @Override
    public void onResume() {
        super.onResume();
        dialogAdd();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sendData = (RecyclerViewContactInterface) getActivity();
        }catch (Exception e){
            Log.e("Error" , e.getMessage().toString());

        }
    }

    /* Metodo para cargar los contactos*/

    public void loadContacts(){
        MainActivity.contactList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();

        ContentResolver contentResolver = getActivity().getContentResolver();
        String sortOrder = ContactsContract.Data.DISPLAY_NAME+" ASC";
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id}, null);

                    while (cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builder.append(name).append(".");
                        builder2.append(phoneNumber).append(".");
                    }
                    cursor2.close();
                }

            }
        }
        cursor.close();

        wbuilder= builder;
        wbuilder2=builder2;


        String names = wbuilder.toString();
        String phones = wbuilder2.toString();

        String[] allNames = names.split("\\.");
        String[] allPhones = phones.split("\\.");

        for(int i=0; i<allNames.length;i++){
            lstContact.add(new Contact(allNames[i],allPhones[i],R.drawable.contact_icon));
            MainActivity.contactList.add(new Contact(allNames[i],allPhones[i],R.drawable.contact_icon));
        }

    }


    /*Metodo que agrega un contacto al arraylist que se carga al iniciar la aplicacion la cual
    contiene el listado de la agenda
    */
    public void addContact(String name, String phone){
        lstContact.add(0,new Contact(name,phone,R.drawable.contact_icon));

    }

    /*Funcion para agregar un contacto desde la aplicacion no retorna nada
        Abre una ventana que contiene un dialogo que nos permite agregar un contacto
     */

    public void dialogAdd(){
        FloatingActionButton btnadd = (FloatingActionButton) getActivity().findViewById(R.id.btn_add);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog add_dialog = new Dialog(v.getContext());
                add_dialog.setContentView(R.layout.dialog_add_contact);
                add_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                add_dialog.show();

                add_dialog.findViewById(R.id.dialog_btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText name = add_dialog.findViewById(R.id.dialog_new_name_id);
                        EditText number = add_dialog.findViewById(R.id.dialog_new_phone_id);

                        String set_name = name.getText().toString();
                        String set_number = number.getText().toString();


                        lstContact.add(new Contact(set_name,set_number,R.drawable.contact_icon));
                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstContact, new RecyclerViewAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(RecyclerViewAdapter contact) {
                                contact.notifyDataSetChanged();
                            }
                        });
                        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        myrecyclerview.setAdapter(recyclerViewAdapter);


                        add_dialog.dismiss();
                    }
                });

            }
        });

    }


}
