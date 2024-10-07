import java.io.FileInputStream
import java.util.Properties

val localProperties=Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))


plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.weather_api"
    compileSdk = 34
    buildFeatures{
        buildConfig=true
    }
    defaultConfig {
        applicationId = "com.example.weather_api"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val apikeyv=localProperties.getProperty("apikey","")
        buildConfigField("String","apikey","\"$apikeyv\"")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}