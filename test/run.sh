#!/bin/sh
cd $(dirname $0)

pkill vault
rm -f target/vault*
mkdir -p target
cd target

VAULT_VER=0.8.3
UNAME=$(uname -s |  tr '[:upper:]' '[:lower:]')
VAULT_ZIP="vault_${VAULT_VER}_${UNAME}_amd64.zip"

wget "https://releases.hashicorp.com/vault/${VAULT_VER}/${VAULT_ZIP}"
unzip ${VAULT_ZIP}

./vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000" &
sleep 1

export export VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
export VAULT_ADDR="http://127.0.0.1:8200"

./vault write secret/github github.oauth2.key=foobar
cd ..

cd ../complete

mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf build

cd ../initial

mvn clean compile
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf target

./gradlew compileJava
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi
rm -rf build

pkill vault
exit
