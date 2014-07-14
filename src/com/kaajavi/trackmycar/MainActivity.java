package com.kaajavi.trackmycar;



/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
*/
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Button;


public class MainActivity extends Activity {
	
	private EditText txtPassword;
	private ToggleButton btnActivado;
	private ToggleButton btnActivarSms;
	private Button btnAccept;
	//private AdView adView;
	//private static final String MY_AD_UNIT_ID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		//Publicidad
		// Crear adView.
		/*adView = new AdView(this);
		adView.setAdUnitId(MY_AD_UNIT_ID);		
		adView.setAdSize(AdSize.BANNER);

		// Buscar LinearLayout suponiendo que se le ha asignado
		// el atributo android:id="@+id/mainLayout".
		FrameLayout layout = (FrameLayout)findViewById(R.id.adsLayout);

		// Añadirle adView.
		layout.addView(adView);
		

		// Iniciar una solicitud genérica.
		AdRequest adRequest = new AdRequest.Builder().build();

		// Cargar adView con la solicitud de anuncio.
		adView.loadAd(adRequest);

		 */
		
		//Cargo texto de clave
		txtPassword = (EditText) findViewById(R.id.txtClave);        
		//Cargo Botones        
		btnActivado = (ToggleButton) findViewById(R.id.btnActivar);
		btnActivado.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{     
				Config.isPositionActive = btnActivado.isChecked();            	
				btnActivarSms.setEnabled(Config.isPositionActive);
				btnActivarSms.setChecked(Config.isSmsActive && Config.isPositionActive);
				btnActivado.setChecked(Config.isPositionActive);  
				writeToFile();
			}
		});
		btnActivarSms = (ToggleButton) findViewById(R.id.btnActivarSms);
		btnActivarSms.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Config.isSmsActive = btnActivarSms.isChecked();            	
				btnActivarSms.setChecked(Config.isSmsActive);
				writeToFile();
			}
		});

		btnAccept = (Button) findViewById(R.id.btnAccept);
		btnAccept.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{            	
				Config.code=txtPassword.getText().toString();
				writeToFile();
				Toast.makeText(getApplicationContext(), "Cambio de Código: " + Config.code, Toast.LENGTH_LONG).show();
			}
		});

		//Seteo opciones de visualización predeterminadas
		readFromFile();
		txtPassword.setText(Config.code);        
		btnActivado.setChecked(Config.isPositionActive);
		btnActivarSms.setChecked(Config.isSmsActive && Config.isPositionActive);        
		btnActivarSms.setEnabled(Config.isPositionActive);
	}

	private void writeToFile() {
		try {           	
			SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
			Editor editor=preferencias.edit();
			editor.putBoolean("isPositionActive", Config.isPositionActive);
			editor.putBoolean("isActive", Config.isSmsActive);
			editor.putString("code", Config.code);
			editor.commit();			
			Toast.makeText(getApplicationContext(), "Se Guardó la configuración", Toast.LENGTH_LONG).show();
		}	
		catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		} 
	}


	private void readFromFile() {					

		try {	
			SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
			Config.code=preferencias.getString("code", "");
			Config.isSmsActive = preferencias.getBoolean("isActive", false);
			Config.isPositionActive = preferencias.getBoolean("isPositionActive", false);
			System.out.println(Config.print());

		}
		catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		}


	}

}