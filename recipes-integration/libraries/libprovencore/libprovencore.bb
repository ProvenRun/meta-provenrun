#
# Copyright (c) 2018-2022, ProvenRun and/or its affiliates. All rights reserved.
#

SUMMARY = "ProvenCore static interface to communicate with Secure World"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

SRC_URI = "file://provencore.c \
           file://provencore.h \
           file://Makefile \
          "
S = "${WORKDIR}"

PACKAGES = "${PN}"
PROVIDES += "libprovencore"
RDEPENDS:${PN} = "provencore-driver"

do_compile() {
    oe_runmake
}

do_install () {
    install -d ${D}${libdir}
    install -m 0644 ${S}/build/libprovencore.a ${D}${libdir}

    install -d ${D}${includedir}
    install -m 0644 ${S}/provencore.h ${D}${includedir}
}

FILES:${PN} += "${includedir}/*"
INSANE_SKIP:${PN} += "installed-vs-shipped"
