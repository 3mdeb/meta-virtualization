SUMMARY = "libguestfs - tools for accessing and modifying virtual machine disk images"
DESCRIPTION = "libguestfs is a set of tools for accessing and modifying \
virtual machine (VM) disk images. You can use this for viewing and editing \
files inside guests, scripting changes to VMs, monitoring disk used/free \
statistics, creating guests, P2V, V2V, performing backups, cloning VMs, \
building VMs, formatting disks, resizing disks, and much more."
HOMEPAGE = "https://libguestfs.org/"
LICENSE = "LGPLv2.1 & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "https://download.libguestfs.org/1.38-stable/libguestfs-${PV}.tar.gz"

SRC_URI[sha256sum] = "f7e808b0e9a77d6f1bb7201b05bd52751ff79f210cea724725585129fa7b55ec"

# The ocaml native is available on in the meta-intel-sgx layer, which creates
# the dependency of meta-virtualization on the meta-intel-sgx - probably we
# would like to avoid that.
# There is another layer with ocaml recipe here:
# https://gitlab.com/wtolkien/meta-ocaml but it seems abandoned
DEPENDS = "bison-native cdrkit-native flex-native gperf-native ocaml-cross-${TARGET_ARCH} findlib-cross-${TARGET_ARCH} qemu-system-native augeas file hivex jansson libconfig libxml2"

inherit autotools bash-completion gettext perlnative

EXTRA_OECONF = " \
    --disable-appliance \
    --disable-erlang \
    --disable-gobject \
    --disable-golang \
    --disable-haskell \
    --disable-introspection \
    --disable-lua \
    --disable-ocaml \
    --disable-perl \
    --disable-php \
    --disable-probes \
    --disable-ruby \
    --without-java \
    --with-gtk=no \
"

PACKAGECONFIG ??= "fuse libvirt nls python readline"

PACKAGECONFIG[libvirt] = "--with-libvirt, --without-libvirt, libvirt,"
PACKAGECONFIG[readline] = "--with-readline,--without-readline,readline"
PACKAGECONFIG[nls] = "--enable-nls,--disable-nls,,"
# python3-setuptools-native
PACKAGECONFIG[python] = "--enable-python,--disable-python,,"
PACKAGECONFIG[fuse] = " --enable-fuse, --disable-fuse, fuse"
