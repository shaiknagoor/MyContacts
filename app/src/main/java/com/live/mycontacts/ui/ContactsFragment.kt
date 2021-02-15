package com.live.mycontacts.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.mycontacts.R
import com.live.mycontacts.adapter.ContactsAdapter
import com.live.mycontacts.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.fragment_contacts.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    lateinit var viewModel: ContactViewModel
    lateinit var contactsAdapter: ContactsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecycler()
        val readContactsUri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = context?.let {
            it.getContentResolver().query(readContactsUri, null, null, null, null)  }
        cursor?.let {
       val list =viewModel.getContacts(cursor)
                  list?.let {
                contactsAdapter.differ.submitList(list)

            }
        }


    }
    private fun setupRecycler() {
        contactsAdapter= ContactsAdapter()
        recyclerView.apply {
            adapter=contactsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }
}