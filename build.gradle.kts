
plugins {
	val kotlinVersion = "1.6.10"

	kotlin("multiplatform").version(kotlinVersion).apply(false)
}

allprojects {
	this.group = "com.assembly"
	this.version = "0.0.1"

	this.repositories {
		mavenCentral()
	}
}

tasks.withType<Copy> {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
