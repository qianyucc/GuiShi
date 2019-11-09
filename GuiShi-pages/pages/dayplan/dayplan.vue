<template>
	<view>
		<view class="cu-list menu sm-border">
			<view class="cu-card" v-show="showTip">
				<view class="cu-item margin-top padding bg-red light">
					<view class="action">
						<text class="cuIcon-notice text-red margin-right-xs"></text>
						<text class="text-red"> 今天还没有计划哦，快去添加一个吧！</text>
					</view>
				</view>
			</view>
			<view class="cu-item" v-for="plan in uPlans" :key="plan.id" @longpress="operate(plan.id)">
				<view class="content padding-tb-sm">
					<view>
						<text class="cuIcon-noticefill text-blue margin-right-xs"></text>{{plan.content}}</view>
					<view class="text-gray text-sm">
						{{plan.startTime + " - " + plan.endTime}}
					</view>
				</view>
				<view class="action">
					<switch class="wx-switch-input-checked" @change="updStatus(plan.id)" :checked="plan.status == 1"></switch>
				</view>
			</view>
		</view>
		<view class="cu-bar bg-white solid-bottom margin-top" v-if="cPlans.length!=0">
			<view class="action">
				<text class="cuIcon-title text-orange "></text>已经完成{{cPlans.length}}条计划啦
			</view>
		</view>
		<view class="cu-list menu sm-border">
			<view class="cu-item" v-for="plan in cPlans" :key="plan.id" @longpress="operate(plan.id)">
				<view class="content padding-tb-sm">
					<view>
						<text class="cuIcon-noticefill text-blue margin-right-xs"></text>{{plan.content}}</view>
					<view class="text-gray text-sm">
						{{plan.startTime + " - " + plan.endTime}}
					</view>
				</view>
				<view class="action">
					<switch class="wx-switch-input-checked" @change="updStatus(plan.id)" :checked="plan.status == 1"></switch>
				</view>
			</view>
		</view>
		<view class="cu-bar bg-white solid-bottom" v-if="tPlans.length!=0">
			<view class="action">
				<text class="cuIcon-title text-orange "></text> 真遗憾，有{{tPlans.length}}条计划未完成
			</view>
		</view>
		<view class="cu-list menu sm-border">
			<view class="cu-item" v-for="plan in tPlans" :key="plan.id" @longpress="operate(plan.id)">
				<view class="content padding-tb-sm">
					<view>
						<text class="cuIcon-infofill text-gray margin-right-xs"></text>{{plan.content}}</view>
					<view class="text-gray text-sm">
						{{plan.startTime + " - " + plan.endTime}}
					</view>
				</view>
				<view class="action">
					<switch class="wx-switch-input-checked" @change="updStatus(plan.id)" :checked="plan.status == 1"></switch>
				</view>
			</view>
		</view>

		<view class="padding flex flex-direction">
			<button class="cu-btn bg-gradual-blue lg" @click="toPath">添加计划</button>
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
				showTip: true,
				plans: [],
				uPlans: [], // uncomplete
				cPlans: [], // complete 
				tPlans: [] // timeout
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			operate(id) {
				uni.showModal({
					content: "确认要删除这条计划吗？",
					success: res => {
						if (res.confirm) {
							uni.request({
								url: url.delPlan + id,
								method: "DELETE",
								success: resp => {
									uni.showToast({
										title: '删除成功！'
									});
									this.getAllPlan(response => {
										this.sort(response.data);
									});
								}
							});
						}
					}
				});
			},
			updStatus(id) {
				let plan = this.plans.find((val, index, arr) => val.id == id);
				let status = 0;
				if (plan.status == 1) {
					status = 0;
				} else {
					status = 1;
				}
				uni.request({
					url: url.updPlanStatus,
					method: "GET",
					data: {
						id: id,
						status: status
					},
					success: resp => {
						this.getAllPlan(response => {
							this.sort(response.data);
						});
					}
				});
			},
			sort(data) {
				console.log(data)
				this.plans = data;
				this.uPlans.length = 0;
				this.cPlans.length = 0;
				this.tPlans.length = 0;
				data.forEach(p => {
					if (p.status == 0) {
						this.uPlans.push(p)
					} else if (p.status == 1) {
						this.cPlans.push(p);
					} else {
						this.tPlans.push(p);
					}
				});
			},
			toPath(e) {
				uni.navigateTo({
					url: 'add-plan/add-plan',
					animationType: "slide-in-right",
					animationDuration: 100
				})
			},
			getAllPlan(callback) {
				uni.request({
					url: url.getAllPlan,
					method: "GET",
					data: {
						uid: this.userInfo.id
					},
					success: callback
				});
			}
		},
		onShow() {
			this.getAllPlan(resp => {
				this.sort(resp.data);
				if (resp.data.length != 0) {
					this.showTip = false;
				}
			});
		},
		onPullDownRefresh() {
			this.getAllPlan(resp => {
				this.sort(resp.data);
				uni.stopPullDownRefresh();
			});
		}
	}
</script>

<style>

</style>
