#
# Copyright (c) 2018-2022, ProvenRun and/or its affiliates. All rights reserved.
#

SUMMARY = "Linux application communicating with secure application toupper"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://uppercase.c \
           file://Makefile \
           file://README.md \
           file://pnr-uppercase-conf.json \
          "

S = "${WORKDIR}"

export STAGING_INCDIR
TARGET_CC_ARCH += "${LDFLAGS}"

PKGR = "1.pl${@d.getVar('XILINX_VER_MAIN').replace('.', '_')}"

DEPENDS = " jsoncpp libaccelize-drm libprovencore"
RDEPENDS:${PN} += " provenrun-uppercase-firmware"
LDFLAGS += "-L${STAGING_LIBDIR} -lrt -lpthread -lstdc++ -ljsoncpp -laccelize_drmc -lprovencore"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/xilinx_appstore
    install -d ${D}${sysconfdir}/xilinx_appstore/provenrun-uppercase
    install -m 0755 ${S}/uppercase ${D}${bindir}/pnr-uppercase
    install -m 0644 ${S}/README.md ${D}${sysconfdir}/xilinx_appstore/provenrun-uppercase/
    install -m 0644 ${S}/pnr-uppercase-conf.json ${D}${sysconfdir}/xilinx_appstore/provenrun-uppercase/
}

pkg_postinst:${PN} () {
    #!/bin/sh -e
    chmod u+s $D${bindir}/pnr-uppercase
    chmod a+rw $D${sysconfdir}/xilinx_appstore
}
