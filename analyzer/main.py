from pprint import pprint
import re


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


results = parse_file('../output.log')
