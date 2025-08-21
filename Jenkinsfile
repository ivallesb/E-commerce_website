pipeline {
    agent any

    // tools {
    //     // Install the Maven version configured as "M3" and add it to the path.
    //     maven "M3"
    // }

    stages {
        
        stage('Checkout'){
            steps {
                // Get some code from a GitHub repository
                git branch: 'feature/jenkins', url: 'https://github.com/ivallesb/E-commerce_website.git' 
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean compile'
                
            }

            
        }
        
        stage('Package'){
            steps{
                bat 'mvn package'
            }
            
            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the war file.
                success{
                    archiveArtifacts 'target/*.war'
                }
                // always {
                //     junit '**/target/surefire-reports/TEST-*.xml'
                // }
            }
        }
    }
}
