package com.example.week1_assessment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var _txtCustomer1 : TextView
    private lateinit var _txtLogo :  TextView
    private lateinit var _txtLogoDescr: TextView
    private lateinit var _lvTasks: ListView

    private lateinit var _txtTask1: TextView
    private lateinit var _txtTask2: TextView
    private lateinit var _txtTask3: TextView

    private lateinit var _btnCreateAcc: Button

    private var adapterCategory: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(this.localClassName.toString(), "onCreate: ")
        initView()

        _btnCreateAcc.setOnClickListener(this::logIn)
    }

    private fun initView() {
        _txtCustomer1 = findViewById(R.id.txtCustomer1)
        _txtLogo = findViewById(R.id.txtLogo)
        _txtLogoDescr = findViewById(R.id.txtLogoDescr)
        _txtTask1 = findViewById(R.id.txtTask1)
        _txtTask2 = findViewById(R.id.txtTask2)
        _txtTask3 = findViewById(R.id.txtTask3)
        _btnCreateAcc = findViewById(R.id.btnCreateAcc)
        _lvTasks = findViewById(R.id.lvTasks)

        _txtCustomer1.text = getString( R.string.customer_line1)
        _txtLogo.text = getString(R.string.logo)
        _txtLogoDescr.text = getString(R.string.logo_description)
        _txtTask1.text = getString(R.string.task_line1)
        _txtTask2.text = getString(R.string.task_line2)
        _txtTask3.text = getString(R.string.task_line3)
        _btnCreateAcc.text = getString(R.string.btn_create)

        adapterCategory = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.tasks_list)
        )
        _lvTasks.adapter = adapterCategory
    }

    private fun logIn(view: View) {

        val navigateIntent = Intent()  //os checks first if it has enough resources to open tthe activity
        navigateIntent.setClass(this, LogInActivity::class.java)
        startActivity(navigateIntent)


    }

    override fun onStart() {
        super.onStart()
        Log.d(this.localClassName.toString(), "onStart: ")
    }
}