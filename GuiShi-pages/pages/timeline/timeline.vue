<template>
	<view class="cu-list menu">
		<uni-fab :pattern="pattern" :content="content" horizontal="right" vertical="bottom" direction="vertical" @trigger="trigger"></uni-fab>
		<view class="cu-card" v-show="showTip">
			<view class="cu-item margin-top padding bg-red light">
				<view class="action">
					<text class="cuIcon-notice text-red margin-right-xs"></text>
					<text class="text-red"> 你还没有倒计时哦，快去添加一个吧！</text>
				</view>
			</view>
		</view>
		<view class="cu-item" v-for="cd in cds" :key="cd.id" @longpress="operate(cd.id)">
			<view class="content">
				<text class="cuIcon-notice text-green"></text>
				<text class="text-grey">{{cd.event}}</text>
			</view>
			<view class="action">
				<text class="text-grey text-sm margin-right-xs">{{cd.endTime}}</text>
				<view class="cu-tag round light" :class="cd.status>30 ? 'line-green' : ( cd.status > 7 ? 'line-yellow' : 'line-red' )">
					<text class="margin-right-xs" :class="cd.status>30 ? 'cuIcon-timefill' : ( cd.status > 7 ? 'cuIcon-infofill' : 'cuIcon-warnfill' )"></text>
					还剩{{cd.status}}天</view>
			</view>
		</view>
		<view class="cu-bar bg-white solid-bottom margin-top" v-if="outCds.length!=0">
			<view class="action">
				<text class="cuIcon-title text-orange "></text> 时间已经过了哦
			</view>
		</view>
		<view class="cu-item" v-for="cd in outCds" :key="cd.id" @longpress="operate(cd.id)">
			<view class="content">
				<text class="cuIcon-notice text-green"></text>
				<text class="text-grey">{{cd.event}}</text>
			</view>
			<view class="action">
				<text class="text-grey text-sm margin-right-xs">{{cd.endTime}}</text>
				<!-- <view class="cu-tag round bg-red light">未完成</view> -->
			</view>
		</view>
	</view>
</template>

<script>
	import uniFab from '@/components/uni-fab/uni-fab.vue'
	import url from "@/request/url.js"
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				showTip: true,
				countDowns: [],
				cds: [],
				outCds: [],
				pattern: {
					color: '#7A7E83',
					backgroundColor: '#fff',
					selectedColor: '#007AFF',
					buttonColor: "#007AFF",
				},
				content: [{
						text: '倒计时',
						active: false,
						iconPath: '/static/image/icon/countdown.png',
						selectedIconPath: '/static/image/icon/countdown-select.png',
					},
					{
						text: '日记',
						active: false,
						iconPath: '/static/image/icon/diary.png',
						selectedIconPath: '/static/image/icon/diary-select.png',
					},
					{
						text: '未来信箱',
						active: false,
						iconPath: '/static/image/icon/mail.png',
						selectedIconPath: '/static/image/icon/mail-select.png',
					}
				]
			}
		},
		components: {
			uniFab
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			operate(id) {
				uni.showModal({
					content: "确认要删除这条倒计时吗？",
					success: res => {
						if (res.confirm) {
							uni.request({
								url: url.delCountDown + id,
								method: "DELETE",
								success: resp => {
									uni.showToast({
										title: '删除成功！'
									});
									this.getAllCountDown(response => {
										this.sort(response.data);
									});
								}
							});
						}
					}
				});
			},
			sort(data) {
				console.log(data)
				this.countDowns = data;
				this.cds.length = 0;
				this.outCds.length = 0;
				this.countDowns.forEach(cd => {
					if (cd.status > 0) {
						this.cds.push(cd);
					} else {
						this.outCds.push(cd);
					}
				});
			},
			getAllCountDown(callback) {
				uni.request({
					url: url.getAllCountDown,
					method: "GET",
					data: {
						uid: this.userInfo.id
					},
					success: callback
				})
			},
			trigger(e) {
				console.log(e);
				this.content[e.index].active = true;
				switch (e.index) {
					case 0:
						uni.navigateTo({
							url: 'add-countdown/add-countdown',
							animationType: "slide-in-right",
							animationDuration: 100
						})
						break;
					case 1:
						uni.navigateTo({
							url: 'add-diary/add-diary',
							animationType: "slide-in-right",
							animationDuration: 100
						})
						break;
					case 2:
						uni.navigateTo({
							url: 'add-mail/add-mail',
							animationType: "slide-in-right",
							animationDuration: 100
						})
						break;
				}
			}
		},
		onShow() {
			this.getAllCountDown(resp => {
				this.sort(resp.data);
				if (resp.data.length != 0) {
					this.showTip = false;
				}
			});
		},
		onPullDownRefresh() {
			this.getAllCountDown(resp => {
				this.sort(resp.data);
				uni.stopPullDownRefresh();
			});
		}
	}
</script>

<style lang="scss">

</style>
