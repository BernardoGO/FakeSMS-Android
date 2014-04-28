package com.rane.fakehst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.widget.*;
import android.view.*;


public class getContacts {

	
	
	public Intent getIntent()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        
        return intent;
	
	}
	
	 public String GetName(Activity a, int resultCode, Intent data)
	    {
	    	 
	           if (resultCode == Activity.RESULT_OK) {
	             Uri contactData = data.getData();
	             Cursor c =  a.managedQuery(contactData, null, null, null, null);
	             if (c.moveToFirst()) {
	               String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	               // TODO Whatever you want to do with the selected contact name.
	               
	               String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
	               String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
	               
	               if ( hasPhone.equalsIgnoreCase("1"))
	                   hasPhone = "true";
	               else
	                   hasPhone = "false" ;
	               
	               
	               if (Boolean.parseBoolean(hasPhone)) 
	               {
	                Cursor phones = a.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
	                while (phones.moveToNext()) 
	                {
	                  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                  
	                  return (phoneNumber);
	                }
	                phones.close();
	               }
	               
	               
	             }
	           }
	           	return "";
	    }
	
	
}


