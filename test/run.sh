#!/bin/sh
cd $(dirname $0)

cd ../complete/spring-samples-datamodel

mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

cd ../complete/cloud-streams-processor
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

cd ../complete/cloud-streams-sink
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target


cd ../complete/cloud-streams-source
mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target


exit
