import java.util.*;

public class Main {
    public int FindGreatestSumOfSubArray(int[] array) {
        // HZ 偶尔会拿些专业问题来忽悠那些非计算机专业的同学。
        // 今天测试组开完会后,他又发话了:
        // 在古老的一维模式识别中, 常常需要计算连续子向量的最大和, 当向量全为正数的时候, 问题很好解决。
        // 但是, 如果向量中包含负数, 是否应该包含某个负数, 并期望旁边的正数会弥补它呢？
        // 例如: {6, -3, -2, 7, -15, 1, 2 ,2}, 连续子向量的最大和为 8(从第 0 个开始, 到第 3 个为止)。
        // 给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是 1)

        List<Integer> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                list.add(sum);
            }
        }
        int max = Integer.MIN_VALUE;
        for (int l : list) {
            if (l > max) {
                int tmp = l;
                l = max;
                max = tmp;
            }
        }
        return max;
    }

    public ArrayList<String> Permutation(String str) {
        // 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
        // 例如输入字符串 abc, 则按字典序打印出由字符 a, b, c 所能排列出来的所有字符串
        // abc, acb, bac, bca, cab 和 cba。

        // 固定第一个字符，递归取得首位后面的各种字符串组合;
        // 再把第一个字符与后面每一个字符交换，并同样递归获得首位后面的字符串组合;
        // 递归的出口，就是只剩一个字符的时候;
        // 递归的循环过程，就是从每个子串的第二个字符开始依次与第一个字符交换，然后继续处理子串。

        ArrayList<String> list = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(str.toCharArray(), 0, list);
            Collections.sort(list);
        }
        return list;
    }
    private void PermutationHelper(char[] chars, int cur, ArrayList<String> list) {
        // 回溯算法
        if (cur == chars.length - 1) {
            list.add(String.valueOf(chars));
        } else {
            Set<Character> charSet = new HashSet<>(); // 去重
            for (int i = cur; i < chars.length; i++) {
                if (!charSet.contains(chars[i])) {
                    charSet.add(chars[i]);
                    swap(chars, cur, i);
                    PermutationHelper(chars, cur + 1, list);
                    // 第二个 swap 用以使得字符数组的顺序回到进入递归前的状态
                    // 这样才不会影响外部的遍历顺序
                    swap(chars, i, cur);
                }
            }
        }
    }
    private void swap(char[] cs,int i,int j){
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}
class Solution {
    // 输入一颗二叉树的根节点和一个整数，按字典序打印出二叉树中结点值的和为输入整数的所有路径。
    // 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。

    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<ArrayList<Integer>> listArray = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if (root == null) {
            return listArray;
        }
        list.add(root.val);
        target -= root.val;
        if (target < 0) {
            // 递归到 target < 0 说明路径错误，回退到父节点继续寻找
            list.remove(list.size() - 1);
            return listArray;
        }
        if (target == 0 && root.left == null && root.right == null) {
            // 不重新 new 的话从始至终 listArray 中所有引用都指向了同一个 list
            listArray.add(new ArrayList<Integer>(list));
        }
        FindPath(root.left, target);
        FindPath(root.right, target);
        // 递归到叶子节点如果还没有找到路径，就要回退到父节点继续寻找，依次类推
        list.remove(list.size() - 1);
        return listArray;
    }
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
class Test {
    public RandomListNode Clone(RandomListNode pHead) {
        // 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针 random 指向一个随机节点）
        // 请对此链表进行深拷贝，并返回拷贝后的头结点。
        // 注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空
        if(pHead == null) {
            return null;
        }
        RandomListNode cur = pHead;
        // 1. 复制每个结点，如复制结点 A 得到 A1，将结点 A1 插到结点 A 后面;
        while (cur != null) {
            RandomListNode cloneNode = new RandomListNode(cur.label);
            cloneNode.next = cur.next;
            cur.next = cloneNode;
            cur = cloneNode.next;
        }
        // 2. 重新遍历链表，复制老结点的随机指针给新结点
        // 如 A1.random = A.random.next;
        cur = pHead;
        while (cur != null) {
            // cur 指老结点
            cur.next.random = (cur.random == null) ? null : cur.random.next;
            cur = cur.next.next;
        }
        // 3. 拆分链表，将链表拆分为原链表和复制后的链表
        cur = pHead;
        RandomListNode cloneHead = pHead.next;
        while (cur != null) {
            RandomListNode cloneCur = cur.next;
            cur.next = cloneCur.next;
            cloneCur.next = (cloneCur.next == null) ? null : cloneCur.next.next;
            cur = cur.next;
        }
        return cloneHead;
    }
}

class Sol {
    public int NumberOf1Between1AndN_Solution(int n) {
        // 求出任意非负整数区间中 1 出现的次数（从 1 到 n 中 1 出现的次数）。

        int count = 0;//1的个数
        int i = 1;//当前位
        int current = 0,after = 0,before = 0;
        while((n/i)!= 0){
            current = (n/i)%10; //当前位数字
            before = n/(i*10); //高位数字
            after = n-(n/i)*i; //低位数字
            //如果为0,出现1的次数由高位决定,等于高位数字 * 当前位数
            if (current == 0)
                count += before*i;
                //如果为1,出现1的次数由高位和低位决定,高位*当前位+低位+1
            else if(current == 1)
                count += before * i + after + 1;
                //如果大于1,出现1的次数由高位决定,//（高位数字+1）* 当前位数
            else{
                count += (before + 1) * i;
            }
            //前移一位
            i = i*10;
        }
        return count;
    }
    public int NumberOf1Between1AndN_Solution1(int n) {
        if(n <= 0) {
            return 0;
        }
        int count = 0;
        for (long i = 1; i <= n; i *= 10) {
            long div = i * 10;
            count += (n / div) * i + Math.min(Math.max(n % div - i + 1, 0), i);
        }
        return count;
    }
}