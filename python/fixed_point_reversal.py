from python.test_utils import test_solution, assert_equals, get_ints


def section_sorter(a):
    sorted_a = a.copy()
    sorted_a.sort()
    left = 0
    reversals = []
    while not a == sorted_a:
        while a[left] == sorted_a[left]:
            left += 1
        min_value = min(a[left:])
        min_index = left + a[left:].index(min_value)
        a[left:min_index + 1] = reversed(a[left:min_index + 1])
        reversals.extend([left, min_index + 1])

    return reversals


def sorter(a, fixed):
    a_sorted = a.copy()
    a_sorted.sort()
    if a[fixed] != a_sorted[fixed]:
        return [-1]
    if len(a) <= 2:
        return []
    if fixed == len(a) - 1:
        reversals = section_sorter(a[:len(a) - 2])
    elif fixed == 0:
        reversals = section_sorter(a[1:])
    else:
        left = section_sorter(a[:fixed])
        right = section_sorter(a[fixed + 1:])
        reversals = []
        reversals.extend(left)
        reversals.extend([x + fixed + 1 for x in right])

    if len(reversals) > 150:
        return [-1]

    print(len(reversals), reversals)
    return reversals


def run_line(match):
    a = get_ints(match.group(1))
    fixed = int(match.group(2))
    expected = get_ints(match.group(3))
    actual = sorter(a, fixed)
    if len(actual) == 1 or len(expected) == 1:
        assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+{(.*)}', run_line)
