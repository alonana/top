from python.test_utils import test_solution, assert_equals

counts = bytearray(bin(x).count("1") for x in range(1024))


def get_digits(n):
    digits = 0
    while n > 0:
        d = n % 10
        digits |= 1 << d
        n = n // 10
    return digits


def compare(a, b):
    bits = counts[a & b]
    # print("counting {} {} = {}".format(a, b, bits))
    return bits


def maxsim(l, r):
    located = [0] * 1024
    for i in range(l, r + 1):
        d = get_digits(i)
        # print("digits for {} are {}".format(i, d))
        located[d] += 1

    max_located = 0
    for i in range(len(located)):
        if located[i] > 0:
            for j in range(i, len(located)):
                if located[j] > 0:
                    if i != j or located[i] > 1:
                        max_located = max(max_located, compare(i, j))
    return max_located


def run_line(match):
    l = int(match.group(1))
    r = int(match.group(2))
    expected = int(match.group(3))
    actual = maxsim(l, r)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(\\d+)', run_line)
