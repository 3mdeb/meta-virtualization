require xen.inc

SRCREV ?= "${AUTOREV}"

XEN_REL = "4.12"
XEN_BRANCH = "staging"
FLASK_POLICY_FILE = "xenpolicy-${XEN_REL}-unstable"

PV = "${XEN_REL}+git${SRCPV}"

S = "${WORKDIR}/git"

### build from remote repo
SRC_URI = " \
    git://github.com/3mdeb/xen.git;branch=${XEN_BRANCH} \
    "

### build from local repo:
#SRC_URI = " \
#    git:///home/user/storage/projects/xen;branch=${XEN_BRANCH};protocol=file \
#    "

DEFAULT_PREFERENCE = "-1"
