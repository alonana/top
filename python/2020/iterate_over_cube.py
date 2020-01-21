from python.test_utils import test_solution, assert_equals, get_ints


def increment(position, located_sum):
    non_zero = 0
    for i in range(2, -1, -1):
        if position[i] != 0:
            non_zero = i
            break


def find_cell(n, index):
    total_cubes = 0
    located_sum = 0
    prev_total = 0
    for s in range(3 * n):
        a = (s + 1) * (s + 2) // 2
        prev_total = total_cubes
        total_cubes += a
        if total_cubes > index:
            located_sum = s
            break

    offset = index - prev_total
    print("located sum", located_sum, "offset", offset)

    position = [0, 0, located_sum]
    for i in range(offset):
        increment(position, located_sum)
    return located_sum


def run_line(match):
    n = int(match.group(1))
    index = int(match.group(2))
    expected = get_ints(match.group(3))
    actual = find_cell(n, index)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+{(.*)}', run_line)
