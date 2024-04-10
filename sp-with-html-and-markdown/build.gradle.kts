import tasks.FormatRendererTask

plugins {
  `cool-plugin-html`
  `cool-plugin-markdown`
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

coolDependenciesHtml
  .variantRenderedFiles
  .outgoing
  .artifact(generatedHtmlRenderedFiles) {
    type = "directory"
  }

coolDependenciesMarkdown
  .variantRenderedFiles
  .outgoing
  .artifact(generatedMarkdownRenderedFiles) {
    type = "directory"
  }
