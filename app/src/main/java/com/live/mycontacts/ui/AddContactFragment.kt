package com.live.mycontacts.ui

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.live.mycontacts.R
import com.live.mycontacts.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.add_contact_fragment.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddContactFragment : Fragment(R.layout.add_contact_fragment) {
    lateinit var viewModel: ContactViewModel
      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)
          viewModel = (activity as MainActivity).viewModel
          submit_btn.setOnClickListener {
              if (!name_et.text.toString().equals("") && !number_et.text.equals("")) {
                  val addContactsUri: Uri = ContactsContract.Data.CONTENT_URI
                  val rowContactId: Long = getRawContactId()

                  val number = number_et.text.toString()
                  val name = name_et.text.toString()
                  context?.let { it1 -> viewModel.insertContact(it1, addContactsUri, rowContactId, name,number) }

              } else {
                  Snackbar.make(view.rootView, "Please Enter Name and Mobile number", Snackbar.LENGTH_SHORT).show()

              }
          }
      }
    private fun getRawContactId(): Long {
        // Inser an empty contact.
        val contentValues = ContentValues()
        val rawContactUri: Uri? = requireActivity(). getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues)
        // Get the newly created contact raw id.
        return ContentUris.parseId(rawContactUri!!)
    }
}