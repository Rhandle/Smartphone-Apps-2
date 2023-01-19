package com.example.simplenotepad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotepad.Models.WorldData
import com.example.simplenotepad.R

class worldAdapter(private val context: Context, val listener : WorldItemClickListener) : RecyclerView.Adapter<worldAdapter.WorldViewHolder>(){
    private val WorldList = ArrayList<WorldData>()
    private val fullWorldList = ArrayList<WorldData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldViewHolder {
        return WorldViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_world_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorldViewHolder, position: Int) {
        val currentWorld = WorldList[position]
        holder.title.text = currentWorld.title
        holder.title.isSelected = true
        holder.summary.text = currentWorld.summary
        holder.date.text = currentWorld.date
        holder.date.isSelected = true

        holder.world_layout.setOnClickListener{
            listener.onItemClicked(WorldList[holder.adapterPosition])
        }

        holder.world_layout.setOnLongClickListener{
            listener.onLongItemClicked(WorldList[holder.adapterPosition], holder.world_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return WorldList.size
    }

    fun updateList(newList : List<WorldData>){
        fullWorldList.clear()
        fullWorldList.addAll(newList)

        WorldList.clear()
        WorldList.addAll(fullWorldList)
        notifyDataSetChanged()
    }

    fun filterList(search : String){
        WorldList.clear()
        for(item in fullWorldList){
            if(item.title?.lowercase()?.contains(search.lowercase())==true){
                WorldList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    inner class WorldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val world_layout = itemView.findViewById<CardView>(R.id.worldItems)
        val title = itemView.findViewById<TextView>(R.id.wl_title)
        val summary = itemView.findViewById<TextView>(R.id.wl_summary)
        val date = itemView.findViewById<TextView>(R.id.wl_last_updated)

    }

    interface WorldItemClickListener{
        fun onItemClicked(wdata : WorldData)
        fun onLongItemClicked(wdata : WorldData, cardView: CardView)
    }
}