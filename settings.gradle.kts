rootProject.name = "assembly-line"

// applications
include("applications:assembly-line")

// modules
include("modules:assembly-utils")

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}
