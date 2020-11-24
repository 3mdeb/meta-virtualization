SUMMARY = ""
DESCRIPTION = ""
HOMEPAGE = ""
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://libvirt.org/sources/glib/libvirt-glib-${PV}.tar.gz"
SRC_URI[sha256sum] = "7fff8ca9a2b723dbfd04223b1c7624251c8bf79eb57ec27362a7301b2dd9ebfe"
SRC_URI[sha512sum] = "b9e7d782c25b5c3a9b2829f1be8162352438ab3505510a1ca7dd64d741eaab158dc9206fdd4b0e3e27b0c049e4bcfc47db0c9a7fa1a84888864e23d3c35c079e"

# NOTE: the following prog dependencies are unknown, ignoring: vapigen
DEPENDS = "glib-2.0 libvirt libxml2"

inherit perlnative pkgconfig autotools gobject-introspection

# disable vala bindings by default
PACAKGECONFIG ??= ""
PACKAGECONFIG[vala] = "--enable-vala, --disable-vala, libvirt, vala, vala"

do_compile_prepend() {
  export GIR_EXTRA_LIBS_PATH="${B}/libvirt-glib/.libs:${B}/libvirt-gconfig/.libs:${B}/libvirt-gobject/.libs"
}
