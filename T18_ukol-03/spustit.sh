#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p target/classes
find src -name '*.java' > target/sources.txt
javac -encoding UTF-8 -d target/classes @target/sources.txt
java -cp target/classes cz.vspj.t18.Main
