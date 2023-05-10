<template>
    <div>
        <!--     页面主体       -->
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入订单编号" suffix-icon="el-icon-search"
                      v-model="orderNo"></el-input>
            <el-input style="width: 200px" placeholder="请输入用户姓名" suffix-icon="el-icon-search"
                      v-model="uname"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <div>
            <div style="margin: 10px 0">
                <el-popconfirm
                        class="ml-5"
                        confirm-button-text='确定'
                        cancel-button-text='我再想想'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定批量删除这些数据吗？"
                        @confirm="delBatch"
                >
                    <el-button type="danger" slot="reference">批量删除订单记录 <i class="el-icon-remove-outline"></i>
                    </el-button>
                </el-popconfirm>
            </div>
            <!--        表格内部操作部分        -->
            <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                      @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="orderNo" label="订单编号" width="160"></el-table-column>
                <el-table-column prop="uname" label="下单用户"></el-table-column>
                <el-table-column prop="totalPrice" label="订单总价格"></el-table-column>
                <el-table-column prop="orderStatus" label="订单状态">
                    <template slot-scope="scope">
                        <el-tag type="danger" v-if="scope.row.orderStatus===0">用户关闭订单</el-tag>
                        <el-tag type="warning" v-if="scope.row.orderStatus===1">未付款</el-tag>
                        <el-tag type="success" v-if="scope.row.orderStatus===2">已付款</el-tag>
                        <el-tag type="primary" v-if="scope.row.orderStatus===3">商家已接单</el-tag>
                        <el-tag type="info" v-if="scope.row.orderStatus===4">交易完成</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="订单创建时间"></el-table-column>
                <el-table-column prop="updateTime" label="订单更新时间"></el-table-column>
                <el-table-column label="操作" width="500" align="center">
                    <template slot-scope="scope">
                        <el-button type="success" @click="DealOrder(scope.row)">处理订单 <i class="el-icon-edit"></i>
                        </el-button>
                        <el-button type="info" @click="OrderDetails(scope.row)">订单明细 <i class="el-icon-edit"></i>
                        </el-button>
                        <el-popconfirm
                                class="ml-5"
                                confirm-button-text='确定'
                                cancel-button-text='我再想想'
                                icon="el-icon-info"
                                icon-color="red"
                                title="您确定取消订单吗？"
                                @confirm="Cancel(scope.row)"
                        >
                            <el-button type="warning" slot="reference">取消订单<i class="el-icon-remove-outline"></i>
                            </el-button>
                        </el-popconfirm>
                        <el-popconfirm
                                class="ml-5"
                                confirm-button-text='确定'
                                cancel-button-text='我再想想'
                                icon="el-icon-info"
                                icon-color="red"
                                title="您确定删除订单吗？"
                                @confirm="Del(scope.row.id)"
                        >
                            <el-button type="danger" slot="reference">删除订单<i class="el-icon-remove-outline"></i>
                            </el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>

            <!--       翻页与页码部分         -->
            <div style="padding: 10px 0">
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

            <el-dialog title="账单明细" :visible.sync="dialogFormVisible" width="80%">
                <el-table :data="DetailsData" border stripe :header-cell-class-name="'headerBg'"
                >
                    <el-table-column prop="orderNo" label="订单编号"/>
                    <el-table-column label="图片预览" width="80">
                        <template slot-scope="scope">
                            <el-popover placement="top-start" title="" trigger="hover">
                                <img :src="scope.row.dishImg" alt="" style="width: 150px;height: 150px">
                                <img slot="reference" :src="scope.row.dishImg" style="width: 30px;height: 30px">
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column prop="dishname" label="餐品名称"/>
                    <el-table-column prop="num" label="点餐数量" style="width: 200px"/>
                    <el-table-column prop="price" label="单价">
                        <template slot-scope="scope">
                            <span>￥{{ scope.row.dishPrice }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="金额" width="180">
                        <template slot-scope="scope">
                            <span>￥{{ scope.row.num * scope.row.dishPrice }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-popconfirm
                                class="ml-5"
                                confirm-button-text='确定'
                                cancel-button-text='我再想想'
                                icon="el-icon-info"
                                icon-color="red"
                                title="您确定删除订单吗？"
                                @confirm="DelItem(scope.row.id)"
                            >
                            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i>
                            </el-button>
                            </el-popconfirm>
                        </template>
                    </el-table-column>
                </el-table>
                <div slot="footer" class="dialog-footer">
                    <el-table :data="Data" border stripe :header-cell-class-name="'headerBg'">
                        <el-table-column>
                            <template slot-scope="scope">
                                <span>当前订单总价格：￥{{ TotalPrice }}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Order",
    data() {
        return {
            tableData: [],
            DetailsData: [],
            Data: [1],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            uname: "",
            orderNo: "",
            form: {},
            dialogFormVisible: false,
            multipleSelection: [],
            TotalPrice: 0,
            OrderNo: "",
            expends: [],
            checks: [],
            showButton: '',
        }
    },
    // 请求分页查询数据
    created() {
        this.load()
    },
    methods: {
        // 将数据库查询操作封装
        load() {
            this.request.get("/order/pageBack", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    orderNo: this.orderNo,
                    uname: this.uname
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total
            });
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
        },
        handleEdit(row) {
            this.form = row
            this.dialogFormVisible = true
        },
        Del(no) {
            this.request.delete("/order/" + no).then(res => {
                if (res) {
                    this.$message.success("删除订单成功")
                    this.load()
                } else {
                    //this.$message.error("删除订单失败")
                    this.$message.success("删除订单成功")
                    this.load()
                }
            })
        },
        DelItem(id) {
            this.request.delete("/order_item/" +id).then(res => {
                if (res) {
                    this.$message.success("删除餐品成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    //this.$message.error("删除订单失败")
                    this.$message.success("删除餐品成功")
                    this.dialogFormVisible = false
                    this.load()
                }
            })
        },
        Cancel(row) {
            let id = row.id;
            let status = row.orderStatus;
            if (status == 0) {
                this.$message.warning("已取消订单，请勿重复取消")
            } else if (status == 4 || status == 3 || status == 2) {
                this.$message.warning("商家已经接受交易，无法操作，请联系商家")
            } else {
                this.request.post("/order/" + id).then(res => {
                    if (res) {
                        this.$message.success("取消订单成功")
                        this.load()
                    } else {
                        this.$message.error("取消订单失败")
                        this.load()
                    }
                })
            }
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/order/del/batch", ids).then(res => {
                if (res) {
                    this.$message.success("批量删除订单记录成功")
                    this.load()
                } else {
                    this.$message.error("批量删除订单记录失败")
                }
            })
        },
        reset() {
            this.uname = ""
            this.orderNo = ""
            this.load()
        },
        OrderDetails(row) {
            this.TotalPrice = row.totalPrice;
            console.log(this.TotalPrice)
            let No = row.orderNo;
            this.dialogFormVisible = true
            this.request.get("/order_item/pageDetails/" + No, {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
            }).then(res => {
                console.log(res)
                this.DetailsData = res.records
                this.OrderNo = res.records[0].orderNo
                console.log(this.OrderNo)
            });
        },
        DealOrder(row) {
            let status = row.orderStatus;
            let No = row.orderNo;
            if (status == 0) {
                this.$message.warning("用户已经关闭订单，后台无法处理订单")
            } else if (status == 1) {
                this.$message.warning("用户尚未支付订单，后台无法处理订单")
            } else if (status == 2) {
                //TODO 需要接单订单的部分
                this.request.post("/order/confirmOrder/" + No + "/" + 3).then(res => {
                        if (res) {
                            this.$message.success("商家成功接单")
                            this.load()
                        } else {
                            this.$message.error("商家接单失败")
                        }
                    }
                )
            } else if (status == 3) {
                //TODO 需要结束订单的部分
                this.request.post("/order/confirmOrder/" + No + "/" + 4).then(res => {
                        if (res) {
                            this.$message.success("商家已经完成订单")
                            this.load()
                        } else {
                            this.$message.error("商家订单处理失败")
                        }
                    }
                )
            } else if (status == 4) {
                this.$message.warning("交易已经完成，交易关闭")
            } else {
                this.$message.error("订单状态异常，请联系管理员")
            }
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
    }
}
</script>

<!--表格头部样式-->
<style>
.headerBg {
    background: #eee !important;
}
</style>
