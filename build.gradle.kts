// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath(kotlin("gradle-plugin", version = "1.9.0"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}