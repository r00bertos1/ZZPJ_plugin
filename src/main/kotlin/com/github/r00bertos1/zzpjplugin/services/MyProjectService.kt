package com.github.r00bertos1.zzpjplugin.services

import com.github.r00bertos1.zzpjplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
