package other

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import other.activity.jmdroidActivityTemplate
import other.card.jmdroidCardTemplate
import other.fragment.jmdroidFragmentTemplate

class JmdroidMvvmTemplateImpl : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(
                jmdroidActivityTemplate,
                jmdroidCardTemplate,
                jmdroidFragmentTemplate
        )
    }
}