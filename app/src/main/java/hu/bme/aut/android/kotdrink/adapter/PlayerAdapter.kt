package hu.bme.aut.android.kotdrink.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.kotdrink.R
import hu.bme.aut.android.kotdrink.data.PlayerItem
import hu.bme.aut.android.kotdrink.databinding.ItemPlayerListBinding

class PlayerAdapter(private val listener: PlayerItemClickListener) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private val items = mutableListOf<PlayerItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlayerViewHolder(
        ItemPlayerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val playerItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(playerItem.sex))
        holder.binding.cbIsPlaying.isChecked = playerItem.isPlaying
        holder.binding.tvName.text = playerItem.name
        holder.binding.cbIsPlaying.setOnCheckedChangeListener { buttonView, isChecked ->
            playerItem.isPlaying = isChecked
            listener.onItemChanged(playerItem)
        }

        holder.binding.ibRemove.setOnClickListener{
            listener.onItemDeleted(playerItem)
        }
    }

    @DrawableRes
    private fun getImageResource(sex: PlayerItem.Sex): Int {
        return when (sex) {
            PlayerItem.Sex.MALE -> R.drawable.maleicon
            PlayerItem.Sex.FEMALE -> R.drawable.femaleicon
        }
    }

    override fun getItemCount(): Int = items.size

    interface PlayerItemClickListener {
        fun onItemChanged(item: PlayerItem)
        fun onItemDeleted(item: PlayerItem)
    }

    inner class PlayerViewHolder(val binding: ItemPlayerListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: PlayerItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(playerItems: List<PlayerItem>) {
        items.clear()

        items.addAll(playerItems)
        notifyDataSetChanged()
    }

    fun deleteItem(item: PlayerItem) {
        val pos = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(pos)
    }
}
