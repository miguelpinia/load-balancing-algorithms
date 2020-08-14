from datetime import datetime
from pprint import pprint
import re
import sys

import matplotlib
import matplotlib.pyplot as plt
import numpy as np


def num_processors(data):
    regex = r"(Processors:) (\d+)"
    match = re.search(regex, data)
    if match is not None:
        return match.group(2)
    return None


def chunks(lst, processors):
    for i in range(0, len(lst), processors):
        yield lst[i: i + processors]


def get_info_execution(info):
    results = info[1:-1].split(',')
    results = [val.strip().split('=') for val in results]
    results = {key: value for [key, value] in results}
    results['algorithms'] = results['algorithms'].split(';')
    results['directed'] = bool(results['directed'])
    results['iterations'] = int(results['iterations'])
    results['vertexSize'] = int(results['vertexSize'])
    return results


def parse_file(path_file):
    with open(path_file, 'r') as log:
        data = log.read()
        processors = int(num_processors(data))
        info = data.split('\n')
        idx_threads = [
            info.index('Threads: {}'.format(thread))
            for thread in range(1, int(processors) + 1)]
        results = get_info_execution(info[0])
        results['processors'] = processors
        execs = {}
        for thread in range(1, processors + 1):
            dict_thread = {}
            pos = idx_threads[thread - 1]
            algs = {alg: info.index('Algorithm:\t{}'.format(alg), pos, pos + 312)
                    for alg in results['algorithms']}
            for key in algs:
                datos = list(filter(lambda x: x != '',
                                    info[algs[key] + 2:algs[key] + 26]))
                statistics = info[algs[key] + 27:algs[key] + 33:2] + \
                    info[algs[key] + 33:algs[key] + 36]
                statistics = {output[0]: output[1].strip()
                              for line in statistics if (output := line.split(':'))
                              is not None}
                datos = [datos[i:i + 4] for i in range(0, len(datos), 4)]
                datos = {idx: {output[0]: int(output[1].strip())
                               for val in line if (output := val.split(':'))
                               is not None}
                         for idx, line in enumerate(datos)}
                dict_thread[key] = {'datos': datos, 'statistics': statistics}
            execs['thread-{}'.format(thread)] = dict_thread
        results['executions'] = execs
        return results


def process_data(results):
    procs = results['processors']
    execs = results['executions']
    iters = results['iterations']
    algs = results['algorithms']
    vals = {alg: [] for alg in algs}
    width = 0.2
    for proc in range(1, procs + 1):
        thread = execs['thread-{}'.format(proc)]
        for alg in algs:
            data_alg = thread[alg]
            data_execs = data_alg['datos']
            vals[alg].append([data_execs[i] for i in range(iters)])
    return vals


def stats(results):
    procs = results['processors']
    execs = results['executions']
    iters = results['iterations']
    algs = results['algorithms']
    vals = {alg: {} for alg in algs}
    width = 0.2
    for proc in range(1, procs + 1):
        thread = execs['thread-{}'.format(proc)]
        for alg in algs:
            data_alg = thread[alg]
            data_execs = data_alg['datos']
            exec_time = [data_execs[i]['Execution time'] for i in range(iters)]
            takes = [data_execs[i]['Takes'] for i in range(iters)]
            puts = [data_execs[i]['Puts'] for i in range(iters)]
            steals = [data_execs[i]['Steals'] for i in range(iters)]
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
                      for d1 in [vals[i] for i in range(1, procs + 1)]]]


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
    # plt.gca().set_position([0, 0, 1, 1])
    plt.gcf().set_size_inches(19.2, 10.8)
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    plt.savefig(
        'statistics-{}-{}-{}.png'.format(results['graphType'],
                                         stat_type, current_time),
        dpi=200)
    plt.close('all')
    # plt.show()


if __name__ == '__main__':
    filename = sys.argv[1]
    results = parse_file(sys.argv[1])
    if len(sys.argv) > 2:
        alg_filter = ['B_WS_NC_MULT', 'WS_NC_MULT', 'IDEMPOTENT_FIFO', 'CILK']
    else:
        alg_filter = None
    generate_graph_stats(results, 'average', alg_filter)
    generate_graph_stats(results, 'median', alg_filter)
    generate_graph_stats(results, 'range', alg_filter)
    generate_graph_stats(results, 'std', alg_filter)
