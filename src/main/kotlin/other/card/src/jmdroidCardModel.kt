package other.card.src

fun jmdroidCardModel(applicationPackage: String, //jm.example.droid.demo
                     packageName: String,  //jm.example.droid.demo.app
                     cardModel: String,  //MainDataModel
):String {
return """
package $applicationPackage.model

import com.squareup.moshi.Json

data class $cardModel(
    @Json(name = "id") val id: Int
)    
""".trimIndent()
}