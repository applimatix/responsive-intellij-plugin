package com.github.tiste.responsiveintellijplugin.settings

import com.github.tiste.responsiveintellijplugin.services.WindowProjectService
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import javax.swing.JComponent

class ApplicationConfigurable : Configurable {

    private var settingsComponent: ApplicationComponent? = null

    override fun getDisplayName(): @ConfigurableName String {
        return "Responsive"
    }

    override fun createComponent(): JComponent? {
        val windowProjectService = WindowProjectService.instance
        settingsComponent = ApplicationComponent(windowProjectService.currentProjectWidth.toString())
        return settingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val settings: ApplicationState = ApplicationState.instance
        val compare: LinkedHashMap<Int, ApplicationState.BreakpointSettings> = LinkedHashMap()
        settingsComponent?.let {
            for (i in it.textFields.indices) {
                    compare[it.getBreakpointAtPosition(i)] = ApplicationState.BreakpointSettings(it.getEditorFontSizeAtPosition(i), it.getUIFontSizeAtPosition(i))
            }
        }
        return compare != settings.breakpoints
    }

    override fun apply() {
        val settings: ApplicationState = ApplicationState.instance
        settings.breakpoints.clear()
        settingsComponent?.let {
            for (i in it.textFields.indices) {
                settings.breakpoints[it.getBreakpointAtPosition(i)] =
                    ApplicationState.BreakpointSettings(it.getEditorFontSizeAtPosition(i), it.getUIFontSizeAtPosition(i))
            }
        }
        WindowProjectService.instance.updateFontForSize(WindowProjectService.instance.currentProjectWidth)
    }

    override fun reset() {
        settingsComponent?.reset()
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}