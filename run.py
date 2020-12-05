import os
import json
from datetime import datetime
import time

alg_config_1 = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'WS_NC_MULT', 'B_WS_NC_MULT']
alg_config_2 = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'WS_NC_MULT_LA', 'B_WS_NC_MULT_LA']
alg_config_3 = ['CHASELEV', 'WS_NC_MULT', 'B_WS_NC_MULT',
'WS_NC_MULT_LA', 'B_WS_NC_MULT_LA']

configs = [alg_config_1, alg_config_2, alg_config_3]
graphs = ['TORUS_2D', 'TORUS_2D_60', 'TORUS_3D', 'TORUS_3D_40', 'RANDOM']
sizes = [1000000, 500000, 250000, 100000, 32768, 4096, 256]
directeds = [True, False]
allTime = [True, False]
zeroCost = ['putSteals', 'putTakes', 'putsTakesSteals']
ws = [(1,1), (3,3), (8,8)]

script = """. analyzer/.venv/bin/activate &&
    java -jar cc.jar &&
    python analyzer/main.py -st experiment-1.json"""

scriptpt = """. analyzer/.venv/bin/activate &&
java -jar cc.jar &&
python analyzer/main.py -t putsTakes.json
"""
scriptps = """. analyzer/.venv/bin/activate &&
java -jar cc.jar &&
python analyzer/main.py -s putsSteals.json
"""

scriptpts = """. analyzer/.venv/bin/activate &&
java -jar cc.jar &&
python analyzer/main.py -ts putsTakesSteals.json
"""

for config in configs:
    for cost in zeroCost:
        for size in sizes:
            if cost == 'putTakes':
                with open('config.json', 'w') as outfile:
                    j = {'algorithms': config,
                         cost: {'operations': 1000000,
                                'size': size,
                                'iters': 20}}
                    print(j)
                    json.dump(j, outfile, indent=2)
                os.system(scriptpt)
            elif cost == 'putSteals':
                with open('config.json', 'w') as outfile:
                    j = {'algorithms': config,
                         cost: {'operations': 1000000,
                                'size': size,
                                'iters': 20}}
                    print(j)
                    json.dump(j, outfile, indent=2)
                os.system(scriptps)
            # else:
            #     for (w, s) in ws:
            #         with open('config.json', 'w') as outfile:
            #             j = {'algorithms': config,
            #                  cost: {'operations': 1000000,
            #                         'size': size,
            #                         'workers': w,
            #                         'stealers': s}}
            #             print(j)
            #             json.dump(j, outfile, indent=2)
            #         os.system(scriptpt)
            print('Ending')
    for graph in graphs:
        if graph in ('TORUS_2D', 'TORUS_2D_60'):
            for size in sizes:
                for directed in directeds:
                    for t in allTime:
                        with open('config.json', 'w') as outfile:
                            j = {'algorithms': config,
                                 'spanningTreeOptions' : {'graphType': graph,
                                                          'vertexSize': 1000,
                                                          'stepSpanningType': 'COUNTER',
                                                          'iterations': 20,
                                                          'directed': directed,
                                                          'stealTime': False,
                                                          'structSize': size,
                                                          'allTime': t}}
                            print(j)
                            json.dump(j, outfile, indent=2)
                        os.system(script)
                        print('Ending')
        elif graph in ('TORUS_3D', 'TORUS_3D_40'):
            for size in sizes:
                for directed in directeds:
                    for t in allTime:
                        with open('config.json', 'w')  as outfile:
                            j = {'algorithms': config,
                                 'spanningTreeOptions': {'graphType': graph,
                                                         'vertexSize': 100,
                                                         'stepSpanningType': 'COUNTER',
                                                         'iterations': 20,
                                                         'directed': directed,
                                                         'stealTime': False,
                                                         'structSize': size,
                                                         'allTime': t}}
                            print(j)
                            json.dump(j, outfile, indent=2)
                        os.system(script)
                        print('Ending')
        else:
            for size in sizes:
                for directed in directeds:
                    for t in allTime:
                        with open('config.json', 'w') as outfile:
                            j = {'algorithms': config,
                                 'spanningTreeOptions': {'graphType': 'RANDOM',
                                                         'vertexSize': 1000000,
                                                         'stepSpanningType': 'COUNTER',
                                                         'iterations': 20,
                                                         'directed': directed,
                                                         'stealTime': False,
                                                         'structSize': size,
                                                         'allTime': t}}
                            print(j)
                            json.dump(j, outfile, indent=2)
                        os.system(script)
                        print('Ending')
# datetime object containing current date and time
cwd = os.getcwd()
now = datetime.now()
dt_string = now.strftime("%d-%m-%Y-%H:%M:%S")
path = os.path.join(cwd, dt_string)
os.mkdir(path)
pspeedup = os.path.join(path, 'speedup')
os.mkdir(pspeedup)
timeup = os.path.join(path, 'time')
os.mkdir(timeup)
statistics = os.path.join(path, 'statistics')
os.mkdir(statistics)
putsTakes = os.path.join(path, 'putsTakes')
os.mkdir(putsTakes)
putsSteals = os.path.join(path, 'putsSteals')
os.mkdir(putsSteals)
putsTakesSteals = os.path.join(path, 'putsTakesSteals')
os.mkdir(putsTakesSteals)
takesDir = os.path.join(path, 'takes')
os.mkdir(takesDir)
putsDir = os.path.join(path, 'puts')
os.mkdir(putsDir)
stealsDir = os.path.join(path, 'steals')
os.mkdir(stealsDir)
os.system('mv takes-*.png {}'.format(takesDir))
os.system('mv puts-*.png {}'.format(putsDir))
os.system('mv steals-*.png {}'.format(stealsDir))
os.system('mv speedup*.png {}'.format(pspeedup))
os.system('mv time*.png {}'.format(timeup))
os.system('mv statistics*.png {}'.format(statistics))
os.system('mv putsTakesSteals*.png {}'.format(putsTakesSteals))
os.system('mv putsSteals*.png {}'.format(putsSteals))
os.system('mv putsTakes*.png {}'.format(putsTakes))
os.system('mv st-* {}'.format(path))
