// https://leetcode.com/problems/find-peak-element/description
// 162. Find Peak Element
class Solution {
    public int findPeakElement(int[] nums) {
        // the constraint that nums[i] != nums[i+1] implies there must be one peak
        // because nums[0] is not a peak means nums[0] < nums[1]
        // then we have nums[1] < nums[2] < ... < nums[n-1]
        // then nums[n-1] must be peak
        // now let h = n/2 we look at nums[0], nums[h] and nums[n-1]
        // if any of nums[0]..nums[h] is a peak, then we can recurse on nums[0..h]
        // else we must have nums[0] < nums[1] < ... < nums[h]
        // similarly recurse on nums[h..n-1] is unsafe if nums[h] > ... > nums[n-1]
        // therefore, we compare nums[h] to nums[0] and nums[n-1]
        // case 1: nums[0] >= nums[h], then recurse on [0..h]
        // case 2: nums[h] <= nums[n-1], then recurse on [h..n-1]
        // case 3: we have nums[h] > nums[0] and nums[h] > nums[n-1], then we need
        // to look at nums[h] and its neighbors h-1 and h+1, if nums[h] is peak
        // then we are done, else it means nums[h-1] > nums[h] or nums[h+1] > nums[h]
        // if nums[h-1] > nums[h], then recurse on 0..h-1, else recurse on h+1..n-1
        int n = nums.length;
        int begin = 0;
        int end = n-1;
        while (end + 1 - begin >= 3) {
            int mid = (end + begin) / 2;
            assert begin < mid && mid < end;
            if (nums[begin] >= nums[mid]) end = mid;
            else if (nums[mid] <= nums[end]) begin = mid;
            else {
                if (isPeak(nums, mid)) return mid;
                else if (nums[mid-1] > nums[mid]) end = mid;
                else begin = mid;
            }
        }
        if (isPeak(nums, begin)) return begin;
        else {
            assert isPeak(nums, end);
            return end;
        }
    }

    // returns true if nums[pos] is a peak
    // that is nums[pos] > nums[pos-1] and nums[pos] > nums[pos+1]
    // out of range elements assume to be -inf
    private boolean isPeak(int[] nums, int pos) {
        final int n = nums.length;
        if (pos == 0) {
            if (pos + 1 == n) return true;
            else return nums[pos] > nums[pos+1];
        }
        if (pos == n-1) return nums[pos] > nums[pos-1];
        assert pos-1 >= 0 && pos+1 < n;
        return nums[pos] > nums[pos-1] && nums[pos] > nums[pos+1];
    }
}
