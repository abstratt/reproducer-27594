package internal

import CoolDependencyBaseContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName

val Project.coolDependencies: CoolDependencyBaseContainer
  get() = extensions.getByName<CoolDependencyBaseContainer>("coolDependencies")
