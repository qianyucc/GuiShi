<template>
	<view>
		<view class="cu-form-group">
			<view class="title">你的邮箱：</view>
			<input placeholder="请输入你的邮箱,用于接受信件..." v-model="mail"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">收信日期：</view>
			<picker mode="date" :value="noticeTime" :start="noticeTime" @change="dateChange">
				<view class="picker">
					{{noticeTime}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<view class="title">标题：</view>
			<input placeholder="请输入标题..." v-model="title"></input>
		</view>
		<view class="cu-form-group margin-top">
			<textarea v-model="content" maxlength="-1" placeholder="开始给未来的自己写信吧..."></textarea>
		</view>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-gradual-blue lg" @tap="submit">提交</button>
		</view>
	</view>
</template>

<script>
	import datetime from '@/utils/datetime.js'
	import {
		mapState
	} from 'vuex'
	import url from '@/request/url.js'
	export default {
		data() {
			return {
				title: '',
				content: '',
				mail: '',
				noticeTime: datetime.getCurrentDate()
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			submit() {
				uni.request({
					url: url.addLetter,
					method: "POST",
					data: {
						uid: this.userInfo.id,
						title: this.title,
						mail: this.mail,
						content: this.content,
						noticeTime: this.noticeTime
					},
					success: resp => {
						console.log(resp.data);
						uni.showToast({
							title: "未来信件已寄出",
							icon: "success"
						});
						uni.navigateBack({
							delta: 1
						});
					}
				});
			},
			dateChange(e) {
				this.date = e.detail.value
			}
		}
	}
</script>

<style scoped>
	.margin-top textarea {
		height: 600upx;
	}
</style>
