package com.wasion.meterreading.domain;

import java.util.ArrayList;
import java.util.List;

import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;

/**
 * 帧数据对象，提供方便的向后遍历API
 * 
 * @author w24882 xieningjie
 * @date 2019年12月13日
 */
public class FrameValue {

	private int index = 0;
	private int currentOffset;
	private String frameValue;
	private List<String> units = new ArrayList<>();

	public FrameValue(String frameValue) {
		super();
		this.frameValue = frameValue;
	}

	/**
	 * 获取指定偏移量下的下一个字符串
	 * 
	 * @param offset
	 *            偏移量
	 * @return 当前指针index与index+offset间的字符串
	 * @author w24882 xieningjie
	 * @date 2019年12月13日
	 */
	public String next(int offset) {
		this.currentOffset = offset;
		validateOffset();
		return next();
	}

	public String next() {
		if (!_hasNext()) {
			throw new MRException(MRExceptionEnum.FrameValueTraverseException, "Out Of Range");
		}
		String nextStr = frameValue.substring(index, index + currentOffset);
		index += currentOffset;
		units.add(nextStr);
		return nextStr;
	}

	private void validateOffset() {
		if (this.currentOffset % 2 != 0) {
			throw new MRException(MRExceptionEnum.FrameValueTraverseException, "Invalid Offset");
		}
	}

	public boolean hasNext(int offset) {
		this.currentOffset = offset;
		validateOffset();
		return _hasNext();
	}

	private boolean _hasNext() {
		int length = this.frameValue.length();
		return (this.index + this.currentOffset) <= length;
	}

	/**
	 * 返回原始帧数据
	 * 
	 * @return 原始帧数据
	 * @author w24882 xieningjie
	 * @date 2019年12月13日
	 */
	public String getRaw() {
		return this.frameValue;
	}

	/**
	 * 查看从当前索引到目标索引的字符串
	 * 
	 * @param index
	 *            目标索引号
	 * @return 待查看的字符串
	 * @author w24882 xieningjie
	 * @date 2019年12月13日
	 */
	public String peekTo(int index) {
		int tmp = index;
		while (tmp < 0) {
			tmp = getRawSize() + index;
		}
		if (tmp < this.index) {
			throw new MRException(MRExceptionEnum.FrameValueTraverseException, "End index is greater than start index");
		}
		return this.frameValue.substring(this.index, tmp);
	}

	/**
	 * 获取原始字符串大小
	 * 
	 * @return 大小
	 * @author w24882 xieningjie
	 * @date 2019年12月13日
	 */
	public int getRawSize() {
		return this.frameValue.length();
	}
}
