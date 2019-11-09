<template>
	<view class="cu-card">
		<view class="cu-form-group">
			<textarea maxlength="-1" v-model="content" placeholder="记录一下今天的心情吧..."></textarea>
		</view>
		<view class="cu-bar bg-white">
			<view class="action">
				上传图片
			</view>
			<view class="action">
				{{imgList.length}}/4
			</view>
		</view>
		<view class="cu-form-group padding-tb-sm">
			<view class="grid col-4 grid-square flex-sub">
				<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
					<image :src="imgList[index]" mode="aspectFill"></image>
					<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
						<text class='cuIcon-close'></text>
					</view>
				</view>
				<view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
					<text class='cuIcon-cameraadd'></text>
				</view>
			</view>
		</view>
		<view class="padding flex flex-direction">
			<button class="cu-btn bg-gradual-blue lg" @click="upload">提交</button>
		</view>
	</view>
</template>

<script>
	import url from "@/request/url.js"
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				imgList: [],
				content: ''
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.userInfo
			})
		},
		methods: {
			upload() {
				if (this.content == '') {
					uni.showToast({
						icon: "none",
						title: "别忘了输入内容哦！"
					});
					return;
				}
				if (this.imgList.length != 0) {
					let timestamp = Date.now();
					this.imgList.forEach(img => {
						uni.uploadFile({
							url: url.addDiary,
							filePath: img,
							name: 'file',
							formData: {
								uid: this.userInfo.id,
								content: this.content,
								timestamp: timestamp
							},
							success: resp => {
								console.log(resp.data);
								this.toMyDiary();
							}
						});
					});
				} else {
					uni.request({
						url: url.addDiary,
						method:"GET",
						data: {
							uid: this.userInfo.id,
							content: this.content,
							timestamp: Date.now()
						},
						success: resp => {
							console.log(resp.data);
							this.toMyDiary();
						}
					});
				}
			},
			toMyDiary() {
				uni.redirectTo({
					url: "../../mime/my-diary/my-diary",
					animationDuration: 100,
					animationType: "slide-in-right"
				});
			},
			ChooseImage() {
				uni.chooseImage({
					count: 4, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths)
						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				this.imgList.splice(e.currentTarget.dataset.index, 1)
			},
		},
	}
</script>

<style>

</style>
