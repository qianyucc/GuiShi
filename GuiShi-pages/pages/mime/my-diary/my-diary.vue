<template>
	<view class="cu-card dynamic">
		<view class="cu-item shadow" v-for="diary in diaries" :key="diary.id">
			<view class="cu-list menu-avatar" @longpress="del(diary.id)">
				<view class="cu-item">
					<view class="cu-avatar round lg" :style="'background-image:url('+userInfo.avatarUrl+');'"></view>
					<view class="content flex-sub">
						<view>{{userInfo.nickName}}</view>
						<view class="text-gray text-sm flex justify-between">
							{{diary.createTime}}
						</view>
					</view>
				</view>
			</view>
			<view class="text-content margin-top">
				{{diary.content}}
			</view>
			<view class="flex padding">
				<view class="bg-img flex-sub only-img margin-right-xs" v-for="(img,index) in diary.images" :key="index" :style="'background-image:url('+img+');'"
				 @tap="preview(index,diary.images)">
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
				diaries: []
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			del(id) {
				uni.showModal({
					title: '确定要删除这条动态码？',
					success: res => {
						if (res.confirm) {
							uni.request({
								url: url.delDiary + id,
								method: "DELETE",
								success: resp => {
									console.log(resp.data);
									uni.showToast({
										title: '删除成功！',
										icon: "success"
									})
									this.getAllDiary();
								}
							})
						}
					}
				})
			},
			preview(index, imgs) {
				uni.previewImage({
					urls: imgs,
					current: index
				});
			},
			getAllDiary() {
				uni.request({
					url: url.getAllDiary,
					data: {
						uid: this.userInfo.id
					},
					success: resp => {
						console.log(resp.data);
						this.diaries = resp.data
					}
				})
			}
		},
		onShow() {
			this.getAllDiary();
		}
	}
</script>

<style>

</style>
