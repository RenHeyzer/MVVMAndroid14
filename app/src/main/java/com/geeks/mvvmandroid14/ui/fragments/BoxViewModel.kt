package com.geeks.mvvmandroid14.ui.fragments

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.mvvmandroid14.data.repositories.BoxRepository
import com.geeks.mvvmandroid14.models.Box

class BoxViewModel : ViewModel() {

    private val repository = BoxRepository()

    private val _boxLiveData = MutableLiveData(BoxUiState<List<Box>>())
    val boxLiveData: LiveData<BoxUiState<List<Box>>> = _boxLiveData

    private val _detailBoxLiveData = MutableLiveData(BoxUiState<Box>())
    val detailBoxLiveData: LiveData<BoxUiState<Box>> = _detailBoxLiveData

    init {
        getBoxes()
    }

    fun getBoxes() {
        val data = repository.getBoxes()
        Handler().postDelayed(
            {
                if (data.size != 10) {
                    val newValue = boxLiveData.value?.copy(isLoading = false, success = data)
                    _boxLiveData.value = newValue
                } else {
                    val newValue =
                        boxLiveData.value?.copy(isLoading = false, error = "large amount of data")
                    _boxLiveData.value = newValue
                }
            },
            3000
        )
    }

    fun handOverTheBox(box: Box) {
        val newValue = detailBoxLiveData.value?.copy(isLoading = false, success = box)
        _detailBoxLiveData.value = newValue
    }
}

data class BoxUiState<T>(
    val isLoading: Boolean = true,
    val error: String? = null,
    val success: T? = null
)

data class DetailBoxUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val success: Box = Box(text = "", color = "")
)