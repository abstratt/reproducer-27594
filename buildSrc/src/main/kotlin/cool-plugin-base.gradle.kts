plugins {
  base
}

val coolDependencies = CoolDependencyBaseContainer(project)

extensions.add("coolDependencies", coolDependencies)

dependencies {
  attributesSchema {
    attribute(CoolTypeAttribute.COOL_TYPE)
    attribute(CoolVariantAttribute.COOL_VARIANT)
  }
}

tasks.withType<tasks.FormatRendererTask>().configureEach {
  outputDir.convention(layout.dir(provider { temporaryDir }))
}
