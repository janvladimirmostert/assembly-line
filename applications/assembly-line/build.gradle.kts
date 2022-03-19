plugins {
	application
	kotlin("multiplatform")
}


kotlin {

	jvm {
		compilations.all {
			kotlinOptions.jvmTarget = "11"
		}
	}
	sourceSets {
		val jvmMain by getting {
			dependencies {

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
	mainClassName = "com.assembly.Demo"
}

