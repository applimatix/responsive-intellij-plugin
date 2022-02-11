package com.github.tiste.responsiveintellijplugin.settings

import com.github.tiste.responsiveintellijplugin.services.ApplicationEditorFontPreferences
import com.github.tiste.responsiveintellijplugin.services.ApplicationUIFontPreferences
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.github.tiste.responsiveintellijplugin.settings.ApplicationState",
    storages = [Storage("ResponsiveSettingsPlugin.xml")]
)
class ApplicationState : PersistentStateComponent<ApplicationState?> {
    var breakpoints: LinkedHashMap<Int, BreakpointSettings> = LinkedHashMap()

    override fun getState(): ApplicationState {
        return this
    }

    override fun loadState(state: ApplicationState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun findBreakpointValue(windowSize: Int): BreakpointSettings {
        var minDistance = Int.MAX_VALUE
        var closerBreakpoint = 0
        for (key in breakpoints.keys) {
            val distance = windowSize - key
            if (distance in 1 until minDistance) {
                minDistance = distance
                closerBreakpoint = key
            }
        }
        return breakpoints.getOrDefault(
            closerBreakpoint,
            BreakpointSettings(
                ApplicationEditorFontPreferences.DEFAULT_FONT_SIZE,
                ApplicationUIFontPreferences.DEFAULT_FONT_SIZE
            )
        )
    }

    companion object {
        @JvmStatic
        val instance: ApplicationState
            get() = ServiceManager.getService(
                ApplicationState::class.java
            )
    }

    data class BreakpointSettings(var editorFontSize: Int = 0, var uiFontSize: Int = 0)
}