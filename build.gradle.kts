import ProjectPathAttribute.Companion.PROJECT_PATH
import modules.CoolModuleSpec
import tasks.AggregateModulesTask

plugins {
  `cool-plugin`
}

dependencies {
  coolDependency(projects.spWithoutPlugin)
  coolDependency(projects.spWithHtml)
  coolDependency(projects.spWithMarkdown)
  coolDependency(projects.spWithHtmlAndMarkdown)
}


val printHtmlRenderedFiles by tasks.registering {
  group = project.name
  val htmlFiles = coolDependenciesHtml.variantRenderedFiles.incomingFiles
  inputs.files(htmlFiles)

  doLast {
    val filesStr = htmlFiles.orNull?.joinToString("\n") { " - ${it.invariantSeparatorsPath}" }
    println("got ${htmlFiles.orNull?.size} files:\n$filesStr")
  }
}


val printMarkdownRenderedFiles by tasks.registering {
  group = project.name
  val markdownFiles = coolDependenciesMarkdown.variantRenderedFiles.incomingFiles
  inputs.files(markdownFiles)

  doLast {
    val filesStr = markdownFiles.orNull?.joinToString("\n") { " - ${it.invariantSeparatorsPath}" }
    println("got ${markdownFiles.orNull?.size} files:\n$filesStr")
  }
}


val aggregateHtmlModules by tasks.registering(AggregateModulesTask::class) {
  group = project.name

  val htmlModuleSpecs =
    coolDependenciesHtml.variantRenderedFiles.incomingArtifacts.map { artifacts ->
      artifacts.map { artifact ->
        val projectPath = artifact.variant.attributes.getAttribute(PROJECT_PATH)
          ?: error("missing PROJECT_PATH attribute in $artifact")

        val moduleOutputDir =
          coolDependenciesHtml.variantRenderedFiles.incomingArtifacts.map { artifacts ->
            artifacts
              .filter { it.variant.attributes.getAttribute(PROJECT_PATH) == projectPath }
              .map(ResolvedArtifactResult::getFile)
              .singleOrNull()
              ?: error("expected only one dir, but got multiple ")
          }

        objects.newInstance<CoolModuleSpec>(projectPath.name).apply {
          outputDirectory.fileProvider(moduleOutputDir)
        }
      }
    }

  this.moduleSpecs.addAllLater(htmlModuleSpecs)
}
