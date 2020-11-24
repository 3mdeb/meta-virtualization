SUMMARY = "Libvirt Sandbox - An application sandbox toolkit"
DESCRIPTION = "The Libvirt Sandbox project is an effort to facilitate the use \
of libvirt virtualization drivers for the purpose of sandboxing applications."
HOMEPAGE = "https://sandbox.libvirt.org/"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://gitlab.com/libvirt/libvirt-sandbox.git;protocol=https"
SRCREV = "0cfbf9990a0d7094bf4d815066012b5b8fc62071"
S = "${WORKDIR}/git"

# the libselinux is a mandatory dependency, which requires using the
# meta-selinux in the build even if we do not use the selinux
DEPENDS = "glib-2.0 intltool-native libtirpc libselinux libvirt libvirt-glib zlib xz"

# If brokensep is not used, following error appears during the do_configure():
# | Makefile.am: installing './INSTALL'
# | Makefile.am: error: required file './AUTHORS' not found
# | config.status: error: ../libvirt-sandbox-0.6.0/GNUmakefile: file not found
inherit pkgconfig autotools gobject-introspection gtk-doc

# it depends on the static LZMA library, set following line in the local.conf
# or in the distro configuration file
# DISABLE_STATIC_pn-xz = ""

do_configure_prepend() {
  # the same workaround is applied in the original autogen.sh file
  # https://gitlab.com/libvirt/libvirt-sandbox/-/blob/master/autogen.sh#L35
  touch ${S}/ChangeLog ${S}/AUTHORS
}
