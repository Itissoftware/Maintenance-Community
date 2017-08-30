package com.mncomunity1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;


public class TabsOrderActivity extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String countVender;
    int countVenderInt;

    String countCustomer;
    int countVenderCustomer;


    LinearLayout cd_history;
    LinearLayout cd_vender;
    LinearLayout cd_vender_question;
    LinearLayout cd_question;

    TextView txt_statu_vender;
    TextView txt_statu_customer;

    String check_vebdor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_knowledge);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        check_vebdor = sp.getString("check_vebdor", "0");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ประวัติการซื้อ");
        setSupportActionBar(toolbar);
        txt_statu_vender = (TextView) findViewById(R.id.txt_statu_vender);
        txt_statu_customer = (TextView) findViewById(R.id.txt_statu_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        countVender = getIntent().getStringExtra("countVender");

        countCustomer = getIntent().getStringExtra("countCustomer");
        if(countCustomer != null){
            countVenderCustomer = Integer.parseInt(countCustomer);
        }else{
            countCustomer = "0";
            countVenderCustomer = Integer.parseInt(countCustomer);
        }


        if(countVender != null){
            countVenderInt = Integer.parseInt(countVender);
        }else{
            countVender = "0";
            countVenderInt = Integer.parseInt(countVender);
        }



        if (countVenderInt > 0) {
            txt_statu_vender.setText("มีผู้สอบถาม!");
            txt_statu_vender.setTextColor(Color.parseColor("#009688"));
        } else {
            txt_statu_vender.setVisibility(View.GONE);
        }

        if(countVenderCustomer > 0 ){
            txt_statu_customer.setText("ผู้ขายเสนอราคามาแล้ว!");
            txt_statu_customer.setTextColor(Color.parseColor("#009688"));
        }else{
            txt_statu_customer.setVisibility(View.GONE);
        }




        cd_history = (LinearLayout) findViewById(R.id.cd_history);
        cd_vender = (LinearLayout) findViewById(R.id.cd_vender);
        cd_vender_question = (LinearLayout) findViewById(R.id.cd_vender_question);
        cd_question = (LinearLayout) findViewById(R.id.cd_question);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        if (check_vebdor.equals("1")) {
            cd_vender.setVisibility(View.VISIBLE);
            cd_vender_question.setVisibility(View.VISIBLE);
            cd_question.setVisibility(View.VISIBLE);
        }else{
            cd_vender.setVisibility(View.GONE);
            cd_vender_question.setVisibility(View.GONE);
            cd_question.setVisibility(View.VISIBLE);
        }


        cd_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iVv = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(iVv);
            }
        });

        cd_vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iV = new Intent(getApplicationContext(), VendorOrderActivity.class);
                startActivity(iV);

            }
        });

        cd_vender_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iVv = new Intent(getApplicationContext(), BitItemActivity.class);
                startActivity(iVv);

            }
        });

        cd_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iVvv = new Intent(getApplicationContext(), ListQuestionActivity.class);
                startActivity(iVvv);

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderFragment(), "ประวัติการซื้อ");
        //  adapter.addFragment(new OrderHistoryFragment(), "ประวัติการสั่งซื้อ");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
