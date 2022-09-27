package com.example.wirtalks.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wirtalks.R
import com.example.wirtalks.model.PodcastEpisodeNetworkEntity
import java.text.SimpleDateFormat
import java.util.*

val MAX_CHARS_PER_DESCRIPTION = 85
// the max number of chars that are shown in the description layout
// after that count, we get ...

class PodcastEpisodesAdapter (private val mList: List<PodcastEpisodeNetworkEntity>) : RecyclerView.Adapter<PodcastEpisodesAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int, episode:PodcastEpisodeNetworkEntity)
    }

    fun setOnIemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // doesn't make sense to get a time date with YEARS...in Milliseconds
        val isOverOneHour = itemsViewModel.audio_length_sec / 3600 > 0
        var dateFormatter = "mm:ss"
        if (isOverOneHour) {
            dateFormatter = "HH:mm:ss"
        }
        val simpleDateFormat = SimpleDateFormat(dateFormatter)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")

        holder.dateTextView.text = msToTimeDate(itemsViewModel.pub_date_ms)
        holder.titleTextView.text = itemsViewModel.title
        holder.descriptionTextView.text = Html.fromHtml(itemsViewModel.description).toString()
        holder.durationTextView.text = simpleDateFormat.format(itemsViewModel.audio_length_sec * 1000)
        holder.playImage.setImageResource(R.drawable.play_)
        holder.detailsTextView.text = "Details"

        holder.playImage.setOnClickListener {
            mListener.onItemClick(position,mList[position])
        }
    }

    private fun msToTimeDate(ms: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(ms)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)
        val playImage: ImageView = itemView.findViewById(R.id.playButton)
        val detailsTextView: TextView = itemView.findViewById(R.id.detailsTextView)

    }
}