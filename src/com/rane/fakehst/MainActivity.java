package com.rane.fakehst;



import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	Button btnSend;
	EditText boxMsg;
	EditText boxNumber;
	Spinner boxTipos;
	Button btnSearch;
	AdView ad1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnSend = (Button)findViewById(R.id.button1);
        boxMsg = (EditText)findViewById(R.id.txtMsg);
        boxNumber = (EditText)findViewById(R.id.txtTo);
        boxTipos = (Spinner)findViewById(R.id.boxEstilo);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        
        int Code = (int) ((Math.random() * 3));
        AdView adView;
        if(Code == 1) {
        	adView = new AdView(this, AdSize.BANNER, "a1510d7ee2e2757");
        } else adView = new AdView(this, AdSize.BANNER, "a1510d7ee2e2757");
        
        adView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        LinearLayout layout = (LinearLayout)findViewById(R.id.adLine);
        layout.addView(adView);
        AdRequest request = new AdRequest();
        adView.loadAd(request);
        
        
        ArrayAdapter<CharSequence> adapter
        = ArrayAdapter.createFromResource(this, 
          R.array.estilos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boxTipos.setAdapter(adapter);   
        
        int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sdkVersion < Build.VERSION_CODES.ECLAIR)
        {
        	btnSearch.setVisibility(Button.GONE);
        }
        
        btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
				if (sdkVersion >= Build.VERSION_CODES.ECLAIR)
		        {
				 getContacts getC = new getContacts();
				 
				 
				 startActivityForResult(getC.getIntent(), 3);
		        }
			}
		});
        
        btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SaveMSG();
			}
		});
    }
    
    public void SaveMSG()
    {
    	String phoneNo = boxNumber.getText().toString();
        String message = boxMsg.getText().toString();  
		if (phoneNo.length()>0 && message.length()>0)    
        {
			// TODO Auto-generated method stub
			ContentValues values = new ContentValues();
			values.put("address", boxNumber.getText().toString());
			values.put("body", boxMsg.getText().toString());
			if(boxTipos.getSelectedItemPosition() == 0) // Recebida
     		{
				getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        	builder.setTitle("Success");
	        	builder.setMessage("Message saved, check your inbox.");
	        	builder.setPositiveButton("OK", null);
	        	AlertDialog dialog = builder.show();
     		}
			else
			{
				getContentResolver().insert(Uri.parse("content://sms/sent"), values);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        	builder.setTitle("Success");
	        	builder.setMessage("Message saved, check your sent messages box.");
	        	builder.setPositiveButton("OK", null);
	        	AlertDialog dialog = builder.show();
			}
			
        	boxMsg.setText("");
        }
		 else{
	        	
	        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        	builder.setTitle("Alert");
	        	builder.setMessage("Fill all the text boxes.");
	        	builder.setPositiveButton("OK", null);
	        	AlertDialog dialog = builder.show();
	        	
	        }
    	
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
      super.onActivityResult(reqCode, resultCode, data);
      
      switch (reqCode) {
      case (3) :
    	  int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
      if (sdkVersion >= Build.VERSION_CODES.ECLAIR)
      {
    	  getContacts getC = new getContacts();
    	  String oldCtt = boxNumber.getText().toString();
    	  boxNumber.setText(getC.GetName(this, resultCode, data)/* +" "+ oldCtt*/);
      }
    	  //setTexto(resultCode, data);
    	  break;
      }   
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	private void onMarketLaunch(String url) {
		
		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
				"market://details?id=%s", url)));
		startActivity(donate);
	}
	
	private void onDevLaunch(String url) {
		
		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
				"market://search?q=pub:%s", url)));
		startActivity(donate);
	}
	
	public void share()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.rane.fakehst");
		startActivity(Intent.createChooser(intent, "Share with"));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_rate:    onMarketLaunch("com.rane.fakehst");
	                            break;
	                            
	        case R.id.menu_moreapps:	onDevLaunch("Ranebord");
	        break;    
            
	        case R.id.menu_share:	share();
	        break;

	    }
	    return true;
	}
}