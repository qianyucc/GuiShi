// const baseUrl = "http://localhost:8083/"
const baseUrl = "http://118.25.208.67:8083/"

export default {
	insOrupdPlan: baseUrl + "api/plan/aou",
	getAllPlan: baseUrl + "api/plan/get",
	updPlanStatus: baseUrl + "api/plan/upd",
	delPlan: baseUrl + "api/plan/del/",
	addCountDown: baseUrl + "api/countdown/aou",
	getAllCountDown: baseUrl + "api/countdown/getAll",
	delCountDown: baseUrl + "api/countdown/del/",
	login: baseUrl + "api/user/login",
	addDiary: baseUrl + "api/diary/upload",
	getAllDiary: baseUrl + "api/diary/get",
	addLetter: baseUrl + "api/letter/add",
	getUserInfo: baseUrl + "api/user/get",
	delDiary: baseUrl + "api/diary/del/"
}
