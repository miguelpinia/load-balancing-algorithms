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
    print(results)
    if alg_filter is not None:
        algs = [alg for alg in results['algorithms'] if alg in alg_filter]
    else:
        algs = results['algorithms']
    procs = results['processors']
    data = {'takes': {}, 'puts': {}, 'steals': {}, 'time': {}, 'speedup': {}}
    chaselev = get_data('time', stat_type, stats(results)['CHASELEV'], procs)[0]
    all_time = results['allTime']
    for alg in algs:
        vals = stats(results)[alg]
        data['takes'][alg] = get_data('takes', stat_type, vals, procs)
        data['puts'][alg] = get_data('puts', stat_type, vals, procs)
        data['steals'][alg] = get_data('steals', stat_type, vals, procs)
        data['time'][alg] = get_data('time', stat_type, vals, procs)
        data['speedup'][alg] = chaselev / get_data('time', stat_type, vals,
                                        procs)
    print('Generating plots {}'.format(stat_type))
    current_time = datetime.now().strftime("%H:%M:%S")
    if stat_type in ('average', 'median'):
        fig1, axes1 = plt.subplots()
        fig1.suptitle('SpeedUp: {}, {}, {}. Init time? {}'.format(
            results['graphType'],
            stat_type,
            'Directed'
            if results['directed']
            else 'Undirected',
            'yes' if all_time else 'no'))
        for alg in algs:
            axes1.plot(np.arange(1, procs + 1), data['speedup'][alg], '-o', label=alg)
        axes1.grid()
        axes1.legend()
        plt.gcf().set_size_inches(9.6, 5.4)
        plt.savefig('speedup-{}-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                              'directed'
                                                              if results['directed']
                                                              else 'undirected',
                                                              results['vertexSize'],
                                                              results['structSize'],
                                                              stat_type,
                                                              'allTime' if all_time
                                                              else 'noTime',
                                                              current_time),
                    dpi=200)
        f, a = plt.subplots()
        f.suptitle('Graph: {} {}, {}. Init time? {}'.format(
            results['graphType'],
            stat_type,
            'Directed'
            if results['directed']
            else 'Undirected',
            'yes' if all_time else 'no'))
        for alg in algs:
            a.plot(np.arange(1, procs + 1), data['time'][alg], '-o', label=alg)
        a.set_ylabel('Nanoseconds')
        a.set_title('{} {}'.format('time', stat_type).title())
        a.grid()
        a.legend()
        plt.gcf().set_size_inches(9.6, 5.4)
        plt.savefig('time-{}-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                           'directed'
                                                           if results['directed']
                                                           else 'undirected',
                                                           stat_type,
                                                           results['vertexSize'],
                                                           results['structSize'],
                                                           'allTime'
                                                           if all_time
                                                           else 'noTime',
                                                           current_time),
                    dpi=200)
    f1, a1 = plt.subplots()
    f1.suptitle('Graph: {}, Takes. Init time? {}'.format(results['graphType'],
                                                         'yes'
                                                         if all_time
                                                         else 'no'))
    for alg in algs:
        a1.plot(np.arange(1, procs + 1),
                data['takes'][alg], '-o', label=alg)
    a1.set_title('{} {}'.format('Takes', stat_type))
    a1.grid()
    a1.legend()
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('takes-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                     'Directed'
                                                     if results['directed']
                                                     else 'Undirected',
                                                     stat_type,
                                                     results['vertexSize'],
                                                     results['structSize'],
                                                     current_time),
                dpi=200)
    f2, a2 = plt.subplots()
    f2.suptitle('Graph: {}, Puts'.format(results['graphType']))
    for alg in algs:
        a2.plot(np.arange(1, procs + 1),
                data['puts'][alg], '-o', label=alg)
    a2.set_title('{} {}'.format('Puts', stat_type))
    a2.grid()
    a2.legend()
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('puts-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                    'Directed'
                                                    if results['directed']
                                                    else 'Undirected',
                                                    stat_type,
                                                    results['vertexSize'],
                                                    results['structSize'],
                                                    current_time),
                dpi=200)
    f3, a3 = plt.subplots()
    f3.suptitle('Graph: {}, Steals'.format(results['graphType']))
    for alg in algs:
        a3.plot(np.arange(1, procs + 1),
                data['steals'][alg], '-o', label=alg)
    a3.set_title('{} {}'.format('Steals', stat_type))
    a3.grid()
    a3.legend()
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('steals-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                      'Directed'
                                                      if results['directed']
                                                      else 'Undirected',
                                                      stat_type,
                                                      results['vertexSize'],
                                                      results['structSize'],
                                                      current_time),
                dpi=200)

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
    plt.savefig(
        'statistics-{}-{}-{}-{}-{}-{}.png'.format(results['graphType'],
                                                  'directed'
                                                  if results['directed']
                                                  else 'undirected',
                                                  stat_type,
                                                  results['vertexSize'],
                                                  results['structSize'],
                                                  current_time),
        dpi=200)
    plt.close('all')


def barchart_puts_steals(path_file):
    """Generate barcharts comparing put and steal times"""
    json_data = read_json(path_file)
    iters = json_data['iters']
    algs = list(map(lambda d: d['algorithm'], json_data['results']))
    steals = list(map(lambda d: np.average(np.sort(d['steals'])[1:iters-1]), json_data['results']))
    puts = list(map(lambda d: np.average(np.sort(d['puts'])[1:iters-1]), json_data['results']))
    total = list(map(lambda d: np.average(np.sort(d['total'])[1:iters-1]), json_data['results']))
    ind = np.arange(len(algs))
    width = 0.25
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - width, puts, width, label='Puts')
    ax.bar(ind, steals, width, label='Steals')
    ax.bar(ind + width, total, width, label='Total')
    ax.set_ylabel('Time')
    ax.set_title('Time done by puts and steals')
    ax.set_xticks(ind)
    ax.set_xticklabels(algs)
    ax.legend()
    fig.tight_layout()
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('putsSteals-{}-{}-{}-{}.png'.format(json_data['operations'],
                                                    json_data['size'],
                                                    json_data['iters'],
                                                    '_'.join(algs)),
                dpi=200)
    plt.close('all')


def barchart_puts_takes(path_file):
    """Generate barcharts comparing put and takes times"""
    json_data = read_json(path_file)
    iters = json_data['iters']
    algs = list(map(lambda d: d['algorithm'], json_data['results']))
    takes = list(map(lambda d: np.average(np.sort(d['takes'])[1:iters-1]), json_data['results']))
    puts = list(map(lambda d: np.average(np.sort(d['puts'])[1:iters-1]), json_data['results']))
    total = list(map(lambda d: np.average(np.sort(d['total'])[1:iters-1]), json_data['results']))
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
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('putsTakes-{}-{}-{}-{}.png'.format(json_data['operations'],
                                                     json_data['size'],
                                                     json_data['iters'],
                                                     '_'.join(algs)),
                dpi=200)
    plt.close('all')


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
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('putsTakesSteals-{}-{}-{}-{}-{}.png'.format(json_data['operations'],
                                                            json_data['size'],
                                                            json_data['workers'],
                                                            json_data['stealers'],
                                                            '_'.join(algs)),
                dpi=200)
    plt.close('all')


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
