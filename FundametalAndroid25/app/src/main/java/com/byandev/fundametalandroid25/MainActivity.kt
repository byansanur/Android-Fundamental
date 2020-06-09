package com.byandev.fundametalandroid25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_new.setOnClickListener(this)
        button_open.setOnClickListener(this)
        button_save.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showLidtFile()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        edit_title.setText("")
        edit_file.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showLidtFile() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("List from file")
        builder.setItems(items) { _, item ->
            loadData(items[item].toString())
        }
        builder.create().show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        edit_title.setText(fileModel.fileName)
        edit_file.setText(fileModel.data)
        Toast.makeText(this, "Loading data ${fileModel.fileName}", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when{
            edit_title.text.toString().isEmpty() -> Toast.makeText(this, "Title can not be empty", Toast.LENGTH_SHORT)
                .show()
            edit_file.text.toString().isEmpty() -> Toast.makeText(this, "File can not be empty", Toast.LENGTH_SHORT)
                .show()
            else -> {
                val title = edit_title.text.toString()
                val textData = edit_file.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = textData
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving ${fileModel.fileName} file", Toast.LENGTH_SHORT).show()
            }
        }
    }

}