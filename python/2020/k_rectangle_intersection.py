from python.test_utils import test_solution, assert_equals, get_ints


class Rect:
    def __init__(self, xl, yl, xh, yh):
        self.xl = xl
        self.yl = yl
        self.xh = xh
        self.yh = yh

    def __repr__(self):
        return "({},{},{},{})".format(self.xl, self.yl, self.xh, self.yh)

    def __eq__(self, other):
        return self.xl == other.xl and self.yl == other.yl and self.xh == other.xh and self.yh == other.yh

    def __hash__(self):
        return hash(self.__repr__())

    def size(self):
        return (self.xh - self.xl) * (self.yh - self.yl)

    def intersect(self, other):
        xh = min(self.xh, other.xh)
        xl = max(self.xl, other.xl)
        if xl >= xh:
            return None
        yh = min(self.yh, other.yh)
        yl = max(self.yl, other.yl)
        if yl >= yh:
            return None
        return Rect(xl, yl, xh, yh)


class Rectangles:
    def __init__(self):
        self.rects = {}

    def __repr__(self):
        return str(self.rects)

    def add(self, rect, included):
        # print("adding {} with included {}".format(rect, included))
        if rect in self.rects:
            existing = self.rects[rect]
            for i in included:
                if i not in existing:
                    existing.append(i)
                    # print("post add update", rect, self.rects[rect])
        else:
            self.rects[rect] = included.copy()
            # print("post add new", rect, self.rects[rect])

    def join(self):
        rects = list(self.rects.keys())
        for i in range(len(rects) - 1):
            for j in range(i + 1, len(rects)):
                intersected = rects[i].intersect(rects[j])
                # print("intersected {}&{} = {}".format(rects[i], rects[j], intersected))
                if intersected is not None:
                    self.add(intersected, self.rects[rects[i]])
                    self.add(intersected, self.rects[rects[j]])
                    print(len(self.rects))

    def cleanup(self, threshold):
        removed = []
        for rect in self.rects.keys():
            if len(self.rects[rect]) < threshold:
                removed.append(rect)
        for rect in removed:
            del self.rects[rect]

    def max_size(self):
        if len(self.rects) == 0:
            return 0
        return max(r.size() for r in self.rects)


def max_intersection(xl, yl, xh, yh, k):
    rects = Rectangles()
    for i in range(len(xl)):
        rects.add(Rect(xl[i], yl[i], xh[i], yh[i]), [i])

    for i in range(2, k + 1):
        rects.join()
        # print("iteration-join", i, rects)
        rects.cleanup(i)
        # print("iteration-cleanup", i, rects)

    return rects.max_size()


def run_line(match):
    xl = get_ints(match.group(1))
    yl = get_ints(match.group(2))
    xh = get_ints(match.group(3))
    yh = get_ints(match.group(4))
    k = int(match.group(5))
    expected = int(match.group(6))
    actual = max_intersection(xl, yl, xh, yh, k)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}, {(.*)}, {(.*)}, (\\d+)\\s+(\\d+)', run_line)
