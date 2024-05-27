package com.example.objetivosmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.objetivosmvvm.databinding.ItemCountryBinding
import com.example.objetivosmvvm.model.Country
import com.example.objetivosmvvm.util.getProgressDrawable
import com.example.objetivosmvvm.util.loadImage

class CountriesAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountriesViewHolder(binding)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountriesViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = getProgressDrawable(binding.root.context)

        fun bind(country: Country) {
            binding.tvName.text = country.name
            binding.tvCapital.text = country.capital
            binding.imageView.loadImage(country.flagPNG, progressDrawable)
        }
    }
}