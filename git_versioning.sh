#!/bin/bash
#
################################################################################
##
################################################################################
#

# disallow using undefined variables
shopt -s -o nounset

GIT_REVISION=$(git rev-list HEAD --all --count)
GIT_BUILD_REV_ID=$(git rev-parse HEAD)
#GIT_BUILD_REV_ID_SHORT=$(git describe --long --tags --dirty --always)
GIT_BUILD_REV_ID_SHORT=$(git describe --long --tags --always)
GIT_GITHUB_BRANCH=$(git rev-parse --abbrev-ref HEAD)

BUILD_ARRAY=( $(git rev-list --timestamp --max-count=1 HEAD) )
GIT_LAST_CHECKIN=$(date -d@${BUILD_ARRAY[0]})
GIT_LAST_CHECKIN_AUTHOR=$(git --no-pager show -s --format='%an <%ae>' ${GIT_BUILD_REV_ID} )

if [[ "${GIT_BUILD_REV_ID}" != "${BUILD_ARRAY[1]}" ]] ; then 
	echo "What is considered HEAD commit does not match, needs investigation"
	exit 1;
fi

for var in ${!GIT_@}; do
   printf "%s%q\n" "$var=" "${!var}"
done