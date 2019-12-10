package com.example.myapplication

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase


class Fragment_both : Fragment() {

    val bothDB = FirebaseDatabase.getInstance()

    var wordListFB = ArrayList<Word>()
    var userId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_both, container, false)


        val wordListtemp  = arguments!!.get("word") as ArrayList<Word>

        var idx = 0
        while(idx<wordListtemp.size){
            wordListFB.add(wordListtemp[idx])

            idx+=1
        }

        userId = arguments!!.get("userId") as String


        var adapter = CustomViewAdapter(wordListFB,2, userId )


        var listview = layout.findViewById(R.id.listview_word_both) as ListView

        listview.setAdapter(adapter)

        val button = layout.findViewById<Button>(R.id.btn_add_word_both)

        button.setOnClickListener{

            println("Test Button In Fragment")

            val popupView = getLayoutInflater().inflate(R.layout.popup_layout_word_detail_word_add,null)
            val buttonClose = popupView.findViewById<Button>(R.id.btn_popup_close)
            val buttonAdd = popupView.findViewById<Button>(R.id.btn_popup_add)
            var textName = popupView.findViewById(R.id.txt_edit_word_name) as EditText
            var textMean = popupView.findViewById(R.id.txt_edit_word_mean) as EditText


            val popupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)



            buttonClose.setOnClickListener {
                popupWindow.dismiss()
            }

            buttonAdd.setOnClickListener{
                if(textMean.text.toString() == ""||textName.text.toString() == ""){
                    Toast.makeText(container?.context, "?", Toast.LENGTH_LONG)
                }else{


                    val bothRef = bothDB.getReference("${userId}/wordNote/${textName.text.toString()}")
                    bothRef.setValue("${textMean.text}")
                    wordListFB.add(Word(textName.text.toString(),textMean.text.toString()))
                    adapter.notifyDataSetChanged()
                    popupWindow.dismiss()



                }
            }

            popupWindow.setFocusable(true)

            popupWindow.showAtLocation(popupView,Gravity.CENTER,0,0)

        }

        return layout

    }



}