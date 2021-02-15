package com.live.mycontacts.viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.live.mycontacts.model.Contact
import kotlinx.coroutines.launch


class ContactViewModel: ViewModel() {
    var list=ArrayList<Contact>();
    fun getContacts(cursor: Cursor):List<Contact> {
viewModelScope.launch {
    cursor.moveToFirst()
    do {
        // Get contact display name.
        val displayNameIndex =
            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val userDisplayName = cursor.getString(displayNameIndex)
        // Get contact phone number.
        val phoneNumberIndex =
            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val phoneNumber = cursor.getString(phoneNumberIndex)

        val contac= Contact(userDisplayName, phoneNumber)
        list.add(contac)

      } while (cursor.moveToNext())

}

        Log.e("aaa_list-", list.toString())
        return list
    }
     fun insertContact(context: Context,addContactsUri: Uri, rawContactId: Long, userName: String,mobileNumber:String) {
        val contentValues = ContentValues()
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, userName)
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
         context.getContentResolver().insert(addContactsUri, contentValues)
         Toast.makeText(context,"Successfully inserted  ",Toast.LENGTH_SHORT).show()

    }
     }








