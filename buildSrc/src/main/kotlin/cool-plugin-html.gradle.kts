import internal.coolDependencies

plugins {
  id("cool-plugin-base")
}

val coolDependenciesHtml = CoolDependencyContainer(project, coolDependencies, nameSuffix = "Html")

extensions.add("coolDependenciesHtml", coolDependenciesHtml)

//coolDependenciesHtml.incoming.configure {
//  extendsFrom(coolDependencies.declaredDependencies.get())
//}
