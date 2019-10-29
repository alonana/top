from python.test_utils import test_solution, assert_equals, get_ints


def find_max_costs(colors_boxes, used_colors):
    unused_colors_amount = 0
    for used in used_colors:
        if not used:
            unused_colors_amount += 1

    enforce_color_usage = len(colors_boxes[0]) <= unused_colors_amount
    max_cost_per_color = [0] * 3
    for color_index in range(len(colors_boxes)):
        if enforce_color_usage and used_colors[color_index]:
            max_cost_per_color[color_index] = {
                'cost': 0,
                'box': None
            }
            continue
        color_boxes = colors_boxes[color_index]
        highest_cost = max(color_boxes)
        max_cost_per_color[color_index] = {
            'cost': highest_cost,
            'box': color_boxes.index(highest_cost)
        }

    # print('max_cost_per_color',max_cost_per_color)
    highest_color = max(max_cost_per_color, key=lambda x: x['cost'])
    highest_color_index = max_cost_per_color.index(highest_color)

    return {
        'color': highest_color_index,
        'box': highest_color['box'],
    }


def remove_box(colors_boxes, box_index, color_to_keep):
    moved = 0
    for color_index, color_boxes in enumerate(colors_boxes):
        if color_index != color_to_keep:
            moved += color_boxes[box_index]
        del color_boxes[box_index]
    return moved


def min_operations(red, green, blue):
    if len(red) < 3:
        return -1

    moved = 0
    colors_boxes = [red, green, blue]
    # print(colors_boxes)

    used_colors = [False] * len(colors_boxes)

    for i in range(len(colors_boxes[0])):
        remove_color_info = find_max_costs(colors_boxes, used_colors)
        # print(remove_color_info)
        moved += remove_box(colors_boxes, remove_color_info['box'], remove_color_info['color'])
        used_colors[remove_color_info['color']] = True
        # print(colors_boxes)
        # print(used_colors)

    return moved


def run_line(match):
    red = get_ints(match.group(1))
    green = get_ints(match.group(2))
    blue = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = min_operations(red, green, blue)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}, {(.*)}\\s+(\\S+)', run_line)
