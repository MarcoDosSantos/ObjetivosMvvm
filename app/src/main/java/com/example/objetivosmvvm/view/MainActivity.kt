package com.example.objetivosmvvm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.objetivosmvvm.databinding.ActivityMainBinding
import com.example.objetivosmvvm.model.Country
import com.example.objetivosmvvm.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CountryViewModel
    private val mAdapter = CountriesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refresh()
        setUpCollectors()
        implementSwipeRefresh()
    }

    private fun implementSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener{
            viewModel.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setUpCollectors() {
        lifecycleScope.launch(IO) {
            viewModel.countries.collectLatest {
                it?.let {
                    runOnUiThread {
                        val countries: ArrayList<Country> = ArrayList(it)
                        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                        mAdapter.updateCountries(countries)
                        binding.recyclerViewCountries.apply {
                            layoutManager = linearLayoutManager
                            adapter = mAdapter
                            setHasFixedSize(true)
                        }
                    }

                }

            }
        }

        lifecycleScope.launch(IO) {
            viewModel.loading.collectLatest {
                runOnUiThread {
                    if (it == true) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }

            }
        }

        lifecycleScope.launch(IO) {
            viewModel.countryLoadError.collectLatest {
                runOnUiThread {
                    if (it == true) {
                        binding.tvErrorMessage.visibility = View.VISIBLE
                        binding.recyclerViewCountries.visibility = View.GONE
                    } else {
                        binding.tvErrorMessage.visibility = View.GONE
                        binding.recyclerViewCountries.visibility = View.VISIBLE
                    }
                }

            }
        }
    }
}