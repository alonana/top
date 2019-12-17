from python.test_utils import test_solution, assert_equals, get_strings


def find_all_options(flowers, sets, selected, k):
    if k == 0:
        sets.append(selected)
        return [{'flowers': flowers, 'sets': sets}]

    result = []
    for c in 'rgbRGB':
        if flowers[c] == 0:
            continue
        next_flowers = flowers.copy()
        next_sets = sets.copy()
        result.extend(find_all_options(next_flowers, next_sets, selected + c, k - 1))

    return result


def find_nexts(current, k):
    flowers = current['flowers']
    sets = current['sets']
    nexts = []

    for r in ['r', 'R']:
        for g in ['g', 'G']:
            for b in ['b', 'B']:
                selected = r + g + b
                if selected.islower() or selected.isupper():
                    continue
                if flowers[r] == 0 or flowers[g] == 0 or flowers[b] == 0:
                    continue
                next_flowers = flowers.copy()
                next_sets = sets.copy()
                next_flowers[r] -= 1
                next_flowers[g] -= 1
                next_flowers[b] -= 1
                for possible in find_all_options(next_flowers, next_sets, selected, k - 3):
                    nexts.append(possible)
    return nexts


def solve(s, k):
    flowers = {
        'r': 0,
        'g': 0,
        'b': 0,
        'R': 0,
        'G': 0,
        'B': 0,
    }
    for f in s:
        flowers[f] += 1

    max_amount = 0
    max_instance = None
    visited = {}
    to_visit = [{'flowers': flowers, 'sets': []}]
    while len(to_visit) > 0:
        current = to_visit.pop()
        print(current)
        current_flowers = current['flowers']
        signature = "{}_{}_{}_{}_{}_{}".format(
            current_flowers['r'],
            current_flowers['g'],
            current_flowers['b'],
            current_flowers['R'],
            current_flowers['G'],
            current_flowers['B'],
        )

        if signature in visited:
            print('skip visited')
            continue
        visited[signature] = True

        if max_amount < len(current['sets']):
            max_amount = len(current['sets'])
            max_instance = current
        nexts = find_nexts(current, k)
        to_visit.extend(nexts)
    return max_instance['sets']


def run_line(match):
    s = match.group(1)
    k = int(match.group(2))
    expected = get_strings(match.group(3))
    actual = solve(s, k)
    assert_equals(len(actual), len(expected))


test_solution(__file__, '"(.*)", (\\d+)\\s+{(.*)}', run_line)
