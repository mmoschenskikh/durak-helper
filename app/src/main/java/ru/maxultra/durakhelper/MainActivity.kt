package ru.maxultra.durakhelper

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import ru.maxultra.durakhelper.ui.DurakHelperScreen

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<DeckViewModel>()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DurakHelperScreen(viewModel = viewModel)
        }

        viewModel.isExiting.observe(this) {
            if (it == true) finish()
        }
    }
}
