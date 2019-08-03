package com.snowcattle.game.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 基于天书，龙之刃代码修改，实现真正AC过滤算法
 *
 * 原有算法：失效的时候退回自动机入口重新匹配下一个字符。 相当于AC算法中把每个点的失效节点置为初始状态节点。
 *
 * 本改进:计算正确的失效节点,当扫描一个字符，并无对应状态转移时根据该节点的失效节点计算转移（原算法回到初始状态，开始下一轮状态转换)，从而降低复杂度，提高效率
 *
 *
 */
public class KeyWordsACFilter implements IKeyWordsFilter {
	/** DFA入口 */
	private final DFANode dfaEntrance;
	/** 要忽略的字符 */
	private final char[] ignoreChars;
	/** 过滤时要被替换的字符 */
	private final char subChar;

	/**
	 * 定义不进行小写转化的字符,在此表定义所有字符,其小写使用原值,以避免转小写后发生冲突:
	 * <ul>
	 * <li>char code=304,İ -> i,即İ的小写是i,以下的说明类似</li>
	 * <li>char code=8490,K -> k,</li>
	 * </ul>
	 */
	private static final char[] ignowLowerCaseChars = { 304, 8490 };

	/**
	 *
	 * @param ignore
	 *            要忽略的字符,即在检测时将被忽略的字符
	 * @param substitute
	 *            过滤时要被替换的字符
	 */
	public KeyWordsACFilter(char[] ignore, char substitute) {
		dfaEntrance = new DFANode();
		this.ignoreChars = new char[ignore.length];
		System.arraycopy(ignore, 0, this.ignoreChars, 0,
				this.ignoreChars.length);
		this.subChar = substitute;
	}

	public boolean initialize(String[] keyWords) {
		clear();
		// 构造DFA
		for (String keyWord : keyWords) {
			String _keyword = keyWord;
			if (_keyword == null || (_keyword = _keyword.trim()).length() == 0) {
				continue;
			}
			char[] patternTextArray = _keyword.toCharArray();
			DFANode currentDFANode = dfaEntrance;
			for (final char _c : patternTextArray) {
				// 逐点加入DFA
				final Character _lc = toLowerCaseWithoutConfict(_c);
				DFANode _next = currentDFANode.dfaTransition.get(_lc);
				if (_next == null) {
					_next = new DFANode();
					currentDFANode.dfaTransition.put(_lc, _next);
				}
				currentDFANode = _next;
			}
			if (currentDFANode != dfaEntrance) {
				currentDFANode.isTerminal = true;
			}
		}

		buildFailNode();
		return true;
	}

	/**
	 * 构造失效节点：
	 * 一个节点的失效节点所代表的字符串是该节点所表示它的字符串的最大 部分前缀
	 */
	private void buildFailNode() {
		// 以下构造失效节点
		List<DFANode> queues = new ArrayList<DFANode>();
		dfaEntrance.failNode = dfaEntrance;//
		for (DFANode node : dfaEntrance.dfaTransition.values()) {
			node.level = 1;
			queues.add(node);
			node.failNode = dfaEntrance;// 失效节点指向状态机初始状态
		}
		DFANode curNode;
		DFANode failNode;
		while (!queues.isEmpty()) {
			curNode = queues.remove(0);// 该节点的失效节点已计算
			failNode = curNode.failNode;
			for (Entry<Character, DFANode> nextTrans : curNode.dfaTransition
					.entrySet()) {
				Character nextKey = nextTrans.getKey();
				DFANode nextNode = nextTrans.getValue();
				// 如果父节点的失效节点中有条相同的出边，那么失效节点就是父节点的失效节点
				while (failNode != dfaEntrance
					   && !failNode.dfaTransition.containsKey(nextKey)) {
					failNode = failNode.failNode;
				}
				nextNode.failNode = failNode.dfaTransition.get(nextKey);
				if (nextNode.failNode == null) {
					nextNode.failNode = dfaEntrance;
				}
				nextNode.level = curNode.level + 1;
				queues.add(nextNode);// 计算下一层

			}

		}
	}

	/**
	 * 基于AC状态机查找匹配，并根据节点层数覆写应过滤掉的字符
	 *
	 */
	public String filt(String s) {
		char[] input = s.toCharArray();
		char[] result = s.toCharArray();
		boolean _filted = false;

		DFANode currentDFANode = dfaEntrance;
		DFANode _next;
		int replaceFrom = 0;
		for (int i = 0; i < input.length; i++) {
			final Character _lc = toLowerCaseWithoutConfict(input[i]);
			_next = currentDFANode.dfaTransition.get(_lc);
			while (_next == null && currentDFANode != dfaEntrance) {
				currentDFANode = currentDFANode.failNode;
				_next = currentDFANode.dfaTransition.get(_lc);
			}
			if (_next != null) {
				// 找到状态转移，可继续
				currentDFANode = _next;
			}
			// 看看当前状态可退出否
			if (currentDFANode.isTerminal) {
				// 可退出，记录，可以替换到这里
				int j = i - (currentDFANode.level - 1);
				if (j < replaceFrom) {
					j = replaceFrom;
				}
				replaceFrom = i + 1;
				for (; j <= i; j++) {
					if (!this.isIgnore(input[j])) {
						result[j] = this.subChar;
						_filted = true;
					}
				}
			}
		}
		if (_filted) {
			return String.valueOf(result);
		} else {
			return s;
		}
	}


	public boolean contain(final String inputMsg) {
		char[] input = inputMsg.toCharArray();
		DFANode currentDFANode = dfaEntrance;
		DFANode _next;
		for (char anInput : input) {
			final Character _lc = toLowerCaseWithoutConfict(anInput);
			_next = currentDFANode.dfaTransition.get(_lc);
			while (_next == null && currentDFANode != dfaEntrance) {
				currentDFANode = currentDFANode.failNode;
				_next = currentDFANode.dfaTransition.get(_lc);
			}
			if (_next != null) {
				// 找到状态转移，可继续
				currentDFANode = _next;
			}
			// 看看当前状态可退出否
			if (currentDFANode.isTerminal) {
				// 可退出，记录，可以替换到这里
				return true;
			}
		}

		return false;
	}

	/**
	 * 初始化时先调用此函数清理
	 */
	private void clear() {
		// 清理入口
		dfaEntrance.dfaTransition.clear();
	}

	/**
	 * 将指定的字符转成小写,如果与{@link #ignowLowerCaseChars}所定义的字符相冲突,则取原值
	 *
	 * @param c
	 * @return
	 */
	private static char toLowerCaseWithoutConfict(final char c) {
		return (c == ignowLowerCaseChars[0] || c == ignowLowerCaseChars[1]) ? c
				: Character.toLowerCase(c);
	}

	/**
	 * 是否属于被忽略的字符
	 *
	 * @param c
	 * @return
	 */
	private boolean isIgnore(final char c) {
		for (char ignoreChar : this.ignoreChars) {
			if (c == ignoreChar) {
				return true;
			}
		}
		return false;
	}

	/**
	 * DFA节点
	 *
	 *
	 */
	private static class DFANode {
		//是否终结状态的节点
		public boolean isTerminal;
		/** 保存小写字母，判断时用小写 */
		private final HashMap<Character, DFANode> dfaTransition;
		//不匹配时走的节点
		DFANode failNode;
		//节点层数
		int level;

		// public boolean canExit = false;
		private DFANode() {
			this.dfaTransition = new HashMap<Character, DFANode>();
			isTerminal = false;
			failNode = null;
			level = 0;
		}

	}

}
