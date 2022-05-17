@Library("monorepo-pipeline-library") _
pipeline {

    agent any

//     environment {
//         GIT_URL = 'https://github.com/kirill-kosenko/monorepo-sample.git'
//     }

    stages {
        stage('Monorepo stage') {
            steps {
                echo env.GIT_URL
                monorepo()
            }
        }

    }
}