package de.baumann.thema;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import de.baumann.thema.helpers.CustomListAdapter;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class FragmentSound extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sound, container, false);

        setHasOptionsMenu(true);

        final String[] itemTITLE ={
                "Ouverture - Hymne" + " (Steven Testelin)" + " | " + getString(R.string.duration) + " 01:49",
                "Canon and Gigue in D major" + " | " + getString(R.string.duration) + "  00:45",
                "Epic" + " (Alexey Anisimov)" + " | " + getString(R.string.duration) + "  01:53",
                "Isn't it" + " | " + getString(R.string.duration) + " 00:01",
                "Jingle Bells Sms" + " | " + getString(R.string.duration) + " 00:04",
                "Wet" + " | " + getString(R.string.duration) + " 00:01",
        };

        final String[] itemURL ={
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/hymne.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/canon.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/epic.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/isnt_it.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/jingle_bells_sms.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/wet.mp3",
        };

        final String[] itemDES ={
                "CC license: https://www.jamendo.com/track/1004091/ouverture-hymne",
                "CC license: https://musopen.org/music/2672/johann-pachelbel/canon-and-gigue-in-d-major/",
                "CC license: https://www.jamendo.com/track/1344095/epic",
                "CC license: https://notificationsounds.com/standard-ringtones/isnt-it-524",
                "CC license: https://notificationsounds.com/message-tones/jingle-bells-sms-523",
                "CC license: https://notificationsounds.com/notification-sounds/wet-431",
        };

        final String[] itemFN ={
                "hymne.mp3",
                "canon.mp3",
                "epic.mp3",
                "isnt_it.mp3",
                "jingle_bells_sms.mp3",
                "wet.mp3",
        };

        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemTITLE, itemURL, itemDES, itemDES);
        listView = (ListView)rootView.findViewById(R.id.bookmarks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String Selecteditem= itemURL[+position];
                final MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(Selecteditem));
                mp.start();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mp.stop();
                    }
                }, 5000);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String Selecteditem= itemURL[+position];
                final String SelecteditemTitle= itemFN[+position];
                final CharSequence[] options = {getString(R.string.set_ringtone), getString(R.string.set_notification),
                        getString(R.string.set_alarm), getString(R.string.play)};

                new AlertDialog.Builder(getActivity())
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

                                if (options[item].equals (getString(R.string.set_ringtone))) {

                                    if (sharedPref.getBoolean ("permission", true)) {
                                        try {

                                            InputStream in;
                                            OutputStream out;
                                            in = new FileInputStream(Selecteditem);
                                            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Ringtones/" + SelecteditemTitle);

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
                                        Snackbar.make(listView, getString(R.string.set_ringtone_suc2), Snackbar.LENGTH_LONG).show();

                                    } else {
                                        File k = new File(Selecteditem);
                                        Uri newUri = Uri.fromFile(k);

                                        RingtoneManager.setActualDefaultRingtoneUri(
                                                getActivity(),
                                                RingtoneManager.TYPE_RINGTONE,
                                                newUri
                                        );
                                        Snackbar.make(listView, getString(R.string.set_ringtone_suc), Snackbar.LENGTH_LONG).show();
                                    }

                                } else if (options[item].equals (getString(R.string.set_notification))) {

                                    if (sharedPref.getBoolean ("permission", true)) {
                                        try {

                                            InputStream in;
                                            OutputStream out;
                                            in = new FileInputStream(Selecteditem);
                                            out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Notifications/" + SelecteditemTitle);

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
                                        Snackbar.make(listView, getString(R.string.set_notification_suc2), Snackbar.LENGTH_LONG).show();

                                    } else {
                                        File k = new File(Selecteditem);
                                        Uri newUri = Uri.fromFile(k);

                                        RingtoneManager.setActualDefaultRingtoneUri(
                                                getActivity(),
                                                RingtoneManager.TYPE_NOTIFICATION,
                                                newUri
                                        );
                                        Snackbar.make(listView, getString(R.string.set_notification_suc), Snackbar.LENGTH_LONG).show();
                                    }

                                } else if (options[item].equals (getString(R.string.set_alarm))) {

                                    File directory_al = new File(Environment.getExternalStorageDirectory()  + "/Alarms/");
                                    if (!directory_al.exists()) {
                                        directory_al.mkdirs();
                                    }

                                    try {

                                        InputStream in;
                                        OutputStream out;
                                        in = new FileInputStream(Selecteditem);
                                        out = new FileOutputStream(Environment.getExternalStorageDirectory()  + "/Alarms/" + SelecteditemTitle);

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

                                    Snackbar.make(listView, getString(R.string.set_alarm_suc), Snackbar.LENGTH_LONG).show();

                                } else if (options[item].equals (getString(R.string.play))) {
                                    final MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(Selecteditem));
                                    mp.start();
                                }
                            }
                        }).show();
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.color).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.help:

                final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.help_sound)));
                Linkify.addLinks(s, Linkify.WEB_URLS);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sound)
                        .setMessage(s)
                        .setPositiveButton(getString(R.string.yes), null);
                dialog.show();
                return true;

            case R.id.settings:
                final CharSequence[] options = {getString(R.string.setting_permission), getString(R.string.setting_permission_not)};
                new AlertDialog.Builder(getActivity())
                        .setItems(options, new DialogInterface.OnClickListener() {
                            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals(getString(R.string.setting_permission))) {

                                    sharedPref.edit()
                                            .putBoolean("permission", false)
                                            .apply();

                                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                                        boolean canDo =  Settings.System.canWrite(getActivity());
                                        if (!canDo) {
                                            new AlertDialog.Builder(getActivity())
                                                    .setMessage(R.string.permissions_2)
                                                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            if (android.os.Build.VERSION.SDK_INT >= 23) {
                                                                Intent grantIntent = new   Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                                                startActivity(grantIntent);
                                                            }
                                                        }
                                                    })
                                                    .setNegativeButton(getString(R.string.no), null)
                                                    .show();
                                        }
                                    }
                                }
                                if (options[item].equals(getString(R.string.setting_permission_not))) {

                                    sharedPref.edit()
                                            .putBoolean("permission", true)
                                            .apply();

                                    File directory_rt = new File(Environment.getExternalStorageDirectory()  + "/Ringtones/");
                                    if (!directory_rt.exists()) {
                                        directory_rt.mkdirs();
                                    }

                                    File directory_nt = new File(Environment.getExternalStorageDirectory()  + "/Notifications/");
                                    if (!directory_nt.exists()) {
                                        directory_nt.mkdirs();
                                    }
                                }
                            }
                        }).show();



                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
