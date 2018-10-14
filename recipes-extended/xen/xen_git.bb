require xen.inc

XEN_REL = "4.8"
XEN_BRANCH = "staging-4.8"
TAG = "RELEASE-4.8.4"
SRC_REF = "tag=${TAG};branch=${XEN_BRANCH}"

FLASK_POLICY_FILE = "xenpolicy-${XEN_REL}-unstable"

PV = "${TAG}"

S = "${WORKDIR}/git"

### build from remote repo
SRC_URI = " \
    git://github.com/3mdeb/xen.git;${SRC_REF} \
    "

### build from local repo:
#SRC_URI = " \
#    git:///home/user/storage/projects/xen;branch=${XEN_BRANCH};protocol=file \
#    "

DEFAULT_PREFERENCE = "-1"
