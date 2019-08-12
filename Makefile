PACKAGE_NAME = sudokuvalidator
VERSION = 1.0-SNAPSHOT
SRC_JAR = sudokuvalidator-$(VERSION).jar
SRC_JAR_DIR = target
STARTER_SCRIPT_LINUX = sudokuvalidator.sh
STARTER_SCRIPT_WINDOWS = sudokuvalidator.bat
STARTER_SCRIPTS = $(STARTER_SCRIPT_LINUX) $(STARTER_SCRIPT_WINDOWS)

.PHONY: clean package default

default: package

clean:
	mvn clean
	rm -f $(PACKAGE_NAME).zip

package: $(PACKAGE_NAME).zip

$(PACKAGE_NAME).zip: $(SRC_JAR_DIR)/$(SRC_JAR)
	$(eval TMPDIR := $(shell mktemp -d))
	cp $(SRC_JAR_DIR)/$(SRC_JAR) $(TMPDIR)/
	cp $(STARTER_SCRIPTS) $(TMPDIR)/
	sed 's/^SET JAR_DIR=.*$$/SET JAR_DIR=./g' -i $(TMPDIR)/$(STARTER_SCRIPT_WINDOWS)
	unix2dos $(TMPDIR)/$(STARTER_SCRIPT_WINDOWS)
	sed 's/^JAR_DIR=.*$$/JAR_DIR=./g' -i $(TMPDIR)/$(STARTER_SCRIPT_LINUX)
	cd $(TMPDIR) && ls -la
	cd $(TMPDIR) && zip $(PACKAGE_NAME) *
	cp $(TMPDIR)/$(PACKAGE_NAME).zip ./
	rm -rf $(TMPDIR)

$(SRC_JAR_DIR)/$(SRC_JAR):
	mvn package