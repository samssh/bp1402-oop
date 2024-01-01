tester:
	rm -r tester.zip 2> /dev/null || true
	zip -r tester.zip test tester_config.json .qsampletest .qtest valid_files
solution:
	rm -r solution.zip 2> /dev/null || true
	zip -r solution.zip src
initial:
	cp -r init/sample sample
	sed -i'.bak' -e 's/package sample;//g' sample/*.java
	rm -rf sample/*.java.bak
	rm -r init.zip 2> /dev/null || true
	zip -r init.zip sample
	rm -rf sample
