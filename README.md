# Monorepo in Maven and Bazel


Action | in working directory  | with Maven | with Bazel
:--- | :---: |:--- |:---
Build the world| `.` | `mvn clean install` | `bazel build //...`
Build the world| `./apps/app1` | `mvn --file ../.. clean install` | `bazel build //...`
Build `app1` and its dependencies| `.` | `mvn --projects :app1 --also-make clean install` | `bazel build //apps/app1`
Build `app1` and its dependencies| `./apps/app1` | `mvn --file ../.. --projects :app1 --also-make clean install` | `bazel build .`
Build `lib1` and its dependents (aka. reverse dependencies or *rdeps* in Bazel parlance)  | `.` | `mvn --projects :lib1 --also-make-dependents clean install` | `bazel build $(bazel query 'rdeps("//...", "//libs/lib1")')`
Print dependencies of `app1`     | `./apps/app1` | `mvn dependency:list` | `bazel query  'deps(.)' --output package` 

 
## a bag of useful tricks for teaching an old dog something new

 1. Building **sparse repo checkouts**  
 2. Defining the current version with an environment variable (that holds e.g current GIT revision)
 3. Focused builds


 TODO:
  Vurderar en "bazel-branch" och en "maven-branch"
  Vurder en "en paket per java package" branch før Bazel
  Test kotlin rules
  Test bazel build cache

  Blog artikel:
     * Monorepo øvergripande
     * Demo-repo: rent praktiskt
     * Bazel!!

