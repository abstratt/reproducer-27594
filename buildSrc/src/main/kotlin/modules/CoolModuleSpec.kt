package modules

import org.gradle.api.Named
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import javax.inject.Inject

abstract class CoolModuleSpec @Inject constructor(
  @get:Input
  val moduleName: String
) : Named {

  @get:InputFiles
  // can't use @InputDirectory because Gradle is bugged and requires the directory exists even though the task hasn't yet run
  // "org.gradle.internal.execution.WorkValidationException - Reason: An input file was expected to be present but it doesn't exist"
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val outputDirectory: DirectoryProperty


  @get:Classpath
  abstract val classpath: ConfigurableFileCollection

  @Internal
  override fun getName(): String = moduleName
}
