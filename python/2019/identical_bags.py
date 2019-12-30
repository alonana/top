from python.test_utils import test_solution, assert_equals, get_ints


def possible(candy, bag_size, bags):
    add = list(map(lambda x: x // bags, candy))
    return sum(add) >= bag_size


def make_bags(candy, bag_size):
    low = 0
    high = pow(10, 19)
    while low + 1 < high:
        mid = (low + high) // 2
        if possible(candy, bag_size, mid):
            low = mid
        else:
            high = mid
    return low


def run_line(match):
    candy = get_ints(match.group(1))
    bag_size = int(match.group(2))
    expected = int(match.group(3))
    actual = make_bags(candy, bag_size)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+(\\d+)', run_line)
