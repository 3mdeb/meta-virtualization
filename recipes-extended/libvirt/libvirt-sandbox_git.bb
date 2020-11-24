SUMMARY = "Libvirt Sandbox - An application sandbox toolkit"
DESCRIPTION = "The Libvirt Sandbox project is an effort to facilitate the use \
of libvirt virtualization drivers for the purpose of sandboxing applications."
HOMEPAGE = "https://sandbox.libvirt.org/"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://gitlab.com/libvirt/libvirt-sandbox.git;protocol=https \
           file://0002-m4-virt-xdr.md-prefix-the-tirpc-include-path-with-SY.patch \
           file://0003-introduce-without-selinux-switch.patch \
           "
SRCREV = "0cfbf9990a0d7094bf4d815066012b5b8fc62071"
S = "${WORKDIR}/git"

DEPENDS = "glib-2.0 intltool-native libtirpc libvirt libvirt-glib zlib xz"

inherit pkgconfig autotools gobject-introspection gtk-doc

# The libvirt-sandbox-init-lxc and libvirt-sandbox-init-qemu are linked with
# -all-static option and need static libraries (-l, -lzma)
# Set following lines in the local.conf or in the distro configuration file
# to build the recipe properly
# DISABLE_STATIC_pn-glibc = ""
# DISABLE_STATIC_pn-xz = ""
# DISABLE_STATIC_pn-zlib = ""

do_configure_prepend() {
  # the same workaround is applied in the original autogen.sh file
  # https://gitlab.com/libvirt/libvirt-sandbox/-/blob/master/autogen.sh#L35
  touch ${S}/ChangeLog ${S}/AUTHORS
}

# the includedir prefix to be used by the m4/virt-xdr.m4 file
export SYSROOT="${RECIPE_SYSROOT}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[selinux] = "--with-selinux,--without-selinux,libselinux"
