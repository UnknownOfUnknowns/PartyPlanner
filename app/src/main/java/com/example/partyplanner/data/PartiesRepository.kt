package com.example.partyplanner.data

/*
/**
 * IMPORTANT!
 * This class is a dummy class that for now just provides data in the form of UiState this will be
 * changed later and is not to be considered as a final solution nor an optimal one. But for now it
 * serves the purpose of providing data such that the view can be developed
 * **/
object PartiesRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    private var parties = mutableListOf(
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
                name = "Seiers Bryllup",
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
                name = "Seiers Bryllup",
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
                name = "Seiers Bryllup",
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
                name = "Seiers Bryllup",
                address = "Druemarken 55",
                zip = "2680",
                city = "Solrød Strand",
                date = LocalDateTime.now()
            )
        )
    )


    fun getParties(): List<PartyUiState> {
        return parties
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addParty(party: PartyUiState) {
        parties.add(party)
    }
}

*/