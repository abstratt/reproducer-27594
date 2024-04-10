package tasks

import modules.CoolModuleSpec
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class AggregateModulesTask @Inject constructor(
  @get:Internal
  val objects: ObjectFactory
) : DefaultTask() {

  @get:OutputDirectory
  val outputDir: File get() = temporaryDir

  @get:Nested
  abstract val moduleSpecs: NamedDomainObjectContainer<CoolModuleSpec>

  @TaskAction
  fun action() {
    moduleSpecs.forEach { module ->
      val moduleOutput = module.outputDirectory.get().asFile

      require(moduleOutput.exists()) { "${moduleOutput.invariantSeparatorsPath} doesn't exist!" }

      outputDir.resolve("${module.moduleName.replaceNonAlphanumeric('_')}").apply {
        createNewFile()
        writeText(moduleOutput.invariantSeparatorsPath)
      }
    }
  }

  companion object {
    private fun String.replaceNonAlphanumeric(replacement: Char): String =
      map { if (it.isLetterOrDigit()) it else replacement }.joinToString("")
  }
}
