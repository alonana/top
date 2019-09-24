import os
import re
import sys


def get_ints(s):
    parts = s.split(sep=', ')
    if '' in parts:
        parts.remove('')
    return [int(x) for x in parts]


def assert_equals(actual, expected):
    if actual != expected:
        sys.stdout.flush()
        sys.stderr.flush()
        error_message = "Expected: {}, but actual is {}".format(expected, actual)
        raise Exception(error_message)


def get_strings(data):
    parts = [s.strip()[1:-1] for s in data.split(',')]
    if '' in parts:
        parts.remove('')
    return parts


def test_solution(caller_file, regex, line_handler):
    print('Starting')

    file_name = os.path.basename(caller_file).replace(".py", ".txt")
    with open(file_name) as f:
        for line in f.readlines():
            if len(line.strip()) > 0:
                print("check line {}".format(line))
                pattern = re.compile(regex)
                match = pattern.search(line)
                if match is None:
                    raise Exception('regex match failed on line {}'.format(line))
                line_handler(match)

    print('Done')
