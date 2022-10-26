package com.example.partyplanner.ui.state

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class PartyViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(PartiesUiState())
    val uiState: StateFlow<PartiesUiState> = _uiState.asStateFlow()

    //for now it is just a dummy function that initializes data
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchParties() {
        _uiState.update {
            it.copy(parties = listOf(
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Fødselsdag",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Barmitzva",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Fødselsdag",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Barmitzva",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Fødselsdag",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Barmitzva",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Fødselsdag",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                ),
                PartyUiState(
                    PartyCoreInfoUiState(
                        name = "Seiers Barmitzva",
                        address = "Druemarken 55",
                        zip = "2680",
                        city = "Solrød Strand",
                        date = LocalDateTime.now()
                    )
                )

            ))
        }
    }
}