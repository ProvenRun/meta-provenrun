#
# This file is the ProvenRun uppercase PL recipe.
#

SUMMARY = "Companion firmware of provenrun-uppercase"
SECTION = "PETALINUX/apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit fpgamanager_custom

PKGR = "1.pl${@d.getVar('XILINX_VER_MAIN').replace('.', '_')}"

SRC_URI:append:k26-kv = "file://kv260-aibox-reid.dtsi \
           file://shell.json \
           file://system.bit \
           file://pnr-uppercase-firmware.xclbin \
          "

S = "${WORKDIR}"
