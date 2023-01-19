package com.example.simplenotepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.simplenotepad.Adapter.worldAdapter
import com.example.simplenotepad.Database.WorldDatabase
import com.example.simplenotepad.Models.WorldData
import com.example.simplenotepad.Models.WorldViewModel
import com.example.simplenotepad.databinding.ActivityWorldListBinding

class MainActivity : AppCompatActivity(), worldAdapter.WorldItemClickListener, PopupMenu.OnMenuItemClickListener{

    private lateinit var binding: ActivityWorldListBinding
    private lateinit var database: WorldDatabase
    lateinit var viewModel: WorldViewModel
    lateinit var adapter: worldAdapter
    lateinit var selectedWorld: WorldData

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val world = result.data?.getSerializableExtra("world") as? WorldData
            if (world != null){
                viewModel.updateNote(world)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorldListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(WorldViewModel::class.java)

        viewModel.allworlds.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = WorldDatabase.getDatabase(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = worldAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val world = result.data?.getSerializableExtra("world") as WorldData
                if (world != null){
                    viewModel.insertWorld(world)
                }
            }
        }
        binding.fabWorld.setOnClickListener{
            val intent = Intent(this, add_world::class.java)
            getContent.launch(intent)
        }
        binding.searchWorldView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    adapter.filterList(newText)
                }
                return true
            }
        })
    }

    override fun onItemClicked(wdata: WorldData) {
        val intent = Intent(this@MainActivity, add_world::class.java)
        intent.putExtra("current_world", wdata)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(wdata: WorldData, cardView: CardView) {
        selectedWorld = wdata
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView){
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_card){
            viewModel.deleteWorld(selectedWorld)
            return true
        }
        return false
    }

}