package other.fragment

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import other.utils.C
import java.io.File

val jmdroidFragmentTemplate get() = template {
    name = "Jmdroid Fragment"
    description = "适用于Jmdroid mvvm框架的Fragment"
    minApi = C.MIN_API
    category = Category.Fragment
    formFactor = FormFactor.Mobile
    screens = listOf(
            WizardUiContext.FragmentGallery,
            WizardUiContext.MenuEntry,
    )

    val packageName = defaultPackageNameParameter

    val className = stringParameter {
        name = "Fragment Name"
        default = "Main"
        help = "只输入名字，不要包含Fragment, 比如Main"
        constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
    }

    val layoutName = stringParameter {
        name = "Layout Name"
        default = "main_fragment_layout"
        help = "请输入布局的名字"
        constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
        suggest = { camelCaseToUnderlines(className.value.toCamelCase()) + "_fragment_layout" }
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
        jmdroidFragmentRecipe(
                data as ModuleTemplateData,
                className.value,
                layoutName.value,
                packageName.value,
                isViewMode.value
        )
    }
}