pipeline {
//   agent {
//        docker {
//            image 'docker.io/library/maven:3.6.1-jdk-8'
//            args '-v /var/jenkins_home/workspace/mono-pipeline:/opt/maven -w /opt/maven'
//        }
//    }

    agent any

//     stages {
//         stage('Build the world') {
//             steps {
//                 sh 'mvn clean install'
//             }
//         }
//
//     }

    stages {
        stage('Build project app1') {
            when {
                changeset "apps/app1/*"
            }
            steps {
                build 'app1-multi-build'
            }
        }
        stage('Build project app2') {
            when {
                changeset "apps/app2/*"
            }
            steps {
                build 'app2-multi-build'
            }
        }
    }
}
