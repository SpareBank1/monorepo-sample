pipeline {
   agent {
        docker {
            image 'docker.io/library/maven:3.6.1-jdk-8'
        }
    }

    stages {
        stage('Build app2') {
            steps {
                sh 'mvn -am -pl :app2 clean install'
            }
        }

    }
}
