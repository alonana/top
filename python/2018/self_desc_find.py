from python.test_utils import test_solution, assert_equals, get_ints


def valid(counts):
    totals = [0] * 10
    for c, d in counts:
        totals[c] += 1
        totals[d] += 1

    couples = []
    for i, t in enumerate(totals):
        if t != 0:
            couples.append((t, i))

    for c, d in counts:
        if (c, d) in couples:
            couples.remove((c, d))
        else:
            return False
    return len(couples) == 0


def get_next_digit(c, digits):
    for i, d in enumerate(digits):
        if c == d:
            if i + 1 < len(digits):
                return digits[i + 1]
    raise Exception("error")


def increase(counts, digits):
    for i, (c, d) in enumerate(counts):

        if d == 0:
            continue

        if c != digits[-1]:
            counts[i] = (get_next_digit(c, digits), d)
            return True

        if len(digits) == 1:
            return False

        counts[i] = (digits[0], d)
    return False


def build_number(counts):
    couples = ["{}{}".format(c, d) for (c, d) in counts]
    couples.sort()
    return "".join(couples)


def heuristic_stop(counts):
    for c, d in counts:
        if d == 9 and c > 1:
            return True
        if d == 8 and c > 1:
            return True
        if d == 7 and c > 2:
            return True
    return False


def construct(digits):
    if len(digits) == 1 and digits[0] == 0:
        return ""
    first_count = digits[0]
    if first_count == 0:
        first_count = digits[1]

    valids = []
    counts = [(first_count, d) for d in digits]
    while True:
        # print(counts)
        if valid(counts):
            valids.append(build_number(counts))
        if not increase(counts, digits):
            break
        if heuristic_stop(counts):
            break

    if len(valids) == 0:
        return ""
    return min(valids)


def run_line(match):
    digits = get_ints(match.group(1))
    expected = match.group(2)
    actual = construct(digits)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+"(.*)"', run_line)
