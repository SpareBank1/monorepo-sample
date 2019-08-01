node {

    def scmvars = checkout(scm)
    def commitHash = scmvars.GIT_COMMIT
    def mavenOptions = "-Drevision=${commitHash}"

    env.MAVEN_OPTS="${mavenOptions}"


    try {
        stage('Determine Jenkinsfile to build') {
            def sout = sh(returnStdout: true, script: 'git diff --name-only origin/master...HEAD')

            def j = findJenkinsfileToRun(sout.split())

            if (j.toString() == "${pwd()}/Jenkinsfile") {
                println("Building the whole world")

                load "Jenkinsfile-build-the-world"

            } else {
                println("Building ${j.toString()}")
                load "${j.toString()}"
            }

        }
        currentBuild.result = 'SUCCESS'
    } catch (err) {
        println("ERR: ${err}")
        currentBuild.result = 'FAILED'
    }
}


@NonCPS
def createFilePath(def path) {
    if (env['NODE_NAME'].equals("master")) {
        File localPath = new File(path)
        return new hudson.FilePath(localPath);
    } else {
        return new hudson.FilePath(Jenkins.getInstance().getComputer(env['NODE_NAME']).getChannel(), path);
    }
}

@NonCPS
def findMostSpecificJenkinsFile(filePath) {
    if (filePath.name == "Jenkinsfile" && ! filePath.isDirectory()) {
        return filePath;
    }

    def potentialJenkinsFile=createFilePath("${filePath.getParent().toString()}/Jenkinsfile")

    if (potentialJenkinsFile.exists()) {
        return potentialJenkinsFile
    }

    return findMostSpecificJenkinsFile(filePath.getParent())
}

@NonCPS
def findJenkinsfileToRun(paths) {

    def foundJenkinsFiles = []

    println("Finding most specific Jenkinsfile for changed files:\n${paths.join('\n')}")

    for (path in paths) {
        def f = createFilePath("${pwd()}/${path}")
        def j = findMostSpecificJenkinsFile(f)
        foundJenkinsFiles = foundJenkinsFiles << j
    }

    println("Selecting from:\n${foundJenkinsFiles.join('\n')}")

    foundJenkinsFiles.unique()

    if (foundJenkinsFiles.size() == 1) {
        return foundJenkinsFiles.get(0)
    } else {
        return "${pwd()}/Jenkinsfile"
    }
}
