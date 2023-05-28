package other.card

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import other.utils.C
import java.io.File

val jmdroidCardTemplate
    get() = template {
        name = "Jmdroid Card"
        description = "适用于Jmdroid mvvm框架的卡片模板"
        minApi = C.MIN_API
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(
                WizardUiContext.MenuEntry,
        )

        val packageName = defaultPackageNameParameter

        val className = stringParameter {
            name = "Card Name"
            default = "Main"
            help = "只输入名字，不要包含Card, 比如Main"
            constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
        }


        val layoutName = stringParameter {
            name = "Layout Name"
            default = "main_card_layout"
            help = "请输入布局的名字"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { camelCaseToUnderlines(className.value.toCamelCase()) + "_card_layout" }
        }
        val cardType = stringParameter {
            name = "Card Type"
            default = "1"
            help = "请输入卡片id, 全局唯一，只能数字，与后台协商好"
            constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY)
        }
        val cardModel = stringParameter {
            name = "Card Data Model"
            default = "MainDataModel"
            constraints = listOf(Constraint.NONEMPTY)
            suggest = { className.value.toCamelCase() + "DataModel" }
        }
        widgets(
                TextFieldWidget(className),
                TextFieldWidget(layoutName),
                Separator,
                TextFieldWidget(cardModel),
                TextFieldWidget(cardType),
                PackageNameWidget(packageName)
        )

        thumb { File("jmdroid_template_activity.png") }

        recipe = { data: TemplateData ->
            jmdroidCardRecipe(
                    data as ModuleTemplateData,
                    className.value,
                    layoutName.value,
                    packageName.value,
                    cardModel.value,
                    cardType.value
            )
        }
    }

fun jmdroidCardRecipe(moduleTemplateData: ModuleTemplateData, value: String, value1: String, value2: String) {
    TODO("Not yet implemented")
}
