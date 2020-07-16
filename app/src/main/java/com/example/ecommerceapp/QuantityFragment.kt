package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class QuantityFragment : android.app.DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragment_view= inflater.inflate(R.layout.fragment_quantity, container, false)

        var quantity_text=fragment_view.findViewById<EditText>(R.id.quantity_text)
        var cart_add=fragment_view.findViewById<ImageView>(R.id.cart_add)

        cart_add.setOnClickListener {

           if(quantity_text.text.toString().equals("")||Integer.parseInt(quantity_text.text.toString())<=0)
           {
               Toast.makeText(activity,"The quantity should be more than 1",Toast.LENGTH_SHORT).show()
           }
            else {

                var url =
                    "http://192.168.43.103/userRegistration/temp_order.php?username=" + LoggedInUser.username + "&product_id=" + LoggedInUser.ordered_product + "&quantity=" + quantity_text.text.toString()
                var requestQueue = Volley.newRequestQueue(activity)
                var StringRequest =
                    StringRequest(Request.Method.GET, url, Response.Listener { response ->

                        var Intent = Intent(activity, TempOrderActivity::class.java)
                        startActivity(Intent)

                    }, Response.ErrorListener { error ->

                    })

                requestQueue.add(StringRequest)
            }

        }

        return fragment_view
    }


}