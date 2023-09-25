package com.example.contactlistdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    private var nameTextView: TextView? = null
    private lateinit  var userName : String
    private lateinit  var userPhNumber : ArrayList<String>
    private lateinit var userEmailId : ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        nameTextView = findViewById(R.id.tvSecUserName)

        /*val bitmap = this.intent.getParcelableExtra<Parcelable>("bitmap") as Bitmap?
        ivSecUserImage.setImageBitmap(bitmap)*/

        val contactsList = intent

         userName = contactsList.getStringExtra("name")?: ""
         userPhNumber = contactsList.getStringArrayListExtra("Phone Number") as ArrayList<String>
        userEmailId = contactsList.getStringArrayListExtra("Email") as ArrayList<String>

        tvSecUserName.text = userName
        tvSecPhoneNum.text = userPhNumber[0]
        tvEmailId.text = userEmailId[0]


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iShareButton -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Name : $userName \nPhone Number : $userPhNumber \n$userEmailId" )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            R.id.iActionSetting -> {
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}