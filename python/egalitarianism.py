from python.test_utils import test_solution, assert_equals, get_strings


def bfs_from(start, is_friend):
    visited = [-1] * len(is_friend)
    to_visit = {start: 0}
    while len(to_visit) > 0:
        node = next(iter(to_visit))
        cost = to_visit[node]
        del to_visit[node]
        visited[node] = cost
        friends = is_friend[node]
        new_cost = cost + 1
        for i in range(len(friends)):
            if friends[i] == 'Y':
                if visited[i] == -1:
                    if i in to_visit:
                        to_visit[i] = min(to_visit[i], new_cost)
                    else:
                        to_visit[i] = new_cost
                else:
                    visited[i] = min(visited[i], new_cost)

    if -1 in visited:
        return -1
    return max(visited)


def max_difference(is_friend, d):
    highest = 0
    for start in range(len(is_friend)):
        value = bfs_from(start, is_friend)
        if value == -1:
            return -1
        highest = max(highest, value)
    return d * highest


def run_line(match):
    is_friend = get_strings(match.group(1))
    d = int(match.group(2))
    expected = int(match.group(3))
    actual = max_difference(is_friend, d)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+(\-?\\d+)', run_line)
