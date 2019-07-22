import re

from python.test_utils import test_solution, assert_equals


def parse(n):
    pattern = re.compile('(\\d+)\\.(\\d*)\\((.*)\\)')
    match = pattern.search(n)
    if match is None:
        raise Exception('failed parsing ' + n)

    return {
        'x': match.group(1),
        'y': match.group(2),
        'z': match.group(3),
    }


def find_common_y_length(a, b):
    a_y_len = len(a['y'])
    if a['y'] == 0:
        a_y_len = 0

    b_y_len = len(b['y'])
    if b['y'] == 0:
        b_y_len = 0

    while a_y_len != b_y_len:
        if a_y_len < b_y_len:
            a_y_len += len(a['z'])
        else:
            b_y_len += len(b['z'])

    return a_y_len


def find_common_z_length(a, b):
    a_z_len = len(a['z'])
    if a['z'] == 0:
        a_z_len = 0

    b_z_len = len(b['z'])
    if b['z'] == 0:
        b_z_len = 0

    while a_z_len != b_z_len:
        if a_z_len < b_z_len:
            a_z_len += len(a['z'])
        else:
            b_z_len += len(b['z'])

    return a_z_len


def extend_y_by_z(n, y_common):
    while len(n['y']) < y_common:
        n['y'] = n['y'] + n['z']


def extend_z_by_z(n, z_common):
    origin_z = n['z']
    if origin_z == 0:
        return 0
    while len(n['z']) < z_common:
        n['z'] = n['z'] + origin_z


def reduce_multiple(n):
    for key_len in range(1, len(str(n))):
        times = len(str(n)) // key_len
        if times == len(str(n)) / key_len:
            key = str(n)[:key_len]
            if key * times == str(n):
                return key

    return n


def truncate_zeros(n):
    if n['z'] != 0:
        return
    while n['y'][-1] == '0':
        n['y'] = n['y'][:-1]


def get_y_safe(n):
    if n['y'] == '':
        return 0
    return int(n['y'])


def pattern_reduce(n):
    pattern = n['y'] + n['z']
    pattern_longer = n['y'] + n['z'] * 5
    print('long pattern ' + pattern_longer)
    for y_len in range(len(pattern)):
        for z_len in range(1, len(pattern)):
            y = pattern[:y_len]
            z = pattern[y_len:y_len + z_len]
            check = y + z
            while len(check) + z_len < len(pattern_longer):
                check += z
            print('pattern ' + check)
            if pattern_longer.startswith(check):
                n['y'] = y
                n['z'] = z
                return


def add(a, b):
    a = parse(a)
    b = parse(b)
    print(periodic_string(a), periodic_string(b))

    y_common = find_common_y_length(a, b)
    extend_y_by_z(a, y_common)
    extend_y_by_z(b, y_common)
    print(periodic_string(a), periodic_string(b))

    z_common = find_common_z_length(a, b)
    extend_z_by_z(a, z_common)
    extend_z_by_z(b, z_common)
    print(periodic_string(a), periodic_string(b))

    multiplier = 1
    c = {
        'x': str(int(a['x']) + int(b['x'])),
        'y': str(get_y_safe(a) + get_y_safe(b)),
        'z': str(int(a['z'] * multiplier) + int(b['z'] * multiplier)),
    }
    print("after a+b: " + periodic_string(c))

    if re.compile('^9+$').match(c['z']):
        c['z'] = '0'
        c['y'] = str(int(c['y']) + 1)

    print("after z nines to y: " + periodic_string(c))

    if re.compile('^9+$').match(c['y']):
        c['y'] = '0'
        c['x'] = str(int(c['x']) + 1)

    print("after y nines to x: " + periodic_string(c))

    if len(c['z']) > multiplier * len(a['z']):
        z = c['z']
        c['z'] = str(int(z[1:]) + 1)
        c['y'] = str(int(c['y']) + int(z[:1]))

    print("after z overflow to y: " + periodic_string(c))

    if len(c['y']) > len(a['y']):
        y = c['y']
        c['y'] = y[1:]
        c['x'] = str(int(c['x']) + int(y[:1]))

    print("after y overflow to x: " + periodic_string(c))

    c['z'] = reduce_multiple(c['z'])
    print("after reduce multiple z: " + periodic_string(c))

    truncate_zeros(c)
    print("after zeros truncate: " + periodic_string(c))

    pattern_reduce(c)
    print("after pattern reduce: " + periodic_string(c))

    return periodic_string(c)


def periodic_string(c):
    y = c['y']
    if y == 0:
        y = ''

    return '{}.{}({})'.format(c['x'], y, c['z'])


def run_line(match):
    a = match.group(1)
    b = match.group(2)
    expected = match.group(3)
    actual = add(a, b)
    assert_equals(actual, expected)


test_solution(__file__, '"(.*)", "(.*)"\\s+"(.*)"', run_line)
