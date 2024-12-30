// https://leetcode.com/problems/longest-mountain-in-array/description/
// 845. Longest Mountain in Array
class Solution {
    enum State {
        FLAT,
        RISE,
        FALL,
        ;
    }
    public int longestMountain(int[] a) {
        State state = State.FLAT;
        int n = a.length;
        int maxlen = 0;
        int up = 0;
        int down = 0;
        for (int i = 0; i < n-1; ++i) {
            switch (state) {
                case FLAT:
                    if (a[i] == a[i+1]) { /* stay flat */ }
                    else if (a[i] < a[i+1]) { state = State.RISE; up = 1; }
                    else { state = State.FALL; down = 1; }
                    break;
                case RISE:
                    if (a[i] == a[i+1]) { state = State.FLAT; up = down = 0; }
                    else if (a[i] < a[i+1]) { ++up; }
                    else { state = State.FALL; down = 1; }
                    break;
                case FALL:
                    if (a[i] == a[i+1]) { 
                        state = State.FLAT; 
                        if (up > 0 && down > 0) maxlen = Math.max(maxlen, up + down + 1);
                        up = down = 0; 
                    } else if (a[i] < a[i+1]) { 
                        state = State.RISE; 
                        if (up > 0 && down > 0) maxlen = Math.max(maxlen, up + down + 1);
                        up = 1; down = 0;
                    } else {
                        ++down;
                    }
            }
        }
        if (up > 0 && down > 0) maxlen = Math.max(maxlen, up + down + 1);
        return maxlen;
    }
}
