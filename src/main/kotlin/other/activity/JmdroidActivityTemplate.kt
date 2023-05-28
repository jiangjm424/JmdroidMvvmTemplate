package other.activity

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import other.utils.C
import java.io.File

val jmdroidActivityTemplate
    get() = template {
        name = "Jmdroid Activity"
        description = "适用于Jmdroid mvvm框架的Activity"
        minApi = C.MIN_API
        category = Category.Activity
        formFactor = FormFactor.Mobile
        screens = listOf(
                WizardUiContext.ActivityGallery,
                WizardUiContext.MenuEntry,
        )

        val packageName = defaultPackageNameParameter

        val className = stringParameter {
            name = "Activity Name"
            default = "Main"
            help = "只输入名字，不要包含Activity, 比如Main"
            constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
        }

        val layoutName = stringParameter {
            name = "Layout Name"
            default = "main_activity_layout"
            help = "请输入布局的名字"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { camelCaseToUnderlines(className.value.toCamelCase()) + "_activity_layout" }
        }

        val isViewMode = booleanParameter {
            name = "Use ViewModel"
            default = true
            help = "是否生成ViewMode代码"
        }

        widgets(
                TextFieldWidget(className),
                CheckBoxWidget(isViewMode),
                TextFieldWidget(layoutName),
                PackageNameWidget(packageName)
        )

        thumb { File("jmdroid_template_activity.png") }

        recipe = { data: TemplateData ->
            jmdroidActivityRecipe(
                    data as ModuleTemplateData,
                    className.value,
                    layoutName.value,
                    packageName.value,
                    isViewMode.value
            )
        }
    }