import os
import re
import sys


def get_ints(s):
    parts = s.split(sep=', ')
    if '' in parts:
        parts.remove('')
    return [int(x) for x in parts]


def min_cost(p, w):
    return 1


def test_solution():
    print('Starting')

    file_name = os.path.basename(__file__).replace(".py", ".txt")
    with open(file_name) as f:
        for line in f.readlines():
            if len(line.strip()) > 0:
                print("check line {}".format(line))
                pattern = re.compile('.*{(.*)}, {(.*)}\\s+(\\S+).*')
                match = pattern.match(line)
                p = get_ints(match.group(1))
                w = get_ints(match.group(2))
                expected = float(match.group(3))
                actual = min_cost(p, w)
                if actual != expected:
                    sys.stdout.flush()
                    sys.stderr.flush()
                    raise Exception("Expected: {}, but actual is {}".format(expected, actual))

    print('Done')


test_solution()
