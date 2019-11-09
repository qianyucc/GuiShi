<script>
	import {
		mapMutations
	} from 'vuex'
	export default {
		methods: {
			...mapMutations(['setCode'])
		},
		onLaunch: function(option) {
			uni.setEnableDebug({
			    enableDebug: true
			});
			uni.getProvider({
				service: 'oauth',
				success: res => {
					uni.login({
						provider: res.provider[0],
						success: resp => {
							this.setCode(resp.code);
						}
					});
				}
			});
		},
		onShow: function() {},
		onHide: function() {}
	}
</script>

<style>
	/*每个页面公共css */
	@import "colorui/main.css";
	@import "colorui/icon.css";
</style>
