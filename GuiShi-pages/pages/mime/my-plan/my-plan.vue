<template>
	<view class="cu-timeline">
		<view class="cu-timeline" v-for="(plan,index) in plans" :key="index">
			<view class="cu-time">{{plan.day}}</view>
			<view class="cu-item" :class="item.status == 1 ? 'cuIcon-roundcheckfill text-blue' : 'cuIcon-roundclosefill text-grey'"
			 v-for="item in plan.data" :key="item.id">
				<view class="content" :class="item.status == 1 ? 'bg-blue' : 'bg-grey'">
					<text>{{item.startTime + '-' + item.endTime}}</text>【{{item.content}}】
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
				plans: []
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			getAllPlan(callback) {
				uni.request({
					url: url.getAllPlan,
					method: "GET",
					data: {
						uid: this.userInfo.id,
						isAll: true
					},
					success: callback
				});
			}
		},
		onLoad() {
			this.getAllPlan(resp => {
				console.log(resp.data);
				resp.data.forEach(item => {
					let tmp = this.plans.find((val, index, arr) => val.day == item.createDate);
					if (tmp) {
						tmp.data.push(item);
					} else {
						this.plans.push({
							day: item.createDate,
							data: [item]
						});
					}
				});
			});
			console.log(this.plans);
		}
	}
</script>

<style scoped>
.cu-time{
	width: 135upx;
}
</style>
