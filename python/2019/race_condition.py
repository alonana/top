from python.test_utils import test_solution, assert_equals, get_ints


def count(gates_operations):
    gates = {}
    global_counter = 0
    for g in gates_operations:
        if g.islower():
            gates[g] = global_counter
        else:
            global_counter = gates[g.lower()] + 1

    return global_counter


def get_lower(index):
    return chr(ord('a') + index)


def get_upper(index):
    return chr(ord('A') + index)


def minimize_for_single_gate(gates):
    min_count = min([v if v > 0 else 1000 for v in gates])
    min_index = gates.index(min_count)

    entries = []
    for i in range(min_count - 1):
        entries.append(get_lower(min_index))
        entries.append(get_upper(min_index))

    entries.append(get_lower(min_index))

    gates[min_index] = 0
    add_all_entries(entries, gates)

    entries.append(get_upper(min_index))

    return entries


def add_all_entries(entries, gates):
    for gate_index, gate_entries in enumerate(gates):
        for i in range(gate_entries):
            entries.append(get_lower(gate_index))
            entries.append(get_upper(gate_index))


def minimize_for_multiple_gate(gates):
    g1 = None
    g2 = None
    for gate_index, gate_entries in enumerate(gates):
        if gate_entries != 0:
            g2 = g1
            g1 = gate_index

    entries = [get_lower(g1), get_lower(g2)]

    for i in range(gates[g1] - 2):
        entries.append(get_upper(g1))
        entries.append(get_lower(g1))

    entries.append(get_upper(g1))
    entries.append(get_upper(g2))
    entries.append(get_lower(g1))

    gates[g1] = 0
    gates[g2] -= 1
    add_all_entries(entries, gates)

    entries.append(get_upper(g1))
    return entries


def minimize(gates):
    none_zero = sum([0 if x == 0 else 1 for x in gates])
    if none_zero == 0:
        return ''

    ones = sum([1 if x == 1 else 0 for x in gates])

    if ones > 0 or none_zero == 1:
        entries = minimize_for_single_gate(gates)
    else:
        entries = minimize_for_multiple_gate(gates)

    return ''.join(entries)


def run_line(match):
    gates = get_ints(match.group(1))
    expected = match.group(2)
    actual = minimize(gates)
    assert_equals(count(actual), count(expected))


test_solution(__file__, '{(.*)}\\s+"(.*)"', run_line)
