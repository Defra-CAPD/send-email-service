#/bin/bash

function outputBuildComment() {
 echo "************************************"
 echo ""
 echo " " $1
 echo ""
 echo "************************************"
}

function run_assembly() {
	outputBuildComment "Building assembly"
	./sbt assembly

	outputBuildComment "Running assembly"
	java -jar uk.gov.defra.capd.mail.service/target/uk.gov.defra.capd.mail.service-assembly-0.4-SNAPSHOT.jar server configuration/local/sendEmailServiceConfiguration.yml
}

function run_dev() {
	outputBuildComment "Running dev"
	./sbt "project sendmail-service" "run server configuration/local/sendEmailServiceConfiguration.yml"
}

REPO_DIR="$( cd "$( dirname "${BASH_SOURCE:-$0}" )" && pwd )"
cd $REPO_DIR


if [ "$1" = "-a" ]; then
	run_assembly
else
	run_dev
fi