package com.scorpioneal.demos.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import com.scorpioneal.demos.R;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * retrieve phone info
 * 
 * 
 */
public class PhoneInfoUtil {
	private static final String TAG = PhoneInfoUtil.class.getSimpleName();
	private static final String FILE_MEMORY = "/proc/meminfo";
	private static final String FILE_CPU = "/proc/cpuinfo";
	public String mIMEI;
	public int mPhoneType;
	public int mSysVersion;
	public String mNetWorkCountryIso;
	public String mNetWorkOperator;
	public String mNetWorkOperatorName;
	public int mNetWorkType;
	public boolean mIsOnLine;
	public String mConnectTypeName;
	public long mFreeMem;
	public long mTotalMem;
	public String mCupInfo;
	public String mProductName;
	public String mModelName;
	public String mManufacturerName;

	private TelephonyManager tManager;

	/**
	 * private constructor
	 */
	private Context mContext;

	private PhoneInfoUtil(Context context) {
		tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		this.mContext = context;
	}

	private static PhoneInfoUtil phoneInfoUtil;

	public static PhoneInfoUtil getInstance(Context context) {
		if (null == phoneInfoUtil) {
			phoneInfoUtil = new PhoneInfoUtil(context);
		}
		return phoneInfoUtil;
	}

	public String getSeveralOtherStatus() {
		StringBuilder builder = new StringBuilder();
		// 获取表示sim卡状态的的数组
		String simStatus[] = mContext.getResources().getStringArray(
				R.array.simStatus);
		// 获取表示手机类型的数组
		String phoneType[] = mContext.getResources().getStringArray(
				R.array.phoneType);
		builder.append("设备编号: " + tManager.getDeviceId() + "\r\n");
		builder.append("设备类型: " + phoneType[tManager.getPhoneType()] + "\r\n");
		if (null == tManager.getDeviceSoftwareVersion()) {
			builder.append("软件版本: " + "未知" + "\r\n");
		} else {
			builder.append("软件版本: " + tManager.getDeviceSoftwareVersion()
					+ "\r\n");
		}
		if (null == tManager.getCellLocation()) {
			builder.append("当前位置: " + "未知" + "\r\n");
		} else {
			builder.append("当前位置: " + tManager.getCellLocation().toString()
					+ "\r\n");
		}

		switch (tManager.getCallState()) {
		case TelephonyManager.CALL_STATE_IDLE:
			builder.append("呼叫状态: " + "空闲" + "\r\n");
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			builder.append("呼叫状态: " + "正在通话" + "\r\n");
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			builder.append("呼叫状态: " + "等待接听" + "\r\n");
			break;
		default:
			break;
		}
		builder.append("电话号码: " + tManager.getLine1Number() + "\r\n");
		builder.append("运营商的国家代码: " + tManager.getNetworkCountryIso() + "\r\n");
		builder.append("SPN: " + tManager.getSimOperatorName().equals("") != null ? "未知"
				: tManager.getSimOperatorName() + "\r\n");
		builder.append("SIM卡的序列号: " + tManager.getSimSerialNumber() + "\r\n");
		builder.append("SIM卡状态: " + simStatus[tManager.getSimState()] + "\r\n");
		builder.append("网络类型: " + getNetworkType(tManager.getNetworkType())
				+ "\r\n");
		return builder.toString();
	}

	// 获取手机网络类型
	private String getNetworkType(int networkType) {
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return "1xRTT";
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return "CDMA";
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return "EDGE";
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return "EHRPD";
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return "EVDO_0";
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return "EVDO_A";
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return "EVDO_B";
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return "GPRS";
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return "HSDPA";
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return "HSPA";
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return "HSPAP";
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return "HSUPA";
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return "IDEN";
		case TelephonyManager.NETWORK_TYPE_LTE:
			return "LTE";
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return "UMTS";
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return "UNKNOWN";
		default:
			return "UNKNOWN";
		}
	}

	/**
	 * get imei
	 * 
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		// check if has the permission
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getDeviceId();
		} else {
			return null;
		}
	}

	/**
	 * get phone type,like :GSM��CDMA��SIP��NONE
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getPhoneType();
	}

	/**
	 * get phone sys version
	 * 
	 * @return
	 */
	public static int getSysVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * Returns the ISO country code equivalent of the current registered
	 * operator's MCC (Mobile Country Code).
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkCountryIso(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkCountryIso();
	}

	/**
	 * Returns the numeric name (MCC+MNC) of current registered operator.may not
	 * work on CDMA phone
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkOperator(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkOperator();
	}

	/**
	 * Returns the alphabetic name of current registered operator.may not work
	 * on CDMA phone
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkOperatorName(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkOperatorName();
	}

	/**
	 * get type of current network
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkType();
	}

	/**
	 * is webservice aviliable
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * get current data connection type name ,like ,Mobile��WIFI��OFFLINE
	 * 
	 * @param context
	 * @return
	 */
	public static String getConnectTypeName(Context context) {
		if (!isOnline(context)) {
			return "OFFLINE";
		}
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.getTypeName();
		} else {
			return "OFFLINE";
		}
	}

	/**
	 * get free memory of phone, in M
	 * 
	 * @param context
	 * @return
	 */
	public static long getFreeMem(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		MemoryInfo info = new MemoryInfo();
		manager.getMemoryInfo(info);
		long free = info.availMem / 1024 / 1024;
		return free;
	}

	/**
	 * get total memory of phone , in M
	 * 
	 * @param context
	 * @return
	 */
	public static long getTotalMem(Context context) {
		try {
			FileReader fr = new FileReader(FILE_MEMORY);
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split("\\s+");
			Log.w(TAG, text);
			return Long.valueOf(array[1]) / 1024;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String getCpuInfo() {
		try {
			FileReader fr = new FileReader(FILE_CPU);
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
				Log.w(TAG, " .....  " + array[i]);
			}
			Log.w(TAG, text);
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get product name of phone
	 * 
	 * @return
	 */
	public static String getProductName() {
		return Build.PRODUCT;
	}

	/**
	 * get model of phone
	 * 
	 * @return
	 */
	public static String getModelName() {
		return Build.MODEL;
	}

	/**
	 * get Manufacturer Name of phone
	 * 
	 * @return
	 */
	public static String getManufacturerName() {
		return Build.MANUFACTURER;
	}

	public static PhoneInfoUtil getPhoneInfo(Context context) {
		PhoneInfoUtil result = new PhoneInfoUtil(context);
		result.mIMEI = getIMEI(context);
		result.mPhoneType = getPhoneType(context);
		result.mSysVersion = getSysVersion();
		result.mNetWorkCountryIso = getNetWorkCountryIso(context);
		result.mNetWorkOperator = getNetWorkOperator(context);
		result.mNetWorkOperatorName = getNetWorkOperatorName(context);
		result.mNetWorkType = getNetworkType(context);
		result.mIsOnLine = isOnline(context);
		result.mConnectTypeName = getConnectTypeName(context);
		result.mFreeMem = getFreeMem(context);
		result.mTotalMem = getTotalMem(context);
		result.mCupInfo = getCpuInfo();
		result.mProductName = getProductName();
		result.mModelName = getModelName();
		result.mManufacturerName = getManufacturerName();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IMEI : " + mIMEI + "\n");
		builder.append("PhoneType : " + mPhoneType + "\n");
		builder.append("SysVersion : " + mSysVersion + "\n");
		builder.append("NetWorkCountryIso : " + mNetWorkCountryIso + "\n");
		builder.append("NetWorkOperator : " + mNetWorkOperator + "\n");
		builder.append("NetWorkOperatorName : " + mNetWorkOperatorName + "\n");
		builder.append("NetWorkType : " + mNetWorkType + "\n");
		builder.append("IsOnLine : " + mIsOnLine + "\n");
		builder.append("ConnectTypeName : " + mConnectTypeName + "\n");
		builder.append("FreeMem : " + mFreeMem + "M\n");
		builder.append("TotalMem : " + mTotalMem + "M\n");
		builder.append("CupInfo : " + mCupInfo + "\n");
		builder.append("ProductName : " + mProductName + "\n");
		builder.append("ModelName : " + mModelName + "\n");
		builder.append("ManufacturerName : " + mManufacturerName + "\n");
		builder.append("OtherInfo : " + getSeveralOtherStatus());
		return builder.toString();
	}

}
