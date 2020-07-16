package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_temp_order.*

class TempOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_order)

        var mArrayList:ArrayList<String> = ArrayList()
        var mAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,mArrayList)
        cart_item.adapter = mAdapter

        var orders_url = "http://192.168.43.103/userRegistration/fetch_temp_order.php?username="+LoggedInUser.username
        var requestQueue = Volley.newRequestQueue(this@TempOrderActivity)

        var jsonArrayRequest = JsonArrayRequest(Request.Method.GET, orders_url, null, Response.Listener { response ->

            for (jsonObjects in 0.until(response.length())) {

                var total_price:Int = response.getJSONObject(jsonObjects).getInt("price")*response.getJSONObject(jsonObjects).getInt("quantity")

                mArrayList.add("Product id : "+response.getJSONObject(jsonObjects).getString("id")+"\n"+"Product name : "+response.getJSONObject(jsonObjects).getString("name")+"\n"+"Total price : "+total_price+"\n"+"Quantity ordered : "+response.getJSONObject(jsonObjects).getString("quantity"))
                mAdapter.notifyDataSetChanged()
            }

        }, Response.ErrorListener { error ->

        })

        requestQueue.add(jsonArrayRequest)



    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean
    {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId==R.id.cont_shop) {
            var intent: Intent = Intent(this,BrandsAvailable::class.java)
            startActivity(intent)

        }
        if(item.itemId==R.id.clear_order)
        {
            var url="http://192.168.43.103/userRegistration/delete_temp_order.php?username="+LoggedInUser.username
            var requestQueue = Volley.newRequestQueue(this@TempOrderActivity)
            var string_requst = StringRequest(Request.Method.GET,url,Response.Listener {response ->

                Toast.makeText(this@TempOrderActivity,"Cart cleared",Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this,BrandsAvailable::class.java)
                startActivity(intent)

            },Response.ErrorListener {error ->

            })

            requestQueue.add(string_requst)
        }
            return super.onOptionsItemSelected(item)
    }
}