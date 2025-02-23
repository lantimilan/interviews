// https://leetcode.com/problems/finding-pairs-with-a-certain-sum
// Note the constraint:
// num1 has at most 1000 elements while num2 has 10^5 elements
// so iterate over num1 is faster than iterate on num2
class FindSumPairs {
    private List<Integer> num1List = new ArrayList<>();
    private HashMap<Integer, Integer> num2Map = new HashMap<>();
    private List<Integer> num2List = new ArrayList<>();

    public FindSumPairs(int[] nums1, int[] nums2) {
        for (int k = 0; k < nums1.length; ++k) {
            num1List.add(nums1[k]);
        }
        for (int k = 0; k < nums2.length; ++k) {
            int val = nums2[k];
            num2Map.put(val, num2Map.getOrDefault(val, 0) + 1);
            num2List.add(val);
        }
    }
    
    public void add(int index, int val) {
        int oldVal = num2List.get(index);
        int newVal = oldVal + val;
        num2List.set(index, newVal);
        int occ = num2Map.get(oldVal);
        if (occ > 1) num2Map.put(oldVal, occ-1);
        else num2Map.remove(oldVal);
        num2Map.put(newVal, num2Map.getOrDefault(newVal, 0) + 1);
    }
    
    public int count(int tot) {
        int res = 0;
        for (int n1 : num1List) {
            int n2 = tot - n1;
            int occ = num2Map.getOrDefault(n2, 0);
            res += occ;
        }
        return res;
    }
}

/**
 * Your FindSumPairs object will be instantiated and called as such:
 * FindSumPairs obj = new FindSumPairs(nums1, nums2);
 * obj.add(index,val);
 * int param_2 = obj.count(tot);
 */
