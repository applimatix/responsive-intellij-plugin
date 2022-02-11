package com.github.tiste.responsiveintellijplugin.settings

import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ApplicationStateTest {
    var applicationState: ApplicationState? = null
    @BeforeEach
    fun init() {
        applicationState = ApplicationState()
        val testMap: LinkedHashMap<Int, ApplicationState.BreakpointSettings> = LinkedHashMap()
        testMap[0] = ApplicationState.BreakpointSettings(12, 12)
        testMap[1000] = ApplicationState.BreakpointSettings(13, 14)
        testMap[2000] = ApplicationState.BreakpointSettings(14, 16)
        applicationState!!.breakpoints = testMap
    }

    @Test
    fun findBreakpointValueWhenBreakpointIsLarge() {
        Assert.assertEquals(12, applicationState!!.findBreakpointValue(900).editorFontSize.toLong())
        Assert.assertEquals(12, applicationState!!.findBreakpointValue(900).uiFontSize.toLong())
    }

    @Test
    fun findBreakpointValueWhenBreakpointIsClose() {
        Assert.assertEquals(13, applicationState!!.findBreakpointValue(1200).editorFontSize.toLong())
        Assert.assertEquals(14, applicationState!!.findBreakpointValue(1200).uiFontSize.toLong())
    }

    @Test
    fun findBreakpointValueWhenBreakpointIsHuge() {
        Assert.assertEquals(14, applicationState!!.findBreakpointValue(5000).editorFontSize.toLong())
        Assert.assertEquals(16, applicationState!!.findBreakpointValue(5000).uiFontSize.toLong())
    }
}