package com.elaniin.technical_test.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.elaniin.technical_test.R
import com.elaniin.technical_test.adapters.RegionsAdapter
import com.elaniin.technical_test.databinding.ActivityLoginBinding
import com.elaniin.technical_test.databinding.ActivityRegionsBinding
import com.elaniin.technical_test.utils.ClickListener
import com.elaniin.technical_test.viewmodels.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegionsActivity : AppCompatActivity(), ClickListener {

    private val viewModel by viewModels<MainMenuViewModel>()
    private var binding: ActivityRegionsBinding? = null

    private val adapter = RegionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)

        binding = ActivityRegionsBinding.inflate(layoutInflater)
        val view = binding!!.root
        viewModel.getRegions()
        binding!!.regionsRecyclerView.adapter = adapter

        setContentView(view)

        registerRegionsData()
    }

    private fun registerRegionsData() {
        viewModel.itemsLiveData.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onClickItemListener(position: Int) {
        TODO("Not yet implemented")
    }
}