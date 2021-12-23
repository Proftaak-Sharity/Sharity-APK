package com.example.sharity_apk

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.sharity_apk.databinding.ActivityMainBinding
import androidx.navigation.NavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            sendEmail()
        }

//      This hides the floating action button (fab) in specific fragments:
        navController.addOnDestinationChangedListener { _, destination,_ ->
            when (destination.id) {
                R.id.CreateCustomer,
                R.id.CreateDriversLicense,
                R.id.CreateBankaccount,
                R.id.CreateReservation,
                R.id.mapsFragment,
                R.id.GetAllReservations -> binding.fab.hide()
            else -> binding.fab.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

//      Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        navController.addOnDestinationChangedListener { _, destination,_ ->
            when(destination.id) {
                R.id.LoginFragment,
                R.id.CreateCustomer,
                R.id.CreateDriversLicense,
                R.id.CreateBankaccount -> menu.findItem(R.id.button_home).isVisible = false
            else -> menu.findItem(R.id.button_home).isVisible = true
            }

            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//      Sets the destination of the buttons in de options menu:
        return when (item.itemId) {
            R.id.button_home -> {
                navController.navigate(R.id.AccountOverview)
                return true
            }
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                return true
            }
            R.id.action_contact -> true
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

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail() {

//      Opens an popup to select email application and send email to Sharity:
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:info@sharity.nl")
            putExtra(Intent.EXTRA_EMAIL, resources.getString(R.string.sharity_email))
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.sharity_email_subject))
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
