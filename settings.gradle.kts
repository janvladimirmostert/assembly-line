rootProject.name = "assembly-line"

// applications
include("applications:assembly-line")

// modules
include("modules:operation")

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}
