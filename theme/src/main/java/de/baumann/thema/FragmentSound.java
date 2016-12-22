package de.baumann.thema;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

                final String SelecteditemMes= itemTITLE[+position];
                final String Selecteditem= itemURL[+position];
                final String SelecteditemTitle= itemFN[+position];
                final String SelecteditemUrl = itemDES[+position].substring(12);
                final CharSequence[] options = {
                        getString(R.string.set_ringtone),
                        getString(R.string.set_notification),
                        getString(R.string.set_alarm),
                        getString(R.string.play),
                        getString(R.string.open)};

                new AlertDialog.Builder(getActivity())
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (options[item].equals (getString(R.string.set_ringtone))) {

                                    File directory_al = new File(Environment.getExternalStorageDirectory()  + "/Ringtones/");
                                    if (!directory_al.exists()) {
                                        directory_al.mkdirs();
                                    }

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

                                        MediaScannerConnection.scanFile(
                                                getActivity(),
                                                new String[]{Environment.getExternalStorageDirectory()  + "/Ringtones/" + SelecteditemTitle},
                                                null,
                                                new MediaScannerConnection.OnScanCompletedListener() {
                                                    @Override
                                                    public void onScanCompleted(String path, Uri uri) {
                                                        Intent intent2 = new Intent(Settings.ACTION_SOUND_SETTINGS);
                                                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        getActivity().startActivity(intent2);
                                                    }
                                                });

                                    } catch (Exception e) {
                                        Log.e("tag", e.getMessage());
                                    }

                                } else if (options[item].equals (getString(R.string.set_notification))) {

                                    File directory_al = new File(Environment.getExternalStorageDirectory()  + "/Notifications/");
                                    if (!directory_al.exists()) {
                                        directory_al.mkdirs();
                                    }

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

                                        MediaScannerConnection.scanFile(
                                                getActivity(),
                                                new String[]{Environment.getExternalStorageDirectory()  + "/Notifications/" + SelecteditemTitle},
                                                null,
                                                new MediaScannerConnection.OnScanCompletedListener() {
                                                    @Override
                                                    public void onScanCompleted(String path, Uri uri) {
                                                        Intent intent2 = new Intent(Settings.ACTION_SOUND_SETTINGS);
                                                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        getActivity().startActivity(intent2);
                                                    }
                                                });

                                    } catch (Exception e) {
                                        Log.e("tag", e.getMessage());
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

                                        MediaScannerConnection.scanFile(
                                                getActivity(),
                                                new String[]{Environment.getExternalStorageDirectory()  + "/Alarms/" + SelecteditemTitle},
                                                null,
                                                new MediaScannerConnection.OnScanCompletedListener() {
                                                    @Override
                                                    public void onScanCompleted(String path, Uri uri) {
                                                        Snackbar.make(listView, R.string.set_alarm_suc, Snackbar.LENGTH_LONG).show();
                                                    }
                                                });

                                    } catch (Exception e) {
                                        Log.e("tag", e.getMessage());
                                    }

                                    Snackbar.make(listView, getString(R.string.set_alarm_suc), Snackbar.LENGTH_LONG).show();

                                } else if (options[item].equals (getString(R.string.play))) {
                                    final MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(Selecteditem));

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(getString(R.string.play))
                                            .setMessage(SelecteditemMes)
                                            .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    mp.stop();
                                                    dialog.cancel();
                                                }
                                            })
                                           .show();
                                    mp.start();
                                } else if (options[item].equals (getString(R.string.open))) {
                                    Uri uri = Uri.parse(SelecteditemUrl); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
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
                SpannableString s;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    s = new SpannableString(Html.fromHtml(getString(R.string.help_sound),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    //noinspection deprecation
                    s = new SpannableString(Html.fromHtml(getString(R.string.help_sound)));
                }
                Linkify.addLinks(s, Linkify.WEB_URLS);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sound)
                        .setMessage(s)
                        .setPositiveButton(getString(R.string.yes), null);
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
