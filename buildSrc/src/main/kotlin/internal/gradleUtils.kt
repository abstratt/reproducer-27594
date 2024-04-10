package internal

import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeContainer

internal fun AttributeContainer.toMap(): Map<Attribute<*>, Any?> =
  keySet().associateWith { getAttribute(it) }
