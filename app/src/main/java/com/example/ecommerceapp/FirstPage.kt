package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPage(var user_activity_login:Boolean=true) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)



        signup_redirect.setOnClickListener {

            signup_redirect.visibility = View.GONE
            login_redirect.visibility = View.GONE
            email_edit.visibility = View.VISIBLE
            username_edit.visibility = View.VISIBLE
            password_edit.visibility = View.VISIBLE
            login_signup_button.visibility = View.VISIBLE
            confirm_password_edit.visibility = View.VISIBLE
            intro_img.setImageResource(R.drawable.dino)

            user_activity_login = false

        }

        login_redirect.setOnClickListener {

            signup_redirect.visibility = View.GONE
            login_redirect.visibility = View.GONE
            username_edit.visibility = View.VISIBLE
            password_edit.visibility = View.VISIBLE
            login_signup_button.visibility = View.VISIBLE

            intro_img.setImageResource(R.drawable.lock)

        }

        login_signup_button.setOnClickListener {

            if(user_activity_login==true)
            {
                if(username_edit.text.toString().equals("")
                    ||password_edit.text.toString().equals(""))
                {
                    Toast.makeText(this@FirstPage, "Please fill every field.", Toast.LENGTH_LONG).show()
                }
                else
                {
                    val signup_url:String = "http://192.168.43.103/userRegistration/loginUser.php?username="+username_edit.text.toString()+
                            "&password="+password_edit.text.toString()
                    var requestQ: RequestQueue = Volley.newRequestQueue(this@FirstPage)
                    var stringRequest = StringRequest(Request.Method.GET, signup_url ,
                        Response.Listener { response ->

                            if(response.equals("error"))
                            {
                                Toast.makeText(this@FirstPage,"Enter the correct username and password",Toast.LENGTH_LONG).show()
                                username_edit.text=null
                                password_edit.text=null
                            }

                            else if(response.equals("User found"))
                            {
                                var intent = Intent(applicationContext,BrandsAvailable::class.java)
                                Toast.makeText(this@FirstPage,"LoggedIn",Toast.LENGTH_LONG).show()
                                LoggedInUser.username = username_edit.text.toString()
                                startActivity(intent)
                                finish()
                            }

                        }, Response.ErrorListener { error ->

                            val dialogBuilder = AlertDialog.Builder(this@FirstPage)
                            dialogBuilder.setTitle("Looks like something wrong!!")
                            dialogBuilder.setMessage("Make sure you are connected to the internet or try again after sometime.")
                            dialogBuilder.create().show()

                        })

                    requestQ.add(stringRequest)
                }
            }

            else if(user_activity_login==false)
            {

                if(username_edit.text.toString().equals("")
                    ||email_edit.text.toString().equals("")
                    ||password_edit.text.toString().equals("")
                    ||confirm_password_edit.text.toString().equals("")) {

                    Toast.makeText(this@FirstPage, "Please fill every field.", Toast.LENGTH_LONG).show()


                }

                else {

                    if (password_edit.text.toString()
                            .equals(confirm_password_edit.text.toString())) {
                        //Registration Codes
                        val signup_url:String = "http://192.168.43.103/userRegistration/registration.php?email="+email_edit.text.toString()+
                                "&username="+username_edit.text.toString()+"&password="+password_edit.text.toString()
                        var requestQ: RequestQueue = Volley.newRequestQueue(this@FirstPage)
                        var stringRequest = StringRequest(Request.Method.GET, signup_url ,
                            Response.Listener { response ->

                                if(response.equals("Error"))
                                {
                                    val dialogBuilder = AlertDialog.Builder(this@FirstPage)
                                    dialogBuilder.setTitle("We cannot sign you up")
                                    dialogBuilder.setMessage("Email address or username already exists, Please enter a different value.")
                                    dialogBuilder.create().show()
                                }

                                else if(response.equals("Registered Succesfully"))
                                {
                                    val dialogBuilder = AlertDialog.Builder(this@FirstPage)
                                    dialogBuilder.setTitle("You are registered successfully!")
                                    dialogBuilder.setMessage("Please Login using the registred username and password.")
                                    dialogBuilder.setPositiveButton("Ok") { dialog, which ->
                                       dialog.dismiss()
                                    }
                                    dialogBuilder.create().show()

                                    user_activity_login=true

                                    username_edit.visibility = View.VISIBLE
                                    password_edit.visibility = View.VISIBLE
                                    email_edit.visibility = View.GONE
                                    confirm_password_edit.visibility = View.GONE
                                    login_signup_button.visibility = View.VISIBLE
                                    intro_img.setImageResource(R.drawable.lock)

                                }

                        }, Response.ErrorListener { error ->

                               val dialogBuilder = AlertDialog.Builder(this@FirstPage)
                                dialogBuilder.setTitle("Looks like something wrong!!")
                                dialogBuilder.setMessage("Make sure you are connected to the internet or try again after sometime.")
                                dialogBuilder.create().show()

                        })

                        requestQ.add(stringRequest)


                    } else {
                        Toast.makeText(this@FirstPage, "Both password do not match, Try entering the passwords again", Toast.LENGTH_LONG).show()
                        password_edit.text=null
                        confirm_password_edit.text=null
                    }

                }

            }

        }

    }
}