import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
	state: {
		code: null,
		userInfo: null
	},
	mutations: {
		setCode(state, code) {
			state.code = code;
		},
		setUserInfo(state, userInfo) {
			state.userInfo = userInfo;
		}
	},
	actions: {

	}
})

export default store
