package(default_visibility = ["//visibility:public"])


sources()


java_library(
    name = "lib2",
    srcs = [":src_main"],
    deps = ["@maven//:org_apache_commons_commons_lang3"],
)

java_test(
    name = "AllTests",
    size = "small",
    srcs = [":src_test"],
    deps = [":lib2", "@maven//:junit_junit"],
    test_class="no.monorepo.lib2.Lib2Test",
)