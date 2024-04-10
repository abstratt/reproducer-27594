import internal.coolDependencies

plugins {
  id("cool-plugin-base")
}

val coolDependenciesMarkdown = CoolDependencyContainer(project, coolDependencies, nameSuffix = "Markdown")

extensions.add("coolDependenciesMarkdown", coolDependenciesMarkdown)

coolDependenciesMarkdown.incoming.configure {
  extendsFrom(coolDependencies.declaredDependencies.get())
}
