package other.activity.src

fun jmdroidViewModelKt(packageName: String, viewModel: String): String = """
package $packageName.vm

import androidx.lifecycle.ViewModel

class $viewModel: ViewModel() {
}
""".trimIndent()