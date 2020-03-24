package no.kristiania.assignment_noforeignland.sqLite

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import no.kristiania.assignment_noforeignland.sqLite.Data.DatabaseHandler


class SQLiteActivity : AppCompatActivity() {

    private var btn_Store: Button? = null
    private var et_name: EditText? = null
    private var databaseHelper: DatabaseHandler? = null
    private var display_list: TextView? = null
    private var arrayList: ArrayList<String>? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAllFromSQLiteDB()

        btn_Store!!.setOnClickListener {

            getAllFromSQLiteDB()


            val pattern = Regex("[[a-zA-Z]+[//s{1}]?]*")
            val matched = pattern.containsMatchIn(et_name.toString())
            if(matched){
                Toast.makeText(this@SQLiteActivity, "Stored Successfully!", Toast.LENGTH_SHORT).show()
                databaseHelper!!.addGroceryDetail(et_name!!.text.toString())
            }else{
                Toast.makeText(this@SQLiteActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
            et_name!!.setText("")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getAllFromSQLiteDB() {

        arrayList = databaseHelper!!.allPlaces
        display_list!!.text = ""
        for (i in arrayList!!.indices) {
            display_list!!.text = display_list!!.text.toString() + "\n" + arrayList!![i]
        }
    }
}