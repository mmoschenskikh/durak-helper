package ru.maxultra.durakhelper

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import ru.maxultra.durakhelper.ui.CardGridComponent

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<DeckViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.deckLiveData.observe(this) {
            Log.d("MainActivity", "Deck changed")
        }
        setContent {
            val deck by viewModel.deckLiveData.observeAsState(emptyList())
            CardGridComponent(deck = deck, onClick = viewModel::onCardClick)
        }
    }

    override fun onBackPressed() {
        ConfirmDialog.show(this) { super.onBackPressed() }
    }
}
