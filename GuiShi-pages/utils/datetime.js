export default {
	getCurrentTime() {
		let date = new Date();
		return date.getHours() + ":" + date.getMinutes();
	},
	getCurrentDate() {
		let date = new Date();
		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	}
	
}
