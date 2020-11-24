SUMMARY = "The virt-manager application is a desktop user interface for \
managing virtual machines through libvirt."
HOMEPAGE = "http://virt-manager.org"

LICENSE = "GPLv2 & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "https://virt-manager.org/download/sources/virt-manager/virt-manager-${PV}.tar.gz"
SRC_URI[sha256sum] = "2b6fe3d90d89e1130227e4b05c51e6642d89c839d3ea063e0e29475fd9bf7b86"
SRC_URI[sha512sum] = "90cd98fe6b269007cd30f628490c65df440abe39b4925c65dc80667e7d80d059752695353ccf6ac3e2436206da311bc402eda50df31874d82ef8fe115966e1ec"

inherit distutils3 gettext gtk-icon-cache bash-completion

# rst2man dependency
DEPENDS += "python3-docutils-native"

VIRT_MANAGER_RDEPENDS = " \
  gobject-introspection \
  libvirt-python \
  libxml2-python \
  python3-argcomplete \
  python3-core \
  python3-datetime \
  python3-difflib \
  python3-io \
  python3-logging \
  python3-math \
  python3-netclient \
  python3-pprint \
  python3-pyatspi \
  python3-pygobject \
  python3-requests \
  python3-shell \
  python3-stringold \
  python3-threading \
  python3-unittest \
  python3-unixadmin \
  python3-xml \
"

RDEPENDS_${PN} += "${VIRT_MANAGER_RDEPENDS}"
RDEPENDS_virtinst += "${VIRT_MANAGER_RDEPENDS}"


# mimic the packaging from Debian to install the CLI tools separately
PACKAGES =+ "virtinst virtinst-bash-completion virtinst-doc"

FILES_virtinst = " \
  ${bindir}/virt-clone \
  ${bindir}/virt-install \
  ${bindir}/virt-xml \
  ${datadir}/virt-manager/virtinst \
"

FILES_virtinst-bash-completion = " \
  ${bindir}/bash-completion/completions/virt-clone \
  ${bindir}/bash-completion/completions/virt-install \
  ${bindir}/bash-completion/completions/virt-xml \
"

FILES_virtinst-doc = " \
  ${datadir}/man/man1/virt-clone.1 \
  ${datadir}/man/man1/virt-install.1 \
  ${datadir}/man/man1/virt-xml.1 \
"

# following files were not packaged automatically - do we really need them and
# where should the go?
FILES_${PN} += "\
  ${datadir}/metainfo \
  ${datadir}/glib-2.0 \
  ${datadir}/metainfo/virt-manager.appdata.xml \
  ${datadir}/glib-2.0/schemas \
  ${datadir}/glib-2.0/schemas/org.virt-manager.virt-manager.gschema.xml \
  ${datadir}/glib-2.0/schemas/gschemas.compiled \
"

# WARNING: only the CL tool from virtinst package was verified at runtime
# the main virt-manager GUI tool is much less intereseting in emebdded
# environment

# The recipetool comment
# WARNING: We were unable to map the following python package/module
# dependencies to the bitbake packages which include them:
#    guestfs
#    virtBootstrap
