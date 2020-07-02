// Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import java.net.URI

val remoteRobotPort: String by project
val junit5Version: String by project
val remoteRobotVersion: String by project
val uiTestFixturesVersion: String by project

repositories {
    maven { url = URI("https://jetbrains.bintray.com/intellij-third-party-dependencies") }
}

plugins {
    jacoco
}

dependencies {
    testImplementation(gradleApi())
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testImplementation("com.intellij.remoterobot:remote-robot:$remoteRobotVersion")
    testImplementation("com.intellij.remoterobot:remote-fixtures:$uiTestFixturesVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
}

tasks.register<Test>("uiTestCore") {
    // we don't want to cache the results of this.
    outputs.upToDateWhen { false }

    systemProperty("robot-server.port", remoteRobotPort)
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)

    systemProperty("GRADLE_PROJECT", "jetbrains-core")
    useJUnitPlatform {
        includeTags("core")
    }
}
