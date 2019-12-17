from python.test_utils import test_solution, assert_equals


def actual_floor(button_pressed):
    actual = 0
    for i in range(1, button_pressed + 1):
        if '4' not in str(i):
            actual += 1
    return actual


def run_line(match):
    button_pressed = int(match.group(1))
    expected = int(match.group(2))
    actual = actual_floor(button_pressed)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+)\\s+(\\d+)', run_line)
