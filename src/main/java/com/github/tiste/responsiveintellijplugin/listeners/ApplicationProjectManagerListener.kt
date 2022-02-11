package com.github.tiste.responsiveintellijplugin.listeners

import com.github.tiste.responsiveintellijplugin.services.WindowProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.StartupManager
import com.intellij.openapi.wm.WindowManager
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

class ApplicationProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        super.projectOpened(project)
        StartupManager.getInstance(project).runAfterOpened {
            WindowManager.getInstance().getIdeFrame(project)!!.component.addComponentListener(object :
                ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    WindowProjectService.instance.updateFontForSize(e.component.width)
                }
            })
        }
    }
}