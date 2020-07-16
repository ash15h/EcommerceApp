package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_brand_products.*

class BrandProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_products)

        var tapped_brand:String = intent.getStringExtra("TAPPED_BRAND")
        Toast.makeText(this,tapped_brand,Toast.LENGTH_LONG).show()

        var mArrayList:ArrayList<Products> = ArrayList<Products>()

        var mAdapter = Adapter(mArrayList)
        var mLinearLayoutManager = LinearLayoutManager(this)

        recycler_view.layoutManager = mLinearLayoutManager
        recycler_view.adapter = mAdapter

        var brand_url = "http://192.168.43.103/userRegistration/fetchProductsUsingBrands.php?Brand="+tapped_brand
        var requestQueue = Volley.newRequestQueue(this@BrandProducts)

        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, brand_url, null, Response.Listener { response ->

                for (jsonObjects in 0.until(response.length())) {
                    val products:Products = Products(response.getJSONObject(jsonObjects).getInt("Id"),response.getJSONObject(jsonObjects).getString("Name"),response.getJSONObject(jsonObjects).getInt("Price"),response.getJSONObject(jsonObjects).getInt("CC"),response.getJSONObject(jsonObjects).getString("Picture"))

                    mArrayList.add(products)

                    mAdapter.notifyDataSetChanged()
                }

            }, Response.ErrorListener { error ->

            })

        requestQueue.add(jsonArrayRequest)

//        for (x in 0..10)
//        {
//            var products:Products = Products(x,"hey",x,x)
//            mArrayList.add(products)
//            mAdapter.notifyDataSetChanged()
//        }

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