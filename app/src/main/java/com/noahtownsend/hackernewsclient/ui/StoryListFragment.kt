package com.noahtownsend.hackernewsclient.ui

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.noahtownsend.hackernewsclient.HNApplication
import com.noahtownsend.hackernewsclient.R
import com.noahtownsend.hackernewsclient.databinding.StoryListFragmentBinding
import com.noahtownsend.hackernewsclient.ui.adapter.StoryItemAdapter
import com.noahtownsend.hackernewsclient.viewmodel.StoryListViewModel
import com.noahtownsend.hackernewsclient.viewmodel.StoryListViewModelFactory

class StoryListFragment : Fragment() {

    companion object {
        fun newInstance() = StoryListFragment()
    }

    private val viewModel: StoryListViewModel by viewModels {
        val application = (requireActivity().application as HNApplication)
        StoryListViewModelFactory(application.database.storyDao())
    }
    private lateinit var binding: StoryListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StoryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storiesList = viewModel.getStories(requireContext())
        val adapter = StoryItemAdapter(storiesList, requireContext())
        storiesList.observe(viewLifecycleOwner) {
            adapter.notifyItemInserted(storiesList.value?.size ?: 0)
            val stories = ArrayList(it)
            viewModel.saveStories(requireContext(), stories)
        }

        adapter.clickedUrl.observe(viewLifecycleOwner) { url ->
            if (url == null || url.isBlank()) {
                return@observe
            }
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
                .build()
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
        }

        binding.apply {
            stories.adapter = adapter
            stories.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}