from python.test_utils import test_solution, assert_equals, get_strings


def find_graph(g):
    result = []
    for i, s in enumerate(g):
        s = s.replace('0', '2')
        s = s.replace('1', '0')
        s = s.replace('2', '1')
        s = s[:i] + '0' + s[i + 1:]
        result.append(s)
    return result


def run_line(match):
    g = get_strings(match.group(1))
    expected = get_strings(match.group(2))
    actual = find_graph(g)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+{(.*)}', run_line)
