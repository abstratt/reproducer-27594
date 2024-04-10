import tasks.FormatRendererTask

plugins {
  `embedded-kotlin`
  `cool-plugin-html`
}

val generatedHtmlRenderedFiles by tasks.registering(FormatRendererTask::class) {
  val projectPath = project.path
  inputs.property("projectPath", projectPath)
  doLast {
    outputDir.get().asFile.resolve("argh.html").apply {
      createNewFile()
      writeText("<html>$projectPath</html>")
    }
  }
}

coolDependenciesHtml
  .variantRenderedFiles
  .outgoing
  .artifact(generatedHtmlRenderedFiles) {
    type = "directory"
  }
