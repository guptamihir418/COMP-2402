#!/usr/bin/python3
import sys
import hashlib
import random

def big_string(i):
    m = hashlib.sha256()
    m.update(str(i).encode('utf8'))
    return m.hexdigest()


""" Usage: bigfile.py [<n>] [<u>]
    Prints n lines, at most u of which are unique."""
if __name__ == "__main__":
    n = 10000
    if len(sys.argv) > 1:
        n = int(sys.argv[1])
    u = n
    if len(sys.argv) > 2:
        u = int(sys.argv[2])

    if u >= n:
        for i in range(n):
            print(big_string(i))
    else:
        strings = [big_string(i) for i in range(u)]
        for i in range(n):
            print(random.choice(strings))
