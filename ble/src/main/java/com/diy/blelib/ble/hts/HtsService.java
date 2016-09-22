package com.diy.blelib.ble.hts;

import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.diy.blelib.profile.BleManager;
import com.diy.blelib.profile.BleProfileService;

public class HtsService extends BleProfileService implements HtsManagerCallbacks {
	private static final String TAG = HtsService.class.getSimpleName();

	public static final String BROADCAST_HTS_MEASUREMENT = "com.diy.blelib.ble.HtsService.BROADCAST_HRS_MEASUREMENT";
	public static final String EXTRA_HTS = "com.diy.blelib.ble.HtsService.EXTRA_HEART";


	private HtsManager mManager;
	private boolean mBinded;

	private final LocalBinder mBinder = new RSCBinder();

	/**
	 * This local binder is an interface for the binded activity to operate with the HTS sensor
	 */
	public class RSCBinder extends LocalBinder {

		public void aaaa() {}
		@Override
		public void sendUserCommand(byte[] command) {
			mManager.sendCommand(command);
		}

		@Override
		public void sendUserCommand(int command) {
			mManager.sendUserCommand(command);
		}
	}

	@Override
	protected LocalBinder getBinder() {
		Log.e(TAG, "getBinder: " );
		return mBinder;
	}

	@Override
	protected BleManager<HtsManagerCallbacks> initializeManager() {
		return mManager = HtsManager.getManager();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate: " );
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(final Intent intent) {
		Log.e(TAG, "onBind: " );
		mBinded = true;
		return super.onBind(intent);
	}

	@Override
	public void onRebind(final Intent intent) {
		mBinded = true;
		// read the battery level when back in the Activity
		/*if (isConnected())
			mManager.readBatteryLevel();*/
	}

	@Override
	public boolean onUnbind(final Intent intent) {
		mBinded = false;
		return super.onUnbind(intent);
	}


	@Override
	public void onBagReceived(float value) {
		final Intent broadcast = new Intent(BROADCAST_HTS_MEASUREMENT);
		broadcast.putExtra(EXTRA_HTS,value);
		LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
	}


}
