package vitorsb.project.logidataprocess.fixtures

internal object DataFixtureFactory {
    object InvalidDatas {
        val oneUserWithInvalidId =
            "00000oito8                             Terra Daniel DDS00000008360000000003     1899.0220210909"
        val oneUserWithInvalidPurchaseDate =
            "0000000008                             Terra Daniel DDS00000008360000000003     1899.0220211331"
        val oneUserWithInvalidProductValue =
            "0000000008                             Terra Daniel DDS00000008360000000003     18 9.0220210909"
        val invalidDateValue = "20211331"
    }
}