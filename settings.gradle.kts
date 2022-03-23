rootProject.name = "assembly-line"

// applications
include("applications:assembly-line")

// modules
include("modules:console")
include("modules:log")


pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}
