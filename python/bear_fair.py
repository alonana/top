from python.test_utils import test_solution, assert_equals, get_ints

FAIR = "fair"
UNFAIR = "unfair"


def is_fair(n, b, up_to, quantity):
    for i in quantity:
        if i > n:
            return UNFAIR

    up_to_total = []
    for i in range(len(up_to)):
        up_to_total.append((up_to[i], quantity[i]))

    up_to_total.sort(key=lambda x: x[0])
    print(up_to_total)

    for u, q in up_to_total:
        remain = b - u
        if q + remain < n:
            print("up to {} there are {} items, remaining {}".format(u, q, remain))
            return UNFAIR

    even = 0
    odd = 0
    wildcard = 0
    prev_q = 0
    prev_u = 0

    total_copy = up_to_total.copy()
    total_copy.append((b, n))

    for curr_u, curr_q in total_copy:
        quantity_diff = curr_q - prev_q
        upto_diff = curr_u - prev_u
        if quantity_diff < 0:
            print("quantity decrease {} from {} to {} on upto {}".format(quantity_diff, prev_q, curr_q, curr_u))
            return UNFAIR
        if quantity_diff > upto_diff:
            print("quantity jump too high from {} to {} on upto {}".format(prev_q, curr_q, curr_u))
            return UNFAIR

        # print("upto_diff {}, quantity_diff {}".format(upto_diff, quantity_diff))
        if upto_diff == quantity_diff:
            if upto_diff % 2 == 1:
                if curr_u % 2 == 0:
                    even += 1
                else:
                    odd += 1
        elif quantity_diff > 0:
            free = upto_diff - quantity_diff
            if free == 1:
                if curr_u % 2 == 0:
                    even += 1
                else:
                    odd += 1
            else:
                free = min(quantity_diff, (upto_diff - quantity_diff) // 2)
                if free > 0:
                    wildcard += free

        prev_u = curr_u
        prev_q = curr_q

    if abs(even - odd) > wildcard:
        print("unbalanced: even {} odd {} wildcard {}".format(even, odd, wildcard))
        return UNFAIR

    print("odd {}, even {}, wildcard {}".format(odd, even, wildcard))

    return FAIR


def run_line(match):
    n = int(match.group(1))
    b = int(match.group(2))
    up_to = get_ints(match.group(3))
    quantity = get_ints(match.group(4))
    expected = match.group(5)
    actual = is_fair(n, b, up_to, quantity)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}, {(.*)}\\s+\"(.*)\"', run_line)
