package com.example.ecommerceapp

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun initializerUI(Id:Int,Name:String,Price:Int,CC:Int,Picture:String)
    {
        itemView.bike_desc.text = "Product Id : " + Id.toString() + "\n" + "Name : " + Name + "\n" + "Ex-Showroom price : " + Price.toString() + "\n" + "CC : " + CC.toString() + "\n"

        Picasso.get().load("http://192.168.43.103/userRegistration/bikes/"+Picture+".jpg").into(itemView.bike_pic);

        itemView.add_cart.setOnClickListener {

            LoggedInUser.ordered_product = Id

            var quantityFragment = QuantityFragment()
            var fragmentManager = (itemView.context as Activity).fragmentManager
            quantityFragment.show(fragmentManager,"TAG")

        }

    }

}