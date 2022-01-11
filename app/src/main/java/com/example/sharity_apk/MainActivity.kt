package com.example.sharity_apk

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
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
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.sharity_apk.databinding.ActivityMainBinding
import androidx.navigation.NavController
import com.example.sharity_apk.config.SharityPreferences
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*
import android.util.DisplayMetrics
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//      send email function by pushing fab
        binding.fab.setOnClickListener {
            sendEmail()
        }

//      This hides the floating action button (fab) in specific fragments:
        navController.addOnDestinationChangedListener { _, destination,_ ->
            when (destination.id) {
                R.id.Login,
                R.id.CreateAccount,
                R.id.CreateCustomer,
                R.id.CreateDriversLicense,
                R.id.CreateBankaccount,
                R.id.CreateReservation,
                R.id.CreateCar,
                R.id.GetReservationDetails,
                R.id.GetSearchedCarDetails,
                R.id.mapsFragment3 -> binding.fab.hide()
                else -> binding.fab.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

//      Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        navController.addOnDestinationChangedListener { _, destination,_ ->
            when(destination.id) {
                R.id.Login,
                R.id.CreateAccount,
                R.id.CreateCustomer,
                R.id.CreateDriversLicense,
                R.id.CreateBankaccount -> menu.findItem(R.id.button_home).isVisible = false
            else -> menu.findItem(R.id.button_home).isVisible = true
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val preferences = SharityPreferences(this)

//      Sets the destination of the buttons in de options menu:
        return when (item.itemId) {
            R.id.button_home -> {
                navController.navigate(R.id.AccountOverview)
                return true
            }
            R.id.button_logout -> {
                preferences.clearPreferences()
                logoutDialog()
                return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


//    Function to send email by pushing the fab
    private fun sendEmail() {
        val preferences = SharityPreferences(this)

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse(
            "mailto:info@sharity.nl" +
                    "?subject=${getString(R.string.sharity_email_subject)} " + preferences.getCustomerNumber())
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "${getString(R.string.sharity_email_subject)} ${preferences.getCustomerNumber()}")

        startActivity(Intent.createChooser(intent, getString(R.string.select_email_account)))
    }


//    inflates logout dialog when pushing logout button
    fun logoutDialog() {
        val preferences = SharityPreferences(this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString(R.string.want_to_logout))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            preferences.clearPreferences()
            navController.navigate(R.id.Login)
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }

    private fun setAppLocale(localeCode: String) {
        val resources: Resources = resources
        val displayMetrics: DisplayMetrics = resources.getDisplayMetrics()
        val configuration: Configuration = resources.getConfiguration()

        configuration.setLocale(Locale(localeCode.toLowerCase()))
        resources.updateConfiguration(configuration, displayMetrics)

        configuration.locale = Locale(localeCode.toLowerCase())
        resources.updateConfiguration(configuration, displayMetrics)
    }
}
