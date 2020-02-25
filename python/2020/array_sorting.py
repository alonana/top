from python.test_utils import test_solution, assert_equals, get_ints


def array_sort(a):
    a.append(2001)

    n = len(a)
    score = [-1] * n
    prev = [-1] * n
    for i in range(n):
        for j in range(i + 1, n):
            if a[j] >= a[i]:
                new_score = score[i] + 1
                if new_score >= score[j]:
                    score[j] = new_score
                    prev[j] = i

    chain = set()
    last = n - 1
    while last != -1:
        last = prev[last]
        chain.add(last)

    sorted_a = []
    lowest = 1
    for i in range(0, n - 1):
        if i in chain:
            lowest = a[i]
        sorted_a.append(lowest)
    return sorted_a


def run_line(match):
    a = get_ints(match.group(1))
    expected = get_ints(match.group(2))
    actual = array_sort(a)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+{(.*)}', run_line)
