package other.activity.src

import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.renderIf

fun jmdroidActivityKt(
        enableViewModel: Boolean,
        viewMode: String,  //MainActivityViewModel
        applicationPackage: String, //jm.example.droid.demo
        packageName: String,  //jm.example.droid.demo.app
        lastClassNameFormat: String,  //MainActivity
        layoutCamelCase: String //MainActivityLayout
): String {
    val importViewModel = renderIf(enableViewModel) { "import $packageName.vm.$viewMode" }
    val fieldViewModel = renderIf(enableViewModel) { "private val viewModel by viewModels<$viewMode>()" }
    return """
package $packageName.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import jm.droid.lib.uibase.AbsBindingActivity
import $applicationPackage.databinding.${layoutCamelCase}Binding
$importViewModel

class $lastClassNameFormat : AbsBindingActivity<${layoutCamelCase}Binding>() {

    $fieldViewModel

    override fun createViewBinding(layoutInflater: LayoutInflater): ${layoutCamelCase}Binding {
        return ${layoutCamelCase}Binding.inflate(layoutInflater)
    }

    override fun setupData(owner: LifecycleOwner) {
    }

    override fun setupView(binding: ${layoutCamelCase}Binding, savedInstanceState: Bundle?) {
    }
}        
""".trimIndent()
}