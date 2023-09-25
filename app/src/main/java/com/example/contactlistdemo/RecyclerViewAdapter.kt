package com.example.contactlistdemo

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private var lstData: ArrayList<ItemsViewModel>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_list, parent, false)
        return MyViewHolder(inflater)
    }

    fun filterList(filterlist: ArrayList<ItemsViewModel>) {
        // below line is to add our filtered list in our course array list.
        lstData = filterlist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = lstData[position]
        //holder.ivUserImage.setImageBitmap(model.image)
        holder.tvUserName.text = model.name
        holder.tvPackageName.text = model.phoneNumber?.get(0) ?: ""

        holder.itemView.setOnClickListener{
            passDataToNextScreen(model, context)

        }
    }

    private fun passDataToNextScreen(model: ItemsViewModel, context: Context) : Resources?{

        val intent = Intent(context, ThirdActivity::class.java)
        Log.d("TAG", "Data passed")

        intent.putExtra("name",model.name)
        intent.putStringArrayListExtra("Phone Number",model.phoneNumber)
        intent.putStringArrayListExtra("Email", model.email)

        context.startActivity(intent)
        return null
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var ivUserImage: ImageView = itemView.findViewById(R.id.ivUserImage)
        var tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        var tvPackageName: TextView = itemView.findViewById(R.id.tvPhoneNumber)



    }

}



///*
//inner class MyViewHolder(val binding: ContactListBinding) :
//    RecyclerView.ViewHolder(binding.root) {
//    val tvUserName = binding.tvUserName
//    val tvPackageName = binding.tvPackageName
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//    return MyViewHolder(
//        ContactListBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//    )
//}
//
//override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//    val item = lstData[position]
//    holder.binding.tvUserName.text = item.name
//    holder.binding.tvPackageName.text = item.phoneNumber
//}
//
//override fun getItemCount(): Int = lstData.size
//}
//*/


















/*private var userModel = ArrayList<ItemsViewModel>()

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder (
    ContactListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
)


override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindUi(position, userModel)
}

override fun getItemCount(): Int {
    return userModel.size
}

class ViewHolder(private val binding: ContactListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindUi(position: Int, userModel: ArrayList<ItemsViewModel>) {

    }
}*/
