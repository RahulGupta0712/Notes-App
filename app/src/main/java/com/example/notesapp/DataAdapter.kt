package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.notesapp.databinding.ItemViewNoteBinding
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast
import render.animations.Render
import render.animations.Zoom

class DataAdapter(private var datalist: ArrayList<DataModel>,private var context: Context) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {
    inner class MyViewHolder(var binding: ItemViewNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewNoteBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        startAnimation(holder.binding.root)

        holder.binding.title.text = datalist[position].title
        holder.binding.content.text = datalist[position].content
        holder.binding.root.backgroundTintList =
            ContextCompat.getColorStateList(context, datalist[position].color)

        holder.binding.root.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.menu_notes, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.viewNote -> {
                        val intent = Intent(context, ViewNote::class.java)
                        intent.putExtra("title", datalist[position].title)
                        intent.putExtra("content", datalist[position].content)
                        intent.putExtra("color", datalist[position].color)
                        context.startActivity(intent)
                        true
                    }

                    R.id.editNote -> {
                        val intent = Intent(context, EditNote::class.java)
                        intent.putExtra("title", datalist[position].title)
                        intent.putExtra("content", datalist[position].content)
                        intent.putExtra("color", datalist[position].color)
                        intent.putExtra("key", datalist[position].key)
                        context.startActivity(intent)
                        true
                    }

                    R.id.deleteNote -> {
                        val databaseReference = FirebaseDatabase.getInstance().reference

                        SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("Delete Note")
                            .setContentText("Are You Sure ?")
                            .setCustomImage(R.drawable.delete_button)
                            .setConfirmButton("YES") { dialog ->
                                databaseReference.child(datalist[position].key).removeValue()
                                    .addOnSuccessListener {
                                        FancyToast.makeText(
                                            context,
                                            "Note deleted successfully",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.SUCCESS,
                                            false
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        FancyToast.makeText(
                                            context,
                                            "Error : ${e.message}",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.ERROR,
                                            false
                                        ).show()
                                    }
//                                notifyDataSetChanged()
                                notifyItemRemoved(position)
                                dialog.dismiss()
                            }
                            .setCancelButton("NO") { dialog ->
                                dialog.dismiss()
                            }
                            .show()

                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun startAnimation(view: View) {
        val render = Render(context)
        render.setAnimation(Zoom().In(view))
        render.start()
    }
}