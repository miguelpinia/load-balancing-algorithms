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
import glob
import itertools
from pprint import pprint


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

def getmaximum(results, mmax):
    algs = results['algorithms']
    procs = results['processors']
    data = {'takes': {}, 'puts': {}, 'steals': {}, 'time': {}, 'speedup': {}}
    chaselev = get_data('time', 'average', stats(results)['CHASELEV'], procs)[0]
    for alg in algs:
        vals = stats(results)[alg]
        data['speedup'][alg] = chaselev / get_data('time', 'average', vals,
                                        procs)
        maximum = np.around(np.amax(data['speedup'][alg]), decimals=2)
        mmax[alg].append(maximum)
    return mmax

def maximus(path):
    """promedio de los máximos de cuqlauier gráfica para cada algoritmo"""
    algs = ['CHASELEV', 'IDEMPOTENT_FIFO', 'IDEMPOTENT_LIFO',
'WS_NC_MULT_LA_OPT', 'B_WS_NC_MULT_LA_OPT']
    l = glob.glob(path+'/st_*.json')
    mmax = {alg: [] for alg in algs}
    for f in l:
        print(f)
        results = read_json(f)
        mmax = getmaximum(results, mmax)
        print(mmax)
    for k in mmax:
        print(k, np.average(mmax[k]))
    return mmax

def getmax(results):
    algs = results['algorithms']

def compare(f):
    results = read_json(f)
    algs = results['algorithms']
    mmax = {alg: [] for alg in algs}
    mmax = getmaximum(results, mmax)
    nmmax =[]
    print(f)
    for k in mmax:
        a = np.around(mmax[k][0], decimals=2)
        b = np.around(mmax['WS_NC_MULT_LA_OPT'][0], decimals=2)
        # print(a, b)
        result = 100 - (( a/b ) * 100)
        # print(k, np.around(result, decimals=2))
    # print('\n===================\n')
    # print(f, mmax)
    return mmax

def comparegraphs(path):
    l = glob.glob(path+'/st_*.json')
    for f in l:
        compare(f)


def generate_graph_stats(results, stat_type, alg_filter=None):
    """#  TODO: Update description (MAPA 2020-09-17)
    >>> results = read_json('./i7_results/st_random_true.json')
    >>> generate_graph_stats(results, 'average')"""
    # print(results)
    if alg_filter is not None:
        algs = [alg for alg in results['algorithms'] if alg in alg_filter]
    else:
        algs = results['algorithms']
    procs = results['processors']
    data = {'takes': {}, 'puts': {}, 'steals': {}, 'time': {}, 'speedup': {}}
    chaselev = get_data('time', stat_type, stats(results)['CHASELEV'], procs)[0]
    all_time = results['allTime']
    mmax = {alg: [] for alg in algs}
    for alg in algs:
        vals = stats(results)[alg]
        data['takes'][alg] = get_data('takes', stat_type, vals, procs)
        data['puts'][alg] = get_data('puts', stat_type, vals, procs)
        data['steals'][alg] = get_data('steals', stat_type, vals, procs)
        data['time'][alg] = get_data('time', stat_type, vals, procs)
        data['speedup'][alg] = chaselev / get_data('time', stat_type, vals,
                                        procs)
        maximum = np.around(np.amax(data['speedup'][alg]), decimals=2)
        minimum = np.around(np.amin(data['speedup'][alg]), decimals=2)
        print('{:<16}\t\t ${}x \sim {}x$'.format(alg, minimum, maximum))
        print('{:<16}:\t\t {}'.format(alg, np.average(maximum)))
        mmax[alg].append(maximum)
    pprint(mmax)
    # print(algs, '\n')
    # pprint.pprint(data['speedup'])
    print('Generating plots {}'.format(stat_type))
    current_time = datetime.now().strftime("%H:%M:%S")
    # plt.style.use('default') #grayscale #tableau-colorblind10
    marker = itertools.cycle(('s', '*', 'p', 'v', 'X', '^', 'D', 'o', 'P', '1'))
    plt.margins(0,0)
    if stat_type in ('average', 'median'):
        fig1, axes1 = plt.subplots()
        axes1.set_ylabel('SpeedUp')
        axes1.set_xlabel('Processors')
        fig1.suptitle('SpeedUp respect to Chase-Lev\nGraph: {} {}'.format(
            'Directed'
            if results['directed']
            else 'Undirected',
            results['graphType']))
        nrange = np.arange(1, procs + 1, 9)
        for alg in algs:
            d = data['speedup'][alg][nrange]
            print('d', d)
            if alg == 'WS_NC_MULT_LA_OPT':
                axes1.plot(nrange, d, marker=next(marker), ls='-', label='WS_WMULT', color='red')
            elif alg == 'B_WS_NC_MULT_LA_OPT':
                axes1.plot(nrange, d, marker=next(marker), ls='-', label='B_WS_WMULT', color='red')
            elif alg == 'CHASELEV':
                axes1.plot(nrange, d, marker=next(marker), ls='--', label=alg, color='#333333')
            else:
                axes1.plot(nrange, d, marker=next(marker), ls=':', label=alg, color='#333333')
        axes1.grid()
        axes1.legend()
        plt.gcf().set_size_inches(19.2, 10.8)
        # plt.gcf().set_size_inches(38.4, 21.6)
        # plt.gcf().set_size_inches(9.6, 5.4)
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

def replace_name(algorithm):
    if algorithm == 'WS_NC_MULT_LA_OPT':
        return 'WS_WMULT'
    if algorithm == 'B_WS_NC_MULT_LA_OPT':
        return 'B_WS_WMULT'
    if algorithm == 'WS_NC_MULT_OPT':
        return 'WS_WMULT_ARRAY'
    return algorithm

def g_puts_steals(path_file):
    """Generate barcharts comparing put and steal times"""
    json_data = read_json(path_file)
    iters = json_data['iters']
    algs = list(map(lambda d: d['algorithm'], json_data['results']))
    algs = list(map(replace_name, algs))
    steals = np.array(list(map(lambda d: np.average(np.sort(d['steals'])), json_data['results'])))
    puts = np.array(list(map(lambda d: np.average(np.sort(d['puts'])), json_data['results'])))
    # total = list(map(lambda d: np.average(np.sort(d['total'])), json_data['results']))
    total = puts + steals
    print('iters', iters, 'size', json_data['size'], 'operations', json_data['operations'])
    print(algs)
    print('total', total)
    print('respect to chase-lev', np.around((total[0] - total) / total[0], decimals=3))
    print('respect to idempotent fifo', np.around((total[1] - total) / total[1], decimals=3))
    print('respect to idempotent lifo', np.around((total[2] - total) / total[2], decimals=3))
    print('steals', steals)
    print('respect to chase-lev', np.around((steals[0] - steals) / steals[0], decimals=3))
    print('respect to idempotent fifo', np.around((steals[1] - steals) / steals[1], decimals=3))
    print('respect to idempotent lifo', np.around((steals[2] - steals) / steals[2], decimals=3))
    print('\n')
    ind = np.arange(len(algs))
    width = 0.25
    plt.style.use('grayscale')
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - width, puts, width, label='Puts', color='#A9A9A9')
    ax.bar(ind, steals, width, label='Steals', color='#696969')
    ax.bar(ind + width, total, width, label='Total', color='#333333')
    ax.set_ylabel('Time (ms)')
    ax.set_title('Puts and steals')
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

def g_puts_steals_norm(path_file):
    json_data = read_json(path_file)
    iters = json_data['iters']
    algs = list(map(lambda d: d['algorithm'], json_data['results']))
    algs = list(map(replace_name, algs))
    steals = list(map(lambda d: np.average(np.sort(d['steals'])[1:iters-1]), json_data['results']))
    steals = steals / steals[0]
    puts = list(map(lambda d: np.average(np.sort(d['puts'])[1:iters-1]), json_data['results']))
    puts = puts / puts[0]
    total = list(map(lambda d: np.average(np.sort(d['total'])[1:iters-1]), json_data['results']))
    total = total / total[0]
    ind = np.arange(len(algs))
    fig, axs = plt.subplots(1, 3, figsize=(9, 3))
    axs[0].bar(algs, puts, 0.25, label='Puts')
    axs[1].bar(algs, steals)
    axs[2].bar(algs, total)
    fig.suptitle('Categorical Plotting')
    fig.tight_layout()
    # plt.show()
    plt.gcf().set_size_inches(9.6, 5.4)
    plt.savefig('putsSteals-norm-{}-{}-{}-{}.png'.format(json_data['operations'],
                                                         json_data['size'],
                                                         json_data['iters'],
                                                         '_'.join(algs)),
                dpi=200)
    plt.close('all')


def g_puts_takes(path_file):
    """Generate barcharts comparing put and takes times"""
    json_data = read_json(path_file)
    iters = json_data['iters']
    algs = list(map(lambda d: d['algorithm'], json_data['results']))
    # print(algs)
    algs = list(map(replace_name, algs))
    takes = np.array(list(map(lambda d: np.average(np.sort(d['takes'])), json_data['results'])))
    puts = np.array(list(map(lambda d: np.average(np.sort(d['puts'])), json_data['results'])))
    # total = list(map(lambda d: np.average(np.sort(d['total'])), json_data['results']))
    total = puts + takes
    print('iters', iters, 'size', json_data['size'], 'operations', json_data['operations'])
    print(algs)
    print('total', total)
    print('respect to chase-lev', np.around((total[0] - total) / total[0], decimals=3))
    print('respect to idempotent fifo', np.around((total[1] - total) / total[1], decimals=3))
    print('respect to idempotent lifo', np.around((total[2] - total) / total[2], decimals=3))
    print('\n')
    ind = np.arange(len(algs))
    width = 0.25
    plt.style.use('grayscale')
    fig, ax = plt.subplots()
    ax.grid(linestyle='--', linewidth=0.2)
    ax.bar(ind - width, puts, width, label='Puts', color='#A9A9A9')
    ax.bar(ind, takes, width, label='Takes', color='#696969')
    ax.bar(ind + width, total, width, label='Total', color='#333333')
    ax.set_ylabel('Time (ms)')
    ax.set_title('Puts and takes')
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

def processps(regex):
    """processps('./cluster_results/putsSteals*.json')"""
    for f_ in glob.glob(regex):
        g_puts_steals(f_)

def processpt(regex):
    """processpt('./cluster_results/putsTakes*.json')"""
    for f_ in glob.glob(regex):
        g_puts_takes(f_)

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
        g_puts_takes(args.filept)
        # barchart_puts_takes(args.filept)
    if args.fileps:
        g_puts_steals(args.fileps)
    if args.filepts:
        barchart_puts_takes_steals(args.filepts)
    if args.filest:
        results = read_json(args.filest)
        generate_graph_stats(results, 'average')
        # generate_graph_stats(results, 'median')
        # generate_graph_stats(results, 'range')
        # generate_graph_stats(results, 'std')


if __name__ == '__main__':
    main()
