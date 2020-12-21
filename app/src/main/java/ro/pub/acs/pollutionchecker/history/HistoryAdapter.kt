package ro.pub.acs.pollutionchecker.history

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ro.pub.acs.pollutionchecker.R
import ro.pub.acs.pollutionchecker.database.SearchedCity
import ro.pub.acs.pollutionchecker.utils.Helper

class HistoryAdapter(
    private val historyViewModel: HistoryViewModel
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var history =  listOf<SearchedCity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = history.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = history[position]

        holder.itemView.setOnClickListener{ view ->
            view.findNavController().navigate(
                HistoryFragmentDirections
                    .actionHistoryFragmentToCityInfoFragment(item.searchId)
            )
        }

        holder.itemView.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener{
            historyViewModel.removeItem(item.searchId)
            notifyItemRemoved(position)
        }

        holder.bind(item)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.listIcon)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
        private val textViewRatingText: TextView = itemView.findViewById(R.id.textViewRatingText)

        fun bind(item: SearchedCity) {
            val (imageId, colorId, textId) = Helper.getRating(item.aqi)

            textViewTitle.text = itemView.context.getString(
                R.string.city_details,
                item.city,
                item.region,
                item.country
            )
            imageView.setImageResource(imageId)
            textViewRating.setBackgroundColor(ContextCompat.getColor(itemView.context, colorId))
            textViewRating.text = item.aqi.toString()
            textViewRatingText.setText(textId)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.fragment_history, parent, false)
                view.setOnClickListener{ it ->
                    it.findNavController().navigate(
                        HistoryFragmentDirections
                            .actionHistoryFragmentToCityInfoFragment())
                }
                return ViewHolder(view)
            }
        }
    }
}