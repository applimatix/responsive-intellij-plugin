package com.github.tiste.responsiveintellijplugin.services

import com.intellij.application.options.EditorFontsConstants
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.colors.impl.AppEditorFontOptions
import com.intellij.openapi.editor.colors.impl.FontPreferencesImpl

class ApplicationEditorFontPreferences {
    private val fontPreferences: FontPreferencesImpl = AppEditorFontOptions.getInstance().fontPreferences as FontPreferencesImpl

    fun setFontSize(fontSize: Int) {
        if (isEditorFontSizeAllowed(fontSize)) {
            fontPreferences.setFontSize(fontPreferences.fontFamily, fontSize)
            EditorFactory.getInstance().refreshAllEditors()
        }
    }

    private fun isEditorFontSizeAllowed(fontSize: Int): Boolean {
        return if (MIN_FONT_SIZE <= fontSize) {
            MAX_FONT_SIZE >= fontSize
        } else false
    }

    companion object {
        val DEFAULT_FONT_SIZE = EditorFontsConstants.getDefaultEditorFontSize()
        val MIN_FONT_SIZE = EditorFontsConstants.getMinEditorFontSize()
        val MAX_FONT_SIZE = EditorFontsConstants.getMaxEditorFontSize()
        val instance: ApplicationEditorFontPreferences
            get() = ServiceManager.getService(
                ApplicationEditorFontPreferences::class.java
            )
    }
}