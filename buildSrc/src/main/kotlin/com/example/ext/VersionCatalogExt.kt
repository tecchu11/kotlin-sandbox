package com.example.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType
import kotlin.jvm.optionals.getOrElse

/**
 * Project.libs get [VersionCatalog] defined in settings.gradle.kts.
 */
internal val Project.libs: VersionCatalog
    get() = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

/**
 * Versioncatalog.of is extension function to get libraries with given alias from [VersionCatalog].
 */
internal fun VersionCatalog.of(alias: String) =
    this.findLibrary(alias).getOrElse {
        throw IllegalArgumentException("does not exsit [$alias] in version catalog")
    }
