package other.fragment.src

import com.android.tools.idea.wizard.template.renderIf

fun jmdroidFragmentKt(
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
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import jm.droid.lib.uibase.AbsBindingFragment
import $applicationPackage.databinding.${layoutCamelCase}Binding
$importViewModel

class $lastClassNameFormat : AbsBindingFragment<${layoutCamelCase}Binding>() {
    $fieldViewModel
    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup?): ${layoutCamelCase}Binding {
        return ${layoutCamelCase}Binding.inflate(inflater, parent, false)
    }

    override fun setupData(binding: ${layoutCamelCase}Binding, owner: LifecycleOwner) {
    }

    override fun setupView(binding: ${layoutCamelCase}Binding, savedInstanceState: Bundle?) {
    }

    override fun onPageFirstComing() {
        super.onPageFirstComing()
    }
}
""".trimIndent()
}