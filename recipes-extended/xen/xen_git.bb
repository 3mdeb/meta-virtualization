require xen.inc

XEN_REL ?= "4.8.4"
XEN_BRANCH ?= "stable-4.8"
XEN_TAG = "RELEASE-${XEN_REL}"

PV = "${XEN_REL}+git${SRCPV}"

S = "${WORKDIR}/git"

SRC_URI = " \
    git://github.com/3mdeb/xen.git;branch=${XEN_BRANCH};tag=${XEN_TAG} \
    file://0001-xenlockprof-format-overflov.patch \
    "

DEFAULT_PREFERENCE = "-1"

PARALLEL_MAKE = ""
