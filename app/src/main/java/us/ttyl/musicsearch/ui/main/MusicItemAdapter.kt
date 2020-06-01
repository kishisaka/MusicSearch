package us.ttyl.musicsearch.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import us.ttyl.musicsearch.R
import us.ttyl.musicsearch.dao.MusicItem

class MusicItemAdapter(private val context: Context,
                       private val onClickCallBack: MusicListFragment.OnClickCallBack):
    RecyclerView.Adapter<MusicItemAdapter.CustomViewHolder>() {

    private var musicItems: List<MusicItem> = listOf()

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var artwork: ImageView = view.findViewById(R.id.artwork)
        var artistName: TextView = view.findViewById(R.id.artistName)
        var collectionName: TextView = view.findViewById(R.id.collectionName)
        var trackName: TextView = view.findViewById(R.id.trackName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.music_item,
                null
            )
        )
    }

    override fun getItemCount(): Int = musicItems.size

    fun setItems(musicItemList: List<MusicItem>) {
        musicItems = musicItemList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.trackName.text = musicItems[position].trackName
        holder.artistName.text = musicItems[position].artistName
        holder.collectionName.text = musicItems[position].collectionName
        Glide.with(context).load(musicItems[position].artworkUrl100)
            .placeholder(R.drawable.ic_error_24px).into(holder.artwork)
        holder.itemView.setOnClickListener {
            onClickCallBack.onClick(
                musicItems[position].collectionName,
                musicItems[position].artistName,
                musicItems[position].trackName,
                musicItems[position].artworkUrl100,
                musicItems[position].previewUrl
            )
        }
    }
}
