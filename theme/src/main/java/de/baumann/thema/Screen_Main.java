package de.baumann.thema;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class Screen_Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton floatingActionButton_wp = (FloatingActionButton) findViewById(R.id.fab_rq);
        assert floatingActionButton_wp != null;
        floatingActionButton_wp.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentWallpaper(), String.valueOf(getString(R.string.title_wallpaper)));
        adapter.addFragment(new FragmentRequest(), String.valueOf(getString(R.string.title_iconrequest)));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    FloatingActionButton floatingActionButton_wp = (FloatingActionButton) findViewById(R.id.fab_wp);
                    assert floatingActionButton_wp != null;
                    floatingActionButton_wp.setVisibility(View.VISIBLE);
                    FloatingActionButton floatingActionButton_rq = (FloatingActionButton) findViewById(R.id.fab_rq);
                    assert floatingActionButton_rq != null;
                    floatingActionButton_rq.setVisibility(View.GONE);
                } else {
                    FloatingActionButton floatingActionButton_wp = (FloatingActionButton) findViewById(R.id.fab_wp);
                    assert floatingActionButton_wp != null;
                    floatingActionButton_wp.setVisibility(View.GONE);
                    FloatingActionButton floatingActionButton_rq = (FloatingActionButton) findViewById(R.id.fab_rq);
                    assert floatingActionButton_rq != null;
                    floatingActionButton_rq.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            return mFragmentTitleList.get(position);// add return null; to display only icons
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.license) {
            new AlertDialog.Builder(Screen_Main.this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_text))
                    .setPositiveButton(getString(R.string.about_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/scoute-dich/Baumann_Thema"));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            }).show();
        }

        return super.onOptionsItemSelected(item);
    }

}