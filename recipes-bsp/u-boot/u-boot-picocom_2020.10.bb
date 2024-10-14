require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/vfw4spl:"
DEPENDS:append = " bc-native u-boot-tools-native python3-setuptools-native"

BRANCH = "dev_v2020.10"
SRCREV = "${AUTOREV}"
SRC_URI = " \
    git://git@github.com/sam-huxtable/u-boot.git;protocol=ssh;branch=${BRANCH} \
    file://mmc-support.cfg \
    file://opensbi-options.cfg \
    file://tftp-mmc-boot.txt \
    file://uEnv.txt \
    file://pc805_vfw4spl.h \
    file://libpc805_vfw4spl.a \
    "

do_compile[depends] += "opensbi-picocom:do_deploy"

do_compile:prepend:pc805() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
    export PICO_VFW_LIB_PATH=${WORKDIR}
}

do_deploy:append:pc805() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}
    fi

    if [ -f "${WORKDIR}/uEnv.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}
    fi

    if [ -f "${B}/u-boot.bin" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${B}/u-boot.bin ${DEPLOYDIR}/u-boot.bin-${MACHINE}-${PV}-${PR}
        ln -sf u-boot.bin-${MACHINE}-${PV}-${PR} u-boot.bin-${MACHINE}
        ln -sf u-boot.bin-${MACHINE}-${PV}-${PR} u-boot.bin
    fi
}

COMPATIBLE_MACHINE = "(pc805)"

