import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyScopeConfiguration
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE
import org.gradle.kotlin.dsl.named

class CoolDependencyBaseContainer(
  project: Project,
) {

  private val objects = project.objects

  private val baseConfigurationName = "coolDependency"

  val declaredDependencies: NamedDomainObjectProvider<DependencyScopeConfiguration> =
    project.configurations.dependencyScope(baseConfigurationName) {
      attributes {
        attribute(USAGE_ATTRIBUTE, objects.named("cool-plugin"))
      }
    }
}
