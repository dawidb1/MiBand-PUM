//package id.aashari.code.miband2.BLE_Service;
//
//import android.bluetooth.BluetoothGattCharacteristic;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//public class FetchActivity {
//    //1 day is 60*24 samples -> 1 samples per 1 minutes
//    private List<MiBandActivitySample> samples = new ArrayList<>(60*24); // 1day per default
//
//    protected BluetoothGattCharacteristic characteristicActivityData;
//    protected BluetoothGattCharacteristic characteristicFetch;
//    protected int fetchCount;
//    protected byte lastPacketCounter; //counts how many fetches needed
//
//    protected void startFetching() throws IOException {
//        samples.clear();
//        lastPacketCounter = -1;
//
//        TransactionBuilder builder = performInitialized("fetching activity data");
//        getSupport().setLowLatency(builder);
//        if (fetchCount == 0) {
//            builder.add(new SetDeviceBusyAction(getDevice(), getContext().getString(R.string.busy_task_fetch_activity_data), getContext()));
//        }
//        fetchCount++;
//
//        characteristicActivityData = getCharacteristic(MiBand2Service.UUID_CHARACTERISTIC_5_ACTIVITY_DATA);
//        builder.notify(characteristicActivityData, false);
//
//        characteristicFetch = getCharacteristic(MiBand2Service.UUID_UNKNOWN_CHARACTERISTIC4);
//        builder.notify(characteristicFetch, true);
//
//        startFetching(builder);
//        builder.queue(getQueue());
//    }
//
//    protected void startFetching(TransactionBuilder builder) {
//        GregorianCalendar sinceWhen = getLastSuccessfulSyncTime();
//        builder.write(characteristicFetch, BLETypeConversions.join(new byte[] { MiBand2Service.COMMAND_ACTIVITY_DATA_START_DATE, MiBand2Service.COMMAND_ACTIVITY_DATA_TYPE_ACTIVTY }, getSupport().getTimeBytes(sinceWhen, TimeUnit.MINUTES)));
//        builder.add(new WaitAction(1000)); // TODO: actually wait for the success-reply
//        builder.notify(characteristicActivityData, true);
//        builder.write(characteristicFetch, new byte[] { MiBand2Service.COMMAND_FETCH_DATA});
//    }
//
//    protected BluetoothGattCharacteristic getCharacteristic(UUID uuid) {
//        return mSupport.getCharacteristic(uuid);
//    }
//
//}
