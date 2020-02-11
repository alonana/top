from python.test_utils import test_solution, assert_equals


def find_distance(search, indexes, default_value):
    if search in indexes:
        locations = indexes[search]
        if len(locations) == 2:
            return locations[1] - locations[0]
    return default_value


def add_location(distance, indexes, position):
    if distance in indexes:
        indexes[distance].append(position)
        indexes[distance] = indexes[distance][-2:]
    else:
        indexes[distance] = [position]


def find_value(default_value, query):
    if query == 0:
        return 0
    indexes = {}
    last = 0
    position = 0
    while query not in indexes:
        position += 1
        distance = find_distance(last, indexes, default_value)
        last = distance
        add_location(distance, indexes, position)
        if position > 10000000:
            return -1

    return indexes[query][0]


def run_line(match):
    default_value = int(match.group(1))
    query = int(match.group(2))
    expected = int(match.group(3))
    actual = find_value(default_value, query)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(-?\\d+)', run_line)
