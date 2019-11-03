from python.test_utils import test_solution, assert_equals


def use_list(skip_index, i):
    return skip_index is None or skip_index != i


def find_max_index(lists, skip_index):
    different_len = False
    for i, l in enumerate(lists):
        if use_list(skip_index, i):
            if len(l) != len(lists[0]):
                different_len = True

    if different_len:
        lens = [len(l) if use_list(skip_index, i) else -1 for i, l in enumerate(lists)]
        max_len = max(lens)
        for i, l in enumerate(lists):
            if use_list(skip_index, i):
                if len(l) == max_len:
                    return i

    max_value = None
    max_index = None

    for i, l in enumerate(lists):
        if use_list(skip_index, i):
            if max_value is None or l[-1] > max_value:
                max_value = l[-1]
                max_index = i

    return max_index


def remove_one(lists, x, y, z):
    empty = 0
    for l in lists:
        if len(l) == 0:
            empty += 1
    if empty > 1:
        return None

    max_index1 = find_max_index(lists, None)
    max_index2 = find_max_index(lists, max_index1)

    removing = [max_index1, max_index2]
    removing.sort()
    # print(removing)

    cost = 0
    for r in removing:
        cost += lists[r][-1]
        del lists[r][-1]

    if removing[0] == 0 and removing[1] == 1:
        return x + cost
    if removing[0] == 1 and removing[1] == 2:
        return y + cost
    return z + cost


def do_delete(a, b, c, x, y, z):
    MOD = 1e9 + 7
    # print(1000000007, MOD)

    a_list = [0] * a
    a_list[0] = 33
    a_list[1] = 42
    for i in range(2, a):
        a_list[i] = (5 * a_list[i - 1] + 7 * a_list[i - 2]) % MOD + 1

    b_list = [0] * b
    b_list[0] = 13
    for i in range(1, b):
        b_list[i] = (11 * b_list[i - 1]) % MOD + 1

    c_list = [0] * c
    c_list[0] = 7
    c_list[1] = 2
    for i in range(2, c):
        c_list[i] = (5 * c_list[i - 1] + 7 * c_list[i - 2]) % MOD + 1

    lists = [0] * 3
    lists[0] = a_list
    lists[1] = b_list
    lists[2] = c_list

    # print(lists)
    for l in lists:
        l.sort()

    total_cost = 0
    while True:
        # print(lists)
        cost = remove_one(lists, x, y, z)
        if cost is None:
            break
        # print(cost)
        total_cost += cost

    result = {
        'min_sum': sum(lists[0]) + sum(lists[1]) + sum(lists[2]),
        'cost': total_cost
    }

    # print(result)
    return result


def run_line(match):
    a = int(match.group(1))
    b = int(match.group(2))
    c = int(match.group(3))
    x = int(match.group(4))
    y = int(match.group(5))
    z = int(match.group(6))
    min_sum = int(match.group(7))
    cost = int(match.group(8))
    actual = do_delete(a, b, c, x, y, z)
    assert_equals(actual['min_sum'], min_sum)
    assert_equals(actual['cost'], cost)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\s+{(\\d+), (\\d+)}', run_line)
