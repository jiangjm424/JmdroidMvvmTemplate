package other.card

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import other.card.res.getCardLayoutXmlString
import other.card.src.jmdroidCardKt
import other.card.src.jmdroidCardModel
import other.utils.C

fun RecipeExecutor.jmdroidCardRecipe(
        moduleData: ModuleTemplateData,
        className: String,//类名
        layoutName: String,//layout 名称
        packageName: String,//当前右键选择新建的路径名称
        cardModel: String,
        cardType: String
) {
    val (projectData, srcOut, resOut, manifestOut) = moduleData

    val ktOrJavaExt = "kt"
    var applicationPackage = projectData.applicationPackage//包名

    if (applicationPackage.isNullOrEmpty()) {
        applicationPackage = escapeKotlinIdentifier(packageName)
    }


    //当前生成类的类型
    val typeName = "Card"


    //拼接当前的className 比如 MainFragment
    val cardName = C.getFormatName(className, typeName)

    // 保存Fragment
    save(
            jmdroidCardKt(
                    applicationPackage,
                    packageName,
                    cardName,
                    layoutName.toCamelCase(),
                    cardModel,
                    cardType
            ),
            srcOut.resolve("$cardName.$ktOrJavaExt")
    )

    // 保存layout xml
    save(
            getCardLayoutXmlString(cardName),
            resOut.resolve("layout/${layoutName}.xml")
    )

    //保存card data model
    save(
            jmdroidCardModel(applicationPackage, packageName, cardModel),
            srcOut.resolve("model/$cardModel.$ktOrJavaExt")
    )
    open(srcOut.resolve("${cardName}.${ktOrJavaExt}"))
}
