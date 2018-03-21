package id.aashari.code.miband2.Helpers;

import java.util.UUID;

/**
 * Created by aashari on 21/05/17.
 * This profile generated based on http://jellygom.com/2016/09/30/Mi-Band-UUID.html
 */

public class CustomBluetoothProfile {

    public static class Basic {
        //MOJE
        public static final String BASE_UUID = "0000%s-0000-1000-8000-00805f9b34fb";
        public static final UUID UUID_CHARACTERISTIC_CONTROL = UUID.fromString("14702856-620a-3973-7c78-9cfff0876abd");
        public static final byte CMD_GET_SLEEP = 0x19;
        public static final UUID UUID_SERVICE_CURRENT_TIME = UUID.fromString((String.format(BASE_UUID, "1805")));
        public static final UUID UUID_SLEEP_DESCRIPTOR = UUID.fromString((String.format(BASE_UUID, "2902")));
        public static final UUID UUID_SERVICE_BATTERY_SERVICE = UUID.fromString((String.format(BASE_UUID, "180F")));
        public static final UUID UUID_CHARACTERISTIC_BATTERY_LEVEL = UUID.fromString((String.format(BASE_UUID, "2A19")));

        public UUID getUUID(UUID base, byte plus){
            return UUID.fromString((String.format(Basic.BASE_UUID, plus)));
        }

        //BYŁO
        public static UUID service = UUID.fromString("0000fee0-0000-1000-8000-00805f9b34fb");
        public static UUID batteryCharacteristic = UUID.fromString("00000006-0000-3512-2118-0009af100700");
    }

    public static class AlertNotification {
        public static UUID service = UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
        public static UUID alertCharacteristic = UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb");
    }

    public static class HeartRate {
        public static UUID service = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
        public static UUID measurementCharacteristic = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
        public static UUID descriptor = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static UUID controlCharacteristic = UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb");
    }



}
