from python.test_utils import test_solution, assert_equals, get_strings


def yesno_not(v):
    if v == 'Y':
        return 'N'
    return 'Y'


def yesno_and(a, b):
    if a == 'Y' and b == 'Y':
        return 'Y'
    return 'N'


def yesno_or(a, b):
    if a == 'Y' or b == 'Y':
        return 'Y'
    return 'N'


def yesno_not_string(v):
    r = []
    for i in range(len(v)):
        r.append(yesno_not(v[i]))
    return "".join(r)


def yesno_and_string(a, b):
    r = []
    for i in range(len(a)):
        r.append(yesno_and(a[i], b[i]))
    return "".join(r)


def yesno_or_string(a, b):
    r = []
    for i in range(len(a)):
        r.append(yesno_or(a[i], b[i]))
    return "".join(r)


def yesno_count_string(v):
    total = 0
    for i in range(len(v)):
        if v[i] == 'Y':
            total += 1
    return total


class LampSolver:
    def __init__(self, solver, lamp):
        self.solver = solver
        self.lamp = lamp
        self.related = None

    def __repr__(self):
        result = "lamp {} {}".format(self.lamp, self.related)
        if self.is_finite():
            result += " final"
        return result

    def check_lamp(self):
        for experiment in range(self.solver.experiments):
            lamp_on = self.solver.experiments_lamps[experiment][self.lamp] == 'Y'
            if lamp_on:
                current_related = self.solver.experiments_switches[experiment]
                self.update_related(current_related)
            else:
                current_non_related = self.solver.experiments_switches[experiment]
                current_related = yesno_not_string(current_non_related)
                self.update_related(current_related)

    def is_finite(self):
        return yesno_count_string(self.related) == 1

    def is_invalid(self):
        return yesno_count_string(self.related) == 0

    def guess(self):
        options = []
        first = True
        alternatives = 0
        for i in range(len(self.related)):
            value = self.related[i]
            if value == 'Y':
                if first:
                    first = False
                else:
                    value = 'N'
                    alternatives += 1
            options.append(value)

        new_related = "".join(options)
        print("guess lamp {} {} to {}".format(self.lamp, self.related, new_related))
        self.related = new_related
        return alternatives

    def update_related(self, current_related):
        if self.related is None:
            self.related = current_related
            return

        self.related = yesno_and_string(self.related, current_related)

    def integrate(self, other):
        if other.is_finite():
            new_related = yesno_and_string(self.related, yesno_not_string(other.related))
            if new_related != self.related:
                self.related = new_related
                return True
        return False


class Solver:
    def __init__(self, switches, lamps):
        self.n = len(switches[0])
        self.experiments = len(switches)
        self.experiments_switches = switches
        self.experiments_lamps = lamps
        self.solvers = []
        self.guess_alternatives = -1

    def __repr__(self):
        return "status:\n" + "\n".join([str(x) for x in self.solvers])

    def solve(self):
        self.learn_from_experiments()
        return self.integrate_and_update()

    def integrate_and_update(self):
        changes = 0
        while True:
            print(self)
            if self.is_invalid():
                return -1
            if self.is_finite():
                return changes
            updated = self.integrate()
            if not updated:
                if self.guess_alternatives == 0:
                    print("reuse experiment")
                else:
                    print("new experiment")
                    changes += 1
                self.guess_alternatives = self.guess()

    def guess(self):
        for i in range(self.n):
            lamp = self.solvers[i]
            if not lamp.is_finite():
                return lamp.guess()

    def is_finite(self):
        for i in range(self.n):
            lamp = self.solvers[i]
            if not lamp.is_finite():
                return False
        return True

    def is_invalid(self):
        for i in range(self.n):
            lamp = self.solvers[i]
            if lamp.is_invalid():
                return True
        return False

    def integrate(self):
        print("integrating")
        any_updated = False
        for i in range(self.n):
            lamp = self.solvers[i]
            if not lamp.is_finite():
                for j in range(self.n):
                    if i != j:
                        updated = lamp.integrate(self.solvers[j])
                        if updated:
                            any_updated = True
                            print("update lamp {} related {}".format(lamp.lamp, lamp.related))
                            if lamp.is_finite():
                                self.guess_alternatives -= 1
        return any_updated

    def learn_from_experiments(self):
        for lamp in range(self.n):
            lamp_solver = LampSolver(self, lamp)
            lamp_solver.check_lamp()
            self.solvers.append(lamp_solver)


def the_min(switches, lamps):
    s = Solver(switches, lamps)
    return s.solve()


def run_line(match):
    switches = get_strings(match.group(1))
    lamps = get_strings(match.group(2))
    expected = int(match.group(3))
    actual = the_min(switches, lamps)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}\\s+(\\S+)', run_line)
