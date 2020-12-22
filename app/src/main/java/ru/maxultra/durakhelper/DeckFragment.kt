package ru.maxultra.durakhelper

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.durakhelper.model.Card

private const val SPAN_COUNT = 4

class DeckFragment : Fragment() {

    private lateinit var mineButton: Button
    private lateinit var friendButton: Button
    private lateinit var enemyButton: Button
    private lateinit var discardButton: Button

    private lateinit var deckRecyclerView: RecyclerView
    private val deckViewModel: DeckViewModel by lazy {
        ViewModelProvider(this).get(DeckViewModel::class.java)
    }

    private val adapter: CardAdapter
        get() = deckRecyclerView.adapter as CardAdapter

    private val cardsChanged = mutableSetOf<Int>()

    private var pixelHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        pixelHeight = resources.displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deck, container, false).apply {
            deckRecyclerView = findViewById(R.id.deck_recycler_view)
            deckRecyclerView.layoutManager =
                GridLayoutManager(this@DeckFragment.context, SPAN_COUNT)
            deckRecyclerView.adapter = CardAdapter()
            resetState()
        }
    }

    private fun resetState() {
        updateUI(adapter.currentList.map {
            it.status = Card.Status.TABLE
            it
        })
        deckRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckViewModel.deckLiveData.observe(viewLifecycleOwner, { updateUI(it) })

        mineButton = view.findViewById(R.id.mine_button)
        friendButton = view.findViewById(R.id.friend_button)
        enemyButton = view.findViewById(R.id.enemy_button)
        discardButton = view.findViewById(R.id.discard_button)

        val listener = View.OnClickListener { v ->
            val newStatus = when (v) {
                mineButton -> Card.Status.MINE
                friendButton -> Card.Status.FRIEND
                enemyButton -> Card.Status.ENEMY
                else -> Card.Status.DISCARD
            }

            val adapter = (deckRecyclerView.adapter as CardAdapter)
            val list = adapter.currentList.mapIndexed { index, it ->
                if (it.status == Card.Status.INGAME) {
                    it.status = newStatus
                    cardsChanged.add(index)
                }
                it
            }
            updateUI(list)
        }

        val bottomBarButtons = setOf(mineButton, friendButton, enemyButton, discardButton)

        fun setProperties(button: Button) {
            button.minHeight = pixelHeight / 10
            button.setOnClickListener(listener)
        }

        bottomBarButtons.forEach { setProperties(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_deck, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_game -> {
                if ((deckRecyclerView.adapter as CardAdapter).currentList.any { it.status != Card.Status.TABLE }) {
                    ConfirmDialog.show(context) { resetState() }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDetach() {
        super.onDetach()
        resetState()
    }

    private fun updateUI(deck: List<Card>) {
        (deckRecyclerView.adapter as CardAdapter).submitList(deck)
        cardsChanged.forEach { deckRecyclerView.adapter?.notifyItemChanged(it, Unit) }
        cardsChanged.clear()
    }

    private inner class CardHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private val coeff = 12

        val cardButton: Button = itemView.findViewById(R.id.card_button)
        val cardLayout: LinearLayout = itemView.findViewById(R.id.card_button_layout)
        private lateinit var card: Card

        init {
            cardButton.setOnClickListener(this)
            val params = cardLayout.layoutParams
            params.height = pixelHeight / coeff
            cardLayout.layoutParams = params
        }

        fun bind(card: Card) {
            this.card = card
            assessImage()
        }


        override fun onClick(v: View?) {
            if (card.status == Card.Status.INGAME)
                card.status = Card.Status.TABLE
            else
                card.status = Card.Status.INGAME
            assessImage()
        }

        fun assessImage() {
            val color = when (card.status) {
                Card.Status.TABLE -> R.color.table_card_color
                Card.Status.MINE -> R.color.my_color
                Card.Status.FRIEND -> R.color.friend_color
                Card.Status.ENEMY -> R.color.enemy_color
                Card.Status.INGAME -> R.color.ingame_color
                Card.Status.DISCARD -> R.color.discard_color
            }
            cardButton.backgroundTintList = context?.getColorStateList(color)
            if (card.status == Card.Status.DISCARD) {
                cardButton.text = ""
            } else {
                cardButton.text = card.toString()
            }
        }
    }

    private inner class CardAdapter : ListAdapter<Card, CardHolder>(DiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            val view = layoutInflater.inflate(R.layout.grid_item_card, parent, false)
            return CardHolder(view)
        }

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            val card = getItem(position)
            holder.bind(card)
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.suit == newItem.suit && oldItem.rank == newItem.rank

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.status == newItem.status
    }

    companion object {
        fun newInstance(): DeckFragment {
            return DeckFragment()
        }
    }
}
