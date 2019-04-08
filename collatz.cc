// collatz.cc
// https://en.wikipedia.org/wiki/Collatz_conjecture
//
// start with n:
// if n is odd, then n = 3*n + 1
// else n = n / 2
// after finite steps, n is down to 1
//
// Given an integer n, output the max steps of transformation number in [1,n]
// into 1.
//
//    less than 10 is 9, which has 19 steps,
//    less than 100 is 97, which has 118 steps,
//    less than 1,000 is 871, which has 178 steps,
//    less than 10,000 is 6,171, which has 261 steps,
//    less than 100,000 is 77,031, which has 350 steps,
//    less than 1 million is 837,799, which has 524 steps,
//    less than 10 million is 8,400,511, which has 685 steps,
//    less than 100 million is 63,728,127, which has 949 steps,
//    less than 1 billion is 670,617,279, which has 986 steps,
#include <cassert>
#include <iostream>
#include <map>
#include <set>
#include <stack>
using namespace std;

typedef long long IntType;
map<IntType, IntType> steps;  // same map applies to all n

IntType simple(IntType k) {
    int s = 0;
    while (k != 1) {
        ++s;
        // cout << k << endl;
        if (k &1) k = k * 3 + 1;
        else k /= 2;
    }
    return s;
}

// Returns (maxelem, maxstep)
pair<IntType, IntType>
calc(IntType n) {
    IntType maxelem = 1;
    IntType maxstep = 0;
    steps[1] = 0;
    for (int i = 2; i <= n; ++i) if (!steps.count(i)) {
        // cout << i << endl;
        set<IntType> seen;
        stack<IntType> st;
        int k = i;
        while (!steps.count(k)) {
            seen.insert(k);
            st.push(k);
            if (k &1) k = k * 3 + 1;
            else k /= 2;
        }
        assert(steps.count(k));
        int s = steps[k];
        while (!st.empty()) {
            int k = st.top();
            steps[k] = ++s;  // cout << k << " " << s << endl;
            st.pop();
        }
        if (s > maxstep) {
            maxstep = s;
            maxelem = i;
        }
    }
    return make_pair(maxelem, maxstep);
}

int main() {
    // this naive impl is actually fast, 0.620s
    // because it is a tight for loop with only simple arithmetic operations
//    int mx = 0, melem = 1;
//    for (int n = 1; n <= 1000000; ++n) {
//        int c = simple(n);
//        if (c > mx) {
//            mx = c; melem = n;
//        }
//        // cout << n << " " << simple(n) << endl;
//    }
//    cout << melem << " " << mx << endl;

    int n = 100000;
    pair<IntType, IntType> p = calc(n);
    cout << "map_size=" << steps.size() << endl;
    cout << "maxelem=" << p.first << " " << "maxstep=" << p.second << endl;
}
