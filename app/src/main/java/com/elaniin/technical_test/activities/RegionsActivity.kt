package com.elaniin.technical_test.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.elaniin.technical_test.R
import com.elaniin.technical_test.adapters.RegionsAdapter
import com.elaniin.technical_test.databinding.ActivityRegionsBinding
import com.elaniin.technical_test.utils.ClickListener
import com.elaniin.technical_test.viewmodels.RegionsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegionsActivity : AppCompatActivity(), ClickListener {

    private val viewModel by viewModels<RegionsViewModel>()
    private var binding: ActivityRegionsBinding? = null

    private val adapter = RegionsAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)

        binding = ActivityRegionsBinding.inflate(layoutInflater)
        val view = binding!!.root
        viewModel.getRegions()
        binding!!.regionsRecyclerView.adapter = adapter

        binding!!.fabLogout.setOnClickListener{
            viewModel.signOutGoogle(this)
            finish()
        }

        setContentView(view)

        getRegionsData()
        showLoading()
    }

    private fun getRegionsData() {
        viewModel.itemsLiveData.observe(this) {
            adapter.setData(it)
        }
    }

    private fun showLoading() {
        if(viewModel.isLoading.value == true){
            binding!!.progressBar.visibility = View.VISIBLE
        } else {
            binding!!.progressBar.visibility = View.GONE
        }
    }

    override fun onClickItemListener(position: Int) {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra("name", viewModel.itemsLiveData.value?.get(position)?.name)
        startActivity(intent)
    }
}