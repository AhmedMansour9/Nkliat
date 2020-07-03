package com.nkliat.Activites

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.nkliat.Model.Profile_Response
import com.nkliat.R
import com.nkliat.ViewModel.Profile_ViewModel
import kotlinx.android.synthetic.main.nav_header_main.view.*

class Navigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
     companion object {
         lateinit var toolbar: Toolbar
         lateinit var T_Title:TextView
     }
    lateinit var profile: Profile_ViewModel
    lateinit var hView:View
    private lateinit var DataSaver: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(this)
        toolbar = findViewById(R.id.toolbar)
        T_Title=findViewById(R.id.T_Title)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        hView = navView.getHeaderView(0)

        showInfo()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_contactus, R.id.nav_myrquests,
                R.id.nav_setting, R.id.nav_myrquests
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showInfo() {
        profile= ViewModelProvider.NewInstanceFactory().create(
            Profile_ViewModel::class.java)
        this.applicationContext?.let {
            profile.getData(DataSaver.getString("token", null)!!,"en", it).observe(this,
                Observer<Profile_Response> { loginmodel ->
                    if(loginmodel!=null) {

                        hView.T_Name.text=loginmodel.data.fullName
                        hView.E_Email.text=loginmodel.data.email
                        Glide.with(this)
                            .load(loginmodel.data.image_path)
                            .error(R.drawable.emptyprofile)
                            .into(hView.Img_Photo)

                    }
                })
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}