package de.baumann.thema;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.baumann.thema.helpers.ColorPicker;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentWallpaper extends Fragment {

    /**
     * The {@link Integer} that stores current fragment selected
     */
    private int mCurrentFragment;

    /**
     * The {@link ArrayList} that will host the wallpapers resource ID's
     */
    private static final ArrayList <Integer> sWallpapers = new ArrayList<>();

    /**
     * The {@link Context} to be used by the app
     */
    private static Context mContext;

    private ViewPager mViewPager;
    private int selectedColorRGB;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallpaper, container, false);

        setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_wp);
        fab.setImageResource(R.drawable.ic_check_white_48dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WallpaperLoader().execute(mCurrentFragment);
            }
        });

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
      sections. We use a {@link FragmentPagerAdapter} derivative, which will
      keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
      to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position) {
                mCurrentFragment = position;
            }
        });

        sWallpapers.clear();

        final Resources resources = getResources();
        final String packageName = getActivity().getApplication().getPackageName();

        fetchWallpapers(resources, packageName);
        mSectionsPagerAdapter.notifyDataSetChanged();

        return mViewPager;
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
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setImageResource(sWallpapers.get(args.getInt(ARG_SECTION_NUMBER)));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.addView(img);

            return holder;
        }
    }

    private void fetchWallpapers(Resources resources, String packageName) {
        final String[] extras = resources.getStringArray(R.array.wallpapers);
        for (String extra : extras) {
            int res = resources.getIdentifier(extra, "drawable", packageName);
            if (res != 0) {
                sWallpapers.add(res);
            }
        }
    }

    class WallpaperLoader extends AsyncTask<Integer, Void, Boolean> {
        final BitmapFactory.Options mOptions;

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

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);

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
        protected void onPreExecute() {
            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.color) {
            final CharSequence[] items = getResources().getStringArray(R.array.colors);
            final Context mContext = getActivity();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.pick_color));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    int color = Color.BLACK;
                    switch (item) {
                        case 0:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#b71c1c");
                            break;
                        case 1:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#1b5e20");
                            break;
                        case 2:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#0d47a1");
                            break;
                        case 3:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#4a148c");
                            break;
                        case 4:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#e65100");
                            break;
                        case 5:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#004d40");
                            break;
                        case 6:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.WHITE;
                            break;
                        case 7:
                            Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.BLACK;
                            break;
                        case 8:
                            final ColorPicker cp = new ColorPicker(getActivity());

                            /* Show color picker dialog */
                            cp.show();

                            FloatingActionButton fab = (FloatingActionButton) cp.findViewById(R.id.fab);
                            fab.setImageResource(R.drawable.ic_check_white_48dp);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    /* Or the android RGB Color (see the android Color class reference) */
                                    selectedColorRGB = cp.getColor();

                                    try {
                                        WallpaperManager wm = WallpaperManager.getInstance(mContext);
                                        // Create 1x1 bitmap to store the color
                                        Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                                        // Make a canvas with which we can draw to the bitmap
                                        Canvas canvas = new Canvas(bmp);
                                        // Fill with color and save
                                        canvas.drawColor(selectedColorRGB);
                                        canvas.save();

                                        wm.setBitmap(bmp);
                                        bmp.recycle();
                                    } catch (IOException e) {
                                        // oh lord!
                                    }
                                    cp.dismiss();
                                    Snackbar.make(mViewPager, R.string.applying, Snackbar.LENGTH_LONG).show();
                                }
                            });
                            break;
                    }

                    try {
                        WallpaperManager wm = WallpaperManager.getInstance(mContext);
                        // Create 1x1 bitmap to store the color
                        Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        // Make a canvas with which we can draw to the bitmap
                        Canvas canvas = new Canvas(bmp);
                        // Fill with color and save
                        canvas.drawColor(color);
                        canvas.save();

                        wm.setBitmap(bmp);
                        bmp.recycle();
                    } catch (IOException e) {
                        // oh lord!
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}