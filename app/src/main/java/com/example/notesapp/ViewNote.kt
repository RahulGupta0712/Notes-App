package com.example.notesapp

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityViewNoteBinding
import com.example.notesapp.databinding.ItemViewNoteBinding

class ViewNote : AppCompatActivity() {
    private val binding by lazy{
        ActivityViewNoteBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val color = intent.getIntExtra("color",R.color.white)

        binding.root.setBackgroundResource(color)

        if(title != null && content != null){
            binding.showTitle.text = title
            binding.showContent.text = content
        }

        binding.backButton2.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}