<template>
	<view>
		<view class="cu-form-group">
			<view class="title">事件名称：</view>
			<input placeholder="请输入事件名称..." v-model="event"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">结束日期：</view>
			<picker mode="date" :value="date" :start="date" end="2040-12-30" @change="dateChange">
				<view class="picker">
					{{date}}
				</view>
			</picker>
		</view>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-gradual-blue lg" @tap="addCountDown">确定</button>
		</view>
	</view>
</template>

<script>
	import datetime from '@/utils/datetime.js'
	import url from "@/request/url.js"
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				date: datetime.getCurrentDate(),
				event: ''
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			addCountDown() {
				if (this.event == '') {
					uni.showToast({
						title: "别忘了输入内容哦！",
						icon: "none"
					});
					return;
				}
				uni.request({
					url: url.addCountDown,
					method: "POST",
					data: {
						uid: this.userInfo.id,
						event: this.event,
						endTime: this.date
					},
					success: resp => {
						uni.showToast({
							title: "添加成功！",
							icon: "success"
						});
						uni.navigateBack({
							delta: 1,
							animationDuration: 100,
							animationType: "slide-out-right"
						});
					}
				})
			},
			dateChange(e) {
				this.date = e.detail.value
			}
		},
	}
</script>

<style scoped lang="scss">

</style>
