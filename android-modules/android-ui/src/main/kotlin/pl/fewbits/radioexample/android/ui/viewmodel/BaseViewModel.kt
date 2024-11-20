package pl.fewbits.radioexample.android.ui.viewmodel

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    internal var initiated = false
}

fun <T : BaseViewModel> T.runOnInit(action: (T) -> Unit) {
    if (!initiated) {
        initiated = true
        action(this)
    }
}
