SUMMARY = "standard image for picocom pc80x board"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL} \
                e2fsprogs mtd-utils packagegroup-core-ssh-openssh \
                openssh-sftp-server e2fsprogs-resize2fs libpam libyang \
                libnetconf2 netopeer2 sysrepo libmxml libssh2 "

EXTRA_IMAGE_FEATURES += "package-management"

TOOLCHAIN_TARGET_TASK += "kernel-devsrc"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "65536"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

inherit extrausers

# password is generated by the command "openssl passwd -crypt <password>".
# user's password is "user".
USER_NAME = "user"
USER_PASSWORD = "Ioc0d4wx/lYy6"

# add sudo, rsync, xmllint command.
IMAGE_INSTALL:append = "sudo rsync libxml2-utils tzdata dhclient cronie rsyslog logrotate "

EXTRA_USERS_PARAMS = " \
    useradd -p '${USER_PASSWORD}' ${USER_NAME}; \
    usermod -a -G sudo ${USER_NAME}; \
"
