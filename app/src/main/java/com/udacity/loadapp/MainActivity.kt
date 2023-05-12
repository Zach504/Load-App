package com.udacity.loadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.udacity.loadapp.databinding.ActivityMainBinding
import com.udacity.loadapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        mainViewModel.downloadInProgress.observe(this, { downloadInProgress ->
            if(downloadInProgress){
                binding.customLoadingButton.isEnabled = false
                binding.customLoadingButton.animateLoading()
            }
            else{
                binding.customLoadingButton.isEnabled = true
                binding.customLoadingButton.stopAnimateLoading()
            }
        })
        binding.customLoadingButton.animateLoading()

        binding.customLoadingButton.setOnClickListener {
            if(mainViewModel.selectedDownloadOption.value?: 0 != 0) {
                //Request permission if required
                mainViewModel.getFile()
            }
            else{
                Toast.makeText(applicationContext, "Select Something To Download", Toast.LENGTH_LONG).show()
            }
        }
    }
}