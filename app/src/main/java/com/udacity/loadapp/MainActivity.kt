package com.udacity.loadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
        setContentView(binding.root)

        binding.customLoadingButton.setOnClickListener {
            if(mainViewModel.selectedDownloadOption.value?: 0 != 0) {
                System.out.println(mainViewModel.selectedDownloadOption.value)
                binding.customLoadingButton.animateLoading()
                //TODO: Download
                mainViewModel.getFile()
            }
            else{
                Toast.makeText(applicationContext, "Select Something To Download", Toast.LENGTH_LONG).show()
            }
        }


    }
}