SUMMARY = "OpenvSwitch"
DESCRIPTION = "Open vSwitch is a production quality, multilayer virtual switch licensed under the open source Apache 2.0 license. It is designed to enable massive network automation through programmatic extension, while still supporting standard management interfaces and protocols (e.g. NetFlow, sFlow, SPAN, RSPAN, CLI, LACP, 802.1ag)"
HOMEPAGE = "http://openvswitch.org/"
SECTION = "networking"
LICENSE = "Apache-2"

DEPENDS += "bridge-utils openssl python perl"

RDEPENDS_${PN} += "util-linux-uuidgen util-linux-libuuid coreutils \
	       python perl perl-module-strict ${PN}-switch ${PN}-controller"
RDEPENDS_${PN}-controller = "${PN} lsb ${PN}-pki"
RDEPENDS_${PN}-switch = "${PN} openssl procps util-linux-uuidgen"
RDEPENDS_${PN}-pki = "${PN}"
RDEPENDS_${PN}-brcompat = "${PN} ${PN}-switch"
RRECOMMENDS_${PN} += "kernel-module-openvswitch"

PR = "r1"

SRC_URI = "http://openvswitch.org/releases/openvswitch-${PV}.tar.gz \
	file://openvswitch-switch \
	file://openvswitch-switch-setup \
	file://openvswitch-controller \
	file://openvswitch-controller-setup \
	file://openvswitch-add-target-python-handling.patch \
	file://openvswitch-add-target-perl-handling.patch \
	"

SRC_URI[md5sum] = "fe8b49efe9f86b57abab00166b971106"
SRC_URI[sha256sum] = "803966c89d6a5de6d710a2cb4ed73ac8d8111a2c44b12b846dcef8e91ffab167"
LIC_FILES_CHKSUM = "file://COPYING;md5=49eeb5acb1f5e510f12c44f176c42253"

# Don't compile kernel modules by default since it heavily depends on
# kernel version. Use the in-kernel module for now.
# distro layers can enable with EXTRA_OECONF_pn_openvswitch += ""
# EXTRA_OECONF = "--with-linux=${STAGING_KERNEL_DIR} KARCH=${TARGET_ARCH}"

EXTRA_OECONF += "TARGET_PYTHON=${bindir}/python \
                 TARGET_PERL=${bindir}/perl \
                "

ALLOW_EMPTY_${PN}-pki = "1"
PACKAGES =+ "${PN}-controller ${PN}-switch ${PN}-brcompat ${PN}-pki"

FILES_${PN}-controller = "${sysconfdir}/init.d/openvswitch-controller \
	${sysconfdir}/default/openvswitch-controller \
	${sysconfdir}/openvswitch-controller \
	${bindir}/ovs-controller"

FILES_${PN}-brcompat = "${sbindir}/ovs-brcompatd"

FILES_${PN}-switch = "${sysconfdir}/init.d/openvswitch-switch \
		   ${sysconfdir}/default/openvswitch-switch \
		   "
# silence a warning
FILES_${PN} += "${datadir}/ovsdbmonitor"

inherit autotools update-rc.d

INITSCRIPT_PACKAGES = "${PN}-switch ${PN}-controller"
INITSCRIPT_NAME_${PN}-switch = "openvswitch-switch"
INITSCRIPT_PARAMS_${PN}-switch = "defaults 71"

INITSCRIPT_NAME_${PN}-controller = "openvswitch-controller"
INITSCRIPT_PARAMS_${PN}-controller = "defaults 72"

do_install_append() {
	install -d ${D}/${sysconfdir}/default/
	install -m 660 ${WORKDIR}/openvswitch-switch-setup ${D}/${sysconfdir}/default/openvswitch-switch
	install -d ${D}/${sysconfdir}/openvswitch-controller
	install -m 660 ${WORKDIR}/openvswitch-controller-setup ${D}/${sysconfdir}/default/openvswitch-controller

	install -d ${D}/${sysconfdir}/init.d/
	install -m 755 ${WORKDIR}/openvswitch-controller ${D}/${sysconfdir}/init.d/openvswitch-controller
	install -m 755 ${WORKDIR}/openvswitch-switch ${D}/${sysconfdir}/init.d/openvswitch-switch
	true || rm -fr ${D}/${datadir}/${PN}/pki
}

pkg_postinst_${PN}-pki () {
	# can't do this offline
        if [ "x$D" != "x" ]; then
                exit 1
        fi
	if test ! -d $D/${datadir}/${PN}/pki; then
            ovs-pki init --dir=$D/${datadir}/${PN}/pki
        fi
}

pkg_postinst_${PN}-controller () {
        # can't do this offline
        if [ "x$D" != "x" ]; then
                exit 1
        fi

	cd $D/${sysconfdir}/openvswitch-controller
        if ! test -e cacert.pem; then
            ln -s $D/${datadir}/${PN}/pki/switchca/cacert.pem cacert.pem
        fi
        if ! test -e privkey.pem || ! test -e cert.pem; then
            oldumask=$(umask)
            umask 077
            ovs-pki req+sign --dir=$D/${datadir}/${PN}/pki tmp controller >/dev/null
            mv tmp-privkey.pem privkey.pem
            mv tmp-cert.pem cert.pem
            mv tmp-req.pem req.pem
            chmod go+r cert.pem req.pem
            umask $oldumask
        fi
}
