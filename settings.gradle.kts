rootProject.name = "assembly-line"

// applications
include("applications:assembly-line")

// modules
include("modules:console")
include("modules:log")
include("modules:operation")


pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}
