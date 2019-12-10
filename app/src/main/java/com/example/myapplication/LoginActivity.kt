package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val db = FirebaseDatabase.getInstance()
    var userList = ArrayList<String>()

    var loginPasswd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userListRef = db.getReference("")

        userListRef.addChildEventListener(userLoginListener)

        button_login.setOnClickListener {
            var idx = 0
            val userIdText = findViewById<TextView>(R.id.edit_login_id)

            while(idx<userList.size){
                if(userList[idx]==userIdText.text.toString()){
                    break
                }

                idx+=1
            }

            if(idx==userList.size){
                val newLoginRef = db.getReference("${userIdText.text}/login")
                newLoginRef.setValue("${edit_login_passwd.text}")
                newLoginRef.addValueEventListener(newLoginListener)

                Handler().postDelayed({
                    val mainIntent = Intent(this, MainActivity::class.java)
                    mainIntent.putExtra("userId", userIdText.text.toString())
                    startActivity(mainIntent)
                    finish()
                }, 1500)


            }else{
                val loginPasswdRef = db.getReference("${userIdText.text}")
                loginPasswdRef.addChildEventListener(passwdListener)

                Handler().postDelayed({
                    println("test : ${edit_login_passwd.text} : ${edit_login_id.text}")
                    println("what :  ${loginPasswd}")
                    if(loginPasswd==edit_login_passwd.text.toString()){
                        val mainIntent = Intent(this, MainActivity::class.java)
                        mainIntent.putExtra("userId", userList[idx])
                        startActivity(mainIntent)
                        finish()
                    }else{
                        Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }, 1500)
            }
        }



    }

    val userLoginListener = object : ChildEventListener {
        override fun onChildRemoved(p0: DataSnapshot) {
            println("login : removed")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            println("${p0.key.toString()} : userId")
            userList.add(p0.key.toString())
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            println("login : changed")
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            println("login : moved")
        }

        override fun onCancelled(p0: DatabaseError) {
            println("login : cancelled")
        }
    }

    val passwdListener = object : ChildEventListener{
        override fun onChildRemoved(p0: DataSnapshot) {
            println("removed")
        }
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            if(p0.key.toString() == "login"){
                loginPasswd = p0.value.toString()
            }
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            println("changed")
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            println("moved")
        }

        override fun onCancelled(p0: DatabaseError) {
            println("cancelled")
        }
    }

    val newLoginListener = object : ValueEventListener{
        override fun onDataChange(p0: DataSnapshot) {
            println("new ID Generated")
        }

        override fun onCancelled(p0: DatabaseError) {
            println("cancelled")
        }

    }
}



