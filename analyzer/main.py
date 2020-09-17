#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Generate charts
Author: Miguel Angel Piña Avelino
email: miguel_pinia@ciencias.unam.mx
twitter: @miguelpinia
"""

import argparse
from datetime import datetime

import json

import matplotlib.pyplot as plt
import numpy as np


def read_json(path_file):
    """Read the json from the `path_file`"""
    with open(path_file) as f:
        data = json.load(f)
        return data


def stats(results):
    """Calculate statistics from results json"""
    procs = results['processors']
    execs = results['executions']
    iters = results['iterations']
    algs = results['algorithms']
    vals = {alg: {} for alg in algs}
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
    """Get the specified data from vals"""
    return [d[result_type]
            for d in [d1[stat_type]
                      for d1 in [vals[i] for i in range(0, procs)]]]


def generate_graph_stats(results, stat_type, alg_filter=None):
    """#  TODO: Update description (MAPA 2020-09-17)"""
    if alg_filter is not None:
        algs = [alg for alg in results['algorithms'] if alg in alg_filter]
    else:
        algs = results['algorithms']
    procs = results['processors']
    data = {'takes': {}, 'puts': {}, 'steals': {}, 'time': {}}
    for alg in algs:
        vals = stats(results)[alg]
        data['takes'][alg] = get_data('takes', stat_type, vals, procs)
        data['puts'][alg] = get_data('puts', stat_type, vals, procs)
        data['steals'][alg] = get_data('steals', stat_type, vals, procs)
        data['time'][alg] = get_data('time', stat_type, vals, procs)
    fig, axes = plt.subplots(2, 2)
    fig.suptitle(
        'Graph: {}, Statistics type: {}'.format(
            results['graphType'],
            stat_type))
    actions = ['takes', 'puts', 'steals', 'time']
    for i, ax in enumerate(axes.flat):
        for alg in algs:
            ax.plot(np.arange(1, procs + 1),
                    data[actions[i]][alg], '-o', label=alg)
            if i == 3:
                ax.set_ylabel('Nanoseconds')
        ax.set_title('{} {}'.format(actions[i], stat_type).title())
        ax.grid()
        ax.legend()
    plt.gcf().set_size_inches(19.2, 10.8)
    current_time = datetime.now().strftime("%H:%M:%S")
    plt.savefig(
        'statistics-{}-{}-{}.png'.format(results['graphType'],
                                         stat_type, current_time),
        dpi=200)
    plt.close('all')


def barchart_puts_steals(path_file):
    """Generate barcharts comparing put and steal times"""
    json_data = read_json(path_file)
    algs = list(map(lambda d: d['Alg'], json_data))
    steals = list(map(lambda d: d['steal_time'], json_data))
    puts = list(map(lambda d: d['put_time'], json_data))
    total = list(map(lambda d: d['total_time'], json_data))
    ind = np.arange(len(algs))
    width = 0.35
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - (2 * width / 3), puts, width, label='Puts')
    ax.bar(ind, steals, width, label='Steals')
    ax.bar(ind + (2 * width / 3), total, width, label='Total')
    ax.set_ylabel('Time')
    ax.set_title('Time done by puts and steals')
    ax.set_xticks(ind)
    ax.set_xticklabels(algs)
    ax.legend()
    fig.tight_layout()
    plt.show()


def barchart_puts_takes(path_file):
    """Generate barcharts comparing put and takes times"""
    json_data = read_json(path_file)
    algs = list(map(lambda d: d['Alg'], json_data))
    takes = list(map(lambda d: d['take_time'], json_data))
    puts = list(map(lambda d: d['put_time'], json_data))
    total = list(map(lambda d: d['total_time'], json_data))
    ind = np.arange(len(algs))
    width = 0.25
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - width, puts, width, label='Puts')
    ax.bar(ind, takes, width, label='Takes')
    ax.bar(ind + width, total, width, label='Total')
    ax.set_ylabel('Time')
    ax.set_title('Time done by puts and takes')
    ax.set_xticks(ind)
    ax.set_xticklabels(algs)
    ax.legend()
    fig.tight_layout()
    plt.show()


def barchart_puts_takes_steals(path_file):
    """Generate barcharts comparing put, takes and steal times"""
    json_data = read_json(path_file)
    info = json_data['results']
    algs = list(map(lambda d: d['Alg'], info))
    takes = list(map(lambda d: d['take_time'], info))
    steals = list(map(lambda d: d['steal_time'], info))
    puts = list(map(lambda d: d['put_time'], info))
    total = list(map(lambda d: d['total_time'], info))
    ind = np.arange(len(algs))
    width = 0.2
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - (3 * width / 2), puts, width, label='Puts')
    ax.bar(ind - (width / 2), takes, width, label='Takes')
    ax.bar(ind + (width / 2), steals, width, label='Steals')
    ax.bar(ind + (3 * width / 2), total, width, label='Total')
    ax.set_ylabel('Time')
    ax.set_title('Time done by puts, takes and steals')
    ax.set_xticks(ind)
    ax.set_xticklabels(algs)
    ax.legend()
    fig.tight_layout()
    plt.show()


def main():
    """#  TODO: Update description (MAPA 2020-09-17)"""
    desc = '''
    Herramienta para el estudio de algoritmos de
    work-stealing. Actualmente permite realizar los gráficos de
    comparación de los siguientes experimentos:\n
    \n
    - Spanning tree\n
    - Puts-takes\n
    - Puts-steals\n
    - Puts-takes-steals\n'''
    parser = argparse.ArgumentParser(description=desc)
    parser.add_argument(
        '-st',
        '--spanningTree',
        dest='filest',
        help='''Genera la gráfica de estadísticas del Spanning Tree'''
    )
    parser.add_argument(
        '-t',
        '--putsTakes',
        dest='filept',
        help='''Genera la gráfica de puts y takes''')
    parser.add_argument(
        '-s',
        '--putsSteals',
        dest='fileps',
        help='''Genera la gráfica de puts y steals''')
    parser.add_argument(
        '-ts',
        '--putsTakesSteals',
        dest='filepts',
        help='''Genera la gráfica de puts, takes y steals''')
    args = parser.parse_args()
    if args.filept:
        barchart_puts_takes(args.filept)
    if args.fileps:
        barchart_puts_steals(args.fileps)
    if args.filepts:
        barchart_puts_takes_steals(args.filepts)
    if args.filest:
        results = read_json(args.filest)
        generate_graph_stats(results, 'average')
        generate_graph_stats(results, 'median')
        generate_graph_stats(results, 'range')
        generate_graph_stats(results, 'std')


if __name__ == '__main__':
    main()
