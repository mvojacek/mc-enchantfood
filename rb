#####
# SOF START
#   @name ReleaseBuild
#   @file rb
#   @author HashtagShell
#   @depends gradlew
# SOF END
#####
#!/bin/bash
# Script to simplify releasing of builds
# @author HashtagShell https://github.com/HashtagShell/

#read -p "Build message: " message
./gradlew releaseBuild
#version = "Build $(./version --get build.properties build)"
#git commit -m "$version"
#git tag -a -m "$message" "$version"

#####
# EOF START
#   @name ReleaseBuild
#   @file rb
#   @author HashtagShell
#   @depends gradlew
# EOF END
#####
