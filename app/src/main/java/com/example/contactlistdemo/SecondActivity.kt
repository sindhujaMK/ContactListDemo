package com.example.contactlistdemo

import android.R.id
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SecondActivity : AppCompatActivity() {
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var rvRecyclerView: RecyclerView? = null
    private var ascendingbtn: ImageButton? = null
    private var descendingBtn: ImageButton? = null
    private var searchView: SearchView? = null
    private var imageView: ImageView? = null
    private var lsData: ArrayList<ItemsViewModel> = ArrayList()


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        ascendingbtn = findViewById(R.id.ibAscImageView)
        rvRecyclerView = findViewById(R.id.rvRecyclerView)
        descendingBtn = findViewById(R.id.ibDesImageView)
        imageView = findViewById(R.id.ivUserImage)
        searchView = findViewById(R.id.svSearchView)
        rvRecyclerView?.layoutManager = LinearLayoutManager(this@SecondActivity)

        lsData = ArrayList()
        getContactList()

        recyclerViewAdapter = RecyclerViewAdapter(lsData, this)

        rvRecyclerView?.adapter = recyclerViewAdapter


        recyclerViewAdapter?.notifyDataSetChanged()

        ascendingbtn!!.setOnClickListener {
            lsData.sortBy {
                it.name?.lowercase()
            }
            recyclerViewAdapter?.filterList(lsData)
        }

        descendingBtn!!.setOnClickListener {
            lsData.sortByDescending {
                it.name?.lowercase()
            }
            recyclerViewAdapter?.filterList(lsData)
        }


        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (lsData.contains(ItemsViewModel())) {
                    (rvRecyclerView?.adapter as Filterable).filter.filter(query)
                } else {
                    Toast.makeText(this@SecondActivity, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filter: ArrayList<ItemsViewModel> = ArrayList()

        for (item in lsData) {
//               try{
            if (item.name?.lowercase()
                    ?.contains(text.lowercase()) == true || (item.phoneNumber?.contains(text) == true)
            ) {
                filter.add(item)
            }
            /* } catch (e: Exception) {
                    Log.e("TAG", "filter: " + e.message)
                }*/
        }
        if (filter.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        }
        recyclerViewAdapter?.filterList(filter)
    }
    /*var projection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.Contacts.PHOTO_ID
    )*/
    @SuppressLint("Range")
    private fun getContactList() {
        val cr = contentResolver
        val cursor: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cursor?.count ?: 0) > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id: String = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String = cursor.getString(
                    cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )

           //val photo =     cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)

                if (cursor.getInt(
                        cursor.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    val phoneNumbers = ArrayList<String>()

                    while (pCur!!.moveToNext()) {
                        val phoneNo: String = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        phoneNumbers.add(phoneNo)

                        Log.i("TAG", "Name: $name")
                        Log.i("TAG", "Phone Number: $phoneNo")
                        Log.d("TAG", "Array Count ${cursor.count}")
                    }

         //get email.---working
                    val emailList = ArrayList<String>()

                        val emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arrayOf(
                                id
                            ), null
                        )
                        while (emailCur!!.moveToNext()) {
                            val email =
                                emailCur!!.getString(emailCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                            emailList.add(email) // Here you will get list of email
                        }
                        emailCur!!.close()


                    val items = ItemsViewModel()
                    items.name = name
                    items.phoneNumber = phoneNumbers
                    items.email = emailList
                    //items.image = myBtmp

                    lsData.add(items)
                    rvRecyclerView?.adapter = RecyclerViewAdapter(lsData, this)

                    pCur.close()
                }
            }

        }

        cursor?.close()
    }
}




/*private fun openPhoto(): InputStream? {
    val contactId : Long? = null
    val contactUri =
        contactId?.let { ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, it) }
    val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
    val cursor =
        contentResolver.query(photoUri, arrayOf(ContactsContract.Contacts.Photo.PHOTO), null, null, null)
            ?: return null
    cursor.use { cursor ->
        if (cursor.moveToFirst()) {
            val data = cursor.getBlob(0)
            if (data != null) {
                return ByteArrayInputStream(data)
            }
        }
    }
    return null
}*/












