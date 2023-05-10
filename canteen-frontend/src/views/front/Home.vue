<template>
    <div>
<!--        滚动栏界面-->
        <div style="margin: 10px 0">
            <el-carousel height="450px" :interval="10000">
                <el-carousel-item v-for="item in imgs" :key="item">
                    <img :src="item" alt="" style="width: 100%">
                </el-carousel-item>
            </el-carousel>
        </div>
    </div>
</template>

<script>
import dish from "@/views/Dish.vue";

export default {
    name: "FrontHome",
    computed: {
        dish() {
            return dish
        }
    },
    data() {
        return {
            imgs: [
                'https://media-cdn.tripadvisor.com/media/photo-s/12/ba/7d/4c/confit-cod-chorizo-red.jpg',
                'https://media-cdn.tripadvisor.com/media/photo-s/16/f9/d1/05/brasserie-blanc-spring.jpg',
                'https://media-cdn.tripadvisor.com/media/photo-f/12/ba/79/b7/hen-egg-watercress-jabugo.jpg'
            ],
            dish: []
        }
    },
    ///echarts/file/front/all
    created() {
        this.load()
    },
    methods: {
        load() {
/*            this.request.get("/dish/front/all").then(res => {
                console.log(res.data)
                this.dish = res.data.filter(v => v.type === 'png' || v.type === 'jpg' || v.type === 'webp')
            })*/
        }
    }
}
</script>

<style>

</style>
