package other.activity

import android.databinding.tool.ext.toCamelCase
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import other.activity.src.jmdroidActivityKt
import other.activity.src.jmdroidViewModelKt
import other.utils.C


/**
 * @param moduleData
 * @param className   Main
 * @param layoutName  main_activity_layout
 * @param packageName 所在包名  com.myapp.demo
 * @param isViewMode
 *
 * 这里布局、viewmodel及activity类名都className生成
 * activity：  ${className}Activity
 * 布局：  ${className}_activity_layout
 * viewmodel：  ${className}ViewModel
 */
fun RecipeExecutor.jmdroidActivityRecipe(
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
    val typeName = "Activity"


    //拼接当前的className 比如 MainActivity
    val activityName = C.getFormatName(className, typeName)

    //拼接当前的viewModel, 比如MainActivityViewModel
    val viewModelName = C.getFormatName(activityName, "ViewModel")
    // 保存Activity
    save(
            jmdroidActivityKt(
                    isViewMode,
                    viewModelName,
                    applicationPackage,
                    packageName,
                    activityName,
                    layoutName.toCamelCase()
            ),
            srcOut.resolve("ui/$activityName.$ktOrJavaExt")
    )

    // 保存layout xml
    save(
            C.getLayoutXmlString(activityName),
            resOut.resolve("layout/${layoutName}.xml")
    )



    if (isViewMode) {
        // 保存viewModel
        save(
                jmdroidViewModelKt(packageName, viewModelName),
                srcOut.resolve("vm/$viewModelName.$ktOrJavaExt")
        )

    }
    generateManifest(
            moduleData = moduleData,
            activityClass = activityName,
            packageName = "$packageName.ui",
            isLauncher = false,
            hasNoActionBar = false,
            generateActivityTitle = false,
    )
    open(srcOut.resolve("ui/${activityName}.${ktOrJavaExt}"))
}