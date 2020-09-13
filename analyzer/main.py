from ast import literal_eval
from datetime import datetime
from distutils.util import strtobool
from pprint import pprint


import re
import sys
import json

import matplotlib
import matplotlib.pyplot as plt
import numpy as np


def read_json(path_file):
    """Read the json from the `path_file`"""
    with open(path_file) as f:
        data = json.load(f)
        return data


def stats(results):
    procs = results['processors']
    execs = results['executions']
    iters = results['iterations']
    algs = results['algorithms']
    vals = {alg: {} for alg in algs}
    width = 0.2
    for proc in range(0, procs):
        thread = execs['thread-{}'.format(proc)]
        for alg in algs:
            data_alg = thread[alg]
            data_execs = data_alg['data']
            exec_time = [data_execs[str(i)]['executionTime']
                         for i in range(iters)]
            takes = [data_execs[str(i)]['takes'] for i in range(iters)]
            puts = [data_execs[str(i)]['puts'] for i in range(iters)]
            steals = [data_execs[str(i)]['steals'] for i in range(iters)]
            vals[alg][proc] = {
                'median': {'time': np.median(exec_time),
                           'takes': np.median(takes),
                           'puts': np.median(puts),
                           'steals': np.median(steals)},
                'average': {'time': np.average(exec_time),
                            'takes': np.average(takes),
                            'puts': np.average(puts),
                            'steals': np.average(steals)},
                'range': {'time': np.ptp(exec_time),
                          'takes': np.ptp(takes),
                          'puts': np.ptp(puts),
                          'steals': np.ptp(steals)},
                'std': {'time': np.std(exec_time),
                        'takes': np.std(takes),
                        'puts': np.std(puts),
                        'steals': np.std(steals)}}
    return vals


def get_data(result_type, stat_type, vals, procs):
    return [d[result_type]
            for d in [d1[stat_type]
                      for d1 in [vals[i] for i in range(0, procs)]]]


def generate_graph_stats(results, stat_type, alg_filter=None):
    if alg_filter is not None:
        algs = [alg for alg in results['algorithms'] if alg in alg_filter]
    else:
        algs = results['algorithms']
    procs = results['processors']
    st = stats(results)
    data = {'takes': {}, 'puts': {}, 'steals': {}, 'time': {}}
    for alg in algs:
        vals = st[alg]
        data['takes'][alg] = get_data('takes', stat_type, vals, procs)
        data['puts'][alg] = get_data('puts', stat_type, vals, procs)
        data['steals'][alg] = get_data('steals', stat_type, vals, procs)
        data['time'][alg] = get_data('time', stat_type, vals, procs)
    fig, axes = plt.subplots(2, 2)
    fig.suptitle(
        'Graph: {}, Statistics type: {}'.format(
            results['graphType'],
            stat_type))
    x = np.arange(1, procs + 1)
    actions = ['takes', 'puts', 'steals', 'time']
    for i, ax in enumerate(axes.flat):
        for alg in algs:
            ax.plot(x, data[actions[i]][alg], '-o', label=alg)
            if i == 3:
                ax.set_ylabel('Nanoseconds')
        ax.set_title('{} {}'.format(actions[i], stat_type).title())
        ax.grid()
        ax.legend()
    plt.gcf().set_size_inches(19.2, 10.8)
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    plt.savefig(
        'statistics-{}-{}-{}.png'.format(results['graphType'],
                                         stat_type, current_time),
        dpi=200)
    plt.close('all')


if __name__ == '__main__':
    filename = sys.argv[1]
    results = read_json(filename)
    alg_filter = None
    if len(sys.argv) > 2:
        alg_filter = ['B_WS_NC_MULT', 'WS_NC_MULT', 'IDEMPOTENT_FIFO', 'CILK']
    generate_graph_stats(results, 'average', alg_filter)
    generate_graph_stats(results, 'median', alg_filter)
    generate_graph_stats(results, 'range', alg_filter)
    generate_graph_stats(results, 'std', alg_filter)
