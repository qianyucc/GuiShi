<template>
	<view class="main">
		<image src="../../static/image/logo.png"></image>
		<view class="text-lg text-gray">合理规划你的时间，提升生活效率</view>
		<button class="cu-btn bg-gradual-blue lg" open-type="getUserInfo" @getuserinfo="getUserInfo">登录</button>
	</view>
</template>

<script>
	import url from '@/request/url.js'
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {
		data() {
			return {}
		},
		computed: {
			...mapState({
				code: state => state.code
			})
		},
		methods: {
			...mapMutations(['setUserInfo']),
			getUserInfo(e) {
				console.log(e);
				uni.showLoading({
					title: "登录中"
				});
				let info = e.detail
				this.setUserInfo(info.userInfo);
				uni.request({
					url: url.login,
					method: "POST",
					data: {
						code: this.code,
						rawData: info.rawData,
						signature: info.signature,
						encryptedData: info.encryptedData,
						iv: info.iv
					},
					success: resp => {
						console.log(resp)
						uni.setStorage({
							key: "user-id",
							data: resp.data.data.id
						});
						uni.hideLoading();
						this.setUserInfo(resp.data.data);
						uni.switchTab({
							url: "../timeline/timeline",
						});
					}
				});
			},
		},
		onLoad() {
			uni.getStorage({
				key: 'user-id',
				success: res => {
					console.log('user-id:'+res.data);
					uni.request({
						url: url.getUserInfo,
						method: "GET",
						data: {
							id: res.data
						},
						success: resp => {
							console.log(resp)
							this.setUserInfo(resp.data);
							uni.switchTab({
								url: "../timeline/timeline",
							});
						}
					});
				}
			});
		}
	}
</script>

<style scoped>
	.main {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: flex-start;
		width: 100%;
		height: 100%;
	}

	.cu-btn {
		margin-top: 200upx;
	}

	image {
		width: 400upx;
		height: 400upx;
	}
</style>
