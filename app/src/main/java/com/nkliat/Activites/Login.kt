package com.nkliat.Activites

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_login.*
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.nkliat.Loading
import com.nkliat.Model.Register_Model
import com.nkliat.R
import com.nkliat.ViewModel.Register_ViewModel
import com.nkliat.utils.NetworkCheck
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class Login : AppCompatActivity() {
    private lateinit var dataSaver: SharedPreferences
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    var file: File? = File("")
    var REQUEST_WRITE_PERMISSION:Int=786
    private var GALLERY = 0

    var isChecked:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(this);
        First_Image()


    }

    private fun First_Image() {
        Img_Addphoto1.setOnClickListener(){
            val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else {
                GALLERY=1
                choosePhotoFromGallary(GALLERY)

            }
        }
    }
    fun choosePhotoFromGallary(number:Int) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, number)
    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(
             this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_PERMISSION)
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
//        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
//            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_PERMISSION -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                    choosePhotoFromGallary(GALLERY)
                }
            }
        }
    }
    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!,
                    this
                )
                file = File(filePath)
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(this).load(file).into(Img_Photo);

                    Img_Addphoto1.visibility=View.GONE

                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }


    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            return contentURI.getPath()!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }

    fun Rela_SignIn(view: View) {
        Constrain_SignIn.setBackgroundColor(Color.WHITE);
        T_Signin.setTextColor(ContextCompat.getColor(this,R.color.gray))
        Constrain_Signip.setBackgroundColor(ContextCompat.getColor(this,R.color.gray))
        T_Signup.setTextColor(Color.WHITE)
        constrain_Register.visibility=GONE
        constrain_Login.visibility=VISIBLE
        Btn_Face.visibility= VISIBLE
        Btn_Google.visibility= VISIBLE
    }

    fun Rela_SignUp(view: View) {
        Constrain_SignIn.setBackgroundColor(ContextCompat.getColor(this,R.color.gray));
        T_Signin.setTextColor(Color.WHITE)
        Constrain_Signip.setBackgroundColor(Color.WHITE)
        T_Signup.setTextColor(ContextCompat.getColor(this,R.color.gray))
        constrain_Register.visibility= VISIBLE
        constrain_Login.visibility= GONE
        Btn_Face.visibility= GONE
        Btn_Google.visibility= GONE

    }

    fun Btn_Register(view: View) {
        var Name = E_NameRegister.text.toString()
        var Phone = e_PhoneRegister.text.toString()
        var email = e_EmailRegister.text.toString()
        var Password = e_PasswordRegister.text.toString().trim()

        if(Name.isEmpty()){
            E_NameRegister.error = "Name required"
            E_NameRegister.requestFocus()
        }
         if(Phone.isEmpty()){
            e_PhoneRegister.error = "Phone required"
            e_PhoneRegister.requestFocus()
        }
         if(email.isEmpty()){
            e_EmailRegister.error = "Email required"
            e_EmailRegister.requestFocus()
        }
         if(Password.isEmpty()){
            e_PasswordRegister.error = "Password required"
            e_PasswordRegister.requestFocus()
        }
         if(Password.length<6||Password.length>16){
             e_PasswordRegister.error = "Min password must be at least 6 Chrachter and  max 16 Chrachter "
            e_PasswordRegister.requestFocus()
        }
        if(!isEmailValid(email)){
            e_EmailRegister.error = "Valid Email required"
            e_EmailRegister.requestFocus()
        } else if(!Name.isEmpty()&&!Phone.isEmpty() &&!email.isEmpty() &&!Password.isEmpty()  &&Password.length>=6||Password.length<=16 ) {
            if(!NetworkCheck.isConnect(this)) {
                startActivity(Intent(this, NoItemInternetImage::class.java))
            }
            var RegisterViewModel: Register_ViewModel = ViewModelProviders.of(this)[Register_ViewModel::class.java]
           Loading.Show(this)
            view.isEnabled=false
            view.hideKeyboard()
            RegisterViewModel.getData(file!!,Name,email, Password,Phone, applicationContext).observe(this,
                Observer<Register_Model> { loginmodel ->
                    view.isEnabled=true
                    Loading.Disable()
                    if (loginmodel != null) {
                        val customer_id = loginmodel.accessToken
                        dataSaver.edit().putString("token", customer_id).apply()
                        val intent = Intent(this, Navigation::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val status: Boolean = RegisterViewModel.getStatus()
                        if (status == true) {
                            Toast.makeText(
                                applicationContext,
                                applicationContext.getString(R.string.emailisused),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                    )

        }
    }
    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    fun Btn_Login(view: View) {
        var email = E_Email.text.toString()
        var Password = E_Password.text.toString().trim()
        if(email.isEmpty()){
            E_Email.error = "Email required"
            E_Email.requestFocus()
        }
        if(Password.isEmpty()){
            E_Password.error = "Password required"
            E_Password.requestFocus()
        }
        if(Password.length<6||Password.length>16){
            E_Password.error = "Min password must be at least 6 Chrachter and  max 16 Chrachter "
            E_Password.requestFocus()
        }
        if(!isEmailValid(email)){
            E_Email.error = "Valid Email required"
            E_Email.requestFocus()
        } else if( !email.isEmpty() &&!Password.isEmpty()  &&Password.length>=6||Password.length<=16 ) {
            if(!NetworkCheck.isConnect(this)) {
                startActivity(Intent(this, NoItemInternetImage::class.java))
            }
                var RegisterViewModel: Register_ViewModel =
                    ViewModelProviders.of(this)[Register_ViewModel::class.java]
            Loading.Show(this)
                view.isEnabled=false
                view.hideKeyboard()
                RegisterViewModel.getLogin(email, Password, applicationContext).observe(this,
                    Observer<Register_Model> { loginmodel ->
                       Loading.Disable()
                        view.isEnabled=true
                        if (loginmodel != null) {
                            val customer_id = loginmodel.accessToken
                            dataSaver.edit().putString("token", customer_id).apply()
                            val intent = Intent(this, Navigation::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                Toast.makeText(
                                    applicationContext,
                                    applicationContext.getString(R.string.wrongemailorpass),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })

        }


    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun checkEye(view: View) {

       if(isChecked){
           E_Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
           icon_Eye?.setImageDrawable(this.getDrawable(R.drawable.icon_darkeye))
           isChecked=false
       }   else{
           E_Password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
           icon_Eye?.setImageDrawable(this.getDrawable(R.drawable.iconeye))
          isChecked=true
       }
        E_Password.setSelection(E_Password.length());

    }


}

