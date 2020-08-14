venv=.venv
venvbin=.venv/bin

if [ -d ${venv} ]; then
        rm -rf ${venv}
fi

virtualenv -p python3.8 ${venv} &&
    source ${venvbin}/activate &&
    pip install -r requirements.txt # &&
    # echo $(which python) &&
    # nohup python main.py >> log.txt 2>&1 &
