
sources()

package(default_visibility = ["//visibility:public"])


java_library(
    name = "lib1",
    srcs = [":src_main"],
    deps = ["//libs/lib2"]
)


java_test(
    name = "AllTests",
    size = "small",

    srcs = [":src_test"],
    deps = [":lib1", "@maven//:junit_junit"],
    test_class="no.monorepo.lib1.Lib1Test",
)