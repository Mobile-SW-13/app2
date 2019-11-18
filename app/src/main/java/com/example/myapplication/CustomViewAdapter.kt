package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView


class CustomViewAdapter(val wordListCV: ArrayList<Word>, val type : Int): BaseAdapter(){

    var defRefresh : Int = -1

    override fun getCount(): Int {
        return wordListCV.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return wordListCV[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var context = parent




        if (view == null) {
            if(type==2)
                view = LayoutInflater.from(context!!.context).inflate(R.layout.layout_word_detail, null) as View
            else if(type==1)
                view = LayoutInflater.from(context!!.context).inflate(R.layout.layout_word_mean, null) as View
            else if(type==0)
                view = LayoutInflater.from(context!!.context).inflate(R.layout.layout_word_name, null) as View
        }

        if(type == 2){

            var wordMean = view?.findViewById(R.id.txt_word_mean) as TextView
            var wordName = view?.findViewById(R.id.txt_word_name) as TextView
            var buttonDeleteWord = view?.findViewById(R.id.btn_word_delete) as Button

            var listViewItem = wordListCV[position]

            wordMean.setText(listViewItem.wordMean)
            wordName.setText(listViewItem.wordName)

            buttonDeleteWord.setOnClickListener {
                wordListCV.remove(listViewItem)
                notifyDataSetChanged()

            }


            return view

        }else if(type == 1){
            var wordMean = view?.findViewById(R.id.txt_word_mean) as TextView
            var wordName = view?.findViewById(R.id.txt_word_name) as TextView
            var buttonDeleteWord = view?.findViewById(R.id.btn_word_delete) as Button

            var listViewItem = wordListCV[position]

            wordMean.setText(listViewItem.wordMean)
            wordName.setText("")

            buttonDeleteWord.setOnClickListener {
                wordListCV.remove(listViewItem)
                notifyDataSetChanged()
            }

            return view

        }else if(type==0){
            var wordMean = view?.findViewById(R.id.txt_word_mean) as TextView
            var wordName = view?.findViewById(R.id.txt_word_name) as TextView
            var buttonDeleteWord = view?.findViewById(R.id.btn_word_delete) as Button

            var listViewItem = wordListCV[position]

            wordMean.setText("")
            wordName.setText(listViewItem.wordName)

            buttonDeleteWord.setOnClickListener {
                wordListCV.remove(listViewItem)
                notifyDataSetChanged()
            }

            return view

        }else{
            var wordMean = view?.findViewById(R.id.txt_word_mean) as TextView
            var wordName = view?.findViewById(R.id.txt_word_name) as TextView
            var buttonDeleteWord = view?.findViewById(R.id.btn_word_delete) as Button

            //var listViewItem = wordList[position]

            wordMean.setText("")
            wordName.setText("")

            buttonDeleteWord.setOnClickListener {

            }

            return view

        }
    }

}

