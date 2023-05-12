package com.udacity.loadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.udacity.loadapp.databinding.ActivityDetailBinding
import com.udacity.loadapp.viewmodels.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(this.intent.hasExtra(getString(R.string.filename_id))){
            detailViewModel.filename = intent.getStringExtra(getString(R.string.filename_id)).toString()
        }
        if(this.intent.hasExtra(getString(R.string.status_id))){
            detailViewModel.status = intent.getStringExtra(getString(R.string.status_id)).toString()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.detailViewModel = detailViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            binding.motionLayout.transitionToEnd {
                finish()
            }
        }
    }
}