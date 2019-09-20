# CI for monorepo
Two containers are provided to demonstrate continous integration of the monorepo:
 * a [simple git repo](Dockerfile.gitrepo) to which the monorepo has been pushed
 * a [customized Jenkins](Dockerfile.jenkins) preconfigured to connect to the git repo and scan for branches with build pipelines

## Instructions
1. Run `docker-compose up --build` to build images and spin up containers
2. Visit Jenkins at http://localhost:8080  - you may need to click "Scan Multibranch Pipeline Now"
3. The git repo is available on a non-standard ssh port to avoid port conflicts: hence  `export GIT_SSH_COMMAND="ssh -oPort=122 -i $PWD/provision/keypair/.ssh/id_rsa"`
4. The private key the you need to access the git repo needs closed file permissions: `chmod 600 provision/keypair/.ssh/id_rsa`
5. Clone the repo: `git clone ssh://git@localhost/home/git/test.git ~/mycheckout`
6. Play around with the clone; adding a branch, changing some files, pushing and take note of how Jenkins picks different Jenkinsfiles to build.

