<template>
    <div>
        <!--    头部-->
        <div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #eee">
            <div style="width: 300px; display: flex; padding-left: 30px">
                <div style="width: 60px">
                    <img src="../../assets/logo.png" alt=""
                         style="width: 50px; position: relative; top: 5px; right: 0">
                </div>
                <div style="flex: 1">欢迎来到餐饮系统</div>
            </div>
            <div style="flex: 1">
                <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router>
                    <el-menu-item index="/front">首页</el-menu-item>
                    <el-menu-item index="/front/custom-menu">点餐</el-menu-item>
                    <el-menu-item index="/front/custom-cart">购物车</el-menu-item>
                    <el-menu-item index="/front/custom-order">订单详情</el-menu-item>
                </el-menu>
            </div>
            <div style="width: 200px">
                <div v-if="!user.username" style="text-align: right; padding-right: 30px">
                    <el-button @click="$router.push('/login')">登录</el-button>
                    <el-button @click="$router.push('/register')">注册</el-button>
                </div>
                <div v-else>
                    <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
                        <div style="display: inline-block">
                            <img :src="user.avatar" alt=""
                                 style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
                            <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                        </div>
                        <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <router-link to="/password" style="text-decoration: none">修改密码</router-link>
                            </el-dropdown-item>
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <router-link to="/person" style="text-decoration: none">个人信息</router-link>
                            </el-dropdown-item>
                            <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                                <span style="text-decoration: none" @click="logout">退出</span>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </div>

        <div style="width: 1000px; margin: 0 auto">
            <router-view/>
        </div>
    </div>
</template>

<script>
import router from "@/router";

export default {
    name: "Front-Home",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {

    },
    methods: {
        router() {
            return router
        },
        logout() {
            this.$store.commit("logout")
            this.$message.success("退出成功")
        }
    }
}
</script>

<style>
.item {
    display: inline-block;
    width: 100px;

    text-align: center;
    cursor: pointer
}

.item a {
    color: #1E90FF;
}

.item:hover {
    background-color: LightPink;
}
</style>
