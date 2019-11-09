<template>
	<view class="cu-list menu sm-border margin-top">
		<view class="cu-list menu-avatar">
			<view class="cu-item ">
				<view class="cu-avatar radius lg" v-if="userInfo!=null" :style="'background-image:url('+userInfo.avatarUrl+');'"></view>
				<view class="content" v-if="userInfo!=null">
					<view class="text-pink">
						<view class="text-cut">{{userInfo.nickName}}</view>
					</view>
					<view class="text-gray text-sm flex">
						<view class="text-cut">{{userInfo.country + ' ' + userInfo.city}}</view>
					</view>
				</view>
				<view class="content" v-else>
					<button class="cu-btn lines-cyan round shadow" open-type="getUserInfo" @getuserinfo="getUserInfo">
						登录
					</button>
				</view>
				<view class="action">
					<view class="text-grey text-xs">今日未完成</view>
					<view class="cu-tag round bg-red sm">{{userInfo.ucplans}}</view>
				</view>
			</view>
		</view>
		<view class="cu-item arrow margin-top">
			<view class="content" @tap="toMyDiary">
				<text class="cuIcon-edit text-grey"></text>
				<text class="text-grey">我的日记</text>
			</view>
		</view>
		<view class="cu-item arrow">
			<view class="content" @tap="toMyPlan">
				<text class="cuIcon-notice text-grey"></text>
				<text class="text-grey">我的计划</text>
			</view>
		</view>
		<view class="cu-item arrow">
			<view class="content" @tap="toMyCountDown">
				<text class="cuIcon-countdown text-grey"></text>
				<text class="text-grey">我的倒计时</text>
			</view>
		</view>
		<view class="cu-item arrow">
			<view class="content">
				<text class="cuIcon-mail text-grey"></text>
				<text class="text-grey">我的信件</text>
			</view>
		</view>
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
			return {
				index: 2
			};
		},
		computed: {
			...mapState({
				code: state => state.code,
				userInfo: state => state.userInfo
			})
		},
		methods: {
			...mapMutations(['setUserInfo']),
			getUserInfo(e) {
				console.log(e);
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
						this.setUserInfo(resp.data.data);
					}
				})
			},
			toMyPlan(e) {
				uni.navigateTo({
					url: 'my-plan/my-plan',
					animationDuration: 100,
					animationType: "slide-in-left"
				})
			},
			toMyDiary() {
				uni.navigateTo({
					url: "my-diary/my-diary",
					animationDuration: 100,
					animationType: "slide-in-right"
				});
			},
			toMyCountDown() {
				uni.navigateTo({
					url: "my-countdown/my-countdown",
					animationDuration: 100,
					animationType: "slide-in-right"
				});
			},
		},
		onShow() {
			uni.request({
				url: url.getUserInfo,
				data: {
					id: this.userInfo.id
				},
				success: resp => {
					console.log(resp.data);
					this.setUserInfo(resp.data);
				}
			});
		}
	}
</script>

<style lang="scss">

</style>
