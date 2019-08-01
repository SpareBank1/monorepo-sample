def sources(visibility=None):
  native.filegroup(
    name = "src_main",
    srcs = ["BUILD"] + native.glob(["src/main/java/**/*.java"]),
  )
  native.filegroup(
    name = "src_test",
    srcs = ["BUILD"] + native.glob(["src/test/java/**/*.java"]),
  )


