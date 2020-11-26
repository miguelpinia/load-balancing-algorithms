import os
import json
from datetime import datetime
import time

alg_config_1 = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'WS_NC_MULT', 'B_WS_NC_MULT']
alg_config_2 = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'WS_NC_MULT_LA', 'B_WS_NC_MULT_LA']
alg_config_3 = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'NEW_B_WS_NC_MULT', 'NEW_B_WS_NC_MULT_LA']
alg_config_4 = ['CHASELEV', 'WS_NC_MULT', 'B_WS_NC_MULT',
'NEW_B_WS_NC_MULT', 'NEW_B_WS_NC_MULT_LA']
alg_config_5 = ['CHASELEV', 'WS_NC_MULT_LA', 'B_WS_NC_MULT_LA',
'NEW_B_WS_NC_MULT', 'NEW_B_WS_NC_MULT_LA']
alg_config_6 = ['CHASELEV', 'WS_NC_MULT', 'B_WS_NC_MULT',
'WS_NC_MULT_LA', 'B_WS_NC_MULT_LA']

configs = [alg_config_1, alg_config_2, alg_config_3, alg_config_4, alg_config_5, alg_config_6]
graphs = ['TORUS_2D', 'TORUS_2D_60', 'TORUS_3D', 'TORUS_3D_40', 'RANDOM']
sizes = [1000000, 500000, 250000, 100000, 32768, 4096, 256]
directeds = [True, False]

script = """. analyzer/.venv/bin/activate &&
    java -jar cc.jar &&
    python analyzer/main.py -st experiment-1.json"""

for config in configs:
    for graph in graphs:
        if graph in ('TORUS_2D', 'TORUS_2D_60'):
            for size in sizes:
                for directed in directeds:
                    with open('config.json', 'w') as outfile:
                        j = {'algorithms': config,
                             'spanningTreeOptions' : {'graphType': graph,
                                                      'vertexSize': 1000,
                                                      'stepSpanningType': 'COUNTER',
                                                      'iterations': 5,
                                                      'directed': directed,
                                                      'stealTime': False,
                                                      'structSize': size}}
                        print(j)
                        json.dump(j, outfile, indent=2)
                    os.system(script)
                    print('Ending')
        elif graph in ('TORUS_3D', 'TORUS_3D_40'):
            for size in sizes:
                for directed in directeds:
                    with open('config.json', 'w')  as outfile:
                        j = {'algorithms': config,
                             'spanningTreeOptions': {'graphType': graph,
                                                     'vertexSize': 100,
                                                     'stepSpanningType': 'COUNTER',
                                                     'iterations': 5,
                                                     'directed': directed,
                                                     'stealTime': False,
                                                     'structSize': size}}
                        print(j)
                        json.dump(j, outfile, indent=2)
                    os.system(script)
                    print('Ending')
        else:
            for size in sizes:
                for directed in directeds:
                    with open('config.json', 'w') as outfile:
                        j = {'algorithms': config,
                             'spanningTreeOptions': {'graphType': 'RANDOM',
                                                     'vertexSize': 1000000,
                                                     'stepSpanningType': 'COUNTER',
                                                     'iterations': 5,
                                                     'directed': directed,
                                                     'stealTime': False,
                                                     'structSize': size}}
                        print(j)
                        json.dump(j, outfile, indent=2)
                    os.system(script)
                    print('Ending')
# datetime object containing current date and time
cwd = os.getcwd()
now = datetime.now()
dt_string = now.strftime("%d-%m-%Y-%H:%M:%S")
path = os.path.join(cwd,dt_string)
os.mkdir(path)
pspeedup = os.path.join(path, 'speedup')
os.mkdir(pspeedup)
timeup = os.path.join(path, 'time')
os.mkdir(timeup)
statistics = os.path.join(path, 'statistics')
os.mkdir(statistics)
os.system('mv speedup*.png {}'.format(pspeedup))
os.system('mv time*.png {}'.format(timeup))
os.system('mv statistics*.png {}'.format(statistics))
