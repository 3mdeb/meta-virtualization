require xen.inc

XEN_REL ?= "4.8.4"
XEN_BRANCH ?= "stable-4.8"
XEN_TAG = "RELEASE-${XEN_REL}"

PV = "${XEN_REL}+git${SRCPV}"

S = "${WORKDIR}/git"

SRC_URI = " \
    git://github.com/3mdeb/xen.git;branch=${XEN_BRANCH};tag=${XEN_TAG} \
    file://0001-xenlockprof-format-overflov.patch \
    file://0002-tools-misc-fix-hypothetical-buffer-overflow-in-xen-lowmemd.patch \
    file://0001-tools-libxc-fix-strncpy-size.patch \
    file://0001-tools-blktap2-fix-hypothetical-buffer-overflow.patch \
    file://0001-tools-blktap2-fix-possible-0-truncation.patch \
    file://0001-tools-xenpmd-fix-possible-0-truncation.patch \
    file://0001-tools-gdbsx-fix-Wstringop-truncation-warning.patch \
    file://0001-tools-kdd-mute-spurious-gcc-warning.patch \
    file://0001-tools-include-sys-sysmacros.h-on-Linux.patch \
    file://0001-xenpmd-make-32-bit-gcc-8.1-non-debug-build-work.patch \
    file://0001-tools-fix-several-format-truncation-warnings-with-GC.patch \
    "

DEFAULT_PREFERENCE = "-1"

# PARALLEL_MAKE = ""
