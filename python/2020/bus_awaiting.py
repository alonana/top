from python.test_utils import test_solution, assert_equals, get_strings


def get_bus_time(bus, arrival_time):
    data = bus.split()
    start = int(data[0])
    interval = int(data[1])
    count = int(data[2])
    end = count * interval + start
    for time in range(start, end, interval):
        if time >= arrival_time:
            return time
    return -1


def waiting_time(buses, arrival_time):
    time = -1
    for bus in buses:
        bus_time = get_bus_time(bus, arrival_time)
        if bus_time != -1:
            if time == -1 or bus_time < time:
                time = bus_time

    if time == -1:
        return -1

    return time - arrival_time


def run_line(match):
    buses = get_strings(match.group(1))
    arrival_time = int(match.group(2))
    expected = int(match.group(3))
    actual = waiting_time(buses, arrival_time)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+(\\S+)', run_line)
