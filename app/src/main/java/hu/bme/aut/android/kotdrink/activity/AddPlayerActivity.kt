package hu.bme.aut.android.kotdrink.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import hu.bme.aut.android.kotdrink.R.drawable
import hu.bme.aut.android.kotdrink.adapter.PlayerAdapter
import hu.bme.aut.android.kotdrink.data.PlayerItem
import hu.bme.aut.android.kotdrink.data.PlayerItemDatabase
import hu.bme.aut.android.kotdrink.databinding.ActivityAddPlayerBinding
import hu.bme.aut.android.kotdrink.fragment.NewPlayerItemDialogFragment
import kotlin.concurrent.thread

class AddPlayerActivity :    AppCompatActivity(),
                        PlayerAdapter.PlayerItemClickListener,
                        NewPlayerItemDialogFragment.NewPlayerItemDialogListener {

    private lateinit var binding: ActivityAddPlayerBinding

    private lateinit var playerItemDatabase: PlayerItemDatabase
    private lateinit var adapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        playerItemDatabase = PlayerItemDatabase.getDatabase(applicationContext)
        createTapTargetView()
        setClickListener()
        initRecyclerView()
    }

    private fun setClickListener() {
        binding.fab.setOnClickListener {
            NewPlayerItemDialogFragment().show(
                supportFragmentManager,
                NewPlayerItemDialogFragment.TAG
            )
        }
        binding.back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun createTapTargetView() {
        TapTargetView.showFor(this,
            TapTarget.forView(binding.fab, "Oh look!", "Tap here to add a new player.")
                .outerCircleAlpha(0.96f)
                .titleTextSize(35)
                .descriptionTextSize(30)
                .drawShadow(true)
                .icon(ContextCompat.getDrawable(applicationContext, drawable.ic_add_white_36dp)),
            object: TapTargetView.Listener(){
                override fun onTargetClick(view: TapTargetView){
                    super.onTargetClick(view)
                    NewPlayerItemDialogFragment().show(
                        supportFragmentManager,
                        NewPlayerItemDialogFragment.TAG
                    )
                }
            })
    }

    private fun initRecyclerView() {
        adapter = PlayerAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = playerItemDatabase.playerItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: PlayerItem) {
        thread {
            playerItemDatabase.playerItemDao().update(item)
            Log.d("AddPlayerActivity", "PlayerItem update was successful")
        }
    }

    override fun onItemDeleted(item: PlayerItem) {
        thread {
            playerItemDatabase.playerItemDao().deleteItem(item)
            runOnUiThread {
                adapter.deleteItem(item)
            }
        }
    }

    override fun onPlayerItemCreated(newItem: PlayerItem) {
        thread {
            playerItemDatabase.playerItemDao().insert(newItem)

            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}