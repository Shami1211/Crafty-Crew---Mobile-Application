package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainDashboard : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    private lateinit var buyerLoginbtn: Button
    private lateinit var buyerRegisterbtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        buyerLoginbtn = findViewById(R.id.buyerLoginbtn)
        buyerRegisterbtn = findViewById(R.id.buyerRegisterbtn)


        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buyerLoginbtn.setOnClickListener(){
            val i = Intent(this,Login::class.java)
            startActivity(i)
        }

        buyerRegisterbtn.setOnClickListener(){
            val i = Intent(this,Register::class.java)
            startActivity(i)
        }

        navigationView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.nav_instructor -> {
                val i = Intent(this,InstructorLogin::class.java)
                startActivity(i)
            }
                R.id.nav_admin -> {
                    val i = Intent(this,Login_Admin::class.java)
                    startActivity(i)
                }

                R.id.nav_seller -> {
                    val i = Intent(this,Activity_Seller_Login::class.java)
                    startActivity(i)
                }

                R.id.nav_about -> {
                    val i = Intent(this,AboutUs::class.java)
                    startActivity(i)
                }
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            true
        }

        return super.onOptionsItemSelected(item)
    }
}