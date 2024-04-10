//import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE
//
//interface MyCustomTypeAttribute : Named {
//  companion object {
//    val myCustomTypeAttribute = Attribute.of(MyCustomTypeAttribute::class.java)
//  }
//}
//
//interface MyCustomVariantAttribute : Named {
//  companion object {
//    val myCustomVariantAttribute = Attribute.of(MyCustomVariantAttribute::class.java)
//  }
//}
//
//val myDependency = configurations.dependencyScope("myDependency") {
//  attributes {
//    attribute(USAGE_ATTRIBUTE, objects.named("my-plugin"))
//  }
//}
//
//
//val myDependencyHtml = configurations.dependencyScope("myDependencyHtml") {
//  extendsFrom(myDependency.get())
//  attributes {
//    attribute(USAGE_ATTRIBUTE, objects.named("my-plugin"))
//    attribute(MyCustomTypeAttribute.myCustomTypeAttribute, objects.named("html"))
//  }
//}
//
//val myDependencyHtmlResolvable: NamedDomainObjectProvider<ResolvableConfiguration> =
//  project.configurations.resolvable("${myDependencyHtml.name}Resolvable") {
//    extendsFrom(myDependencyHtml.get())
//    attributes {
//      attribute(USAGE_ATTRIBUTE, objects.named("my-plugin"))
//      attribute(MyCustomTypeAttribute.myCustomTypeAttribute, objects.named("html"))
//    }
//  }
//
//val myDependencyHtmlConsumable: NamedDomainObjectProvider<ConsumableConfiguration> =
//  project.configurations.consumable("${myDependencyHtml.name}Consumable") {
//    attributes {
//      attribute(USAGE_ATTRIBUTE, objects.named("my-plugin"))
//      attribute(MyCustomTypeAttribute.myCustomTypeAttribute, objects.named("html"))
//    }
//  }
//
//val myDependencyHtmlConsumableVariantRenderedFiles: ConfigurationVariant =
//  myDependencyHtmlConsumable.get().outgoing.variants
//    .create("${myDependencyHtmlConsumable.name}RenderedFiles") {
//      attributes {
//        attribute(USAGE_ATTRIBUTE, objects.named("my-plugin"))
//        attribute(MyCustomTypeAttribute.myCustomTypeAttribute, objects.named("html"))
//        attribute(MyCustomVariantAttribute.myCustomVariantAttribute, objects.named("RenderedFiles"))
//      }
//    }
//
//
//extensions.add(
//  ConfigurationVariant::class,
//  "myDependencyHtmlConsumableVariantRenderedFiles",
//  myDependencyHtmlConsumableVariantRenderedFiles
//)
