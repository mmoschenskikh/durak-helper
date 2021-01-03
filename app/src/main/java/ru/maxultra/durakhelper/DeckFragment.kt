package ru.maxultra.durakhelper

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
    private var trumpSuit: Card.Suit? = null

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
        trumpSuit = null
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
        fun changedTrumpSuit(newSuit: Card.Suit): Boolean {
            trumpSuit?.let { addSuitCardsChanged(it) }
            trumpSuit = newSuit
            addSuitCardsChanged(trumpSuit!!)
            updateCardsChanged()
            return true
        }
        return when (item.itemId) {
            R.id.new_game -> {
                if ((deckRecyclerView.adapter as CardAdapter).currentList.any { it.status != Card.Status.TABLE } || trumpSuit != null) {
                    ConfirmDialog.show(context) { resetState() }
                }
                true
            }
            R.id.trump_diamonds -> changedTrumpSuit(Card.Suit.DIAMONDS)
            R.id.trump_clubs -> changedTrumpSuit(Card.Suit.CLUBS)
            R.id.trump_hearts -> changedTrumpSuit(Card.Suit.HEARTS)
            R.id.trump_spades -> changedTrumpSuit(Card.Suit.SPADES)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addSuitCardsChanged(suit: Card.Suit) {
        cardsChanged.addAll((0..deckViewModel.deckSize).toSet().filter { it % 4 == suit.ordinal })
    }

    override fun onDetach() {
        super.onDetach()
        resetState()
    }

    private fun updateUI(deck: List<Card>) {
        (deckRecyclerView.adapter as CardAdapter).submitList(deck)
        updateCardsChanged()
    }

    private fun updateCardsChanged() {
        cardsChanged.forEach { deckRecyclerView.adapter?.notifyItemChanged(it, Unit) }
        cardsChanged.clear()
    }

    private inner class CardHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private val coeff = Card.Rank.values().size + 3

        private val cardButton: Button = itemView.findViewById(R.id.card_button)
        private val cardLayout: LinearLayout = itemView.findViewById(R.id.card_button_layout)
        private var cardDrawable: Drawable? = null
        private lateinit var card: Card

        init {
            setCardLayoutHeight()
            cardButton.setOnClickListener(this)
            cardLayout.setOnClickListener(this)
        }

        fun bind(card: Card) {
            this.card = card
            cardDrawable = findCardDrawable()
            setCardLayoutBackground()
            assessCardAppearance()
        }

        override fun onClick(v: View?) {
            card.status =
                if (v == cardButton && card.status == Card.Status.INGAME)
                    Card.Status.TABLE
                else
                    Card.Status.INGAME

            assessCardAppearance()
        }

        private fun setCardLayoutHeight() {
            val params = cardLayout.layoutParams
            params.height = pixelHeight / coeff
            cardLayout.layoutParams = params
        }

        private fun setCardLayoutBackground() {
            cardLayout.background =
                if (card.suit == trumpSuit)
                    context?.let {
                        ColorDrawable(ContextCompat.getColor(it, R.color.trump_suit_color))
                    }
                else
                    null
        }

        private fun findCardDrawable(): Drawable? {
            val drawable = when (card.suit) {
                Card.Suit.CLUBS -> ResourcesCompat.getDrawable(resources, R.drawable.clubs, null)
                Card.Suit.HEARTS -> ResourcesCompat.getDrawable(resources, R.drawable.hearts, null)
                Card.Suit.SPADES -> ResourcesCompat.getDrawable(resources, R.drawable.spades, null)
                Card.Suit.DIAMONDS ->
                    ResourcesCompat.getDrawable(resources, R.drawable.diamonds, null)
            }
            val size = ((pixelHeight / coeff) / 2.5).toInt()
            drawable?.setBounds(0, 0, size, size)
            return drawable
        }

        private fun assessCardAppearance() {
            setCardColor()
            setCardTypeface()
            setCardVisibility()
            if (card.status != Card.Status.DISCARD) {
                cardButton.text = card.rank.rankString
                setCardDrawable()
            }
        }

        private fun setCardColor() {
            val color = when (card.status) {
                Card.Status.TABLE -> R.color.table_card_color
                Card.Status.MINE -> R.color.my_color
                Card.Status.FRIEND -> R.color.friend_color
                Card.Status.ENEMY -> R.color.enemy_color
                else -> R.color.ingame_color
            }
            cardButton.backgroundTintList =
                context?.let { ContextCompat.getColorStateList(it, color) }
        }

        private fun setCardDrawable() {
            cardButton.setCompoundDrawables(null, null, cardDrawable, null)
        }

        private fun setCardTypeface() {
            cardButton.typeface =
                if (card.status == Card.Status.INGAME)
                    Typeface.DEFAULT_BOLD
                else
                    Typeface.DEFAULT
        }

        private fun setCardVisibility() {
            cardButton.visibility =
                if (card.status == Card.Status.DISCARD)
                    View.INVISIBLE
                else
                    View.VISIBLE
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
