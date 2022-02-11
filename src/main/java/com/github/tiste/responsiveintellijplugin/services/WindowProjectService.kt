package com.github.tiste.responsiveintellijplugin.services

import com.github.tiste.responsiveintellijplugin.settings.ApplicationState
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.wm.WindowManager

class WindowProjectService {
    val currentProjectWidth: Int
        get() {
            val projects = ProjectManager.getInstance().openProjects
            var activeProject: Project? = null
            for (project in projects) {
                val window = WindowManager.getInstance().suggestParentWindow(project)
                if (window != null && window.isActive) {
                    activeProject = project
                }
            }
            return try {
                WindowManager.getInstance().getIdeFrame(activeProject)?.component?.width ?: 0
            } catch (e: NullPointerException) {
                0
            }
        }

    fun updateFontForSize(width: Int) {
        val settings = ApplicationState.instance
        val fontSizes = settings.findBreakpointValue(width)

        val applicationEditorFontPreferences: ApplicationEditorFontPreferences =
            ApplicationEditorFontPreferences.instance
        applicationEditorFontPreferences.setFontSize(fontSizes.editorFontSize)
        val applicationUIFontPreferences: ApplicationUIFontPreferences =
            ApplicationUIFontPreferences.instance
        applicationUIFontPreferences.setFontSize(fontSizes.uiFontSize)
    }

    companion object {
        val instance: WindowProjectService
            get() = ServiceManager.getService(WindowProjectService::class.java)
    }
}