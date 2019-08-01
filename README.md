# Monorepo
This repo showcases how one could structure and build monorepos with either Apache
Maven or Bazel. Maven is really not a monorepo-*native* build tool (e.g. lacks
trustworthy incremental builds, can only build java code natively, is recursive and
struggles with partial repo checkouts) but can be made good use of with some tricks
and usage of a couple of lesser know command line switches.

## The repo
A couple of toy applications under `apps/` depend on shared code under `libs/`.

```
.

├── apps
│   ├── app1
│   ├── app2
│   └── app2
└── libs
    ├── lib1
    ├── lib2
    └── lib3
```

## Maven and Bazel: a basic comparison of typical actions
Action | in working directory  | with Maven | with Bazel
:--- | :---: |:--- |:---
Build the world| `.` | `mvn clean package -DskipTests` | `bazel build //...:*`
Run `app1`| `.` | `java -jar apps/app1/target/app1-1.0-SNAPSHOT.jar`| `java -jar bazel-bin/apps/app1/app1_deploy.jar`
Build and test the world| `.` | `mvn clean package` | `bazel test //...:*`
Build the world| `./apps/app1` | `mvn --file ../.. clean package -DskipTests` | `bazel build //...:*`
Build `app1` and its dependencies| `.` | `mvn --projects :app1 --also-make clean package -DskipTests` | `bazel build //apps/app1:*`
Build `app1` and its dependencies| `./apps/app1` | `mvn --file ../.. --projects :app1 --also-make clean package -DskipTests` | `bazel build :*`
Build `lib1` and its dependents (aka. reverse dependencies or *rdeps* in Bazel parlance)  | `.` | `mvn --projects :lib1 --also-make-dependents clean package -DskipTests` | `bazel build $(bazel query 'rdeps("//...", "//libs/lib1")')`
Print dependencies of `app1`| `./apps/app1` | `mvn dependency:list` | `bazel query  'deps(.)' --output package` 

**Notes**
 * Maven is a *recursive* build tool that needs an reference to the root module with `--file` 
 when building from a sub folder. In contrast will Bazel discover the entire project from 
 any sub folder and packages to built can be specified both absolutely from root (`//`) and 
 relative the current working directory.
 * Bazel doesn't build the so-called *implicit* deploy jar-target (`:app1_deploy.jar`) if we
 refrain from building with the `:*` suffix. Deploy jar is Bazel speak før 
 über-/super-/fat-jar/.
 * Maven has no trustworthy support for incremental builds, hence one typically always build
 from a `clean` state.

## Continous integration
The [ci/](ci/README.md) sub folder contains a self-contained ci-stack for building the monorepo.


---
 
## a bag of useful tricks for teaching an old dog something new

 1. Building **sparse repo checkouts**  
 2. Defining the current version with an environment variable (that holds e.g current GIT revision)
 3. Focused builds


 TODO:
  Vurderar en "bazel-branch" och en "maven-branch"
  Vurder en "en paket per java package" branch før Bazel
  Test kotlin rules
  Test bazel build cache
  Test genrule før att bygga frontend kode
  Test spring boot app

  Blog artikel:
     * Monorepo øvergripande
     * Demo-repo: rent praktiskt
     * Bazel!!

