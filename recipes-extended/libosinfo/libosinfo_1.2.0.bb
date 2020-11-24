SUMMARY = "libosinfo - The Operating System information database"
DESCRIPTION = "The goal of libosinfo is to provide a single place containing all \
the information about an operating system that is required in order to \
provision and manage it in a virtualized environment"
HOMEPAGE = "https://libosinfo.org/"
LICENSE = "LGPLv2.1 & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "https://releases.pagure.org/libosinfo/libosinfo-${PV}.tar.gz"
SRC_URI[sha256sum] = "ee254fcf3f92447787a87b3f6df190c694a787de46348c45101e8dc7b29b5a78"
SRC_URI[sha512sum] = "d98a107a08c5e2cddd746631b3ceac4278184ea5561ad2372d772b8ae253dc1363f217778d105387e5b5c5e86be9607668aa94929ad9b4ee48ab7abf50fb6a01"

DEPENDS = "libxslt curl libxml2 glib-2.0 intltool-native"

inherit pkgconfig gettext perlnative autotools gobject-introspection gtk-doc

# package binaries separately
PACKAGES =+ "${PN}-bin"
FILES_${PN}-bin = "${bindir}"

# disable vala bindings by default
PACAKGECONFIG ??= ""
PACKAGECONFIG[vala] = "--enable-vala, --disable-vala, libvirt, vala, vala"
