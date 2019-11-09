// 服务器域名
const baseUrl = 'https://www.qianyucc.xyz/wxapp/';
// const baseUrl = 'https://localhost:8083/wxapp/';
// 用户登录
const loginUrl = baseUrl + 'api/user/login';
// 添加信息
const addUrl = baseUrl + 'api/add';
// 获取倒计时
const getCountDownUrl = baseUrl + 'api/getCountDown';
// 获取计划
const getPlanUrl = baseUrl + 'api/getPlan';
// 获取信件
const getLetterUrl = baseUrl + 'api/getLetter';
// 获取日记
const getDiaryUrl = baseUrl + 'api/getDiary';
// 删除数据
const deleteUrl = baseUrl + 'api/delete';
// 更新数据
const updateUrl = baseUrl + 'api/update';
// 获取完成和未完成的计划数量
const getPlanCountUrl = baseUrl + 'api/getPlanCount';
// 获取按照日期分组的计划
const getPlanGroupByDate = baseUrl + 'api/getPlanGroupByDate';

module.exports = {
	loginUrl:loginUrl,
	addUrl:addUrl,
	getCountDownUrl:getCountDownUrl,
	getPlanUrl:getPlanUrl,
	getLetterUrl:getLetterUrl,
	getDiaryUrl:getDiaryUrl,
	deleteUrl:deleteUrl,
	updateUrl:updateUrl,
  getPlanCountUrl: getPlanCountUrl,
  getPlanGroupByDate: getPlanGroupByDate
};
