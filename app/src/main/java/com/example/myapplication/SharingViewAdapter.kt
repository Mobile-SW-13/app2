package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity


class SharingViewAdapter(val context : Context, val listUser : List<String>) : BaseAdapter() {



    override fun getCount(): Int {
        return listUser.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return listUser[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        println("adapter Set")
        if(convertView == null){
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_userlist_detail, null)

            val userIdView = view.findViewById<TextView>(R.id.txt_userlist_name)

            userIdView.text = listUser[position]

            return view
        }else{
            return view!!
        }



    }
}