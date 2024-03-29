plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.example.pokemonclasses"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokemonclasses"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.camera:camera-core:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Fragment
    val fragment_version = "1.6.2"
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    implementation ("androidx.fragment:fragment:$fragment_version")

    implementation ("androidx.activity:activity:1.8.2")

    //Navigation
    val nav_version = "2.7.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Room database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    //glide library
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.13.0")
    // Retrofit with Moshi Converter
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // OkHttp and LoginInterceptor (To see in logcat requests and responses)
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //Lottie animation
    implementation ("com.airbnb.android:lottie:6.3.0")

    //cameraX
    val camerax_version = "1.3.1"
    implementation ("androidx.camera:camera-core:${camerax_version}")
    implementation ("androidx.camera:camera-camera2:${camerax_version}")
    implementation ("androidx.camera:camera-lifecycle:${camerax_version}")

    implementation ("androidx.camera:camera-view:${camerax_version}")
    implementation ("androidx.camera:camera-extensions:${camerax_version}")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}