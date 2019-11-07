# Monorepo sample
This repo showcases how one could structure and build monorepos with either Apache Maven or Bazel.

## The repo
A couple of toy applications under `apps/` depend on shared code under `libs/`.

```
.

├── apps
│   ├── app1
│   └── app2
└── libs
    ├── lib1
    └── lib2
```

## Continous integration
The [`ci/`](ci/README.md) sub folder contains a self-contained ci-stack for building the monorepo with Jenkins.

## Maven and Bazel: a comparison of basic actions
Maven is really not a monorepo-*native* build tool (e.g. lacks
trustworthy incremental builds, can only build java code natively, is recursive and
struggles with partial repo checkouts) but can be made good use of with some tricks
and usage of a couple of lesser known command line switches.

Action | in working directory  | with Maven | with Bazel
:--- | :---: |:--- |:---
Build the world| `.` | `mvn clean package -DskipTests` | `bazel build //...:*`
Run `app1`| `.` | `java -jar apps/app2/target/app1-1.0-SNAPSHOT.jar`| `java -jar bazel-bin/apps/app1/app1_deploy.jar`
Build and test the world| `.` | `mvn clean package` | `bazel test //...:*`
Build the world| `./apps/app1` | `mvn --file ../.. clean package -DskipTests` | `bazel build //...:*`
Build `app1` and its dependencies| `.` | `mvn --projects :app1 --also-make clean package -DskipTests` | `bazel build //apps/app1:*`
Build `app1` and its dependencies| `./apps/app1` | `mvn --file ../.. --projects :app1 --also-make clean package -DskipTests` | `bazel build :*`
Build `lib1` and its dependents (aka. reverse dependencies or *rdeps* in Bazel parlance)  | `.` | `mvn --projects :lib1 --also-make-dependents clean package -DskipTests` | `bazel build $(bazel query 'rdeps("//...", "//libs/lib1")')`
Print dependencies of `app1`| `./apps/app1` | `mvn dependency:list` | `bazel query  'deps(.)' --output package` 

**Notes**
 * Maven is a *recursive* build tool that needs an reference to the root module with `--file` 
 when building from a sub folder. In contrast will Bazel discover the entire project from 
 any sub folder and packages to be built can be specified both absolutely from root (`//`) and 
 relative the current working directory.
 * Bazel doesn't build the so-called *implicit* deploy jar-target (`:app1_deploy.jar`) if we
 refrain from building with the `:*` suffix. Deploy jar is Bazel speak før 
 über-/super-/fat-jar. 
 * Maven has no trustworthy support for incremental builds, hence one typically always build
 from a `clean` state.

## Sparse checkouts
A monorepo will naturally grow quite large quite fast and for many reasons engineers will for the
most time prefer to only have a subset of the tree checked out at once. Git *sparse checkouts* provides
a mechanism for archieving this.

Executing:
```bash
git config core.sparseCheckout true
echo '/*'           >  .git/info/sparse-checkout
echo '!/apps/*'     >> .git/info/sparse-checkout
echo '/apps/app1/*' >> .git/info/sparse-checkout
```
enables sparse checkouts and configures git to checkout the the whole tree except all apps other
than app1. To make it happen, either run a checkout command or to update the work tree in-place:
```
git read-tree -mu HEAD
```

## Notes for Maven
### Sparse checkouts
While Bazel is fine with sparse checkouts, Maven struggles as a sparse checkout typically implies
that there will be pom.xml-references to submodules that no longer are present on disc. A hack to
work-around this is to use Maven profiles that only activates when the corresponding pom.xml-file
is available on disc:
```xml
<profile>
  <id>app2</id>
  <activation>
    <file>
      <exists>app2/pom.xml</exists>
    </file>
  </activation>
  <modules>
    <module>app2</module>
  </modules>
</profile>
```

### Build parallelization
Maven requires explicit setup to build modules in parallell. This can be provided as a switch on the
command-line (`--threads 2`) or in [`.mvn/maven.config`](.mvn/maven.config).

### Versioning
Maven requires you to provide an version for every module in its [Project Object Model](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html).
This is confusing and superflous in monorepos as the they are typically exclusively versioned by the version control system.
This repo takes advantage of Maven's newish support for so-called [CI Friendly Versions](https://maven.apache.org/maven-ci-friendly.html).
This makes it possible to ensure that the version of all Maven modules are set to current git HEAD at build-time:
```bash
$ mvn clean install -Drevision=$(git rev-parse HEAD)
```
To ensure that no CI-friendly version placeholders (`<version>${revision}</version>`) are present in the pom-files that are installed,
this repo utilizes the [flatten-maven-plugin](https://www.mojohaus.org/flatten-maven-plugin/) to resolve them to their values before install.

### Partial builds
Maven's lack of incremental build support can lead to long build times in a large monorepo.
The [partial-build-plugin](https://github.com/lesfurets/partial-build-plugin) can be used to only build modules that has
a diff against the trunk branch.
