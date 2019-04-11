package com.example.savergy;

import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.ParcelUuid;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private android.support.design.widget.TabLayout tabLayout;

    private OutputStream outputStream;
    private InputStream inStream;
    private BluetoothAdapter bluetoothAdapter;

    private static final int SPEECH_REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        Intent myService=new Intent(this,ForeGroundService.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner min=findViewById(R.id.min);
        final Spinner hour=findViewById(R.id.Hour);
        min.setVisibility(View.INVISIBLE);
        final Spinner Bdevices=findViewById(R.id.devices);





        //Bluetooth

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "Device doesn't support Bluetooth", Toast.LENGTH_LONG).show();

            // Device doesn't support Bluetooth
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.size() > 0) {
            Object[] devices = (Object []) bondedDevices.toArray();
           // BluetoothDevice device = (BluetoothDevice) devices[1];//TODODODODODOO
           // ParcelUuid[] uuids = device.getUuids();
           // Log.d("asda",device.getName());
            String BLDevice[] = new String[devices.length];
            for(int i=0;i<devices.length;i++)
            {
                BluetoothDevice devicee = (BluetoothDevice) devices[i];//TODODODODODOO
                BLDevice[i]=devicee.getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_item, BLDevice);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Bdevices.setAdapter(adapter);



        }

        //END of bluetooth handling







        tabLayout = (TabLayout) findViewById(R.id.TabLayouts);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayouts);

            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    Toast.makeText(MainActivity.this, "Tab " + "Alarm tab", Toast.LENGTH_LONG).show();
                    Button button=findViewById(R.id.button);
                    button.setVisibility(View.VISIBLE);
                    TextView text=findViewById(R.id.textView);
                    text.setVisibility(View.VISIBLE);
                    SeekBar seek=findViewById(R.id.seekBar);
                    seek.setVisibility(View.VISIBLE);
                    Spinner min=findViewById(R.id.min);
                    min.setVisibility(View.INVISIBLE);
                    hour.setVisibility(View.INVISIBLE);
                    TextView hourText=findViewById(R.id.hourText);
                    hourText.setVisibility(View.INVISIBLE);
                    TextView minText=findViewById(R.id.minText);
                    minText.setVisibility(View.INVISIBLE);
                    Button alarm=findViewById(R.id.Alarm);
                    alarm.setVisibility(View.INVISIBLE);
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    Toast.makeText(MainActivity.this, "Tab " + "Control tab", Toast.LENGTH_LONG).show();
                    Button button=findViewById(R.id.button);
                    button.setVisibility(View.INVISIBLE);
                    TextView text=findViewById(R.id.textView);
                    text.setVisibility(View.INVISIBLE);
                    SeekBar seek=findViewById(R.id.seekBar);
                    seek.setVisibility(View.INVISIBLE);
                    Spinner min=findViewById(R.id.min);
                    min.setVisibility(View.VISIBLE);
                    hour.setVisibility(View.VISIBLE);
                    TextView hourText=findViewById(R.id.hourText);
                    hourText.setVisibility(View.VISIBLE);
                    TextView minText=findViewById(R.id.minText);
                    minText.setVisibility(View.VISIBLE);
                    Button alarm=findViewById(R.id.Alarm);
                    alarm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //ALARM UI
        String Hours[] = new String[24];
        String Minutes[]= new String[60];
        for(int i=0;i<24;i++)
        {
            Hours[i]=i+1+"";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, Hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour.setAdapter(adapter);

        for(int i=0;i<60;i++)
        {
            if(i<9){
                Minutes[i]="0"+(i+1)+"";
            }else{
                Minutes[i]=i+1+"";
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, Minutes);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min.setAdapter(adapter1);




        //SEEKER BAR
        SeekBar bar=findViewById(R.id.seekBar);
        int barProg=bar.getProgress();
        TextView intensity=(TextView)findViewById(R.id.textView);
        Log.d("pepega", ""+barProg);
        //intensity.setText(barProg);


        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                // t1.setTextSize(progress);
                try {
                    write(progress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView intensity=findViewById(R.id.textView);
                intensity.setText("Intensity:"+progress);
                Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();

            }
        });


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);





        startService(myService);
    }

    public void write(Integer s) throws IOException {
        outputStream.write(s);

    }

    public void Scan(View view){
        try {
            write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SeekBar bar=findViewById(R.id.seekBar);

        if(bar.getProgress()==0) {
            bar.setProgress(100);
        }else{
            bar.setProgress(0);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void alarmOncClick(View view) {
        Spinner min=findViewById(R.id.min);
        Spinner hour=findViewById(R.id.Hour);

        Toast.makeText(getApplicationContext(), "Alarm set for: "+hour.getSelectedItem().toString()+":"+min.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.getNotificationChannel("pepega");
        Notification notification = new NotificationCompat.Builder(this,"pepega")
                .setContentTitle("SmartHome and alarm service running")
                .setContentText("Alarm set for: "+hour.getSelectedItem().toString()+":"+min.getSelectedItem().toString())
                .setSmallIcon(R.drawable.ic_bluetooth_connected_black_24dp)
                .build();
        notificationManager.notify(1, notification);
    }


    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.d("SPEECH",spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onConnect(View view){
        Spinner Bdevices=findViewById(R.id.devices);

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Object[] devices = (Object []) bondedDevices.toArray();
        BluetoothDevice device = (BluetoothDevice) devices[Bdevices.getSelectedItemPosition()];//TODODODODODOO
        ParcelUuid[] uuids = device.getUuids();
        // Log.d("asda",device.getName());
        String BLDevice[] = new String[devices.length];


        BluetoothSocket socket = null;

        try {
                socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                Button but=findViewById(R.id.Connect);
                but.setText("Connected");
            } catch (IOException e) {
                e.printStackTrace();
            Button but=findViewById(R.id.Connect);

            but.setText("Failed to connect");

            Toast.makeText(MainActivity.this, "Unable to connect", Toast.LENGTH_LONG).show();

        }
            try {
                socket.connect();
                Button but=findViewById(R.id.Connect);
                but.setText("Connected");
            } catch (IOException e) {
                e.printStackTrace();
                Button but=findViewById(R.id.Connect);

                but.setText("Failed to connect");

                Toast.makeText(MainActivity.this, "Unable to connect", Toast.LENGTH_LONG).show();
            }
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /*public void onDestroy() {
        stopService(myService);
        super.onDestroy();
    }
*/

}


