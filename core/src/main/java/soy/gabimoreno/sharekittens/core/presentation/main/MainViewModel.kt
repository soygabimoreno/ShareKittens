package soy.gabimoreno.sharekittens.core.presentation.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import soy.gabimoreno.libbase.viewmodel.StatelessBaseViewModel

class MainViewModel(
) : StatelessBaseViewModel<
        MainViewModel.ViewEvents>() {

    init {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToMainList)
        }
    }

    sealed class ViewEvents {
        object NavigateToMainList : ViewEvents()
    }
}
