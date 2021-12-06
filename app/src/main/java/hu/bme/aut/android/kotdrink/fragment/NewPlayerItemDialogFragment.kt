package hu.bme.aut.android.kotdrink.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.kotdrink.R
import hu.bme.aut.android.kotdrink.data.PlayerItem
import hu.bme.aut.android.kotdrink.databinding.DialogNewPlayerItemBinding

class NewPlayerItemDialogFragment : DialogFragment() {
    interface NewPlayerItemDialogListener {
        fun onPlayerItemCreated(newItem: PlayerItem)
    }

    private lateinit var listener: NewPlayerItemDialogListener

    private lateinit var binding: DialogNewPlayerItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewPlayerItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewPlayerItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewPlayerItemBinding.inflate(LayoutInflater.from(context))
        binding.spSex.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )


        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_player)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onPlayerItemCreated(getPlayerItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getPlayerItem() = PlayerItem(
        name = binding.etName.text.toString(),
        sex = PlayerItem.Sex.getByOrdinal(binding.spSex.selectedItemPosition) ?: PlayerItem.Sex.FEMALE,
        isPlaying = binding.cbIsPlaying.isChecked,
        playedLastGame = false
    )

    companion object {
        const val TAG = "NewPlayerItemDialogFragment"
    }
}
