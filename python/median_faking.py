from python.test_utils import test_solution, assert_equals, get_ints


def minimize(f, m, data, goal):
    i = 0
    friends = []
    for friend in range(f):
        measures = []
        for measure in range(m):
            measures.append(data[i])
            i += 1
        measures.sort()
        friends.append(measures)

    data.sort()
    print(data)

    mid_index = (len(data) - 1) // 2
    if data[mid_index] == goal:
        return [0, 0]

    persons = 0
    required_changes = 0

    if data[mid_index] > goal:
        while mid_index >= 0 and data[mid_index] > goal:
            required_changes += 1
            mid_index -= 1

        available_changes = []
        for measures in friends:
            available = 0
            for m in measures:
                if m >= goal:
                    available += 1
            available_changes.append(available)
    else:
        while mid_index < len(data) and data[mid_index] < goal:
            required_changes += 1
            mid_index += 1

        available_changes = []
        for measures in friends:
            available = 0
            for m in measures:
                if m <= goal:
                    available += 1
            available_changes.append(available)

    includes_goal = [goal in measures for measures in friends]
    changes = required_changes
    while changes > 0:
        print(available_changes)
        print(includes_goal)
        persons += 1
        max_available = max(available_changes)
        max_available_index = available_changes.index(max_available)
        if includes_goal[max_available_index]:
            available_changes[max_available_index] = 1
            changes -= (max_available - 1)
        else:
            available_changes[max_available_index] = 0
            changes -= max_available

    return [persons, required_changes]


def run_line(match):
    f = int(match.group(1))
    m = int(match.group(2))
    data = get_ints(match.group(3))
    goal = int(match.group(4))
    expected_persons = int(match.group(5))
    expected_changes = int(match.group(6))
    expected = [expected_persons, expected_changes]
    actual = minimize(f, m, data, goal)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}, (\\d+)\\s+{(\\d+), (\\d+)}', run_line)
