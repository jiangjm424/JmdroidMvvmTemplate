package other.fragment

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import other.fragment.src.jmdroidFragmentKt
import other.utils.C


/**
 * @param moduleData
 * @param className   Main
 * @param layoutName  main_Fragment_layout
 * @param packageName 所在包名  com.myapp.demo
 * @param isViewMode
 *
 * 这里布局、viewmodel及Fragment类名都className生成
 * Fragment：  ${className}Fragment
 * 布局：  ${className}_Fragment_layout
 * viewmodel：  ${className}ViewModel
 */
fun RecipeExecutor.jmdroidFragmentRecipe(
        moduleData: ModuleTemplateData,
        className: String,//类名
        layoutName: String,//layout 名称
        packageName: String,//当前右键选择新建的路径名称
        isViewMode: Boolean,//是否生成ViewMode代码
) {

    val (projectData, srcOut, resOut, manifestOut) = moduleData

    val ktOrJavaExt = "kt"
    var applicationPackage = projectData.applicationPackage//包名

    if (applicationPackage.isNullOrEmpty()) {
        applicationPackage = escapeKotlinIdentifier(packageName)
    }


    //当前生成类的类型
    val typeName = "Fragment"


    //拼接当前的className 比如 MainFragment
    val fragmentName = C.getFormatName(className, typeName)

    //拼接当前的viewModel, 比如MainFragmentViewModel
    val viewModelName = C.getFormatName(fragmentName, "ViewModel")
    // 保存Fragment
    save(
            jmdroidFragmentKt(
                    isViewMode,
                    viewModelName,
                    applicationPackage,
                    packageName,
                    fragmentName,
                    layoutName.toCamelCase()
            ),
            srcOut.resolve("ui/$fragmentName.$ktOrJavaExt")
    )

    // 保存layout xml
    save(
            C.getLayoutXmlString(fragmentName),
            resOut.resolve("layout/${layoutName}.xml")
    )



    if (isViewMode) {
        // 保存viewModel
        save(
                C.jmdroidViewModelKt(packageName, viewModelName),
                srcOut.resolve("vm/$viewModelName.$ktOrJavaExt")
        )

    }
    open(srcOut.resolve("ui/${fragmentName}.${ktOrJavaExt}"))
}