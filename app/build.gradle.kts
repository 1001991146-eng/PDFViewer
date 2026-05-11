plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pdfviewer"
    compileSdk = 36
    compileSdkExtension = 19

    defaultConfig {
        applicationId = "com.example.pdfviewer"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Official AndroidX PDF Viewer library
    implementation("androidx.pdf:pdf-viewer:1.0.0-alpha18")
    implementation(libs.pdf.viewer.fragment)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}