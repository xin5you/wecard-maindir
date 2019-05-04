package com.cn.iboot.diy.api.operate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.service.impl.BaseServiceImpl;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.domain.PositOprUpdate;
import com.cn.iboot.diy.api.operate.mapper.PositOprStatisticsMapper;
import com.cn.iboot.diy.api.operate.service.PositOprStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("positOprStatisticsService")
public class PositOprStatisticsServiceImpl extends BaseServiceImpl<PositOprStatistics>
implements PositOprStatisticsService {

	private Logger logger = LoggerFactory.getLogger(PositOprStatisticsServiceImpl.class);

	@Autowired
	private PositOprStatisticsMapper positOprStatisticsMapper;

	@Override
	public PageInfo<PositOprStatistics> getPositOprStatisticsPage(int startNum, int pageSize,
			PositOprStatistics positOprStatistics) {
		List<PositOprStatistics> list = new ArrayList<PositOprStatistics>();
		if (positOprStatistics.getStartDate() != null && !("").equals(positOprStatistics.getStartDate())
				&& positOprStatistics.getEndDate() != null && !("").equals(positOprStatistics.getEndDate())){
			PageHelper.startPage(startNum, pageSize);
			list = positOprStatisticsMapper.getList(positOprStatistics);
		}
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		if (pageList.getList() != null && pageList.getList().size() > 0) {
			pageList.getList().stream().filter(pos -> {
				if(pos!=null)
					pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				return true;
			}).collect(Collectors.toList());
		}
		return pageList;
	}

	@Override
	public String getShopInfByShopCode(String shopCode) {
		String shopName = positOprStatisticsMapper.getShopInfByShopCode(shopCode);
		return shopName;
	}

	@Override
	public PageInfo<PositOprStatistics> getShopStatisticsMonthSetPage(int startNum, int pageSize,
			PositOprStatistics positOprStatistics) {
		List<PositOprStatistics> list = new ArrayList<PositOprStatistics>();
		if (positOprStatistics.getStartDate() != null && !("").equals(positOprStatistics.getStartDate())
				&& positOprStatistics.getEndDate() != null && !("").equals(positOprStatistics.getEndDate())) {
			PageHelper.startPage(startNum, pageSize);
			list = positOprStatisticsMapper.getShopStatisticsSet(positOprStatistics);
		}
		/*
		 * }List<PositOprStatistics> p = new ArrayList<>();
		 *//**
		 * 按月分组统计
		 */
		/*
		 * if(list!=null&&list.size()>0)
		 * list.stream().collect(Collectors.groupingBy(PositOprStatistics::
		 * getDateSub)) .forEach((date,pList)->{ Optional<PositOprStatistics>
		 * sum = pList.stream().reduce((p1,p2)->{
		 * p1.setPayAmt(p1.getPayAmt()+p2.getPayAmt());
		 * p1.setuPayAmt(p1.getuPayAmt()+p2.getuPayAmt()); return p1; });
		 * p.add(sum.orElse(new PositOprStatistics())); });
		 *//**
		 * 格式化日期和金额
		 *//*
		 * list = p.stream().filter(pos->{
		 * pos.setSettleDate(pos.getSettleDate().substring(0,
		 * 4)+"年"+pos.getSettleDate().substring(4,6)+"月");
		 * pos.setPayAmt(NumberUtils.formatMoney(pos.getPayAmt()));
		 * pos.setuPayAmt(NumberUtils.formatMoney(pos.getuPayAmt())); return
		 * true; }).collect(Collectors.toList());
		 */
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		/**
		 * 格式化日期和金额
		 */
		if (pageList.getList() != null && list.size() > 0)
			pageList.getList().stream().filter(pos -> {
				pos.setSettleDate(
						pos.getSettleDate().substring(0, 4) + "年" + pos.getSettleDate().substring(4, 6) + "月");
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				pos.setuPayAmt(NumberUtils.RMBCentToYuan(pos.getuPayAmt()));
				return true;
			}).collect(Collectors.toList());
		return pageList;
	}

	@Override
	public PageInfo<PositOprStatistics> getShopStatisticsDaySetPage(int startNum, int pageSize,
			PositOprStatistics positOprStatistics) {

		PageHelper.startPage(startNum, pageSize);
		List<PositOprStatistics> list = positOprStatisticsMapper.getPositStatisticsSet(positOprStatistics);
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		/**
		 * 格式化日期，金额，状态
		 */
		if (pageList.getList() != null && list.size() > 0)
			pageList.getList().stream().filter(pos -> {
				pos.setSettleDate(pos.getSettleDate().substring(0, 4) + "年" + pos.getSettleDate().substring(4, 6) + "月"
						+ pos.getSettleDate().substring(6, 8) + "日");
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				pos.setuPayAmt(NumberUtils.RMBCentToYuan(pos.getuPayAmt()));
				pos.setStat2(Constants.DIYStat.findByCode(pos.getStat()) == null ? ""
						: Constants.DIYStat.findByCode(pos.getStat()).getName());
				return true;
			}).collect(Collectors.toList());
		return pageList;
	}

	@Override
	public PageInfo<PositOprStatistics> getPositStatisticsSetPage(int startNum, int pageSize,
			PositOprStatistics positOprStatistics) {
		PageHelper.startPage(startNum, pageSize);
		List<PositOprStatistics> list = positOprStatisticsMapper.getPositStatisticsSet(positOprStatistics);
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		/**
		 * 格式化日期和金额
		 */
		if (pageList.getList() != null && list.size() > 0)
			pageList.getList().stream().filter(pos -> {
				pos.setSettleDate(
						pos.getSettleDate().substring(0, 4) + "年" + pos.getSettleDate().substring(4, 6) + "月");
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				pos.setuPayAmt(NumberUtils.RMBCentToYuan(pos.getuPayAmt()));
				return true;
			}).collect(Collectors.toList());
		return pageList;
	}

	@Override
	public PageInfo<PositOprStatistics> getPositStatisticsDaySetPage(int startNum, int pageSize,
			PositOprStatistics positOprStatistics) {
		PageHelper.startPage(startNum, pageSize);
		List<PositOprStatistics> list = positOprStatisticsMapper.getPositStatisticsDaySet(positOprStatistics);
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		if (pageList.getList() != null && list.size() > 0)
			pageList.getList().stream().filter(pos -> {
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				pos.setuPayAmt(NumberUtils.RMBCentToYuan(pos.getuPayAmt()));
				return true;
			}).collect(Collectors.toList());
		return pageList;
	}

	@Override
	public String getMchntInfByMchntCode(String mchntCode) {
		String mchntName = positOprStatisticsMapper.getMchntInfByMchntCode(mchntCode);
		return mchntName;
	}

	@Transactional
	@Override
	public synchronized String updatePositProStatistics(String sid, String updateUser, List<Map<String, Object>> list)
			throws Exception {
		boolean flag = false;
		String result = "";
		if (list != null && !list.isEmpty())
			for (Map<String, Object> m : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rid", (String) m.get("id"));
				map.put("payamt", NumberUtils.RMBYuanToCent((String) m.get("payAmt")));
				map.put("updateuser", (String) m.get("updateUser"));
				positOprStatisticsMapper.updatePositProStatistics(map);
				String results = (String) map.get("results");
				if ("0".equals(results)) {
					logger.error("保存失败-->[{}]！");
					flag = true;
					result = results;
					break;
				} else if ("1".equals(results)) {
					logger.info("保存成功！");
				} else if ("2".equals(results)) {
					logger.info("修改三次不能再修改！");
					flag = true;
					result = results;
					break;
				}
			}

		if (!flag) {
			String totalPay = positOprStatisticsMapper.getShopTotalPayById(sid);
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("pid", sid);
			m2.put("payamt", totalPay);
			m2.put("updateuser", updateUser);
			positOprStatisticsMapper.updateShopProStatistics(m2);
			result = (String) m2.get("results");
			if ("0".equals(result)) {
				throw new Exception("保存失败！");
			}
		}

		return result;
	}

	@Override
	public List<PositOprUpdate> getPositOprUploadList(PositOprStatistics ps) {
		List<PositOprUpdate> list = positOprStatisticsMapper.getPositOprUploadList(ps);
		return list.stream().filter(e -> {
			e.setPayAmt(NumberUtils.RMBCentToYuan(e.getPayAmt()));
			return true;
		}).collect(Collectors.toList());
	}

	@Override
	public String getPositOprTotalPay(PositOprStatistics pos) {
		String totalPayAmt = positOprStatisticsMapper.getPositOprTotalPay(pos);
		if(totalPayAmt!=null){
			totalPayAmt = NumberUtils.RMBCentToYuan(totalPayAmt);
		}else{
			totalPayAmt="0.00";
		}
		return totalPayAmt;
	}

	@Override
	public PageInfo<PositOprStatistics> getPositCurStatisticsPage(int startNum, int pageSize, PositOprStatistics positOprStatistics) {
		List<PositOprStatistics> list = new ArrayList<PositOprStatistics>();
		List<PositOprStatistics> list2 = new ArrayList<PositOprStatistics>();

		String num = positOprStatisticsMapper.getCurLogNum();
		if(num!=null)
			positOprStatistics.setNum(num);

		if (positOprStatistics.getStartDate() != null && !("").equals(positOprStatistics.getStartDate())
				&& positOprStatistics.getEndDate() != null && !("").equals(positOprStatistics.getEndDate())) {
			PageHelper.startPage(startNum, pageSize);	
			list = positOprStatisticsMapper.getPositCurStatistics(positOprStatistics);
			list2 = positOprStatisticsMapper.getPositCurStatistics(positOprStatistics); //作统计总额和笔数

		}

		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		if (pageList.getList() != null && pageList.getList().size() > 0) {
			pageList.getList().stream().filter(pos -> {
				pos.setCardPayAmt(NumberUtils.RMBCentToYuan(pos.getCardPayAmt()));
				pos.setQuickPayAmt(NumberUtils.RMBCentToYuan(pos.getQuickPayAmt()));
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				return true;
			}).collect(Collectors.toList());
			/**
			 * 统计总额和笔数，并分装成对象
			 */
			Optional<PositOprStatistics> sum = list2.stream().reduce((p1,p2)->{
				Double d = Double.parseDouble(p1.getPayAmt())+Double.parseDouble(p2.getPayAmt());
				p1.setPayAmt(d.toString());
				p1.setCardPayNum(p1.getCardPayNum()+p2.getCardPayNum()); 
				p1.setQuickPayNum(p1.getQuickPayNum()+p2.getQuickPayNum());
				return p1;} 
					);
			PositOprStatistics p = sum.orElse(new PositOprStatistics());
			/**
			 * 将总额存到list的第一个对象中
			 */
			if(p!=null){
				pageList.getList().get(0).setTotalPay(NumberUtils.RMBCentToYuan(p.getPayAmt()));
				pageList.getList().get(0).setTotalNum(p.getCardPayNum()+p.getQuickPayNum());
			}
		}

		return pageList;
	}

	@Override
	public PageInfo<PositOprStatistics> getPositCurStatisticsPage(int startNum, int pageSize,
			Map<String, Object> params) {

		List<PositOprStatistics> list = new ArrayList<PositOprStatistics>();
		if (params.get("startdate")!= null && !("").equals(params.get("startdate"))
				&&params.get("enddate") != null && !("").equals(params.get("enddate"))) {

			try{
				positOprStatisticsMapper.getPositCurStatistics2(params);
			}catch(Exception e){
				e.printStackTrace();
			}	

		}
		PageHelper.startPage(startNum, pageSize);	
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> l= (List<Map<String,Object>>) params.get("results");
		for (Map<String,Object> m : l) {
			PositOprStatistics p = new PositOprStatistics();
			p.setShopName(m.get("shopName").toString());
			p.setCardPayAmt(m.get("cardPayAmt").toString());
			p.setCardPayNum(Integer.valueOf(String.valueOf(m.get("cardPayNum"))));
			p.setQuickPayAmt(m.get("quickPayAmt").toString());
			p.setQuickPayNum(Integer.valueOf( String.valueOf(m.get("quickPayNum"))));
			p.setPayAmt(m.get("payAmt").toString());
			list.add(p);
		}
		PageInfo<PositOprStatistics> pageList = new PageInfo<PositOprStatistics>(list);
		if (pageList.getList() != null && pageList.getList().size() > 0) {
			pageList.getList().stream().filter(pos -> {
				if(pos!=null)
					pos.setCardPayAmt(NumberUtils.RMBCentToYuan(pos.getCardPayAmt()));
				pos.setQuickPayAmt(NumberUtils.RMBCentToYuan(pos.getQuickPayAmt()));
				pos.setPayAmt(NumberUtils.RMBCentToYuan(pos.getPayAmt()));
				return true;
			}).collect(Collectors.toList());
		}


		return pageList;
	}


	@Override
	public String getCurLogNum() {
		return positOprStatisticsMapper.getCurLogNum();
	}

}
