rootProject.name = "cmr-tests"

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}


include(
  ":sp-with-html",
  ":sp-with-markdown",
  ":sp-without-plugin",
  ":sp-with-html-and-markdown",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
