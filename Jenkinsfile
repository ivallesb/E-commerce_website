pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    environment {
        // Define environment variables
        MAVEN_OPTS = '-Xmx1024m'
        PROJECT_NAME = 'mycart'
    }

    options {
        // Keep only the last 10 builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Timeout the build after 30 minutes
        timeout(time: 30, unit: 'MINUTES')
        // Add timestamps to console output
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out code from repository..."
                // Get some code from a GitHub repository
                git branch: 'feature/jenkins', url: 'https://github.com/ivallesb/E-commerce_website.git'
                
            }
        }

        // stage('Code Quality') {
            // parallel {
                stage('Compile') {
                    steps {
                        echo "Compiling the application..."
                        bat 'mvn clean compile'
                    }
                }
                
                // stage('Code Analysis') {
                //     steps {
                //         echo "Running static code analysis..."
                //         // Add static code analysis tools like SpotBugs or PMD
                //         script {
                //             try {
                //                 bat 'mvn spotbugs:check'
                //             } catch (Exception e) {
                //                 echo "SpotBugs not configured, skipping..."
                //             }
                //         }
                //     }
                // }
            // }
        // }

        stage('Test') {
            steps {
                echo "Running tests..."
                script {
                    try {
                        bat 'mvn test'
                    } catch (Exception e) {
                        echo "No tests found or test execution failed"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
            post {
                always {
                    // Publish test results if they exist
                    script {
                        if (fileExists('target/surefire-reports/*.xml')) {
                            junit 'target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo "Packaging the application..."
                bat 'mvn package -DskipTests'
            }
            post {
                success {
                    echo "Archiving artifacts..."
                    archiveArtifacts artifacts: 'target/*.war', 
                                   fingerprint: true,
                                   allowEmptyArchive: false
                    
                    // Store build info
                    script {
                        def warFile = bat(returnStdout: true, script: 'dir /B target\\*.war').trim()
                        echo "Generated WAR file: ${warFile}"
                    }
                }
            }
        }


        stage('Deploy to Staging') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                    branch 'feature/jenkins'
                }
            }
            steps {
                echo "Deploying to staging environment..."
                script {
                    // Add your deployment logic here
                    // For example, copy to a staging server or deploy to a container
                    echo "WAR file ready for deployment: target/${PROJECT_NAME}.war"
                    
                    // Example: Deploy to local Tomcat (uncomment and modify as needed)
                    // bat 'copy target\\*.war "C:\\apache-tomcat\\webapps\\"'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo "Deploying WAR to Nexus..."
                withCredentials([usernamePassword(credentialsId: 'nexus_user', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    configFileProvider([configFile(fileId: 'maven_settings', variable: 'MAVEN_SETTINGS')]) {
                        bat 'mvn deploy -s %MAVEN_SETTINGS% -DskipTests'
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline execution completed."
            // Clean workspace after build
            cleanWs()
        }
        success {
            echo "Build succeeded! 🎉"
            // Add success notifications here (email, Slack, etc.)
        }
        failure {
            echo "Build failed! ❌"
            // Add failure notifications here
        }
        unstable {
            echo "Build completed but is unstable ⚠️"
        }
    }
}
