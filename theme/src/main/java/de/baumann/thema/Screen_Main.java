package de.baumann.thema;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Screen_Main extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

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

        FloatingActionButton floatingActionButton_wp = (FloatingActionButton) findViewById(R.id.fab_wp);
        assert floatingActionButton_wp != null;
        floatingActionButton_wp.setVisibility(View.GONE);

        FloatingActionButton floatingActionButton_rq = (FloatingActionButton) findViewById(R.id.fab_rq);
        assert floatingActionButton_rq != null;
        floatingActionButton_rq.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int hasWRITE_EXTERNAL_STORAGE = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(Screen_Main.this)
                            .setMessage(R.string.permissions)
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (android.os.Build.VERSION.SDK_INT >= 23)
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            })
                            .setNegativeButton(getString(R.string.no), null)
                            .show();
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        File directory = new File(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.isnt_it);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/isnt_it.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.jingle_bells_sms);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/jingle_bells_sms.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.hymne);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/hymne.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.epic);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/epic.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.canon);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/canon.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        try {

            InputStream in;
            OutputStream out;
            in = getResources().openRawResource(R.raw.wet);
            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/wet.mp3");

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentSound(), String.valueOf(getString(R.string.sound)));
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
                    floatingActionButton_wp.setVisibility(View.GONE);
                    FloatingActionButton floatingActionButton_rq = (FloatingActionButton) findViewById(R.id.fab_rq);
                    assert floatingActionButton_rq != null;
                    floatingActionButton_rq.setVisibility(View.GONE);
                } else if (position == 1) {
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

            SpannableString s;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                s = new SpannableString(Html.fromHtml(getString(R.string.about_text),Html.FROM_HTML_MODE_LEGACY));
            } else {
                //noinspection deprecation
                s = new SpannableString(Html.fromHtml(getString(R.string.about_text)));
            }
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(Screen_Main.this)
                    .setTitle(R.string.about_title)
                    .setMessage(s)
                    .setPositiveButton(getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .show();
            d.show();
            ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (id == R.id.donate) {
            Uri uri = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NP6TGYDYP9SHY"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}