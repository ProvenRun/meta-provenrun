#
# Copyright (c) 2021-2022, ProvenRun and/or its affiliates. All rights reserved.
#

SOURCES := provencore.c
LIB     := libprovencore.a

LDFLAGS +=
LIBS    +=

BUILD_DIR := ./build

OBJS    := $(addprefix $(BUILD_DIR)/,$(patsubst %.c,%.o,$(SOURCES)))
DEPS    := $(OBJS:.o=.d)

ARCH=aarch64-linux
Q       := @

all: $(BUILD_DIR)/$(LIB)

-include $(DEPS)

$(BUILD_DIR)/%.o: %.c | $(BUILD_DIR)
	@echo "  CC      $<" $(ECHO_OUTPUT)
	$(Q)mkdir -p $(dir $@)
	@echo "$(Q)$(CC) $(CFLAGS) -c $< -o $@"
	$(Q)$(CC) $(CFLAGS) -c $< -o $@
	$(Q)$(CC) $(CFLAGS) -M -MT $@ -MF $(BUILD_DIR)/$*.d $<

$(BUILD_DIR):
	@mkdir -p $(BUILD_DIR)

$(BUILD_DIR)/$(LIB): $(OBJS)
	@echo "  AR      $(notdir $@)" $(ECHO_OUTPUT)
	@echo "$(Q)$(AR) rcs $@ $(OBJS)"
	$(Q)$(AR) rcs $@ $(OBJS)
	@echo $(ECHO_OUTPUT)
	@echo "Built $(notdir $@) successfully" $(ECHO_OUTPUT)
	@echo $(ECHO_OUTPUT)

.PHONY: clean
clean:
	@echo "  CLEAN" $(ECHO_OUTPUT)
	$(Q)rm -rf $(BUILD_DIR)
