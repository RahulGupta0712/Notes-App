package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var Adapter : DataAdapter
    private lateinit var datalist : ArrayList<DataModel>
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        datalist = ArrayList()

        Adapter = DataAdapter(datalist, this)

        binding.recyclerViewNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewNotes.adapter = Adapter

        binding.spinKit.setIndeterminateDrawable(ThreeBounce())

        if(datalist.isEmpty()){
            binding.recyclerViewNotes.isVisible = true
        }

        // fetch data from database
        databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()

                binding.spinKit.isVisible = true
                binding.recyclerViewNotes.isVisible = false

                for(snap in snapshot.children){
                    val note = snap.getValue(DataModel::class.java)
                    note?.let {
                        datalist.add(note)
                    }
                }

                datalist.reverse()  // showing latest first

                Adapter.notifyDataSetChanged()

                binding.spinKit.isVisible = false
                binding.recyclerViewNotes.isVisible = true
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // add note
        binding.addNoteButton.setOnClickListener {
            val key = databaseReference.push().key
            val intent = Intent(this, EditNote::class.java)
            intent.putExtra("title", "")
            intent.putExtra("content", "")
            intent.putExtra("key", key)
            intent.putExtra("color", R.color.white)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finishAffinity()
        }
        return super.onKeyDown(keyCode, event)
    }

}