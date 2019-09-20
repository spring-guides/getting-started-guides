#!/bin/sh
cd $(dirname $0)

cd ../complete/spring-samples-datamodel

mvn clean install
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

cd ../cloud-streams-processor
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

cd ../cloud-streams-sink
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target


cd ../cloud-streams-source
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target


exit
