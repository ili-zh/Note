/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ashulin.algorithms;

import com.github.ashulin.algorithms.doc.Complexity;
import com.github.ashulin.algorithms.doc.Source;
import com.github.ashulin.algorithms.doc.Tag;
import com.github.ashulin.algorithms.doc.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Tag(Type.HASH_MAP)
public class HashMaps {

    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     *
     * <p>注意：若s 和 t中每个字符出现的次数都相同，则称s 和 t互为字母异位词。 s 和 t 仅包含小写字母
     */
    @Source(242)
    @Tag(Type.STRING)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] hash = new int[26];
        for (char c : s.toCharArray()) {
            hash[c - 'a']--;
        }

        for (char c : t.toCharArray()) {
            if (hash[c - 'a']++ >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     *
     * <p>如果可以，返回 true ；否则返回 false 。
     *
     * <p>magazine 中的每个字符只能在 ransomNote 中使用一次。 ransomNote 和 magazine 由小写英文字母组成
     */
    @Source(383)
    @Tag(Type.STRING)
    public boolean canConstruct(String t, String s) {
        if (s.length() < t.length()) {
            return false;
        }
        int[] hash = new int[26];
        for (char c : s.toCharArray()) {
            hash[c - 'a']--;
        }

        for (char c : t.toCharArray()) {
            if (hash[c - 'a']++ >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     *
     * <p>字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
     *
     * <p>strs[i] 仅包含小写字母
     *
     * <p>strs = ["eat", "tea", "tan", "ate", "nat", "bat"] 输出:
     * [["bat"],["nat","tan"],["ate","eat","tea"]]
     */
    @Source(49)
    @Tag(Type.STRING)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> hash = new java.util.HashMap<>(strs.length);
        for (String str : strs) {
            int[] counter = new int[26];
            for (char c : str.toCharArray()) {
                counter[c - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] > 0) {
                    builder.append((char) (i + 'a'));
                    builder.append(counter[i]);
                }
            }
            List<String> array =
                    hash.computeIfAbsent(builder.toString(), (key) -> new ArrayList<>());
            array.add(str);
        }
        return new ArrayList<>(hash.values());
    }

    /**
     * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     *
     * <p>异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。 s 和 p 仅包含小写字母
     */
    @Source(438)
    @Complexity(time = "O(n+m)", space = "O(1)")
    @Tag(Type.STRING)
    @Tag(Type.TWO_POINTERS)
    @Tag(Type.SLIDING_WINDOW)
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) {
            return result;
        }
        int[] hash = new int[26];
        for (char c : p.toCharArray()) {
            hash[c - 'a']++;
        }
        int counter = 0;
        char[] chars = s.toCharArray();
        for (int fast = 0, slow = 0; fast < s.length(); ) {
            if (hash[chars[fast++] - 'a']-- > 0) {
                counter++;
            }
            if (counter == p.length()) {
                result.add(slow);
            }
            if (fast - slow == p.length() && hash[chars[slow++] - 'a']++ >= 0) {
                counter--;
            }
        }
        return result;
    }

    @Source(349)
    @Complexity(time = "O(m+n)", space = "O(m)")
    @Tag(Type.ARRAY)
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> hash = new HashSet<>();
        List<Integer> result = new ArrayList<>(Math.min(nums1.length, nums2.length));
        for (int i : nums1) {
            hash.add(i);
        }
        for (int i : nums2) {
            if (hash.remove(i)) {
                result.add(i);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 给你两个整数数组nums1 和 nums2
     * ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     */
    @Source(350)
    @Complexity(time = "O(m+n)", space = "O(min(n, m))")
    @Tag(Type.ARRAY)
    public int[] intersect(int[] nums1, int[] nums2) {
        int[] less = nums1;
        int[] more = nums2;
        if (less.length > more.length) {
            less = nums2;
            more = nums1;
        }
        Map<Integer, Integer> hash = new java.util.HashMap<>(less.length);
        for (int i : less) {
            hash.compute(i, (key, value) -> value == null ? 1 : ++value);
        }
        List<Integer> result = new ArrayList<>(less.length);
        for (int i : more) {
            hash.compute(
                    i,
                    (key, value) -> {
                        if (value == null) {
                            return null;
                        }
                        if (value > 0) {
                            result.add(key);
                        }
                        return --value;
                    });
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 编写一个算法来判断一个数 n 是不是快乐数。
     *
     * <p>「快乐数」定义为：
     *
     * <p>对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。 如果 可以变为
     * 1，那么这个数就是快乐数。 如果 n 是快乐数就返回 true ；不是，则返回 false 。
     */
    @Source(202)
    @Complexity(time = "O(logn)", space = "O(logn)")
    public boolean isHappy(int n) {
        Set<Integer> hash = new HashSet<>();
        int cur = n;
        while (!hash.contains(cur)) {
            hash.add(cur);
            int sum = getNext(cur);
            if (sum == 1) {
                return true;
            }
            cur = sum;
        }
        return false;
    }

    public int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d * d;
        }
        return sum;
    }

    /**
     * 9 -> 81.
     *
     * <p>99 -> 162.
     *
     * <p>999 -> 243.
     *
     * <p>9,999 -> 324.
     *
     * <p>9,999,999,999,999 -> 1,053.
     *
     * <p>收敛在243及以内，由于一定会形成环形链表，快乐数时快慢指针一定在 数1 相遇；
     */
    @Source(202)
    @Complexity(time = "O(n)", space = "O(1)")
    @Tag(Type.TWO_POINTERS)
    @Tag(Type.SIN)
    @Tag(Type.MATH)
    public boolean isHappy2(int n) {
        int slow = n;
        int fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(fast);
            fast = getNext(fast);
        } while (slow != fast);

        return fast == 1;
    }

    /**
     * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
     *
     * <p>你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     *
     * <p>你可以按任意顺序返回答案。
     */
    @Source(1)
    @Complexity(time = "O(n)", space = "O(n)")
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hash = new java.util.HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int r = target - nums[i];
            Integer index = hash.get(r);
            if (index != null) {
                return new int[] {index, i};
            }
            hash.put(nums[i], i);
        }
        return new int[] {};
    }

    /**
     * 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
     *
     * <p>为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -2^28 到 2^28 - 1 之间，最终结果不会超过
     * 2^31 - 1 。
     */
    @Source(454)
    @Complexity(time = "O(n^2)", space = "O(n^2)")
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> hash = new java.util.HashMap<>(nums1.length);
        for (int i : nums1) {
            for (int j : nums2) {
                hash.compute(i + j, (k, v) -> v == null ? 1 : ++v);
            }
        }

        int result = 0;
        for (int i : nums3) {
            for (int j : nums4) {
                result += hash.getOrDefault(-i - j, 0);
            }
        }
        return result;
    }

    /**
     * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
     * 答案中不可以包含重复的三元组。
     */
    @Source(15)
    @Tag(Type.SLIDING_WINDOW)
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 2) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int l = 0; l < nums.length - 2 && nums[l] <= 0; l++) {
            // 去重
            if (l > 0 && nums[l] == nums[l - 1]) {
                continue;
            }
            int m = l + 1;
            int r = nums.length - 1;
            while (m < r) {
                int sum = nums[l] + nums[m] + nums[r];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[l], nums[m], nums[r]));
                    // 去重
                    while (m < r && nums[m] == nums[m + 1]) {
                        m++;
                    }
                    while (m < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    m++;
                    r--;
                } else if (sum < 0) {
                    m++;
                } else {
                    r--;
                }
            }
        }
        return result;
    }

    /**
     * 给你一个由 n 个整数组成的数组nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组[nums[a], nums[b], nums[c],
     * nums[d]]（若两个四元组元素一一对应，则认为两个四元组重复）：
     *
     * <p>0 <= a, b, c, d < n a、b、c 和 d 互不相同 nums[a] + nums[b] + nums[c] + nums[d] == target 你可以按
     * 任意顺序 返回答案 。
     */
    @Source(18)
    @Tag(Type.SLIDING_WINDOW)
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int l = j + 1;
                int r = nums.length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    } else if (sum > target) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }
        }
        return result;
    }
}
