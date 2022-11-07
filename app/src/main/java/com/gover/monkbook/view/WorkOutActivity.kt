package com.gover.monkbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.gover.monkbook.R
import com.gover.monkbook.adapter.WorkOutRecyclerAdapter
import com.gover.monkbook.model.UserData
import kotlinx.android.synthetic.main.activity_work_out.*
import kotlinx.android.synthetic.main.recycler_row.*

class WorkOutActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : WorkOutRecyclerAdapter

    var postListesi = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = WorkOutRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter
        verileriAl()



    }
    fun verileriAl(){

        database.collection("Post").orderBy("tarih",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if (!snapshot.isEmpty){

                        val documents = snapshot.documents

                        postListesi.clear()

                        for (document in documents){
                            val kullaniciEmail = document.get("kullaniciemail") as String
                            if (auth.currentUser?.email == kullaniciEmail){
                                val kullaniciYorum = document.get("kullaniciyorum") as String
                                val gorselUrl = document.get("gorselurl") as String
                                val indirilenPost = UserData(kullaniciEmail,kullaniciYorum,gorselUrl)
                                postListesi.add(indirilenPost)
                            }



                        }

                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.workout_share){
            val intent = Intent(this, WorkOutShareActivity::class.java)
            startActivity(intent)
        }else if (item.itemId == R.id.exit){
            auth.signOut()
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }else if (item.itemId == R.id.current_user) {
            val user = auth.currentUser?.email
            Toast.makeText(applicationContext,user,Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}