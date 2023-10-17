require linux-mainline-common.inc

SUMMARY = "Pico ast-v5_2_0 Linux 5.4"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "${AUTOREV}"
BRANCH = "dev_pc805_bringup"
SRC_URI = "git://git@glhk.picocomtech.com/tools/linux.git;protocol=ssh;branch=${BRANCH} \
           file://pc805_defconfig \
           file://pc805.dts \
          "

do_configure:append:pc805() {
    mkdir -p ${S}/arch/riscv/boot/dts/picocom
    cp ${WORKDIR}/pc805.dts ${S}/arch/riscv/boot/dts/picocom
}

LINUX_VERSION ?= "v5.4.147"
LINUX_VERSION_EXTENSION:append:pc805 = "-pc805"

KERNEL_DEFCONFIG:pc805 = "${WORKDIR}/pc805_defconfig"

COMPATIBLE_MACHINE = "(pc805|pc803)"
