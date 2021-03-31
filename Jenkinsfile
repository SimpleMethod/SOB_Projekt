pipeline {
    agent any
    tools
    {
    gradle "Gradle7"
    }
    stages {
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
         stage('Test') {
                steps {
                    sh 'gradle test'
                    }
        }
         stage('Deploy') {
                steps {
                    sh 'echo "Template deploy"'
                    }
        }
         stage('Run') {
                steps {
                    sh 'echo "Template Run"'
                    }
        }        
    }
}
