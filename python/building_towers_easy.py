from python.test_utils import test_solution, assert_equals, get_ints


def reduce_buildings(buildings, building, top):
    building -= 1
    # print('update building {} to top {}'.format(building, top))
    if buildings[building] < top:
        return

    buildings[building] = top

    last_height = top
    for i in range(building + 1, len(buildings)):
        if buildings[i] <= last_height + 1:
            break
        buildings[i] = last_height + 1
        last_height += 1

    last_height = top
    for i in range(building - 1, 0, -1):
        if buildings[i] <= last_height + 1:
            break
        buildings[i] = last_height + 1
        last_height += 1


def max_height(N, x, t):
    buildings = [0] * N
    for i in range(N):
        buildings[i] = i

    for i in range(len(x)):
        reduce_buildings(buildings, x[i], t[i])
        # print(buildings)
    return max(buildings)


def run_line(match):
    N = int(match.group(1))
    x = get_ints(match.group(2))
    t = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = max_height(N, x, t)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), {(.*)}, {(.*)}\\s+(\\d+)', run_line)
