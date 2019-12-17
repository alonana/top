import re

from python.test_utils import test_solution, assert_equals


def replace_digit(number, digit):
    if "?" not in number:
        return int(number)
    if number[0] == '?' and digit == 0:
        return None

    return int(number.replace("?", str(digit)))


def find_digit(equation):
    pattern = re.compile('(.*)\\*(.*)=(.*)')
    match = pattern.search(equation)
    if match is None:
        raise Exception("failed parsing of ".format(equation))
    a = match.group(1)
    b = match.group(2)
    c = match.group(3)

    valid = []
    for i in range(10):
        a_num = replace_digit(a, i)
        b_num = replace_digit(b, i)
        c_num = replace_digit(c, i)

        if a_num is None or b_num is None or c_num is None:
            continue

        if a_num * b_num == c_num:
            valid.append(i)

    if len(valid) != 1:
        return -1

    return valid[0]


def run_line(match):
    equation = match.group(1)
    expected = int(match.group(2))
    actual = find_digit(equation)
    assert_equals(actual, expected)


test_solution(__file__, '"(.*)"\\s+(-?\\d+)', run_line)
