.PHONY: lint format test android desktop web web-dist ios-framework

lint:
	./gradlew lintKotlin

format:
	./gradlew formatKotlin

test:
	./gradlew :shared:jvmTest

android:
	./gradlew :androidApp:installDebug

desktop:
	./gradlew :composeApp:run

web:
	./gradlew :composeApp:wasmJsBrowserDevelopmentRun

web-dist:
	./gradlew :composeApp:wasmJsBrowserDistribution

ios-framework:
	./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
