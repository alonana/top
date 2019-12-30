from python.test_utils import test_solution


def place_stickers(n):
    mid = (n + 1) // 2
    res = []
    for i in range(n):
        if i < mid:
            res.append(i)
        else:
            last = res[-1]
            res.append(last + mid)
    print(n * n / 4)
    print(res)


def run_line(match):
    n = int(match.group(1))
    place_stickers(n)


test_solution(__file__, '(\\d+)\\s+{.*}', run_line)
