package com.example.simplenotepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplenotepad.Models.WorldData
import com.example.simplenotepad.databinding.ActivityAddWorldBinding
import java.text.SimpleDateFormat
import java.util.*

class add_world : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorldBinding

    private lateinit var wdata : WorldData
    private lateinit var oldwdata : WorldData
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddWorldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldwdata = intent.getSerializableExtra("current_world") as WorldData
            binding.worldTitleEdit.setText(oldwdata.title)
            binding.worldSummaryEdit.setText(oldwdata.summary)
            isUpdate = true
        }catch (e : Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener{
            val title = binding.worldTitleEdit.text.toString()
            val summary = binding.worldSummaryEdit.text.toString()

            if(title.isNotEmpty() || summary.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if(isUpdate){
                    wdata = WorldData(
                        oldwdata.id, title, summary, formatter.format(Date())
                    )
                }
                else{
                    wdata = WorldData(
                        null, title, summary, formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("world", wdata)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            else{
                Toast.makeText(this@add_world, "Text is empty. Please enter something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
    }
}