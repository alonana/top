import time

from python.test_utils import test_solution, assert_equals

last_debug = time.time()


def debug(m):
    global last_debug
    now = time.time()
    if now - last_debug > -1:
        last_debug = now
        print(m)


def arithmetic_sum(a1, d, n):
    return (n / 2) * (2 * a1 + (n - 1) * d)


def fight_time_only_attack(hp, attack, level):
    if attack == hp:
        return 1

    times_min = 1
    times_max = 1000000000000

    first = attack
    add = attack * level / 100

    while times_max > times_min + 1:
        mid = (times_min + times_max) // 2
        sum = arithmetic_sum(first, add, mid)
        debug("between {} {}, mid {} sum={}".format(times_min, times_max, mid, sum))
        if sum == hp:
            return mid
        if sum > hp:
            times_max = mid
        else:
            times_min = mid

    return times_max


def fight_time_with_spell(hp, attack, level, duration):
    if attack == hp:
        return 1

    times_min = 2
    times_max = 1000000000000

    first = attack
    add = attack * level / 100

    while times_max > times_min + 1:
        debug("between {} {}".format(times_min, times_max))
        mid = (times_min + times_max) // 2
        attack_sum = arithmetic_sum(first, add, mid - 1)
        spell_sum = min(mid - 1, duration) * 4 * attack
        sum = attack_sum + spell_sum
        debug("mid {} attack {} spell {}".format(mid, attack_sum, spell_sum))
        if sum == hp:
            return mid
        if sum > hp:
            times_max = mid
        else:
            times_min = mid

    return times_max


def fight_time(hp, attack, level, duration):
    only_attack_times = fight_time_only_attack(hp, attack, level)
    spell_cast_times = fight_time_with_spell(hp, attack, level, duration)
    debug("attack only {} spell {}".format(only_attack_times, spell_cast_times))
    return min(only_attack_times, spell_cast_times)


def run_line(match):
    hp = int(match.group(1))
    attack = int(match.group(2))
    level = int(match.group(3))
    duration = int(match.group(4))
    expected = int(match.group(5))
    actual = fight_time(hp, attack, level, duration)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\d+)', run_line)
