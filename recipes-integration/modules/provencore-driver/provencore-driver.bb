#
# Copyright (c) 2021-2022, ProvenRun and/or its affiliates. All rights reserved.
#

SUMMARY = "Recipe for building external provencore-driver Linux kernel module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

SRC_URI = "git://github.com/ProvenRun/provencore-driver.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
PV = "3.05"

S = "${WORKDIR}/git/drivers/provencore"

do_configure:append () {
    cp -r ${WORKDIR}/git/include/misc ${WORKDIR}/git/drivers/provencore/ree
    cp -r ${WORKDIR}/git/include/misc ${WORKDIR}/git/drivers/provencore/shdev
    mv ${WORKDIR}/git/drivers/provencore/ree/Makefile.in ${WORKDIR}/git/drivers/provencore/ree/Makefile
    mv ${WORKDIR}/git/drivers/provencore/shdev/Makefile.in ${WORKDIR}/git/drivers/provencore/shdev/Makefile
}

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR} M=${S} -C ${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE += "CONFIG_PROVENCORE_REE=m"
KERNEL_MODULE_AUTOLOAD += "pnc_ree"

EXTRA_OEMAKE:append:k26 += "PNC_CONFIG_MK=${WORKDIR}/git/configs/pnc_config_xilinx_zynqmp.mk"

