# CI for monorepo
Two containers are provided to demonstrate continous integration of the monorepo:
 * a [simple git repo](Dockerfile.gitrepo) to which the monorepo has been pushed
 * a [customized Jenkins](Dockerfile.jenkins) preconfigured to connect to the git repo and scan for branches with build pipelines

## Instructions
1. Run `docker compose up --build` to build images and spin up containers
2. Visit Jenkins at http://localhost:8080  - you may need to click "Scan branches now"
3. The git repo is available on a non-standard ssh port to avoid port conflicts: hence  `export GIT_SSH_COMMAND="ssh -oPort=122 -i ci/provision/keypair/.ssh/id_rsa"`
4. Clone the repo: `git clone ssh://git@localhost/home/git/test.git ~/mycheckout`
5. Play around with the clone, changing some files, pushing och check how Jenkins handles it.

N.B. ensure that the path to the private key above is absolute and matches your local folder structure!
