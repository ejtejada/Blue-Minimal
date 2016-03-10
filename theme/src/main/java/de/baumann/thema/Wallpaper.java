/*
 * Copyright (C) 2013 XenonHD
 *
 * Licensed under the GNU GPLv2 license
 *
 * The text of the license can be found in the LICENSE file
 * or at https://www.gnu.org/licenses/gpl-2.0.txt
 */

package de.baumann.thema;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

@SuppressLint("ParserError")
public class Wallpaper extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    /**
     * Menu item used for "Apply" button on actionbar
     */
    private static final int MENU_APPLY = Menu.FIRST;
    
    /**
     * The {@link Integer} that stores current fragment selected
     */
    private int mCurrentFragment;
    
    /**
     * The {@link ArrayList} that will host the wallpapers resource ID's
     */
    static ArrayList <Integer> sWallpapers = new ArrayList<Integer>();
    
    /**
     * The {@link String[]} that will store wallpaper name
     */
    String[] mWallpaperInfo;
    
    /**
     * The {@link Context} to be used by the app
     */
    static Context mContext;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener(){
            public void onPageSelected(int position) {
                mCurrentFragment = position;
            }
        });
        
        sWallpapers.clear();

        final Resources resources = getResources();
        final String packageName = getApplication().getPackageName();

        fetchWallpapers(resources, packageName, R.array.wallpapers);
	    mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new WallpaperFragment();
            Bundle args = new Bundle();
            args.putInt(WallpaperFragment.ARG_SECTION_NUMBER, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return sWallpapers.size();
        }

    }

    public static class WallpaperFragment extends Fragment {
        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mContext = getActivity();
            Bundle args = getArguments();
            LinearLayout holder = new LinearLayout(mContext);
            holder.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            ImageView img = new ImageView(mContext);
            img.setLayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setImageResource(sWallpapers.get(args.getInt(ARG_SECTION_NUMBER)));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.addView(img);
            return holder;
        }
    }

    private void fetchWallpapers(Resources resources, String packageName, int list) {
        final String[] extras = resources.getStringArray(list);
        for (String extra : extras) {
            int res = resources.getIdentifier(extra, "drawable", packageName);
            if (res != 0) {
                sWallpapers.add(res);
            }
        }
    }

    class WallpaperLoader extends AsyncTask<Integer, Void, Boolean> {
        BitmapFactory.Options mOptions;
        ProgressDialog mDialog;

        WallpaperLoader() {
            mOptions = new BitmapFactory.Options();
            mOptions.inDither = false;
            mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        }
        
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                Bitmap b = BitmapFactory.decodeResource(getResources(),
                        sWallpapers.get(params[0]), mOptions);

                Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                int width = d.getWidth();
                int height = d.getHeight();

                final WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                wallpaperManager.setWallpaperOffsetSteps(1, 1);
                wallpaperManager.suggestDesiredDimensions(width, height);

                try {
                    wallpaperManager.setBitmap(b);
                } catch (IOException e) {
                    // If we crash, we will probably have a null bitmap
                    // return before recycling to avoid exception
                    throw new NullPointerException();
                }

                // Help GC
                b.recycle();

                return true;
            } catch (OutOfMemoryError e) {
                return false;
            } catch(NullPointerException e){
                return false;
            }
        }
        
        @Override
        protected void onPostExecute(Boolean success) {
            mDialog.dismiss();
        }
        
        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(mContext, null, getString(R.string.applying));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.wallpaper_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            moveTaskToBack(true);
            return false;
        }

        if (id == R.id.license) {
            new AlertDialog.Builder(Wallpaper.this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_text))
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/scoute-dich/Baumann_Thema"));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton(getString(R.string.about_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
        }

        if (id == R.id.action_apply) {
            {
                new WallpaperLoader().execute(mCurrentFragment);
            }
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
