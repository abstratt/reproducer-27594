import tasks.FormatRendererTask

plugins {
  `cool-plugin-markdown`
}

val generatedMarkdownRenderedFiles by tasks.registering(FormatRendererTask::class) {
  val projectPath = project.path
  inputs.property("projectPath", projectPath)
  doLast {
    outputDir.get().asFile.resolve("woo.md").apply {
      createNewFile()
      writeText("_woo_ $projectPath")
    }
  }
}

coolDependenciesMarkdown
  .variantRenderedFiles
  .outgoing
  .artifact(generatedMarkdownRenderedFiles) {
    type = "directory"
  }
