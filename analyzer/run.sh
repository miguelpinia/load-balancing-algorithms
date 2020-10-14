venv=.venv
venvbin=.venv/bin

if [ -d ${venv} ]; then
        rm -rf ${venv}
fi

REQUIRED_PKG="libgirepository1.0-dev"
PKG_OK=$(dpkg-query -W --showformat='${Status}\n' $REQUIRED_PKG|grep "install ok installed")
echo Checking for $REQUIRED_PKG: $PKG_OK
if [ "" = "$PKG_OK" ]; then
  echo "No $REQUIRED_PKG. Setting up $REQUIRED_PKG."
  sudo apt-get --yes install $REQUIRED_PKG
fi

virtualenv -p python3.8 ${venv} &&
    source ${venvbin}/activate &&
    pip install -r requirements.txt # &&
    # echo $(which python) &&
    # nohup python main.py >> log.txt 2>&1 &
