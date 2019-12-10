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

                Handler().postDelayed({
                    val mainIntent = Intent(this, MainActivity::class.java)
                    mainIntent.putExtra("userId", userIdText.text.toString())
                    startActivity(mainIntent)
                    finish()
                }, 1500)


            }else{
                val loginPasswdRef = db.getReference("${userIdText.text}/login")
                loginPasswdRef.addChildEventListener(passwdListener)

                Handler().postDelayed({
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            println("${p0.key.toString()} : userId")
            userList.add(p0.key.toString())
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            println("changed")
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    val passwdListener = object : ChildEventListener{
        override fun onChildRemoved(p0: DataSnapshot) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            if(p0.key.toString() == "login"){
                loginPasswd = p0.value.toString()
            }
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    val newLoginListener = object : ValueEventListener{
        override fun onDataChange(p0: DataSnapshot) {
            println("new ID Generated")
        }

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}



