#!/bin/bash

KEY_UID=F4DF9A6C
VERSION=0.1.0

# release into local repo
./gradlew clean publishMavenJavaPublicationToDryRepository -Prelease=true

echo ''
echo '=== BUILD DONE, ONTO PUBLISHING ==='
echo ''

# move into local repo
pushd pretty/build/repo

# sign all the artifacts in the local repo
read -s -p "GPG key passphrase: " passphrase
echo
for f in com/madisp/pretty/pretty/*/*.{jar,pom}; do
	gpg -u $KEY_UID --passphrase $passphrase --sign --detach-sign -a $f
done

# publish to staging
pushd com/madisp/pretty/pretty/$VERSION
FILES=""
for f in *.{asc,jar,pom}; do
	FILES="$f $FILES"
done
FILES=${FILES%" "}
jar -cvf bundle.jar $FILES
popd # com/madisp/pretty/pretty/$VERSION

popd # pretty/build/repo
