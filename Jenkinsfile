pipeline {
//   agent {
//        docker {
//            image 'docker.io/library/maven:3.6.1-jdk-8'
//            args '-v /var/jenkins_home/workspace/mono-pipeline:/opt/maven -w /opt/maven'
//        }
//    }

    agent any

    stages {
        stage('Build the world') {
            steps {
                sh 'mvn clean install'
            }
        }

    }
}
