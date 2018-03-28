package id.aashari.code.miband2.BLE_Service;

import java.util.UUID;

/**
 * Created by Dawid on 23.03.2018.
 */

public class BluetoothService {
    public static final String BASE_UUID = "0000%s-0000-1000-8000-00805f9b34fb";

    public static class MiBandService{
        public static final UUID UUID_SERVICE_MIBAND_SERVICE = UUID.fromString(String.format(BASE_UUID, "FEE0"));
        public static final UUID UUID_SERVICE_MIBAND2_SERVICE = UUID.fromString(String.format(BASE_UUID, "FEE1"));
        public static final UUID UUID_SERVICE_HEART_RATE = UUID.fromString(String.format(BASE_UUID, "180D"));
        public static final UUID UUID_SERVICE_FIRMWARE_SERVICE = UUID.fromString("00001530-0000-3512-2118-0009af100700");

        public static final UUID UUID_CHARACTERISTIC_FIRMWARE = UUID.fromString("00001531-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_FIRMWARE_DATA = UUID.fromString("00001532-0000-3512-2118-0009af100700");

        public static final UUID UUID_UNKNOWN_CHARACTERISTIC0 = UUID.fromString("00000000-0000-3512-2118-0009af100700");
        public static final UUID UUID_UNKNOWN_CHARACTERISTIC1 = UUID.fromString("00000001-0000-3512-2118-0009af100700");
        public static final UUID UUID_UNKNOWN_CHARACTERISTIC2 = UUID.fromString("00000002-0000-3512-2118-0009af100700");
        /**
         * Alarms, Display and other configuration.
         */
        public static final UUID UUID_CHARACTERISTIC_3_CONFIGURATION = UUID.fromString("00000003-0000-3512-2118-0009af100700");
        public static final UUID UUID_UNKNOWN_CHARACTERISTIC4 = UUID.fromString("00000004-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_5_ACTIVITY_DATA = UUID.fromString("00000005-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_6_BATTERY_INFO = UUID.fromString("00000006-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_7_REALTIME_STEPS = UUID.fromString("00000007-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_8_USER_SETTINGS = UUID.fromString("00000008-0000-3512-2118-0009af100700");
        // service uuid fee1
        public static final UUID UUID_CHARACTERISTIC_AUTH = UUID.fromString("00000009-0000-3512-2118-0009af100700");
        public static final UUID UUID_CHARACTERISTIC_DEVICEEVENT = UUID.fromString("00000010-0000-3512-2118-0009af100700");

        public static final int ALERT_LEVEL_NONE = 0;
        public static final int ALERT_LEVEL_MESSAGE = 1;
        public static final int ALERT_LEVEL_PHONE_CALL = 2;
        public static final int ALERT_LEVEL_VIBRATE_ONLY = 3;
    }

    public static class BLEServices{
        public static final UUID UUID_SERVICE_ALERT_NOTIFICATION = UUID.fromString((String.format(BASE_UUID, "1811")));
        public static final UUID UUID_SERVICE_AUTOMATION_IO = UUID.fromString((String.format(BASE_UUID, "1815")));
        public static final UUID UUID_SERVICE_BATTERY_SERVICE = UUID.fromString((String.format(BASE_UUID, "180F")));
        public static final UUID UUID_SERVICE_BLOOD_PRESSURE = UUID.fromString((String.format(BASE_UUID, "1810")));
        public static final UUID UUID_SERVICE_BODY_COMPOSITION = UUID.fromString((String.format(BASE_UUID, "181B")));
        public static final UUID UUID_SERVICE_BOND_MANAGEMENT = UUID.fromString((String.format(BASE_UUID, "181E")));
        public static final UUID UUID_SERVICE_CONTINUOUS_GLUCOSE_MONITORING = UUID.fromString((String.format(BASE_UUID, "181F")));
        public static final UUID UUID_SERVICE_CURRENT_TIME = UUID.fromString((String.format(BASE_UUID, "1805")));
        public static final UUID UUID_SERVICE_CYCLING_POWER = UUID.fromString((String.format(BASE_UUID, "1818")));
        public static final UUID UUID_SERVICE_CYCLING_SPEED_AND_CADENCE = UUID.fromString((String.format(BASE_UUID, "1816")));
        public static final UUID UUID_SERVICE_DEVICE_INFORMATION = UUID.fromString((String.format(BASE_UUID, "180A")));
        public static final UUID UUID_SERVICE_ENVIRONMENTAL_SENSING = UUID.fromString((String.format(BASE_UUID, "181A")));
        public static final UUID UUID_SERVICE_GENERIC_ACCESS = UUID.fromString((String.format(BASE_UUID, "1800")));
        public static final UUID UUID_SERVICE_GENERIC_ATTRIBUTE = UUID.fromString((String.format(BASE_UUID, "1801")));
        public static final UUID UUID_SERVICE_GLUCOSE = UUID.fromString((String.format(BASE_UUID, "1808")));
        public static final UUID UUID_SERVICE_HEALTH_THERMOMETER = UUID.fromString((String.format(BASE_UUID, "1809")));
        public static final UUID UUID_SERVICE_HEART_RATE = UUID.fromString((String.format(BASE_UUID, "180D")));
        public static final UUID UUID_SERVICE_HUMAN_INTERFACE_DEVICE = UUID.fromString((String.format(BASE_UUID, "1812")));
        public static final UUID UUID_SERVICE_IMMEDIATE_ALERT = UUID.fromString((String.format(BASE_UUID, "1802")));
        public static final UUID UUID_SERVICE_INDOOR_POSITIONING = UUID.fromString((String.format(BASE_UUID, "1821")));
        public static final UUID UUID_SERVICE_INTERNET_PROTOCOL_SUPPORT = UUID.fromString((String.format(BASE_UUID, "1820")));
        public static final UUID UUID_SERVICE_LINK_LOSS = UUID.fromString((String.format(BASE_UUID, "1803")));
        public static final UUID UUID_SERVICE_LOCATION_AND_NAVIGATION = UUID.fromString((String.format(BASE_UUID, "1819")));
        public static final UUID UUID_SERVICE_NEXT_DST_CHANGE = UUID.fromString((String.format(BASE_UUID, "1807")));
        public static final UUID UUID_SERVICE_PHONE_ALERT_STATUS = UUID.fromString((String.format(BASE_UUID, "180E")));
        public static final UUID UUID_SERVICE_PULSE_OXIMETER = UUID.fromString((String.format(BASE_UUID, "1822")));
        public static final UUID UUID_SERVICE_REFERENCE_TIME_UPDATE = UUID.fromString((String.format(BASE_UUID, "1806")));
        public static final UUID UUID_SERVICE_RUNNING_SPEED_AND_CADENCE = UUID.fromString((String.format(BASE_UUID, "1814")));
        public static final UUID UUID_SERVICE_SCAN_PARAMETERS = UUID.fromString((String.format(BASE_UUID, "1813")));
        public static final UUID UUID_SERVICE_TX_POWER = UUID.fromString((String.format(BASE_UUID, "1804")));
        public static final UUID UUID_SERVICE_USER_DATA = UUID.fromString((String.format(BASE_UUID, "181C")));
        public static final UUID UUID_SERVICE_WEIGHT_SCALE = UUID.fromString((String.format(BASE_UUID, "181D")));
    }


//        //KOSZ
//        public static final byte CMD_GET_SLEEP = 0x19;
//        public UUID getUUID(UUID base, byte plus){
//            return UUID.fromString((String.format(Basic.BASE_UUID, plus)));
//        }
}
