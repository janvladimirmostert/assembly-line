plugins {
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
			}
		}
		val jvmTest by getting {
			dependencies {
				implementation(kotlin("test-junit"))
			}
		}
	}
}
