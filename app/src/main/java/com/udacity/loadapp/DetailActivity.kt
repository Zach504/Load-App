package com.udacity.loadapp

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.udacity.loadapp.databinding.ActivityDetailBinding
import com.udacity.loadapp.viewmodels.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.cancelAll()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        if(this.intent.hasExtra(getString(R.string.filename_id))){
            detailViewModel.filename = intent.getStringExtra(getString(R.string.filename_id)).toString()
        }
        if(this.intent.hasExtra(getString(R.string.status_id))){
            detailViewModel.status = intent.getStringExtra(getString(R.string.status_id)).toString()
            if(detailViewModel.status.equals(getString(R.string.success))){
                binding.status.setTextColor(getColor(R.color.green))
            }
            else{
                binding.status.setTextColor(getColor(R.color.red))
            }
        }
        binding.detailViewModel = detailViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        binding.returnButton.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        //I am not a huge fan of this but in order to get any consistency in whether or not the animation is visible this seemed to be necessary
        lifecycleScope.launch(Dispatchers.IO){
            sleep(100)
            withContext(Dispatchers.Main) {
                binding.motionLayout.transitionToEnd()
            }
        }

    }


}