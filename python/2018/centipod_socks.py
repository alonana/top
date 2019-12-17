from python.test_utils import test_solution, assert_equals, get_ints


def fill_available(F, available_per_color, bucket_socks):
    used_socks = 0
    for color_index, color_available in enumerate(available_per_color):
        add = F - color_available - 1
        if add > 0:
            bucket_color_socks = bucket_socks[color_index]
            update = min(add, bucket_color_socks)
            available_per_color[color_index] += update
            bucket_socks[color_index] -= update
            used_socks += update
    return used_socks


def fewest_socks(C, F, bucket_socks):
    used_socks = 0
    available_per_color = [0] * len(bucket_socks)
    for c in range(C):

        used_socks += fill_available(F, available_per_color, bucket_socks)

        max_index = find_max_index(bucket_socks)
        if bucket_socks[max_index] == 0:
            return -1

        available_per_color[max_index] = 0
        bucket_socks[max_index] -= 1
        used_socks += 1

    return used_socks


def find_max_index(bucket_socks):
    max_index = None
    for color_index, bucket_color_socks in enumerate(bucket_socks):
        if max_index is None:
            max_index = color_index
        elif bucket_color_socks > bucket_socks[max_index]:
            max_index = color_index
    return max_index


def run_line(match):
    C = int(match.group(1))
    F = int(match.group(2))
    sock_count = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = fewest_socks(C, F, sock_count)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}\\s+(-?\\d+)', run_line)
