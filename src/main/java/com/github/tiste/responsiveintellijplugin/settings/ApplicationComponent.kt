package com.github.tiste.responsiveintellijplugin.settings

import com.github.tiste.responsiveintellijplugin.services.ApplicationEditorFontPreferences
import com.github.tiste.responsiveintellijplugin.services.ApplicationUIFontPreferences
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import org.apache.commons.lang.math.NumberUtils
import javax.swing.JComponent
import javax.swing.JPanel

class ApplicationComponent(currentWindowSize: String?) {
    val panel: JPanel
    private val firstBreakpoint = JBTextField("0")
    private val firstEditorFontSize = JBTextField("" + ApplicationEditorFontPreferences.DEFAULT_FONT_SIZE)
    private val firstUIFontSize = JBTextField("" + ApplicationUIFontPreferences.DEFAULT_FONT_SIZE)
    private val secondBreakpoint = JBTextField("0")
    private val secondEditorFontSize = JBTextField("" + ApplicationEditorFontPreferences.DEFAULT_FONT_SIZE)
    private val secondUIFontSize = JBTextField("" + ApplicationUIFontPreferences.DEFAULT_FONT_SIZE)
    private val thirdBreakpoint = JBTextField("0")
    private val thirdEditorFontSize = JBTextField("" + ApplicationEditorFontPreferences.DEFAULT_FONT_SIZE)
    private val thirdUIFontSize = JBTextField("" + ApplicationUIFontPreferences.DEFAULT_FONT_SIZE)
    val textFields = arrayOf(
        arrayOf(firstBreakpoint, firstEditorFontSize, firstUIFontSize),
        arrayOf(secondBreakpoint, secondEditorFontSize, secondUIFontSize),
        arrayOf(thirdBreakpoint, thirdEditorFontSize, thirdUIFontSize)
    )

    init {
        val windowSize = JBLabel(currentWindowSize!!)
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Actual window size: "), windowSize, 1, false)
            .addSeparator()
            .addLabeledComponent(JBLabel("Enter first breakpoint: "), firstBreakpoint, 1, false)
            .addLabeledComponent(JBLabel("Enter first editor font size: "), firstEditorFontSize, 1, false)
            .addLabeledComponent(JBLabel("Enter first ui font size: "), firstUIFontSize, 1, false)
            .addSeparator()
            .addLabeledComponent(JBLabel("Enter second breakpoint: "), secondBreakpoint, 1, false)
            .addLabeledComponent(JBLabel("Enter second editor font size: "), secondEditorFontSize, 1, false)
            .addLabeledComponent(JBLabel("Enter second ui font size: "), secondUIFontSize, 1, false)
            .addSeparator()
            .addLabeledComponent(JBLabel("Enter third breakpoint: "), thirdBreakpoint, 1, false)
            .addLabeledComponent(JBLabel("Enter third editor font size: "), thirdEditorFontSize, 1, false)
            .addLabeledComponent(JBLabel("Enter third ui font size: "), thirdUIFontSize, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = firstBreakpoint

    fun getBreakpointAtPosition(position: Int): Int {
        return NumberUtils.toInt(textFields[position][0].text, 0)
    }

    fun getEditorFontSizeAtPosition(position: Int): Int {
        return NumberUtils.toInt(textFields[position][1].text, ApplicationEditorFontPreferences.DEFAULT_FONT_SIZE)
    }

    fun getUIFontSizeAtPosition(position: Int): Int {
        return NumberUtils.toInt(textFields[position][2].text, ApplicationUIFontPreferences.DEFAULT_FONT_SIZE)
    }

    fun reset() {
        val settings: ApplicationState = ApplicationState.instance
        val keys = settings.breakpoints.keys.toList()
        for (i in keys.indices) {
            val breakpoint = keys[i]
            textFields[i][0].text = breakpoint.toString()
            textFields[i][1].text = settings.breakpoints[breakpoint]?.editorFontSize.toString()
            textFields[i][2].text = settings.breakpoints[breakpoint]?.uiFontSize.toString()
        }
    }
}