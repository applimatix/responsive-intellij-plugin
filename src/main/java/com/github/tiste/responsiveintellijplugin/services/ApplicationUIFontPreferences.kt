package com.github.tiste.responsiveintellijplugin.services

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.editor.EditorFactory

//
// https://github.com/mskonovalov/intellij-hidpi-profiles/blob/2017.1/src/main/java/ms/konovalov/intellij/hidpi/FontProfileManager.kt
class ApplicationUIFontPreferences {

    private val uiSettings = UISettings.instance

    fun setFontSize(fontSize: Int) {
        if (isUIFontSizeAllowed(fontSize)) {
            uiSettings.fontSize = fontSize
            uiSettings.overrideLafFonts = true
            uiSettings.fireUISettingsChanged()
            EditorFactory.getInstance().refreshAllEditors()
            LafManager.getInstance().updateUI()
            ActionToolbarImpl.updateAllToolbarsImmediately()
        }
    }

    private fun isUIFontSizeAllowed(fontSize: Int): Boolean {
        return if (MIN_FONT_SIZE <= fontSize) {
            MAX_FONT_SIZE >= fontSize
        } else false
    }

    companion object {
        val DEFAULT_FONT_SIZE = UISettings.defFontSize
        const val MIN_FONT_SIZE = 12
        const val MAX_FONT_SIZE = 48
        val instance: ApplicationUIFontPreferences
            get() = ServiceManager.getService(
                ApplicationUIFontPreferences::class.java
            )
    }
}