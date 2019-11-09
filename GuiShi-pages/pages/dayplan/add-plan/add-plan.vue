<template>
	<view>
		<view class="cu-form-group">
			<view class="title">事件名称：</view>
			<input v-model="content" placeholder="请输入事件名称..."></input>
		</view>
		<view class="cu-form-group">
			<view class="title">开始时间：</view>
			<picker mode="time" v-model="startTime" :start="startTime" end="23:59" @change="stimeChange">
				<view class="picker">
					{{startTime}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<view class="title">结束时间：</view>
			<picker mode="time" v-model="endTime" :start="startTime" end="23:59" @change="etimeChange">
				<view class="picker">
					{{endTime}}
				</view>
			</picker>
		</view>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-gradual-blue lg" @tap="addPlan">确定</button>
		</view>
	</view>
</template>

<script>
	import url from '@/request/url.js'
	import datetime from '@/utils/datetime.js'
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				startTime: datetime.getCurrentTime(),
				endTime: datetime.getCurrentTime(),
				content: ''
			}
		},
		watch: {
			startTime(val, old) {
				this.endTime = val;
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			check() {
				if (this.content == '') {
					uni.showToast({
						icon: "none",
						title: "记得填写内容哦"
					})
					return false;
				}
				return true;
			},
			addPlan(e) {
				if (this.check()) {
					uni.request({
						url: url.insOrupdPlan,
						method: "POST",
						data: {
							uid: this.userInfo.id,
							content: this.content,
							startTime: this.startTime,
							endTime: this.endTime
						},
						success: resp => {
							if (resp.statusCode == 200) {
								uni.showToast({
									title: "添加成功！",
									icon: "success"
								});
							} else {
								uni.showToast({
									icon: "none",
									title: "服务器忙，请稍后再试！"
								})
							}
							uni.navigateBack({
								delta: 1,
								animationDuration: 100,
								animationType: "slide-out-right"
							});
						}
					});
				}
			},
			stimeChange(e) {
				this.startTime = e.detail.value
			},
			etimeChange(e) {
				this.endTime = e.detail.value
			},
		}
	}
</script>

<style>

</style>
