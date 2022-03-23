plugins {
	application
	kotlin("multiplatform")
}

kotlin {

	jvm {
		compilations.all {
			kotlinOptions.jvmTarget = "17"
		}
	}
	sourceSets {
		val jvmMain by getting {
			dependencies {
				implementation(project(":modules:console"))
				implementation(project(":modules:log"))

				val coroutinesVersion = "1.6.0"
				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
			}
		}
		val jvmTest by getting {
			dependencies {
				implementation(kotlin("test-junit"))
			}
		}
	}
}

application {
	mainClass.set("com.assembly.Demo")
}

