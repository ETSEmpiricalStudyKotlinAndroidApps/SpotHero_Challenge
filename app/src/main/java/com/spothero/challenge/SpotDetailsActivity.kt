package com.spothero.challenge

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SpotDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_details)

        val spotId = intent.getIntExtra(EXTRA_SPOT_ID, -1)
        Toast.makeText(this, spotId.toString(), Toast.LENGTH_SHORT).show()
    }
}