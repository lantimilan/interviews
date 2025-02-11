// 1438
// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        // two pointer
        // [left, right] is subarray with diff <= limit but [left, right+1] has diff > limit
        // then move left to left +1, and move right to next end point
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int maxSize = 0;
        int left = 0, right = 0; // interval [left, right)
        for (int n : nums) {
            ++right;
            if (map.containsKey(n)) {
                map.put(n, map.get(n) + 1);
            } else {
                map.put(n, 1);
                while (map.lastKey() - map.firstKey() > limit) {
                    int val = nums[left]; ++left;
                    if (map.get(val) > 1) map.put(val, map.get(val) - 1);
                    else map.remove(val);
                }
            }
            maxSize = Math.max(maxSize, right - left);
        }
        return maxSize;
    }
}
