import CoolTypeAttribute.Companion.COOL_TYPE
import CoolVariantAttribute.Companion.COOL_VARIANT
import ProjectPathAttribute.Companion.PROJECT_PATH
import internal.toMap
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationVariant
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import org.gradle.api.attributes.Usage
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE
import org.gradle.api.logging.Logging
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.named
import java.io.File

class CoolDependencyContainer(
  project: Project,
  baseContainer: CoolDependencyBaseContainer,
  nameSuffix: String,
  type: String = nameSuffix,
) {

  private val objects = project.objects
  private val providers = project.providers
  private val coolPluginUsage: Usage = objects.named("cool-plugin")
  private val coolType: CoolTypeAttribute = objects.named(type)
  private val projectPath: Provider<ProjectPathAttribute> =
    providers.provider { project.path }.map { objects.named<ProjectPathAttribute>(it) }

  private val baseConfigurationName = "coolDependency${nameSuffix}"

  val declaredDependencies: NamedDomainObjectProvider<Configuration> =
    project.configurations.register(baseConfigurationName) {
      isCanBeResolved = false
      isCanBeConsumed = false
      isCanBeDeclared = true
      extendsFrom(baseContainer.declaredDependencies.get())
      attributes {
        attribute(USAGE_ATTRIBUTE, coolPluginUsage)
        attribute(COOL_TYPE, coolType)
      }
    }

  val incoming: NamedDomainObjectProvider<Configuration> =
    project.configurations.register("${baseConfigurationName}Incoming") {
      isCanBeResolved = true
      isCanBeConsumed = false
      isCanBeDeclared = false
      extendsFrom(declaredDependencies.get())
      attributes {
        attribute(USAGE_ATTRIBUTE, coolPluginUsage)
        attribute(COOL_TYPE, coolType)
      }
    }


  val incomingIntransitive: NamedDomainObjectProvider<Configuration> =
    project.configurations.register("${baseConfigurationName}IncomingIntransitive") {
      isCanBeResolved = true
      isCanBeConsumed = false
      isCanBeDeclared = false
      isTransitive = false
      extendsFrom(declaredDependencies.get())
      attributes {
        attribute(USAGE_ATTRIBUTE, coolPluginUsage)
        attribute(COOL_TYPE, coolType)
      }
    }


  val outgoingProvider: NamedDomainObjectProvider<Configuration> =
    project.configurations.register("${baseConfigurationName}Outgoing") {
      isCanBeResolved = false
      isCanBeConsumed = true
      isCanBeDeclared = false
      extendsFrom(declaredDependencies.get())
      attributes {
        attribute(USAGE_ATTRIBUTE, coolPluginUsage)
        attribute(COOL_TYPE, coolType)
      }
    }


  class CoolVariant(
    variant: String,
    objects: ObjectFactory,
    projectPath: Provider<ProjectPathAttribute>,
    private val coolPluginUsage: Usage,
    private val type: CoolTypeAttribute,
    private val baseOutgoingProvider: NamedDomainObjectProvider<Configuration>,
    private val baseIncomingResolver: NamedDomainObjectProvider<Configuration>,
  ) {
    private val logger = Logging.getLogger(CoolVariant::class.java)

    //    private val usage: Usage = objects.named("cool-plugin")
    private val variantAttribute: CoolVariantAttribute = objects.named(variant)

    val outgoing: ConfigurationVariant =
      baseOutgoingProvider.get().outgoing.variants
        .create("${baseOutgoingProvider.name}$variant") {
          attributes {
            attribute(USAGE_ATTRIBUTE, coolPluginUsage)
            attribute(COOL_TYPE, type)
            attribute(COOL_VARIANT, variantAttribute)
            attributeProvider(PROJECT_PATH, projectPath)
          }
        }

    val incomingArtifacts =
      baseIncomingResolver.map { incoming ->
        incoming.incoming
          .artifactView {
            withVariantReselection()
            attributes {
              attribute(USAGE_ATTRIBUTE, coolPluginUsage)
              attribute(COOL_TYPE, type)
              attribute(COOL_VARIANT, variantAttribute)
            }
            lenient(true)
          }
          .artifacts
          .filter { artifact ->
            val valid = artifact.variant.attributes.getAttribute(COOL_VARIANT) == variantAttribute
            when {
              !valid -> logger.info("warning - got invalid type ${artifact.id} ${artifact.id::class.simpleName}  | attributes:${artifact.variant.attributes.toMap()} ")
              else   -> logger.info("got valid type ${artifact.id} ${artifact.id::class.simpleName}  | attributes:${artifact.variant.attributes.toMap()} ")
            }
            valid
          }
      }


    val incomingFiles: Provider<List<File>> =
      incomingArtifacts.map { it.map(ResolvedArtifactResult::getFile) }
  }


  private fun CoolVariant(variant: String) =
    CoolVariant(
      variant = variant,
      objects = objects,
      projectPath = projectPath,
//      providers = providers,
      coolPluginUsage = coolPluginUsage,
      type = coolType,
      baseOutgoingProvider = outgoingProvider,
      baseIncomingResolver = incoming
    )

  val variantRenderedFiles = CoolVariant("RenderedFiles")
  val variantJars = CoolVariant("Jars")

//  val outgoingVariantJars: ConfigurationVariant =
//    createOutgoingVariant("Jars")
}
