pipeline {
    agent any

    tools {
        jdk 'jdk-25'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/ajazjamadar/Employee-Management-System.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.*', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                cp target/*.war /opt/tomcat/webapps/
                '''
            }
        }
    }

    post {

        success {
            echo 'Deployment Successful'
        }

        failure {
            echo 'Deployment Failed'
        }
    }
}
