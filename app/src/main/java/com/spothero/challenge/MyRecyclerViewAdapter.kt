package com.spothero.challenge

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spothero.challenge.data.model.Spot
import com.spothero.challenge.databinding.ListItemBinding
import java.text.NumberFormat

/**
 * @author Hojat Ghasemi,
 * 2022-04-06
 * https://github.com/hojat72elect
 *
 * These 2 classes will give data to our recycler view.
 */
class MyRecyclerViewAdapter(
    private val clickListener: (Spot) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    private val spotsList = ArrayList<Spot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // here we create each item of the recycler view.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(spotsList[position], clickListener)
    }

    /**
     * Returns the number of items that are supposed to be drawn on the
     * recycler view.
     */
    override fun getItemCount(): Int {
        return spotsList.size
    }

    /**
     * reloads all the items in the recycler view (used in the
     * initialization time).
     */
    fun setList(spots: List<Spot>) {
        spotsList.clear()
        spotsList.addAll(spots)
    }
}


/**
 * The "binding" variable in this class will connect to the layout in the list_item.xml
 */
class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val TAG = "SpotHeroApp"

    /**
     * The function that will be called for each spot in the list (for
     * loading the UI in list).
     */
    fun bind(spot: Spot, clickListener: (Spot) -> Unit) {

        Glide.with(binding.root.context)
            .load(Uri.parse("file:/${spot.facilityPhoto}"))
            .into(binding.imageView)

        binding.tvAddress.text = spot.address.street
        binding.tvDistance.text = spot.distance
        binding.tvPrice.text = NumberFormat.getCurrencyInstance().format(spot.price / 100)


        binding.listItemLayout.setOnClickListener {
            clickListener(spot)
        }
    }

}