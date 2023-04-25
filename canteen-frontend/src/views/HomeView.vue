<!--主页面布局样式代码-->
<template>
    <el-container style="min-height: 100vh">

        <!--  侧边栏菜单  -->
        <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41);">
            <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
                     background-color="rgb(48, 65, 86)"
                     text-color="#fff"
                     active-text-color="#ffd04b"
                     :collapse-transition="false"
                     :collapse="isCollapse">
                <div style="height: 60px; line-height: 60px; text-align: center">
                    <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
                    <b style="color: white" v-show="logoTextShow">后台管理系统</b>
                </div>
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-message"></i>
                        <span slot="title">导航一</span>
                    </template>
                    <el-menu-item-group title="分组2">
                        <el-menu-item index="1-3">选项3</el-menu-item>
                    </el-menu-item-group>
                    <el-submenu index="1-4">
                        <template slot="title">选项4</template>
                        <el-menu-item index="1-4-1">选项4-1</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span slot="title">导航二</span>
                    </template>
                    <el-submenu index="2-4">
                        <template slot="title">选项4</template>
                        <el-menu-item index="2-4-1">选项4-1</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-submenu index="3">
                    <template slot="title">
                        <i class="el-icon-setting"></i>
                        <span slot="title">导航三</span>
                    </template>
                    <el-submenu index="3-4">
                        <template slot="title">选项4</template>
                        <el-menu-item index="3-4-1">选项4-1</el-menu-item>
                    </el-submenu>
                </el-submenu>
            </el-menu>
        </el-aside>

        <el-container>

            <!--    页面头部        -->
            <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
                <div style="flex: 1; font-size: 20px">
                    <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
                </div>
                <el-dropdown style="width: 70px; cursor: pointer">
                    <span>IronmanJay</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
                        <el-dropdown-item style="font-size: 14px; padding: 5px 0">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>

            <!--     页面主体       -->
            <el-main>
                <!--       页签         -->
                <div style="margin-bottom: 30px">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>

                <!--        搜索部分        -->
                <div style="margin: 10px 0">
                    <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
                    <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message"
                              class="ml-5"></el-input>
                    <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position"
                              class="ml-5"></el-input>
                    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
                </div>

                <!--      表格外部操作部分          -->
                <div style="margin: 10px 0">
                    <el-button type="primary">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                    <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
                    <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
                    <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>
                </div>

                <!--        表格内部操作部分        -->
                <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
                    <el-table-column prop="id" label="ID" width="80"></el-table-column>
                    <el-table-column prop="username" label="用户名" width="140"></el-table-column>
                    <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
                    <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
                    <el-table-column prop="phone" label="电话" width="140"></el-table-column>
                    <el-table-column prop="address" label="地址"></el-table-column>
                    <el-table-column label="操作" width="200" align="center">
                        <template slot-scope="scope">
                            <el-button type="success">编辑 <i class="el-icon-edit"></i></el-button>
                            <el-button type="danger">删除 <i class="el-icon-remove-outline"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!--       翻页与页码部分         -->
                <div style="padding: 10px 0">
                    <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                            :current-page="pageNum"
                            :page-sizes="[2, 5, 10, 20]"
                            :page-size="pageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="total">
                    </el-pagination>
                </div>

            </el-main>

        </el-container>

    </el-container>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: 'Home',
    created() {
        this.load()
    },
    data() {
        //初始化数据
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 2,
            username: "",
            msg: "IronmanJay",
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false, //默认侧边栏打开
            sideWidth: 200,
            logoTextShow: true, //在默认侧边栏打开的情况下，侧边栏图标显示
            headerBg: 'headerBg'
        }
    },
    methods: {
        collapse() {  //点击收缩侧边栏按钮触发
            this.isCollapse = !this.isCollapse
            if (this.isCollapse) {  //收缩侧边栏
                this.sideWidth = 64
                this.collapseBtnClass = 'el-icon-s-unfold'
                this.logoTextShow = false
            } else {   //展开侧边栏
                this.sideWidth = 200
                this.collapseBtnClass = 'el-icon-s-fold'
                this.logoTextShow = true
            }
        },
        load() {
            fetch("http://localhost:9090/user/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize+"&username="+this.username).then(res => res.json()).then(res => {
                console.log(res)
                this.tableData = res.data
                this.total = res.total
            })
        },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize=pageSize
            this.load()
        },
        handleCurrentChange(pageNum){
            console.log(pageNum)
            this.pageNum=pageNum
            this.load()
        }
    }
}
</script>

<!--表格头部样式-->
<style>
.headerBg {
    background: #eee !important;
}
</style>
