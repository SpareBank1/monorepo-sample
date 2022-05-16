pipeline {

    agent any

    stages {
        stage('Build project app1') {
            when {
                changeset "**/apps/app1/*.*"
//                 expression {
//                     sh(returnStatus: true, script: 'git diff  origin/master --name-only | grep --quiet "^apps/app1/.*"') == 0
//                 }
            }
            steps {
                ensureMultibranchJobExists('app1-multi-build')
                build job: "app1-multi-build/${env.BRANCH_NAME}"
            }
        }
        stage('Build project app2') {
            when {
                changeset "**/apps/app2/*.*"
//                 expression {
//                     sh(returnStatus: true, script: 'git diff  origin/master --name-only | grep --quiet "^apps/app2/.*"') == 0
//                 }
            }
            steps {
                ensureMultibranchJobExists('app2-multi-build')
                build job: "app2-multi-build/${env.BRANCH_NAME}"
            }
        }
    }
}

def ensureMultibranchJobExists(rootJob) {
  def branch = env.BRANCH_NAME.replaceAll('/', '%252F')

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
  --user root:'118f3a37298bcccdbc74030afef12f3d62' \
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
