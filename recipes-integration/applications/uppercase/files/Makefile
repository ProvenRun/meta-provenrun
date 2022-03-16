#
# Copyright (c) 2021-2022, ProvenRun and/or its affiliates. All rights reserved.
#

CROSS_COMPILE ?= arm-linux-gnueabi-

Q := @

HOST_CC ?= gcc
CC ?= $(CROSS_COMPILE)gcc

uppercase: uppercase.c
	@echo "  CC      $^"
	$(Q)$(CC) $(CFLAGS) --std=gnu99 $^ $(LDFLAGS) -o $@

all: uppercase

clean:
	@rm -f uppercase

install: uppercase
	install -D -m 755 $< $(INSTALL_PATH)/usr/bin

.PHONY: clean
