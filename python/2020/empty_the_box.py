from python.test_utils import test_solution, assert_equals

probabilities = {}


def get_next(dice, tokens):
    token_location = dice - 1
    if token_location < len(tokens) and tokens[token_location]:
        cloned = tokens.copy()
        cloned[token_location] = False
        return cloned

    return None


def sum_tokens(tokens):
    summed = 0
    for i in range(len(tokens)):
        if tokens[i]:
            summed += i + 1
    print("sum of {} is {}".format(tokens, summed))
    return summed


def recurse(tokens):
    estimate = 0
    for dice, probability in probabilities.items():
        print("tokens {} dice is {}".format(tokens, dice))
        next = get_next(dice, tokens)
        if next is None:
            print("game over")
            estimate += probability * sum_tokens(tokens)
        else:
            estimate += probability * recurse(next)

    return estimate


def min_expected_penalty(d, t):
    global probabilities
    options = 0
    probabilities = {}
    for d1 in range(d):
        for d2 in range(d):
            options += 1
            summed = d1 + d2 + 2
            if summed in probabilities:
                probabilities[summed] += 1
            else:
                probabilities[summed] = 1

    for k, v in probabilities.items():
        probabilities[k] = v / options

    print(probabilities)

    tokens = [True] * t

    return recurse(tokens)


def run_line(match):
    d = int(match.group(1))
    t = int(match.group(2))
    expected = float(match.group(3))
    actual = min_expected_penalty(d, t)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(\\S+)', run_line)
