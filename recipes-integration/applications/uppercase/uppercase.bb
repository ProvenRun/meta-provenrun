#
# Copyright (c) 2018-2022, ProvenRun and/or its affiliates. All rights reserved.
#

SUMMARY = "Linux application communicating with secure application toupper"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://uppercase.c \
           file://Makefile \
           file://README.md \
          "

S = "${WORKDIR}"

DEPENDS = "libprovencore"
LDFLAGS += "-L${STAGING_LIBDIR} -lprovencore"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/uppercase ${D}${bindir}
}
