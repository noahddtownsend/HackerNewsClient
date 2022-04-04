package com.noahtownsend.hackernewsclient.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.noahtownsend.hackernewsclient.R
import com.noahtownsend.hackernewsclient.Story
import com.noahtownsend.hackernewsclient.databinding.StoryItemBinding

class StoryItemAdapter(private val values: LiveData<List<Story>>, private val context: Context) :
    RecyclerView.Adapter<StoryItemAdapter.StoryItemHolder>() {

    private val _clickedUrl = MutableLiveData("")
    val clickedUrl: LiveData<String> get() = _clickedUrl

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return StoryItemHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryItemHolder, position: Int) {
        holder.bind(values.value!![position], _clickedUrl)
    }

    override fun getItemCount(): Int {
        return values.value?.size ?: 0
    }

    class StoryItemHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story, clickedUrl: MutableLiveData<String>) {
            binding.apply {
                title.text = story.title
                author.text = binding.root.context.getString(R.string.author, story.by)
                score.text = binding.root.context.getString(R.string.score, story.score.toString())
            }

            binding.root.setOnClickListener {
                clickedUrl.value = story.url
            }
        }
    }
}