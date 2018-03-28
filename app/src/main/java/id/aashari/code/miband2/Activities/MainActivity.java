package id.aashari.code.miband2.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import id.aashari.code.miband2.BasicProject.CustomBluetoothProfile;
import id.aashari.code.miband2.R;
import id.aashari.code.miband2.BLE_Service.BluetoothService;

public class MainActivity extends Activity {


    Boolean isListeningHeartRate = false;

    BluetoothAdapter bluetoothAdapter;
    BluetoothGatt bluetoothGatt;
    BluetoothDevice bluetoothDevice;

    Button btnStartConnecting, btnGetBatteryInfo, btnGetHeartRate, btnWalkingInfo, btnStartVibrate, btnStopVibrate, btnTest;
    EditText txtPhysicalAddress;
    TextView txtState, txtByte, txtDevicesFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeObjects();
        initilaizeComponents();
        initializeEvents();

        getBoundedDevice();

    }

    // wyświetla powiązane przez bluettoth urządzenia
    void getBoundedDevice() {
        Set<BluetoothDevice> boundedDevice = bluetoothAdapter.getBondedDevices();
        String showText = "";
        String devicesAddress = "";

        for (BluetoothDevice bd : boundedDevice) {
            showText += bd.getName() + "   " + bd.getAddress() + "\n";
//            if (bd.getName().contains("MI Band 2")) {
//                txtPhysicalAddress.setText(bd.getAddress());
//            }
        }
        showText += "Jeśli twojego urządzenia nie ma na liście, sparuj przez bluetooth!";
        txtDevicesFound.setText(showText);
    }

    void initializeObjects() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void initilaizeComponents() {
        btnStartConnecting = (Button) findViewById(R.id.btnStartConnecting);
        btnGetBatteryInfo = (Button) findViewById(R.id.btnGetBatteryInfo);
        btnWalkingInfo = (Button) findViewById(R.id.btnWalkingInfo);
        btnStartVibrate = (Button) findViewById(R.id.btnStartVibrate);
        btnStopVibrate = (Button) findViewById(R.id.btnStopVibrate);
        btnGetHeartRate = (Button) findViewById(R.id.btnGetHeartRate);
        txtPhysicalAddress = (EditText) findViewById(R.id.txtPhysicalAddress);
        txtState = (TextView) findViewById(R.id.txtState);
        txtByte = (TextView) findViewById(R.id.txtByte);
        txtDevicesFound = (TextView) findViewById(R.id.tv_devices_found);

        btnTest = (Button) findViewById(R.id.btnTest);
    }

    // podłączenie akcji na buttonnach
    void initializeEvents() {
        btnStartConnecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnecting();
            }
        });
        btnGetBatteryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBatteryStatus();
            }
        });
        btnStartVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate();
            }
        });
        btnStopVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVibrate();
            }
        });
        btnGetHeartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanHeartRate();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTest();
            }
        });
    }

    void startConnecting() {

        String address = txtPhysicalAddress.getText().toString();
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);

        Log.v("test", "Connecting to " + address);
        Log.v("test", "Device name " + bluetoothDevice.getName());

        bluetoothGatt = bluetoothDevice.connectGatt(this, true, bluetoothGattCallback);
    }

    void stateConnected() {
        bluetoothGatt.discoverServices();
        txtState.setText("Connected");
    }

    void stateDisconnected() {
        bluetoothGatt.disconnect();
        txtState.setText("Disconnected");
    }

    void startScanHeartRate() {
        txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.controlCharacteristic);
        bchar.setValue(new byte[]{21, 2, 1});
        bluetoothGatt.writeCharacteristic(bchar);
    }

    void listenHeartRate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.HeartRate.service)
                .getCharacteristic(CustomBluetoothProfile.HeartRate.measurementCharacteristic);
        bluetoothGatt.setCharacteristicNotification(bchar, true);
        BluetoothGattDescriptor descriptor = bchar.getDescriptor(CustomBluetoothProfile.HeartRate.descriptor);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
        isListeningHeartRate = true;
    }

    void getBatteryStatus() {
        txtByte.setText("...");
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
                .getCharacteristic(CustomBluetoothProfile.Basic.batteryCharacteristic);

//        bluetoothGatt.readCharacteristic(bchar);
        if (!bluetoothGatt.readCharacteristic(bchar)) {
            Toast.makeText(this, "Failed get battery info", Toast.LENGTH_SHORT).show();
        }
    }

    public static final byte COMMAND_ACTIVITY_DATA_START_DATE = 0x01;
    public static final byte COMMAND_ACTIVITY_DATA_TYPE_ACTIVTY = 0x01;

    //    BUTTON TESTOWY
    //todo button testowy
    void getTest(){
        txtByte.setText("...testowanie...");
//        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.Basic.service)
//                .getCharacteristic(BluetoothService.MiBandService.UUID_CHARACTERISTIC_5_ACTIVITY_DATA);
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(BluetoothService.MiBandService.UUID_SERVICE_MIBAND_SERVICE)
                .getCharacteristic(BluetoothService.MiBandService.UUID_CHARACTERISTIC_5_ACTIVITY_DATA);

        Calendar today = Calendar.getInstance();
        Calendar yesterday = (Calendar)today.clone();
        yesterday.add(Calendar.HOUR,-2);
        String displayYesterday = yesterday.getTime().toString();

        bluetoothGatt.setCharacteristicNotification(bchar, true);
        bchar.setValue(join(
                new byte[]{COMMAND_ACTIVITY_DATA_START_DATE,COMMAND_ACTIVITY_DATA_TYPE_ACTIVTY}
                ,getTimeBytes((yesterday),TimeUnit.MINUTES)
        ));
        bluetoothGatt.writeCharacteristic(bchar);

//        bluetoothGatt.readCharacteristic(bchar);
//        BluetoothGattDescriptor descriptor = bchar.getDescriptor(CustomBluetoothProfile.HeartRate.descriptor);
//        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        bluetoothGatt.writeDescriptor(descriptor);
//        if (!bluetoothGatt.readCharacteristic(bchar)) {
//            Toast.makeText(this, "Failed get battery info", Toast.LENGTH_SHORT).show();
//        }
    }
    //region metody do buttona testowego
    public byte[] getTimeBytes(Calendar calendar, TimeUnit precision) {
        byte[] bytes;
        if (precision == TimeUnit.MINUTES) {
            bytes = shortCalendarToRawBytes(calendar, true);
//        } else if (precision == TimeUnit.SECONDS) {
//            bytes = BLETypeConversions.calendarToRawBytes(calendar, true);
        } else {
            throw new IllegalArgumentException("Unsupported precision, only MINUTES and SECONDS are supported till now");
        }
        byte[] tail = new byte[] { 0, mapTimeZone(calendar.getTimeZone()) }; // 0 = adjust reason bitflags? or DST offset?? , timezone
//        byte[] tail = new byte[] { 0x2 }; // reason
        byte[] all = join(bytes, tail);
        return all;
    }
    public static byte[] join(byte[] start, byte[] end) {
        if (start == null || start.length == 0) {
            return end;
        }
        if (end == null || end.length == 0) {
            return start;
        }

        byte[] result = new byte[start.length + end.length];
        System.arraycopy(start, 0, result, 0, start.length);
        System.arraycopy(end, 0, result, start.length, end.length);
        return result;
    }


    public static byte[] shortCalendarToRawBytes(Calendar timestamp, boolean honorDeviceTimeOffset) {

        // The mi-band device currently records sleep
        // only if it happens after 10pm and before 7am.
        // The offset is used to trick the device to record sleep
        // in non-standard hours.
        // If you usually sleep, say, from 6am to 2pm, set the
        // shift to -8, so at 6am the device thinks it's still 10pm
        // of the day before.

//        if (honorDeviceTimeOffset) {
//            int offsetInHours = MiBandCoordinator.getDeviceTimeOffsetHours();
//            if (offsetInHours != 0) {
//                timestamp.add(Calendar.HOUR_OF_DAY, offsetInHours);
//            }
//        }

        // MiBand2:
        // year,year,month,dayofmonth,hour,minute

        byte[] year = fromUint16(timestamp.get(Calendar.YEAR));
        return new byte[] {
                year[0],
                year[1],
                fromUint8(timestamp.get(Calendar.MONTH) + 1),
                fromUint8(timestamp.get(Calendar.DATE)),
                fromUint8(timestamp.get(Calendar.HOUR_OF_DAY)),
                fromUint8(timestamp.get(Calendar.MINUTE))
        };
    }
    public static byte[] fromUint16(int value) {
        return new byte[] {
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff),
        };
    }
    public static byte fromUint8(int value) {
        return (byte) (value & 0xff);
    }
    public static byte mapTimeZone(TimeZone timeZone) {
        int utcOffsetInHours =  (timeZone.getRawOffset() / (1000 * 60 * 60));
        return (byte) (utcOffsetInHours * 4);
    }
    //endregion

    void startVibrate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
        bchar.setValue(new byte[]{2});
        if (!bluetoothGatt.writeCharacteristic(bchar)) {
            Toast.makeText(this, "Failed start vibrate", Toast.LENGTH_SHORT).show();
        }
    }

    void stopVibrate() {
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(CustomBluetoothProfile.AlertNotification.service)
                .getCharacteristic(CustomBluetoothProfile.AlertNotification.alertCharacteristic);
        bchar.setValue(new byte[]{0});
        if (!bluetoothGatt.writeCharacteristic(bchar)) {
            Toast.makeText(this, "Failed stop vibrate", Toast.LENGTH_SHORT).show();
        }
    }

    // Nadpisane funckje z BLE SDK
    final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.v("test", "onConnectionStateChange");

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                stateConnected();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                stateDisconnected();
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.v("test", "onServicesDiscovered");
            listenHeartRate();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.v("test", "onCharacteristicRead");

            byte[] data = characteristic.getValue();
            txtByte.setText(Arrays.toString(data));
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.v("test", "onCharacteristicWrite");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            txtState.setText("onCharacteristicChanged achieved");
            super.onCharacteristicChanged(gatt, characteristic);
            Log.v("test", "onCharacteristicChanged");
            txtState.setText("onCharacteristicChanged achieved");
            byte[] data = characteristic.getValue();
            txtState.setText("data achieved");
            txtByte.setText(Arrays.toString(data));
            txtState.setText("data displayed");

            //todo ashari
          /*  switch (data[0]) {
                case 0x1A:
                   Log.d("sleep", data.toString() );
            }*/
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.v("test", "onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.v("test", "onDescriptorWrite");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.v("test", "onReliableWriteCompleted");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.v("test", "onReadRemoteRssi");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.v("test", "onMtuChanged");
        }

    };

}
