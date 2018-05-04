package com.m00061016.contactsapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Agregando los fragmentos al adaptador

        adapter.AddFragment(new FragmentCall(),"");
        adapter.AddFragment(new FragmentContact(),"");
        adapter.AddFragment(new FragmentFav(),"");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_call);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);

        //Remover las sombras de la barra de accion

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);


    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton addFloating = (FloatingActionButton) findViewById(R.id.btn_add);

        addFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog add = new Dialog(viewPager.getContext());
                add.setContentView(R.layout.dialog_add_contact);
                add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                add.show();

                add.findViewById(R.id.dialog_btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.dismiss();
                    }
                });
            }
        });

    }
}
