/*
 * Created by Joseph Dalughut on 16/06/2021, 03:17
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.main.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.farahani.spaceitemdecoration.SpaceItemDecoration
import ng.agrimart.android.R
import ng.agrimart.android.databinding.FragmentCatalogBinding
import ng.agrimart.android.view.main.catalog.data.CatalogCategoryAdapter

/**
 * A [Fragment] which displays a catalog of the items being sold..
 * Use the [CatalogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CatalogFragment : Fragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment DashboardFragment.
         */
        @JvmStatic
        fun newInstance() = CatalogFragment()
    }

    lateinit var binding: FragmentCatalogBinding
    private val viewModel: CatalogViewModel by viewModels()

    private lateinit var categoryAdapter: CatalogCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatalogBinding.inflate(inflater)

        attachCatalogAdapter()
        return binding.root
    }

    private fun attachCatalogAdapter() {
        categoryAdapter = CatalogCategoryAdapter {

        }
        binding.vwRecycler.adapter = categoryAdapter
        binding.vwRecycler.addItemDecoration(
            SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_catalog_category), true)
        )
        viewModel.categoryData.observe(viewLifecycleOwner,  { page ->
            categoryAdapter.submitData(viewLifecycleOwner.lifecycle, page)
        })
    }

}