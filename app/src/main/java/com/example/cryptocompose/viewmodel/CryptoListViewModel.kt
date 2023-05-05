package com.example.cryptocompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocompose.model.CryptoList
import com.example.cryptocompose.model.CryptoListItem
import com.example.cryptocompose.repository.CryptoRepository
import com.example.cryptocompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel(){

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun loadCryptos(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when(result){
                is Resource.Success ->{
                    val cryptoItems = result.data!!.mapIndexed{index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                    }
                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error ->{
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
        }
    }
}