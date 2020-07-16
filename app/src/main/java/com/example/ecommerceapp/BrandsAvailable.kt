package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_brands_available.*

class BrandsAvailable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands_available)

        var brand_list = ArrayList<String>()

        var mArrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, brand_list)

        brands_list_disp.adapter = mArrayAdapter

        var brand_url = "http://192.168.43.103/userRegistration/fetchProducts.php"
        var requestQueue = Volley.newRequestQueue(this@BrandsAvailable)

        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, brand_url, null, Response.Listener { response ->

                for (jsonObjects in 0.until(response.length())) {
                    brand_list.add(response.getJSONObject(jsonObjects).getString("Brand"))
                    mArrayAdapter.notifyDataSetChanged()
                }

            }, Response.ErrorListener { error ->

            })

        requestQueue.add(jsonArrayRequest)

        brands_list_disp.setOnItemClickListener { parent, view, position, id ->

            val tapped_brand = brand_list.get(position)
            var intent: Intent = Intent(this@BrandsAvailable,BrandProducts::class.java)

            intent.putExtra("TAPPED_BRAND",tapped_brand)

            startActivity(intent)

        }

    }
    override fun onCreateOptionsMenu(menu: Menu?):Boolean
    {
        menuInflater.inflate(R.menu.view_cart,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.cart_view)
        {
            var Intent = Intent(this,TempOrderActivity::class.java)
            startActivity(Intent)
        }
        return super.onOptionsItemSelected(item)
    }
}