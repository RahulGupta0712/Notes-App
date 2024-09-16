plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.notesapp"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.github.gayanvoice:android-animations-kotlin:1.0.1") // render animation
    implementation ("io.github.shashank02051997:FancyToast:2.0.2")  // fancy toast
    implementation ("com.github.ybq:Android-SpinKit:1.4.0") // progress bar
    implementation ("com.airbnb.android:lottie:+") // lottie animation
    implementation("com.github.f0ris.sweetalert:library:1.5.6") // alert dialog
}