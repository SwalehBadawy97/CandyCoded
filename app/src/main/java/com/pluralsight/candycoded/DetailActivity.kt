package com.pluralsight.candycoded

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pluralsight.candycoded.DB.CandyContract.CandyEntry
import com.pluralsight.candycoded.DB.CandyDbHelper
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    var mCandyImageUrl = ""
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = this@DetailActivity.intent
        if (intent != null && intent.hasExtra("position")) {
            val position = intent.getIntExtra("position", 0)
            val dbHelper = CandyDbHelper(this)
            val db = dbHelper.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM candy", null)
            cursor.moveToPosition(position)
            val candyName = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_NAME
                )
            )
            val candyPrice = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_PRICE
                )
            )
            mCandyImageUrl = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_IMAGE
                )
            )
            val candyDesc = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_DESC
                )
            )
            val textView = findViewById<TextView>(R.id.text_view_name)
            textView.text = candyName
            val textViewPrice = findViewById<TextView>(R.id.text_view_price)
            textViewPrice.text = candyPrice
            val textViewDesc = findViewById<TextView>(R.id.text_view_desc)
            textViewDesc.text = candyDesc
            val imageView = findViewById<ImageView>(
                R.id.image_view_candy
            )
            Picasso.with(this).load(mCandyImageUrl).into(imageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail, menu)
        return true
    }

    // ***
    // TODO - Task 4 - Share the Current Candy with an Intent
    // ***
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        createShareIntent()
        return super.onOptionsItemSelected(item)
    }

    private fun createShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareString = SHARE_DESCRIPTION + mCandyImageUrl + HASHTAG_CANDYCODED
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString)
        startActivity(shareIntent)
    }

    companion object {
        const val SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - "
        const val HASHTAG_CANDYCODED = " #candycoded"
    }
}