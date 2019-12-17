from python.test_utils import test_solution, assert_equals, get_ints


def recurse_possible(water_width, land_width, blocks, depth, current_height):
    print("water {} land {} depth {} current height {} blocks {}"
          .format(water_width, land_width, depth, current_height, blocks))

    if water_width > 0:

        try_height = current_height + depth
        if blocks[try_height] > 0:
            blocks[try_height] -= 1
            if recurse_possible(water_width - 1, land_width, blocks, depth, current_height):
                return True
            blocks[try_height] += 1

        try_height = current_height + depth + 1
        if blocks[try_height] > 0:
            blocks[try_height] -= 1
            if recurse_possible(water_width - 1, land_width, blocks, depth, current_height + 1):
                return True
            blocks[try_height] += 1

    elif land_width > 0:

        if current_height == 0:
            return True

        try_height = current_height
        if blocks[try_height] > 0:
            blocks[try_height] -= 1
            if recurse_possible(0, land_width - 1, blocks, 0, current_height):
                return True
            blocks[try_height] += 1

        try_height = current_height + 1
        if blocks[try_height] > 0:
            blocks[try_height] -= 1
            if recurse_possible(0, land_width - 1, blocks, 0, current_height + 1):
                return True
            blocks[try_height] += 1

    else:
        return True


def is_it_even_possible(water_width, land_width, block_height, depth):
    blocks = [0] * 202
    for h in block_height:
        blocks[h] += 1
    if recurse_possible(water_width, land_width, blocks, depth, 0):
        return "POSSIBLE"
    return "IMPOSSIBLE"


def run_line(match):
    water_width = int(match.group(1))
    land_width = int(match.group(2))
    block_height = get_ints(match.group(3))
    depth = int(match.group(4))
    expected = match.group(5)
    actual = is_it_even_possible(water_width, land_width, block_height, depth)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}, (\\d+)\\s+"(.*)"', run_line)
