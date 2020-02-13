SUMMARY = "User space components of the Ceph file system"
LICENSE = "LGPLv2.1 & GPLv2 & Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://COPYING-LGPL2.1;md5=fbc093901857fcd118f065f900982c24 \
                    file://COPYING-GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING;md5=601c21a554d728c3038ca292b83b8af0 \
"
inherit cmake python3native python3-dir systemd
# Disable python pybind support for ceph temporary, when corss compiling pybind,
# pybind mix cmake and python setup environment, would case a lot of errors.

SRC_URI = "http://download.ceph.com/tarballs/ceph-${PV}.tar.gz \
           file://0001-ceph-fix-build-errors-for-cross-compile.patch \
           file://ceph.conf \
           file://0001-rgw-add-executor-type-for-basic_waitable_timers.patch \
           file://0001-common-rgw-workaround-for-boost-1.72.patch \
           file://0001-rgw-beast-handle_connection-takes-io_context.patch \
"

SRC_URI[md5sum] = "8100ce9820714554e6d4717f6f0aa4da"
SRC_URI[sha256sum] = "9606dc80553bd97e138cd80b6bbbc117b6b26c66248e490a4e49fc0d4d853862"

DEPENDS = "boost bzip2 curl expat gperf-native \
           keyutils libaio libibverbs lz4 \
           nspr nss \
           oath openldap openssl \
           python3 python3-cython-native rabbitmq-c rocksdb snappy udev \
           valgrind xfsprogs zlib \
"
SYSTEMD_SERVICE_${PN} = " \
        ceph-radosgw@.service \
        ceph-radosgw.target \
        ceph-mon@.service \
        ceph-mon.target \
        ceph-mds@.service \
        ceph-mds.target \
        ceph-osd@.service \
        ceph-osd.target \
        ceph.target \
        ceph-rbd-mirror@.service \
        ceph-rbd-mirror.target \
        ceph-volume@.service \
        ceph-mgr@.service \
        ceph-mgr.target \
        ceph-crash.service \
        rbdmap.service \
"
OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE = "-DWITH_MANPAGE=OFF \
                 -DWITH_FUSE=OFF \
                 -DWITH_SPDK=OFF \
                 -DWITH_LEVELDB=OFF \
                 -DWITH_LTTNG=OFF \
                 -DWITH_BABELTRACE=OFF \
                 -DWITH_TESTS=OFF \
                 -DWITH_MGR=OFF \
                 -DWITH_MGR_DASHBOARD_FRONTEND=OFF \
                 -DWITH_SYSTEM_BOOST=ON \
                 -DWITH_SYSTEM_ROCKSDB=ON \
                 -DWITH_RDMA=OFF \
                 -DWITH_RADOSGW_AMQP_ENDPOINT=OFF \
                 -DPYTHON_INSTALL_DIR=${PYTHON_SITEPACKAGES_DIR} -DPYTHON_DESIRED=3 \
                 -DPYTHON_EXECUTABLE=${PYTHON} \
"

do_configure_prepend () {
	echo "set( CMAKE_SYSROOT \"${RECIPE_SYSROOT}\" )" >> ${WORKDIR}/toolchain.cmake
	echo "set( CMAKE_DESTDIR \"${D}\" )" >> ${WORKDIR}/toolchain.cmake
	echo "set( PYTHON_SITEPACKAGES_DIR \"${PYTHON_SITEPACKAGES_DIR}\" )" >> ${WORKDIR}/toolchain.cmake
}

do_install_append () {
	sed -i -e 's:^#!/usr/bin/python$:&3:' \
		-e 's:${WORKDIR}.*python3:${bindir}/python3:' \
		${D}${bindir}/ceph ${D}${bindir}/ceph-crash \
		${D}${bindir}/ceph-volume ${D}${bindir}/ceph-volume-systemd
	find ${D} -name SOURCES.txt | xargs sed -i -e 's:${WORKDIR}::'
	install -d ${D}${sysconfdir}/ceph
	install -m 644 ${WORKDIR}/ceph.conf ${D}${sysconfdir}/ceph/
	install -d ${D}${systemd_unitdir}
	mv ${D}${libexecdir}/systemd/system ${D}${systemd_unitdir}
	mv ${D}${libexecdir}/ceph/ceph-osd-prestart.sh ${D}${libdir}/ceph
	mv ${D}${libexecdir}/ceph/ceph_common.sh ${D}${libdir}/ceph
	# WITH_FUSE is set to OFF, remove ceph-fuse related units
	rm ${D}${systemd_unitdir}/system/ceph-fuse.target ${D}${systemd_unitdir}/system/ceph-fuse@.service
}

do_install_append_class-target () {
	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/tmpfiles.d
		echo "d /var/lib/ceph/crash/posted 0755 root root - -" > ${D}${sysconfdir}/tmpfiles.d/ceph-placeholder.conf
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/default/volatiles
		echo "d root root 0755 /var/lib/ceph/crash/posted none" > ${D}${sysconfdir}/default/volatiles/99_ceph-placeholder
	fi
}

pkg_postinst_${PN}() {
	if [ -z "$D" ] && [ -e ${sysconfdir}/init.d/populate-volatile.sh ] ; then
		${sysconfdir}/init.d/populate-volatile.sh update
	fi
}

FILES_${PN} += "\
		${libdir}/rados-classes/*.so.* \
		${libdir}/ceph/compressor/*.so \
		${libdir}/rados-classes/*.so \
		${libdir}/ceph/*.so \
"

FILES_${PN} += " \
    /etc/tmpfiles.d/ceph-placeholder.conf \
    /etc/default/volatiles/99_ceph-placeholder \
"

FILES_${PN}-python = "\
                ${PYTHON_SITEPACKAGES_DIR}/* \
"
RDEPENDS_${PN} += "\
		python3-core \
		python3-misc \
		python3-modules \
		python3-prettytable \
		${PN}-python \
"
COMPATIBLE_HOST = "(x86_64).*"
PACKAGES += " \
	${PN}-python \
"
INSANE_SKIP_${PN}-python += "ldflags"
INSANE_SKIP_${PN} += "dev-so"
CCACHE_DISABLE = "1"

CVE_PRODUCT = "ceph ceph_storage ceph_storage_mon ceph_storage_osd"
