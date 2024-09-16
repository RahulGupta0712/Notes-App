package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityEditNoteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast

class EditNote : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditNoteBinding.inflate(layoutInflater)
    }

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

        databaseReference = FirebaseDatabase.getInstance().reference

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val key = intent.getStringExtra("key")

        var color = intent.getIntExtra("color", R.color.white)
        binding.root.setBackgroundResource(color)

        if (title != null && content != null && key != null) {
            binding.titleEditText.setText(title)
            binding.contentEditText.setText(content)
        } else {
            FancyToast.makeText(
                this,
                "Some Error Occurred",
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false
            ).show()
            FancyToast.makeText(
                this,
                "Try Again",
                FancyToast.LENGTH_SHORT,
                FancyToast.CONFUSING,
                false
            ).show()
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.saveNote.setOnClickListener {
            val newTitle = binding.titleEditText.text.toString()
            val newContent = binding.contentEditText.text.toString()

            if (newTitle.isEmpty()) {
                FancyToast.makeText(
                    this,
                    "Title can't be empty",
                    FancyToast.LENGTH_LONG,
                    FancyToast.WARNING,
                    false
                ).show()

            } else {
                key?.let {
                    databaseReference.child(key)
                        .setValue(DataModel(key, newTitle, newContent, color))
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                FancyToast.makeText(
                                    this,
                                    "Note saved successfully",
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.SUCCESS,
                                    false
                                ).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                FancyToast.makeText(
                                    this,
                                    "Error : ${it.exception?.message}",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false
                                ).show()
                            }
                        }
                }
            }
        }

        // setting background color
        binding.blue.setOnClickListener {
            color = R.color.light_sky_blue
            binding.root.setBackgroundResource(color)
        }

        binding.seagreen.setOnClickListener {
            color = R.color.light_sea_green
            binding.root.setBackgroundResource(color)
        }

        binding.rose.setOnClickListener {
            color = R.color.misty_rose
            binding.root.setBackgroundResource(color)
        }

        binding.yellow.setOnClickListener {
            color = R.color.light_yellow
            binding.root.setBackgroundResource(color)
        }

        binding.pink.setOnClickListener {
            color = R.color.light_pink
            binding.root.setBackgroundResource(color)
        }

        binding.orange.setOnClickListener {
            color = R.color.light_salmon
            binding.root.setBackgroundResource(color)
        }

        binding.green.setOnClickListener {
            color = R.color._light_green
            binding.root.setBackgroundResource(color)
        }
    }
}