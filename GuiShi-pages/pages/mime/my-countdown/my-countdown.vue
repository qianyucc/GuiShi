<template>
	<view>
		<view class="cu-timeline">
			<view class="cu-item" v-for="cd in countdowns" :key="cd.id">
				<view class="content">
					<view class="cu-capsule radius">
						<view class="cu-tag" :class="cd.status < 0?'bg-grey':'bg-cyan'">{{cd.status < 0 ? '已过期' : '未完成'}}</view>
						<view class="cu-tag line-cyan" :class="cd.status < 0?'line-grey':'line-cyan'">{{cd.endTime}}</view>
					</view>
					<view class="margin-top">{{cd.event}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import url from '@/request/url.js'
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				countdowns: [],
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			getAllCountDown(callback) {
				uni.request({
					url: url.getAllCountDown,
					method: "GET",
					data: {
						uid: this.userInfo.id
					},
					success: callback
				});
			}
		},
		onLoad() {
			this.getAllCountDown(resp => {
				console.log(resp.data);
				this.countdowns = resp.data;
			});
		}
	}
</script>

<style>

</style>
