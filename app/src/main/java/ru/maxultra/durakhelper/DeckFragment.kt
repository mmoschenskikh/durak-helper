package ru.maxultra.durakhelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.durakhelper.model.Card

private const val SPAN_COUNT = 4

class DeckFragment : Fragment() {

    private lateinit var deckRecyclerView: RecyclerView
    private val deckViewModel: DeckViewModel by lazy {
        ViewModelProvider(this).get(DeckViewModel::class.java)
    }
    private var adapter: CardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deck, container, false).apply {
            deckRecyclerView = findViewById(R.id.deck_recycler_view)
            deckRecyclerView.layoutManager =
                GridLayoutManager(this@DeckFragment.context, SPAN_COUNT)
            deckRecyclerView.adapter = CardAdapter(deckViewModel.deck)
        }
    }

    private inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardButton: TextView = itemView.findViewById(R.id.card_button)
    }

    private inner class CardAdapter(val deck: List<Card>) : RecyclerView.Adapter<CardHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            val view = layoutInflater.inflate(R.layout.grid_item_card, parent, false)
            return CardHolder(view)
        }

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            val card = deck[position]
            holder.cardButton.text = card.toString()
        }

        override fun getItemCount() = deck.size
    }

    companion object {
        fun newInstance(): DeckFragment {
            return DeckFragment()
        }
    }
}
