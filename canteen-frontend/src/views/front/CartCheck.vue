<template>
  <!--     页面主体       -->
    <div>
        <!--        搜索部分        -->
        <div style="margin: 10px 0">
            <h2>购物车</h2>
        </div>

        <!--        表格内部操作部分        -->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                  @selection-change="handleSelectionChange"
                  @select="handleSum"
                  @select-all="handleSum"
                  ref="multipleTable">
            <el-table-column type="selection" label="全选" width="80"></el-table-column>
            <el-table-column prop="cid" label="ID" width="80"></el-table-column>
            <el-table-column label="图片预览" width="80">
                <template slot-scope="scope">
                    <el-popover placement="top-start" title="" trigger="hover">
                        <img :src="scope.row.image" alt="" style="width: 150px;height: 150px">
                        <img slot="reference" :src="scope.row.image" style="width: 30px;height: 30px">
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="餐品名称"></el-table-column>
            <el-table-column prop="num" label="点餐数量">
                <template slot-scope="scope">
                    <el-button style="padding-left: 10px" @click="Drop(scope.row.cid,scope.row.num)">-</el-button>
                    <span style="padding-left: 20px;padding-right: 20px">{{ scope.row.num }}</span>
                    <el-button style="padding-right: 10px" @click="Add(scope.row.cid)">+</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="价格">
                <template slot-scope="scope">
                    <span>￥{{ scope.row.price }}</span>
                </template>
            </el-table-column>
            <el-table-column label="金额" width="180">
                <template slot-scope="scope">
                    <span>￥{{ scope.row.num * scope.row.price }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" @click="DeleteFromCart(scope.row.cid)">取消此菜品<i
                            class="el-icon-menu"></i>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--       翻页与页码部分         -->
        <div style="padding: 10px 0">

            <div>
                <div style="margin: 20px;">
                    <h3>购物车总价为 :￥{{ sumPrice }}</h3>
                </div>

                <el-button style="float: right" type="danger" @click="SendToOrder">结算<i
                        class="el-icon-menu"></i>
                </el-button>
            </div>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Front-Cart-Check",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            multipleSelection: [],
            menuData: [],
            props: {
                label: 'name',
            },
            expends: [],
            checks: [],
            dishId: 0,
            sumPrice: 0,
        }
    },
    // 请求分页查询数据
    created() {
        this.load()
    },
    methods: {
        handleSum() {
            this.sumPrice = 0;
            this.$nextTick(() => {
                this.$refs.multipleTable.selection.forEach(item => {
                    this.sumPrice += (item.price * item.num)
                });
            })
        },
        // 将数据库查询操作封装
        load() {
            this.request.get("/cart/pageUser", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total
            });
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        reset() {
            this.name = ""
            this.load()
        },
        // 动态分页请求
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        Drop(id, num) {
            if (num <= 1) {
                this.$message.warning("餐品数量不得为0")
                this.load()
            } else {
                this.request.post("/cart/num/drop/" + id).then(res => {
                    if (res) {
                        //this.$message.success("餐品减少成功")
                        this.load()
                    } else {
                        this.$message.error("餐品减少失败")
                        this.load()
                    }
                })
            }
            this.handleSum();
        },
        Add(id) {
            this.request.post("/cart/num/add/" + id).then(res => {
                if (res) {
                    //this.$message.success("餐品增加成功")
                    this.load()
                } else {
                    this.$message.error("餐品增加失败")
                    this.load()
                }
                this.handleSum();
            });
        },
        DeleteFromCart(id) {
            this.request.delete("/cart/" + id).then(res => {
                if (res) {
                    //this.$message.success("购物车取消成功")
                    this.load()
                } else {
                    this.$message.error("购物车取消失败")
                    this.load()
                }
            })
            this.sumPrice = 0;
            this.handleSum();
        },
        SendToOrder(){

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