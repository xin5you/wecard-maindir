package com.cn.thinkx.pms.socket.encdec;

import com.cn.thinkx.pms.socket.util.GlobalSocketConst;


public class EncDec72_DataIN {
	public byte[] index = new byte[GlobalSocketConst.INDEX_LEN]; /* 银行索引号BMK */

	public byte[] initial_vecotr = new byte[8]; /* CBC加密的初始向量 */

	public byte[] encdec_flag = new byte[GlobalSocketConst.FLAG_LEN]; /* 加/解密标识，0--解密，1--加密；*/
	
	public byte[] algorithm_flag = new byte[GlobalSocketConst.FLAG_LEN]; /* 算法标识，第0位：ECB＝0，CBC＝1，第1位：3DES＝0，DES＝1；*/

	public byte[] data_len = new byte[GlobalSocketConst.DATA_LEN]; /* 数据长度 */

	public byte[] data ; /* 参与加解密的数据 */

	public EncDec72_DataIN(byte[] index,  byte[] initial_vecotr, byte[] encdec_flag,byte[] algorithm_flag,
			byte[] data_len, byte[] data) {
		System.arraycopy(index, 0, this.index, 0, GlobalSocketConst.INDEX_LEN);
		System.arraycopy(initial_vecotr, 0, this.initial_vecotr, 0, initial_vecotr.length);
		System.arraycopy(encdec_flag, 0, this.encdec_flag, 0, encdec_flag.length);
		System.arraycopy(algorithm_flag, 0, this.algorithm_flag, 0, algorithm_flag.length);
		System.arraycopy(data_len, 0, this.data_len, 0, data_len.length);
		this.data = new byte[data.length];
		System.arraycopy(data, 0, this.data, 0, data.length);
	}
}