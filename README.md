# java-gradle-template
 Generic Java project template following best practices

## Technology Choices and Reasoning
- Git Hooks - Automatically run formatting + unit tests on commit.
- Gradle - Currently the "best" build system due to convention-over-configuration, ability to skip over tasks, and flexibility. (Plus less XML)
    - Kotlin DSL - Statically typed and better IDE support than Groovy DSL
    - Spotless Gradle Plugin - Automatically reformats code
        - Google Java Format - Reasonable default for formatting
- Jib - Google Container builder that automatically applies best practices for a Docker image, using Distroless images
- JUnit 5 - Latest version of JUnit with new features, such as an improved Extension API
- Reckon - Automatically and deterministically get a build version, using the Git repository information 
- SpotBugs - Static analysis tool to catch errors and follow best-practices