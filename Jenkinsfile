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
//                 changeset "apps/app1/**"
                expression {  // there are changes in some-directory/...
                                sh(returnStatus: true, script: 'git diff  origin/master --name-only | grep --quiet "^apps/app1/.*"') == 0
                            }
            }
            steps {
                ensureMultibranchJobExists('app1-multi-build')
                echo "===============> branch name: $env.BRANCH_NAME"
//                 build "../app1-multi-build_${env.BRANCH_NAME}"
                build job: "app1-multi-build/${env.BRANCH_NAME}"
            }
        }
        stage('Build project app2') {
            when {
//                 changeset "apps/app2/*"
                expression {  // there are changes in some-directory/...
                                                sh(returnStatus: true, script: 'git diff  origin/master --name-only | grep --quiet "^apps/app2/.*"') == 0
                                            }
            }
            steps {
                build 'app2-multi-build'
            }
        }
    }
}

def ensureMultibranchJobExists(targetJob) {
  def branch = env.BRANCH_NAME.replaceAll('/', '%252F')
  def rootJob = targetJob
  println "************> branch: $branch"
  println "************> job: $rootJob"

  if (branch == null) {
    throw new NullPointerException('branch is required')
  }
  if (rootJob == null) {
    throw new NullPointerException('rootJob is required')
  }

  // env.JENKINS_URL ends with a slash.
  env.ENSURE_MULTIBRANCH_JOB_EXISTS_URL = "${env.JENKINS_URL}job/$rootJob/job/$branch/"
  print "Ensuring multibranch job exists: ${env.ENSURE_MULTIBRANCH_JOB_EXISTS_URL}"

  def lastHttpStatusCode = null
  for (int i=0; i < 12; i++) {
    lastHttpStatusCode = sh(
      returnStdout: true,
      script: """
#!/bin/bash
set -euo pipefail

curl \
  --output /dev/null \
  --silent \
  --user devtools: '118f3a37298bcccdbc74030afef12f3d62' \
  --write-out '%{http_code}' \
  "${ENSURE_MULTIBRANCH_JOB_EXISTS_URL}" \
;
      """.trim(),
    )
    if (lastHttpStatusCode == '200') {
      break
    } else {
      print "Last status code: $lastHttpStatusCode"
    }

    sleep(
      time: 10,
      unit: 'SECONDS',
    )
  }

  if (lastHttpStatusCode != '200') {
    error "${env.ENSURE_MULTIBRANCH_JOB_EXISTS_URL} failed. Expected 200 status code, but received: $lastHttpStatusCode"
  }
}
