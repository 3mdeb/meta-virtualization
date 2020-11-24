SUMMARY = "libhivex - a self-contained library for reading and writing Windows \
Registry 'hive' binary files. "
DESCRIPTION = "Hivex is a library for extracting the contents of Windows \
Registry 'hive' files. It is designed to be secure against buggy or malicious \
registry files."
HOMEPAGE="http://libguestfs.org"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f23a23b996e90732d119709c8aca08db"

SRC_URI = "https://download.libguestfs.org/hivex/hivex-${PV}.tar.gz \
           file://0001-remove-images-from-SUBDIRS.patch \
           file://0001-ocaml-Makefile.am-install-to-libdir.patch \
           "

SRC_URI[sha256sum] = "8a1e788fd9ea9b6e8a99705ebd0ff8a65b1bdee28e319c89c4a965430d0a7445"

# NOTE: the following prog dependencies are unknown, ignoring: ruby ghc rake pod2man pod2text
DEPENDS = "libxml2"

# package binaries separately from the library
PACKAGES =+ "${PN}-bin"
FILES_${PN}-bin = "${bindir}"
RDEPENDS_${PN}-bin = "bash"
# FILES_${PN} += "${libdir}/stublibs ${libdir}/ocaml"

# ERROR: hivex-1.3.18-r0 do_package_qa: QA Issue: /usr/lib/hivex/mlhivex.cma contained in package hivex requires ocaml(CamlinternalFormatBasics), but no providers found in RDEPENDS_hivex? [file-rdeps]
# ERROR: hivex-1.3.18-r0 do_package_qa: QA Issue: /usr/lib/hivex/mlhivex.cma contained in package hivex requires ocaml(Pervasives), but no providers found in RDEPENDS_hivex? [file-rdeps]
# ERROR: hivex-1.3.18-r0 do_package_qa: QA Issue: /usr/lib/hivex/mlhivex.cma contained in package hivex requires ocaml(Unix), but no providers found in RDEPENDS_hivex? [file-rdeps]
# ERROR: hivex-1.3.18-r0 do_package_qa: QA Issue: /usr/lib/hivex/mlhivex.cma contained in package hivex requires ocaml(runtime), but no providers found in RDEPENDS_hivex? [file-rdeps]
# ERROR: hivex-1.3.18-r0 do_package_qa: QA Issue: /usr/lib/hivex/mlhivex.cma contained in package hivex requires ocaml(Callback), but no providers found in RDEPENDS_hivex? [file-rdeps]
# INSANE_SKIP_${PN} = "file-rdeps staticdev"

inherit perlnative python3native autotools gettext ocaml findlib

EXTRA_OECONF = " \
    --disable-perl \
    --disable-python \
    --disable-ruby \
    --enable-nls \
    --disable-rpath \
"

PACKAGECONFIG ??= "nls ocaml readline"

PACKAGECONFIG[nls] = "--enable-nls,--disable-nls,,"
PACKAGECONFIG[readline] = "--with-readline,--without-readline,readline"
PACKAGECONFIG[static] = "--enable-static,--disable-static,"
PACKAGECONFIG[ocaml] = "--enable-ocaml,--disable-ocaml,,"
