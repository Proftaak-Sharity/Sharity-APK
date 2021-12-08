package com.example.sharity_apk

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Insets
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.ui.onNavDestinationSelected
import com.example.sharity_apk.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.NavController




class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            sendEmail()

        }

//        This hides the floating action button (fab) in specific fragments.
        navController.addOnDestinationChangedListener { _, destination,_ ->
            when (destination.id) {
                R.id.ContactForm,
                R.id.CreateCustomer -> binding.fab.hide()
            else -> binding.fab.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_contact -> {
                navController.navigate(R.id.ContactForm)
                return true }
            R.id.action_logout -> {
                navController.navigate(R.id.LoginFragment)
                return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun sendEmail() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("sharity@gmail.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, "Email from Sharity app")
        i.putExtra(Intent.EXTRA_TEXT, "My question:\n\n")
        try {
            startActivity(Intent.createChooser(i, "Choose email client:"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this@MainActivity,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
