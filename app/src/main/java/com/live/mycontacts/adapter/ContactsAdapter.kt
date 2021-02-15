package com.live.mycontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.live.mycontacts.model.Contact
import com.live.mycontacts.R
import kotlinx.android.synthetic.main.contact_row.view.*

class ContactsAdapter :RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    }
    private val differCallBack=object:DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.number==newItem.number
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
           return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_row,parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact=differ.currentList.get(position)
        holder.itemView.apply {
         name_tv.text=contact.name
         number_tv.text=contact.number
            setOnClickListener {
                onItemClickListener?.let {
                    it(contact)
                }
            }
        }

    }


    private var onItemClickListener:((Contact)->Unit)?=null

    fun setOnItemClickListener(listner:(Contact)->Unit){
        onItemClickListener=listner
    }

}


