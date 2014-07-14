package com.kaajavi.trackmycar;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;

public class Position implements LocationListener {

	public static double latitude=0;  
	public static double longitude=0; 
	public static float presicion=0;		
	private String phone = "";
	private Context context;
	public Position(String phone, Context context){
			this.phone=phone;
			this.context = context;
	 }
	
	@Override  
	public void onLocationChanged(Location loc)  
	{  		
		loc.getLatitude();  
		loc.getLongitude();  

		latitude=loc.getLatitude();  
		longitude=loc.getLongitude();
		presicion=loc.getAccuracy();
		System.out.println("Posicionado");
		if (Config.sendSmS){
			System.out.println("Enviando posicion:");
			System.out.println("Posicionado en: " +Position.latitude + " - " + Position.longitude +
					"\n maps.google.com/?q=" + Position.latitude+","+ Position.longitude +
					"\n Presicion: " + presicion);
			if(Config.isSmsActive){
				System.out.println("Enviando por sms:");
				SmsManager sms = SmsManager.getDefault();	        	
				sms.sendTextMessage(this.phone, null, 
						"Posicionado en: (" +Position.latitude + " -- " + Position.longitude +
						")\n http://maps.google.com/?q=" + Position.latitude+","+ Position.longitude +
						"\n Presicion: " + presicion, null, null);
			}else{
				System.out.println("SMS DESACTIVADO");
			}
			Config.sendSmS = false;			
			
			try {
				LocationManager mlocManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
				mlocManager.removeUpdates(this);
				System.out.println("TERMINADO");
				this.finalize();				
			} catch (Throwable e) {
				System.out.println("NO PUDE FINALIZAR EL POSICIONAMIENTO!");
			}
		}
	}  

	@Override  
	public void onProviderDisabled(String provider)  
	{  
		//print "Currently GPS is Disabled";  
	}  
	@Override  
	public void onProviderEnabled(String provider)  
	{  
		//print "GPS got Enabled";
		
	}  
	@Override  
	public void onStatusChanged(String provider, int status, Bundle extras)  
	{  
		System.out.println("Cambiando estado - " + provider + "  - " + status);
	}  

}
