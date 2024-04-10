package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.OutputDirectory

abstract class FormatRendererTask : DefaultTask() {
  @get:OutputDirectory
  abstract val outputDir: DirectoryProperty
}
