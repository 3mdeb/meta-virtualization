# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "Container bootstrapping tool"
HOMEPAGE = "https://github.com/virt-manager/virt-bootstrap"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
# NOTE: Original package / source metadata indicates license is: GPL-3.0+
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
LICENSE = "GPLv3 & GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

SRC_URI = "http://virt-manager.org/download/sources/virt-bootstrap/virt-bootstrap-${PV}.tar.gz"
SRC_URI[md5sum] = "4e01a9137a5394354c38e6c91e1414b1"
SRC_URI[sha1sum] = "bde0018a29f43bd9e8661a399a947fb07f8b04ea"
SRC_URI[sha256sum] = "987ba0f4f8f14836ae006e8ea9a3794e6f20af396660d00ee5c3094db5d46985"
SRC_URI[sha384sum] = "02a812df1d308554288bcec7e7fa585afc2b9558a81b5b808f8c779ce0ade97ac96a7c8c5689487027000ab3a618d7f5"
SRC_URI[sha512sum] = "b9259b37adf3970fb6975ee793a1931cca50d90f44934c41a51fdda9fe359d09c083df25c07b2f94641af128749932e5a26a8207fc5e2c607b6593bcf4276c97"

inherit setuptools3

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.
RDEPENDS_${PN} += "python3-compression python3-core python3-crypt python3-io python3-unittest"

# WARNING: We were unable to map the following python package/module
# dependencies to the bitbake packages which include them:
#    guestfs
#    mock
#    passlib.hosts
#    virtBootstrap
