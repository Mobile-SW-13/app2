package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_find.*
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity()  {

    val db = FirebaseDatabase.getInstance()
    var userList = ArrayList<String>()
    var otherWordList = ArrayList<Word>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        if(intent.hasExtra("userList")){
            var getFindBundle = intent.getBundleExtra("userList")
            userList = getFindBundle.get("user") as ArrayList<String>
        }

        println(userList[0])

        var listview = findViewById<ListView>(R.id.listview_userList)
        var adapter = SharingViewAdapter(this, userList)
        listview.adapter = adapter

        button_share_search.setOnClickListener {
            var idx = 0

            while(idx<userList.size){
                if(userList[idx]==edit_share_search.text.toString()){
                    break
                }

                idx+=1
            }

            if(idx!=userList.size){
                val otherRef = db.getReference("${adapter.getItem(idx)}/wordNote")

                otherRef.addChildEventListener(postListner)

                Handler().postDelayed({
                    if(otherWordList.size!=0){
                        var otherBundle  = Bundle()
                        otherBundle.putSerializable("word", otherWordList)
                        val intentOther = Intent(this,OtherActivity::class.java)
                        intentOther.putExtra("wordList", otherBundle)
                        startActivity(intentOther)
                    }else{

                    }

                }, 1500)
            }else{
                Toast.makeText(this, "찾으시는 유저는 없습니다.", Toast.LENGTH_SHORT).show();
            }



        }

        listview.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItemId(position) // The item that was clicked


            println("pos : ${adapter.getItem(position)}")
            val otherRef = db.getReference("${adapter.getItem(position)}/wordNote")

            otherRef.addChildEventListener(postListner)

            Handler().postDelayed({
                if(otherWordList.size!=0){
                    var otherBundle  = Bundle()
                    otherBundle.putSerializable("word", otherWordList)
                    val intentOther = Intent(this,OtherActivity::class.java)
                    intentOther.putExtra("wordList", otherBundle)
                    intentOther.putExtra("userID", adapter.getItem(position).toString())
                    startActivity(intentOther)
                }else{
                    Toast.makeText(this, "해당 유저의 단어장은 비어있습니다.", Toast.LENGTH_SHORT).show();
                }

            }, 1500)




        }



        button_share_back.setOnClickListener {
            finish()
        }



    }

    val postListner = object : ChildEventListener {
        override fun onChildRemoved(p0: DataSnapshot) {
            println("share : removed")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            println("share : added")
            otherWordList.add(Word(p0.key.toString(),p0.value.toString()))
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            println("share : changed")
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            println("share : moved")
        }

        override fun onCancelled(p0: DatabaseError) {
            println("share : cancelled")
        }

    }

    val searchListner = object : ChildEventListener {
        override fun onChildRemoved(p0: DataSnapshot) {
            println("share : removed")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            println("share : added")
            otherWordList.add(Word(p0.key.toString(),p0.value.toString()))
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            println("share : changed")
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            println("share : moved")
        }

        override fun onCancelled(p0: DatabaseError) {
            println("share : cancelled")
        }

    }

    override fun onRestart() {
        super.onRestart()
        otherWordList.clear()
    }


}