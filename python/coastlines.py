from python.test_utils import test_solution, assert_equals, get_strings


def neighbors(sea_map, row, col):
    rows = len(sea_map)
    cols = len(sea_map[0])
    result = []
    # up
    if row > 0:
        result.append((row - 1, col))

    # left
    if col > 0:
        result.append((row, col - 1))

    # down
    if row + 1 < rows:
        result.append((row + 1, col))

    # right
    if col + 1 < cols:
        result.append((row, col + 1))

    return result


def assign_island(sea_map, row, col, next_island):
    island_index = sea_map[row][col]
    if island_index != 'X':
        return next_island

    to_check = [(row, col)]
    while to_check:
        row, col = to_check.pop()
        sea_map[row][col] = next_island
        for next_row, next_col in neighbors(sea_map, row, col):
            if sea_map[next_row][next_col] == 'X':
                to_check.append((next_row, next_col))

    return next_island + 1


def update_coastlines(sea_map, row, col, coastlines):
    island_index = sea_map[row][col]
    if island_index == '.':
        return

    next_cells = neighbors(sea_map, row, col)
    count = 4 - len(next_cells)
    for next_row, next_col in next_cells:
        if sea_map[next_row][next_col] != island_index:
            count += 1

    if island_index in coastlines:
        coastlines[island_index] += count
    else:
        coastlines[island_index] = count


def longest(sea_map):
    rows = len(sea_map)
    cols = len(sea_map[0])
    for row in range(rows):
        sea_map[row] = list(sea_map[row])

    next_island = 1
    for row in range(rows):
        for col in range(cols):
            next_island = assign_island(sea_map, row, col, next_island)

    coastlines = {}
    for row in range(rows):
        for col in range(cols):
            update_coastlines(sea_map, row, col, coastlines)

    if len(coastlines) == 0:
        return 0
    return max(coastlines.values())


def run_line(match):
    sea_map = get_strings(match.group(1))
    expected = int(match.group(2))
    actual = longest(sea_map)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\d+)', run_line)
