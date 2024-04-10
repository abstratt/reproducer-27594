import org.gradle.api.Named
import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeCompatibilityRule
import org.gradle.api.attributes.CompatibilityCheckDetails


interface CoolTypeAttribute : Named {
  companion object {
    val COOL_TYPE = Attribute.of(CoolTypeAttribute::class.java)
  }
}

interface CoolVariantAttribute : Named {
  companion object {
    val COOL_VARIANT = Attribute.of(CoolVariantAttribute::class.java)
  }
}


interface ProjectPathAttribute : Named {
  companion object {
    val PROJECT_PATH = Attribute.of(ProjectPathAttribute::class.java)
  }
}
