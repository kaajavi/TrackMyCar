package com.kaajavi.trackmycar;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;



public class SMSReceiver extends BroadcastReceiver
{
	@SuppressLint("DefaultLocale")
	public void onReceive(Context context, Intent intent)
	{
		Bundle myBundle = intent.getExtras();
		SmsMessage [] messages = null;
		//String strMessage = "";
		String phone="";		

		//TODO: y el Config.isActive == true!!
		if (myBundle != null && Config.isPositionActive)
		{
			Object [] pdus = (Object[]) myBundle.get("pdus");
			messages = new SmsMessage[pdus.length];			
			for (int i = 0; i < messages.length; i++)
			{
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);			
				//strMessage += "SMS From: " + messages[i].getOriginatingAddress();
				//strMessage += " : ";
				//strMessage += messages[i].getMessageBody();
				//strMessage += "\n";
				String cuerpo = "SMS: " + messages[i].getMessageBody().toUpperCase();				
				if (cuerpo.contains(Config.code.toUpperCase()))
				{
					Config.sendSmS = true;				
					phone=messages[i].getOriginatingAddress();
					abortBroadcast();
				}
			}
			if (Config.sendSmS){
				System.out.println("Llega sms con codigo");
				//Probando notificaciones
				NotificationCompat.Builder mBuilder =
						new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(context.getResources().getString(R.string.strBusquedaSolicitada));

				LocationManager mlocManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
				Position listener = new Position(phone,context);
				if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
					System.out.print(" x GPS");
					mBuilder.setContentText(context.getResources().getString(R.string.strBuscandoPorGps));
					mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 2, listener);					
				}else{
					if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
						System.out.print(" x Network");
						mBuilder.setContentText(context.getResources().getString(R.string.strBuscandoPorGsm));						//
						mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 1000, 0, listener);
					}
				}				

				NotificationManager mNotificationManager =
						(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(1, mBuilder.build());

			}else{
				System.out.println("Llega un sms cualquiera.");

			}

		}


	}    
}
